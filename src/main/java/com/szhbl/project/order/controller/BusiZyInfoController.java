package com.szhbl.project.order.controller;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.common.utils.poi.ExportWordUtils;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.client.VO.ProblemFileVo;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocumentsType;
import com.szhbl.project.documentcenter.mapper.OrderDocumentMapper;
import com.szhbl.project.order.domain.BusiOrderColumn;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.track.GoodsTrackMq;
import com.szhbl.project.order.domain.track.ZyInfoClients;
import com.szhbl.project.order.mapper.BusiZyInfoMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中亚信息Controller
 *
 * @author dps
 * @date 2020-08-17
 */
@RestController
@RequestMapping("/order/zyInfo")
public class BusiZyInfoController extends BaseController {
    @Autowired
    private IBusiZyInfoService busiZyInfoService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiZyInfoMapper busiZyInfoMapper;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private OrderDocumentMapper orderDocumentMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 客户端中亚运单列表-托书列表
     */
    @GetMapping("/orderList")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引", name = "pageNum", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(value = "每页显示记录数", name = "pageSize", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(value = "客户id", name = "clientId", paramType = "query", dataType = "String", required = true)
    })
    public TableDataInfo zyOrderList(ZyInfoClients busiZyInfo)
    {
        startPage();
        if(StringUtils.isNotEmpty(busiZyInfo.getOrderNumber())){
            busiZyInfo.setOrderNumber((busiZyInfo.getOrderNumber()).trim());
        }
        if(StringUtils.isNotEmpty(busiZyInfo.getOrderUnloadsite())){
            busiZyInfo.setOrderUnloadsite((busiZyInfo.getOrderUnloadsite()).trim());
        }
        List<ZyInfoClients> list = busiZyInfoService.selectZyOrderList(busiZyInfo);
        for(ZyInfoClients trackItem:list){
            //箱号、已编辑箱号个数
            String orderId = trackItem.getOrderId();
            List<ZyInfoClients> boxList = busiZyInfoService.selectZyBoxList(orderId);
            if(boxList.size()>0){
                int i = 0;
                String boxStr = "";
                for(ZyInfoClients boxItem:boxList){
                    if(StringUtils.isNotEmpty(boxItem.getXianghao())){
                        boxStr += boxItem.getXianghao();
                        boxStr += "/";
                    }
                    if("1".equals(boxItem.getIsApplynum()) || "2".equals(boxItem.getIsApplynum())){
                        i++;
                    }
                }
                if(StringUtils.isNotEmpty(boxStr)){
                    boxStr = StringUtils.substring(boxStr,0,-1); //截掉末尾斜杠
                }
                trackItem.setBoxNumbers(boxStr);
                trackItem.setBoxEditNumber(i);
            }
            //箱型
            String containerType = trackItem.getContainerType();
            if(StringUtils.isNotEmpty(containerType)){
                containerType = commonService.containerTypeName(containerType);
                trackItem.setContainerType(containerType);
            }
        }
        return getDataTable(list);
    }

    /**
     * 客户端中亚运单列表-箱子列表
     */
    @GetMapping("/boxList")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "托书id", name = "orderId", paramType = "query", dataType = "String", required = true)
    })
    public TableDataInfo zyBoxList(String orderId) {
        startPage();
        List<ZyInfoClients> list = busiZyInfoService.selectZyBoxList(orderId);
        for (ZyInfoClients trackItem : list) {
            /*获取上传的船级证书*/
            DocOrderDocument document = new DocOrderDocument();
            document.setOrderId(orderId);
            document.setFileTypeKey(DocumentsType.CERTIFICATE_OF_CLASSIFICATION);
            document.setContainerNo(trackItem.getXianghao());
            List<DocOrderDocument> documents = orderDocumentMapper.selectOrderDocumentListForDesc(document);
            if (documents.size() > 0) {
                trackItem.setCcUrl(documents.get(0).getFileUrl());
                trackItem.setCcFileName(documents.get(0).getFileName());
            }
            document.setFileTypeKey(DocumentsType.BOXING_PHOTO_FILE);
            document.setFormSystem("1");
            List<DocOrderDocument> boxingPhotos = orderDocumentMapper.selectOrderDocumentListForDesc(document);
            if (boxingPhotos.size() > 0) {
                DocOrderDocument boxing = boxingPhotos.get(0);
                if (StringUtils.isNotEmpty(boxing.getFileUrl()) || StringUtils.isNotEmpty(boxing.getFileName())) {
                    List<ProblemFileVo> urls = new ArrayList<>();
                    String[] fileUrlArr = boxing.getFileUrl().split(";");
                    String[] fileNameArr = boxing.getFileName().split(";");
                    for (int j = 0; j < fileNameArr.length; j++) {
                        ProblemFileVo url = new ProblemFileVo();
                        url.setName(fileNameArr[j]);
                        url.setUrl(fileUrlArr[j]);
                        urls.add(url);
                    }
                    trackItem.setBoxPhotos(urls);
                }
                trackItem.setDocId(boxing.getId());
                trackItem.setSealnumber(boxing.getSealnumber());
                trackItem.setBoxingphotoFail(boxing.getBoxingphotoFail());
                trackItem.setBoxingphotoStatus(boxing.getBoxingphotoStatus());
                trackItem.setConSealFail(boxing.getContainerFail());
                trackItem.setConSealStatus(boxing.getContainerStatus());
            }
            //箱号校验
            Boolean result = commonService.checkDigit(trackItem.getXianghao());
            if (result) {
                trackItem.setBoxNumCheck("箱号正确");
            } else {
                trackItem.setBoxNumCheck("箱号错误");
            }
            //可编辑状态,操作状态
            String isApplynum = trackItem.getIsApplynum(); //运单状态 0未上传1已上传2已审核
            if (StringUtils.isNotEmpty(isApplynum)) {
                if ("0".equals(isApplynum)) {
                    trackItem.setIsOperate("未上传");
                    trackItem.setIsEdit("0");  //可编辑
                }
                if ("1".equals(isApplynum)) {
                    trackItem.setIsOperate("已上传");
                    trackItem.setIsEdit("0");  //可编辑
                }
                if ("2".equals(isApplynum)) {
                    trackItem.setIsOperate("已审核");
                    trackItem.setIsEdit("1");  //不可编辑
                }
            } else {
                trackItem.setIsOperate("未上传");
                trackItem.setIsEdit("0");  //可编辑
            }
        }

        // 获取托书信息（去回程。整拼箱。箱量。是否自送货。）
        BusiOrderColumn busiOrderColumn = busiShippingorderService.selectBusiOrderColumnByOrderId(orderId);
        if ("0".equals(busiOrderColumn.getIsConsolidation())
                && "1".equals(busiOrderColumn.getShipOrderBinningWay())
                && "1".equals(busiOrderColumn.getExamline())) {
            if (list.size() < busiOrderColumn.getContainerCount()) {
                int count = busiOrderColumn.getContainerCount() - list.size();
                for (int i = 0; i < count; i++) {
                    ZyInfoClients zyInfo = new ZyInfoClients();
                    zyInfo.setOrderNumber(busiOrderColumn.getOrderNumber());
                    zyInfo.setOrderId(orderId);
                    zyInfo.setClassEastandwest(busiOrderColumn.getClassEastandwest());
                    zyInfo.setIsConsolidation(busiOrderColumn.getIsConsolidation());
                    zyInfo.setShipOrderBinningWay(busiOrderColumn.getShipOrderBinningWay());
                    list.add(zyInfo);
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 客户端中亚运单-上传运单信息
     */
    @Log(title = "中亚信息", businessType = BusinessType.UPDATE)
    @PutMapping("/zyUpload")
    public AjaxResult zyUpload(@RequestBody ZyInfoClients busiZyInfo)
    {
        return toAjax(busiZyInfoService.zyUpload(busiZyInfo));
    }

    /**
     * 客户端中亚运单-导出运单信息
     */
    @Log(title = "客户端中亚运单-导出运单信息", businessType = BusinessType.EXPORT)
    @GetMapping("/exportZyInfo")
    @ApiOperation("客户端中亚运单-导出运单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "记录id",name = "costId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult exportZyInfo(HttpServletRequest request, HttpServletResponse response, String costId){
        AjaxResult result = null;
        if(StringUtils.isNotEmpty(costId)){
            GoodsTrackMq zyInfo = busiZyInfoMapper.selectBusiZyInfoByZyId(costId);
            if(StringUtils.isNotNull(zyInfo)){
                String orderId = zyInfo.getOrderId();
                BusiShippingorders orderInfo = busiShippingorderService.selectBusiShippingorderByIdOld(orderId); //订单表
                if(StringUtils.isNotNull(orderInfo)){
                    Map<String,Object> params = new HashMap<>();
                    String fileName = "中亚运单.docx";
                    if(StringUtils.isNotEmpty(zyInfo.getBoxNum())){
                        fileName = zyInfo.getBoxNum()+".docx";
                    }
                    params.put("f1",StringUtils.isNotEmpty(zyInfo.getBoxNum())?zyInfo.getBoxNum():".");  //箱号
                    String containerType = orderInfo.getContainerType(); //箱型
                    if(StringUtils.isNotEmpty(orderInfo.getContainerType())){
                        containerType = commonService.containerTypeName(orderInfo.getContainerType());
                    }
                    params.put("f2",StringUtils.isNotEmpty(containerType)?containerType:".");
                    params.put("f3",StringUtils.isNotEmpty(zyInfo.getConsignee())?zyInfo.getConsignee():".");  //收货人
                    params.put("f4",StringUtils.isNotEmpty(zyInfo.getBoxBelong())?zyInfo.getBoxBelong():".");  //还箱信息（箱属）
                    String f5 = "";  //最终到站名称（中/俄文）及站编
                    if(StringUtils.isNotEmpty(zyInfo.getArrivalstation())){
                        f5 += zyInfo.getArrivalstation();
                    }
                    if(StringUtils.isNotEmpty(zyInfo.getArrivalstationhs())){
                        f5 += zyInfo.getArrivalstationhs();
                    }
                    params.put("f5",StringUtils.isNotEmpty(f5)?f5:".");
                    params.put("f6",StringUtils.isNotEmpty(zyInfo.getCarrierstationcode())?zyInfo.getCarrierstationcode():".");  //承运人车站代码
                    params.put("f7",StringUtils.isNotEmpty(zyInfo.getPaymentcode())?zyInfo.getPaymentcode():".");  //付费代码
                    params.put("f8",StringUtils.isNotEmpty(orderInfo.getOrderUnloadsite())?orderInfo.getOrderUnloadsite():".");  //下货站
                    String f9 = "";  //收货人（英/俄文）
                    if(StringUtils.isNotEmpty(zyInfo.getConsigneeE())){
                        f9 += zyInfo.getConsigneeE();
                    }
                    if(StringUtils.isNotEmpty(zyInfo.getConsigneeR())){
                        f9 += zyInfo.getConsigneeR();
                    }
                    params.put("f9",StringUtils.isNotEmpty(f9)?f9:".");
                    params.put("f23",StringUtils.isNotEmpty(zyInfo.getConsignorC())?zyInfo.getConsignorC():".");  //发货人
                    String f10 = ""; //发货人（英俄文）
                    if(StringUtils.isNotEmpty(zyInfo.getConsignor())){
                        f10 += zyInfo.getConsignor();
                    }
                    if(StringUtils.isNotEmpty(zyInfo.getConsignorR())){
                        f10 += zyInfo.getConsignorR();
                    }
                    params.put("f10",StringUtils.isNotEmpty(f10)?f10:".");
                    String f11 = ""; //货物中文品名及税号及外文释义
                    if(StringUtils.isNotEmpty(zyInfo.getGoodsnameC())){
                        f11 += zyInfo.getGoodsnameC();
                    }
                    if(StringUtils.isNotEmpty(zyInfo.getGoodsnameE())){
                        f11 += zyInfo.getGoodsnameE();
                    }
                    if(StringUtils.isNotEmpty(zyInfo.getGoodsnamehs())){
                        zyInfo.setGoodsnamehs("HS CODE:"+zyInfo.getGoodsnamehs());
                        f11 += zyInfo.getGoodsnamehs();
                    }
                    if(StringUtils.isNotEmpty(zyInfo.getGoodsnames())){
                        f11 += zyInfo.getGoodsnames();
                    }
                    params.put("f11",StringUtils.isNotEmpty(f11)?f11:".");
                    params.put("f12",StringUtils.isNotEmpty(zyInfo.getCbxwarehouse())?zyInfo.getCbxwarehouse():".");  //到站监管库信息（CBX仓库信息）
                    params.put("f13",StringUtils.isNotEmpty(zyInfo.getConsignorstate())?zyInfo.getConsignorstate():".");  //发货人声明
                    params.put("f14",StringUtils.isNotEmpty(zyInfo.getSupplycontractno())?zyInfo.getSupplycontractno():".");  //供货合同号
                    if(StringUtils.isNotEmpty(orderInfo.getOrderUnloadsite())){//下货站
                        params.put("f15",orderInfo.getOrderUnloadsite());
                        if("霍尔果斯".equals(orderInfo.getOrderUnloadsite())){
                            params.put("f15",".");
                        }
                    }else{
                        params.put("f15",".");
                    }
                    params.put("f16",StringUtils.isNotEmpty(zyInfo.getYundanNumber())?zyInfo.getYundanNumber():".");  //件数17栏
                    params.put("f17",StringUtils.isNotNull(zyInfo.getYundanWeight())?zyInfo.getYundanWeight():".");  //毛重18栏
                    params.put("f18",StringUtils.isNotNull(zyInfo.getYundanXweight())?("+"+zyInfo.getYundanXweight()):".");  //箱重
                    params.put("f19",StringUtils.isNotNull(zyInfo.getYundanTotalweight())?zyInfo.getYundanTotalweight():".");  //总重
                    params.put("f20",StringUtils.isNotEmpty(zyInfo.getPrivateline())?zyInfo.getPrivateline():".");  //运单第五栏 专用线信息
                    params.put("f21",StringUtils.isNotEmpty(zyInfo.getGjkouan())?zyInfo.getGjkouan():".");  //运单第6栏 国境口岸站
                    params.put("f22",StringUtils.isNotEmpty(zyInfo.getFormalities())?zyInfo.getFormalities():".");  //运单第28栏 办理海关及其他行政手续记载
                    InputStream is = this.getClass().getResourceAsStream("/templates/orderexport/zyinfo.docx");
                    result = ExportWordUtils.exportWord(is,"D:/wordexport",fileName,params,request,response);
                }
            }
        }
        return result;
    }

    /**
     * 平台端中亚运单列表
     */
    @GetMapping("/boxListPf")
    @ApiOperation("平台端中亚运单列表")
    public TableDataInfo zyBoxListPf(ZyInfoClients busiZyInfo){
        startPage();
        if(StringUtils.isNotEmpty(busiZyInfo.getOrderNumber())){
            busiZyInfo.setOrderNumber((busiZyInfo.getOrderNumber()).trim());
        }
        if(StringUtils.isNotEmpty(busiZyInfo.getXianghao())){
            busiZyInfo.setXianghao((busiZyInfo.getXianghao()).trim());
        }
        List<ZyInfoClients> list = busiZyInfoService.selectZyBoxListPf(busiZyInfo);
        for(ZyInfoClients trackItem:list){
            //运单状态
            String isApplynum = trackItem.getIsApplynum(); //运单状态 0未上传1已上传2已审核
            if(StringUtils.isNotEmpty(isApplynum)){
                if("0".equals(isApplynum)){
                    trackItem.setIsApplynum("未上传");
                }
                if("1".equals(isApplynum)){
                    trackItem.setIsApplynum("已上传");
                }
                if("2".equals(isApplynum)){
                    trackItem.setIsApplynum("已审核");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 获取中亚信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:query')")
    @GetMapping(value = "/{costId}")
    public AjaxResult getInfo(@PathVariable("costId") String costId)
    {
        return AjaxResult.success(busiZyInfoService.selectBusiZyInfoById(costId));
    }

    /**
     * 大口岸中亚运单推送
     * http://127.0.0.1:8083/order/zyInfo/zyPushDka?testKey=dka
     * http://42.228.11.179:8083/order/zyInfo/zyPushDka?testKey=dka
     */
//    @GetMapping("/zyPushDka")
//    public void zyPushDka(String testKey) throws JsonProcessingException {
//        if("dka".equals(testKey)){
//            List<String> costIdList = busiZyInfoMapper.selectZyFyCostId();
//            if(costIdList.size()>0){
//                for(String zyItem :costIdList){
//                    //发送消息队列
//                    String costId = zyItem;
//                    if(StringUtils.isNotEmpty(costId)){
//                        GoodsTrackMq zyinfo = busiZyInfoMapper.selectBusiZyInfoByZyId(costId);
//                        if(StringUtils.isNotNull(zyinfo)){
//                            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//                            MessageProperties header = new MessageProperties();
//                            header.getHeaders().put("__TypeId__","Object");
//                            ObjectMapper objectMapper = new ObjectMapper();
//                            Message message = new Message(objectMapper.writeValueAsBytes(zyinfo), header);
//                            rabbitTemplate.convertAndSend(PlatformTrackRabbitmqConfig.PLATFORM_GOODSSTATUS_TOPIC_EXCHANGE, "platform.goods.status.zyinfo", message,correlationData);
//                        }
//                    }
//                }
//            }
//        }
//    }

    /**
     * 修改中亚信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:edit')")
    @Log(title = "中亚信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiZyInfo busiZyInfo)
    {
        return toAjax(busiZyInfoService.updateBusiZyInfo(busiZyInfo));
    }

    /**
     * 查询中亚信息列表
     */
    @PreAuthorize("@ss.hasPermi('order:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiZyInfo busiZyInfo)
    {
        startPage();
        List<BusiZyInfo> list = busiZyInfoService.selectBusiZyInfoList(busiZyInfo);
        return getDataTable(list);
    }

    /**
     * 导出中亚信息列表
     */
    @PreAuthorize("@ss.hasPermi('order:info:export')")
    @Log(title = "中亚信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiZyInfo busiZyInfo)
    {
        List<BusiZyInfo> list = busiZyInfoService.selectBusiZyInfoList(busiZyInfo);
        ExcelUtil<BusiZyInfo> util = new ExcelUtil<BusiZyInfo>(BusiZyInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增中亚信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:add')")
    @Log(title = "中亚信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiZyInfo busiZyInfo)
    {
        return toAjax(busiZyInfoService.insertBusiZyInfo(busiZyInfo));
    }

    /**
     * 删除中亚信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:remove')")
    @Log(title = "中亚信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{costIds}")
    public AjaxResult remove(@PathVariable String[] costIds)
    {
        return toAjax(busiZyInfoService.deleteBusiZyInfoByIds(costIds));
    }
}
