package com.szhbl.project.order.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.basic.domain.DangerousGoods;
import com.szhbl.project.basic.mapper.DangerousGoodsMapper;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.Merchandiser;
import com.szhbl.project.order.domain.vo.OrderImportList;
import com.szhbl.project.order.mapper.BusiShipOrderMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShipOrderService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.trains.service.IBusiClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/order/shipOrder")
@Api(tags = "订舱管理2")
public class BusiShipOrderController extends BaseController {
    @Autowired
    private IBusiShipOrderService busiShipOrderService;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private BusiShipOrderMapper busiShipOrderMapper;
    @Autowired
    private IBusiClassesService busiClassesService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private DangerousGoodsMapper dangerousGoodsMapper;

    /**
     * 查询托书箱量
     */
    @GetMapping("/orderZgCount")
    @ApiOperation("查询托书箱量")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "托书id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult orderZgCount(String orderId){
        String zgCount = "";
        zgCount = busiShipOrderService.orderZgCount(orderId);
        return AjaxResult.success(zgCount);
    }

    /**
     * 托书修改整柜箱量
     */
    @PreAuthorize("@ss.hasPermi('shipOrder:zgCountChange:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/zgCountChange")
    @ApiOperation("托书修改整柜箱量")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "箱量",name = "containerBoxamount",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult zgCountChange(@RequestBody BusiShippingorders busiShippingorder){
        int result = busiShipOrderService.zgCountChange(busiShippingorder);
        if(result == 0){
            return AjaxResult.error("数据异常，请重试");
        }
        if(result == 2){
            return AjaxResult.error("新输入箱量与原箱量一致");
        }
        if(result == 3){
            return AjaxResult.error("整柜托书可编辑");
        }
        return toAjax(result);
    }

    /**
     * 客户跟单列表
     */
    @GetMapping("/merchandiserList")
    @ApiOperation("跟单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "0：去程，1：回程",name = "classEastandwest",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "客户id",name = "clientId",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo merchandiserList(String classEastandwest, String clientId){
        List<Merchandiser> merchandiserList = new ArrayList<>();
        if(StringUtils.isNotEmpty(classEastandwest) && StringUtils.isNotEmpty(clientId)){
            String merchandiserIds = busiShipOrderMapper.selectMerchandiser(classEastandwest,clientId);
            if(StringUtils.isNotEmpty(merchandiserIds)){
                String[] merchandiserId = merchandiserIds.trim().split("\\|");
                merchandiserList = busiShipOrderMapper.selectMerchandiserList(merchandiserId);
            }
        }
        return getDataTable(merchandiserList);
    }

    /**
     * 未审核托书再次订舱
     */
    //@PreAuthorize("@ss.hasPermi('shipOrder:orderCreateMore:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/orderCreateMore")
    public AjaxResult orderCreateMore(@RequestBody BusiShippingorders busiShippingorder) throws JsonProcessingException {
        //查询是否包含危险品
        String goodsName = busiShippingorder.getGoodsName();
        String goodsEnName = busiShippingorder.getGoodsEnName();
        String language = busiShippingorder.getLanguage();
        if(StringUtils.isNotEmpty(goodsName)){
            DangerousGoods dangoods = dangerousGoodsMapper.selectDangerousGoodsByNameByLevel(goodsName,goodsEnName);
            if(StringUtils.isNotNull(dangoods)){
                if("en".equals(language)){
                    return AjaxResult.error(dangoods.getSpecialEnRemark());
                }else{
                    return AjaxResult.error(dangoods.getSpecialremark());
                }
            }
        }
        if(StringUtils.isEmpty(busiShippingorder.getOrderMerchandiserId()) && StringUtils.isEmpty(busiShippingorder.getOrderMerchandiser())){
            if("en".equals(language)){
                return AjaxResult.error("Merchandiser cannot be empty");
            }else{
                return AjaxResult.error("跟单员不能为空");
            }
        }
        int result = busiShipOrderService.insertShiporder(busiShippingorder);
        if(result == 2){
            if("en".equals(language)){
                return AjaxResult.error("Insufficient space");
            }else{
                return AjaxResult.error("班列剩余舱位不足，请重新选择班列");
            }
        }
        if(result == 4){
            if("en".equals(language)){
                return AjaxResult.error("The train has left");
            }else{
                return AjaxResult.error("班列已发车，请重新选择班列");
            }
        }
        if(result == 5){
            if("en".equals(language)){
                return AjaxResult.error("Please reselect the train");
            }else{
                return AjaxResult.error("请重新选择班列");
            }
        }
        if(result == 3){
            return AjaxResult.error("服务器异常，请重试");
        }
        return toAjax(result);
    }

    /**
     * 委托书汇总订单列表导出
     */
    //http://192.168.18.72:8083/order/shipOrder/export?deptCode=ZHKJ&orderClassBh=ZIHEB201028   ZIHEB201028
    @PreAuthorize("@ss.hasPermi('order:shippingorder:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HttpServletResponse response,OrderImportList busiShippingorder) throws IOException {
        List<OrderImportList> list = new ArrayList<>();
        String fileName = "";
        if(StringUtils.isEmpty(busiShippingorder.getOrderNumber()) && StringUtils.isEmpty(busiShippingorder.getClassNumber()) && StringUtils.isNull(busiShippingorder.getClassDate()) && StringUtils.isNull(busiShippingorder.getClassDateStart())
                && StringUtils.isNull(busiShippingorder.getClassDateEnd()) && StringUtils.isEmpty(busiShippingorder.getOrderClassBh()) && StringUtils.isEmpty(busiShippingorder.getOrderUploadcode())
                && StringUtils.isEmpty(busiShippingorder.getOrderUnloadcode()) && StringUtils.isEmpty(busiShippingorder.getLineTypeid()) && StringUtils.isEmpty(busiShippingorder.getIsexamline())
                && StringUtils.isEmpty(busiShippingorder.getIsconsolidation()) && StringUtils.isEmpty(busiShippingorder.getClassEastandwest()) && StringUtils.isEmpty(busiShippingorder.getStation()) && StringUtils.isEmpty(busiShippingorder.getOrderMerchandiser())
                && StringUtils.isEmpty(busiShippingorder.getOrderMerchandiserNumber()) && StringUtils.isEmpty(busiShippingorder.getClientTjr()) && StringUtils.isEmpty(busiShippingorder.getClientUnit())
                && StringUtils.isEmpty(busiShippingorder.getShipOrederName()) && StringUtils.isEmpty(busiShippingorder.getReceiveOrderName()) && StringUtils.isEmpty(busiShippingorder.getGoodsName())
                && StringUtils.isEmpty(busiShippingorder.getShipOrderBinningway()) && StringUtils.isEmpty(busiShippingorder.getReceiveOrderIspart()) && StringUtils.isNull(busiShippingorder.getPutoffClass())
                && StringUtils.isEmpty(busiShippingorder.getShipFhSite()) && StringUtils.isEmpty(busiShippingorder.getReceiveHxAddress()) && StringUtils.isEmpty(busiShippingorder.getYuyan())){
            return AjaxResult.error("请输入筛选条件");
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.isNotEmpty(busiShippingorder.getOrderNumber())){
                busiShippingorder.setOrderNumber((busiShippingorder.getOrderNumber()).trim());
            }
            list = busiShippingorderService.selectOrderImportList(busiShippingorder);
            //文件名
            String classNumber = "";  //班列号
            String classnum = "";
            String classDate = "";   //班列日期
            if(list.size()>0){
                OrderImportList selectOne = list.get(0);
                classNumber = selectOne.getClassNumber();
                classnum = selectOne.getClassNumber();
                if(StringUtils.isNotEmpty(classNumber) && classNumber.contains("/")){
                    classNumber = StringUtils.replace(classNumber,"/","-");
                }
                classDate = StringUtils.isNotNull(selectOne.getClassDate())? dateFormat.format(selectOne.getClassDate()):"";
            }
            //fileName = classNumber+"班列客户托书信息汇总表("+classDate+")";
            fileName = classNumber+"班列客户托书信息汇总表"+classDate;
            //整柜数量
            Double zgCount = busiShipOrderMapper.importZgCount(busiShippingorder);
            //拼箱体积重量
            OrderImportList pxVm = busiShipOrderMapper.importPxVm(busiShippingorder);
            String tV = StringUtils.isNotNull(pxVm)?pxVm.getTotalVolumn():"";
            String tW = StringUtils.isNotNull(pxVm) ? pxVm.getTotalWeight() : "";
            TemplateExportParams params = new TemplateExportParams("E:/szhbl/uploadPath/model/orderImportDc.xls");
            //String params = "D:/szhbl/uploadPath/model/orderImportDc.xls";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("classnum", classnum);
            map.put("zC", zgCount);
            map.put("tV", tV);
            map.put("tW", tW);
            List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
            for(OrderImportList orderItem:list){
                Map<String, String> lm = new HashMap<String, String>();
                //推荐人
                String clientTjr = orderItem.getClientTjr();
                if(StringUtils.isNotEmpty(clientTjr)){
                    if(clientTjr.contains("/")){
                        clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                    }
                    if(clientTjr.contains("-")){
                        clientTjr = clientTjr.substring(clientTjr.lastIndexOf("-")+1);
                    }
                }
                lm.put("m1",clientTjr);
                lm.put("m2",orderItem.getClientUnit()); //订舱方
                String orderBelongTo = orderItem.getOrderAuditBelongto(); //箱属
                lm.put("m3",StringUtils.isEmpty(orderBelongTo)?"":"0".equals(orderBelongTo)?"委托ZIH":"自备");
                lm.put("m4",orderItem.getClientAddress()); //订舱方地址
                lm.put("m5",orderItem.getClientContacts()); //联系人
                lm.put("m6",orderItem.getClientTel()); //联系方式
                lm.put("m7",orderItem.getShipOrederName()); //发货方
                lm.put("m8",orderItem.getShipOrederAddress()); //发货方地址
                lm.put("m9",orderItem.getShipOrederContacts()); //联系人
                lm.put("m10",orderItem.getShipOrederPhone()); //联系方式
                String clientBiningWay = orderItem.getClientOrderBindingway(); //报关方式
                lm.put("m11",StringUtils.isEmpty(clientBiningWay)?"":"0".equals(clientBiningWay)?"委托ZIH代办":"不委托ZIH");
                lm.put("m12",orderItem.getClientBgCost()); //报关费用
                //到货方式
                String binningway = orderItem.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                String shipThTypeId = orderItem.getShipThTypeId(); //委托陆港提货 0整箱到车站,1散货到车站
                String shipZsTypeId = orderItem.getShipZsTypeId(); //自送货 0散货到车站，1整箱到车站
                if("0".equals(binningway)){
                    shipThTypeId = StringUtils.isEmpty(shipThTypeId)?"":"0".equals(shipThTypeId)?"整箱到车站":"散货到堆场"; //装箱方式
                    lm.put("m14",shipThTypeId);
                    Date shipOrderUnloadtime = orderItem.getShipOrderUnloadtime(); //提货时间
                    lm.put("m15",StringUtils.isNull(shipOrderUnloadtime)?"":dateFormats.format(shipOrderUnloadtime));
                }
                if("1".equals(binningway)){
                    shipZsTypeId = StringUtils.isEmpty(shipZsTypeId)?"":"0".equals(shipZsTypeId)?"散货到堆场":"整箱到车站"; //装箱方式
                    lm.put("m14",shipZsTypeId);
                    Date shipOrderSendtime = orderItem.getShipOrderSendtime(); //自送货时间
                    lm.put("m15",StringUtils.isNull(shipOrderSendtime)?"":dateFormats.format(shipOrderSendtime));
                }
                binningway = StringUtils.isEmpty(binningway)?"":"0".equals(binningway)?"委托ZIH提货":"自送货";
                lm.put("m13",binningway);
                lm.put("m16",orderItem.getShipOrderUnloadaddress()); //提货地址
                if("0".equals(orderItem.getClassEastandwest())){
                    lm.put("m17",orderItem.getDetailedAddress()); //去程提货详细地址
                }
                lm.put("m18",orderItem.getShipOrderUnloadcontacts()); //联系人
                lm.put("m19",orderItem.getShipOrderUnloadway()); //联系方式
                lm.put("m20",orderItem.getShipThCost()); //提货费用
                lm.put("m21",orderItem.getShipThCostNo()); //提货费用编号
                lm.put("m22",orderItem.getStation()); //车站名称
                lm.put("m23",orderItem.getReceiveOrderName()); //收货方名称
                lm.put("m24",orderItem.getReceiveOrderAddress()); //收货方地址
                lm.put("m25",orderItem.getReceiveOrderContacts()); //收货方联系人
                lm.put("m26",orderItem.getReceiveOrderPhone()); //收货方联系方式
                String receiveOrderIsclearance = orderItem.getReceiveOrderIsclearance(); //是否由ZIH代理清关 0=否,1=是
                lm.put("m27",StringUtils.isEmpty(receiveOrderIsclearance)?"":"0".equals(receiveOrderIsclearance)?"否":"是"); //到站后由zih代理清关
                lm.put("m28",orderItem.getReceiveQgCost()); //清关费用
                String receiveOrderIspart = orderItem.getReceiveOrderIspart(); //到站后由zih分拨
                lm.put("m29",StringUtils.isEmpty(receiveOrderIspart)?"":"0".equals(receiveOrderIspart)?"否":"是");
                //分拨方式
                if("1".equals(receiveOrderIspart)){
                    String distributionType = orderItem.getDistributionType();
                    distributionType = StringUtils.isEmpty(distributionType)?"":"0".equals(distributionType)?"整柜派送":"散货派送";
                    lm.put("m80",distributionType);
                }
                lm.put("m30",orderItem.getReceiveOrderPartaddress()); //分拨地址
                if("1".equals(orderItem.getClassEastandwest())){
                    lm.put("m31",orderItem.getDetailedAddress()); //回程分拨详细地址
                }
                lm.put("m32",orderItem.getReceiveOrderZihcontacts()); //分拨联系人
                lm.put("m33",orderItem.getReceiveOrderZihtel()); //分拨联系方式
                lm.put("m34",orderItem.getReceiveShCost()); //分拨费用
                lm.put("m35",orderItem.getReceiveShCostId()); //送货费用编号
                lm.put("m36",orderItem.getReceiveOrderReceiveemail()); //在途信息接收邮箱
                lm.put("m37",orderItem.getDictName()); //贸易条款
                lm.put("m38",orderItem.getOrderUploadsite()); //上货站
                lm.put("m39",orderItem.getOrderUnloadsite()); //下货站
                //箱型箱量
                if("0".equals(orderItem.getIsconsolidation())){
                    String containerType = orderItem.getContainerType();
                    String boxAmount = orderItem.getContainerBoxamount();
                    if(StringUtils.isNotEmpty(containerType) && StringUtils.isNotEmpty(boxAmount)){
                        Double boxAmountI = Double.valueOf(boxAmount);
                        if(containerType.contains("20")){
                            boxAmountI = boxAmountI/2;
                        }
                        lm.put("m40",String.valueOf(boxAmountI)); //箱量
                    }
                    if(StringUtils.isNotEmpty(containerType)){
                        containerType = commonService.containerTypeName(orderItem.getContainerType());
                        if(StringUtils.isNotEmpty(containerType)){
                            orderItem.setContainerType(containerType);
                        }
                        lm.put("m41",orderItem.getContainerType()); //箱型
                    }
                }
                if("1".equals(orderItem.getIsconsolidation())){
                    lm.put("m40","0"); //箱量
                }
                lm.put("m42",orderItem.getSitecost()); //站到站运费
                lm.put("m43",""); //发货地城市
                //提箱地
                if("0".equals(orderItem.getClassEastandwest())){
                    lm.put("m44",orderItem.getShipFhSite());
                }
                if("1".equals(orderItem.getClassEastandwest())){
                    lm.put("m44",orderItem.getShipHyd());
                }
                lm.put("m45",orderItem.getReceiveHxAddress()); //还箱地
                //箱管费用(提箱费+还箱费)
                String xgCost = "";
                if(StringUtils.isNotEmpty(orderItem.getPickUpBoxFee())){
                    xgCost = orderItem.getPickUpBoxFee();
                }
                if(StringUtils.isNotEmpty(orderItem.getReturnBoxFee())){
                    if(StringUtils.isNotEmpty(xgCost)){
                        xgCost += "+";
                    }
                    xgCost += orderItem.getReturnBoxFee();
                }
                lm.put("m46",xgCost);
                lm.put("m47",orderItem.getGoodsName()); //货物中文品名
                lm.put("m48",orderItem.getGoodsEnName()); //货物英文品名
                lm.put("m49",orderItem.getGoodsReport()); //上货站报关hs
                lm.put("m50",orderItem.getGoodsClearance()); //下货站清关hs
                lm.put("m51",orderItem.getGoodsPacking()); //最外层包装形式
                lm.put("m52",orderItem.getGoodsNumber()); //最外层包装数量
                lm.put("m53",orderItem.getGoodsStandard()); //最外层包装规格
                lm.put("m54",orderItem.getGoodsCbm()); //总体积
                lm.put("m55",orderItem.getGoodsKgs()); //总毛重
                String goodsIsscheme = orderItem.getGoodsIsscheme(); //是否需要装箱方案 0=是，1=否
                lm.put("m56",StringUtils.isEmpty(goodsIsscheme)?"":"0".equals(goodsIsscheme)?"是":"否");
                String shipOrderIsdispatch = orderItem.getShipOrderIsdispatch(); //是否派监装员 0=否，1=是
                lm.put("m57",StringUtils.isEmpty(shipOrderIsdispatch)?"":"0".equals(shipOrderIsdispatch)?"否":"是");
                lm.put("m58",orderItem.getShipJzCost()); //监装费用
                lm.put("m59",orderItem.getOrderNumber()); //委托书编号
                lm.put("m60",orderItem.getClientYwNumber()); //业务编号
                lm.put("m61",orderItem.getOrderMerchandiser()); //跟单员
                Date tjTime = orderItem.getTjTime(); //提交托书时间
                lm.put("m62",StringUtils.isNull(tjTime)?"":dateFormats.format(tjTime));
                lm.put("m63",orderItem.getRemark()); //备注
                String isconsolidation = orderItem.getIsconsolidation(); //整拼箱
                lm.put("m64",StringUtils.isEmpty(isconsolidation)?"":"0".equals(isconsolidation)?"整箱":"拼箱");
                String cannotpileup = orderItem.getGoodsCannotpileup(); //是否可堆叠 0否1是
                lm.put("m65",StringUtils.isEmpty(cannotpileup)?"":"0".equals(cannotpileup)?"否":"是");
                String fragile = orderItem.getGoodsFragile(); //是否易碎 0否1是
                lm.put("m66",StringUtils.isEmpty(fragile)?"":"0".equals(fragile)?"否":"是");
                String general = orderItem.getGoodsGeneral(); //单件超长超重 0否1是
                lm.put("m67",StringUtils.isEmpty(general)?"":"0".equals(general)?"否":"是");
                //审核状态
                String isexamline = orderItem.getIsexamline();
                if(StringUtils.isNotEmpty(isexamline)) {
                    isexamline = "0".equals(isexamline) ? "待审核" : "1".equals(isexamline) ? "审核通过" : "2".equals(isexamline) ? "审核失败" : "3".equals(isexamline) ? "取消委托" : "4".equals(isexamline) ? "转待审核中" : "5".equals(isexamline) ? "草稿" : "6".equals(isexamline) ? "转待审核失败" : "7".equals(isexamline) ? "箱管部审核中" : "8".equals(isexamline) ? "箱管部审核失败" : "9".equals(isexamline) ? "报价中" : "10".equals(isexamline) ? "客户确认中" : "11".equals(isexamline) ? "公路审核中" : "12".equals(isexamline) ? "集疏审核中" : "13".equals(isexamline) ? "撤舱审核中" : "14".equals(isexamline) ? "订舱报价中" : "订舱确认中";
                }
                lm.put("m68",isexamline); //审核状态
                lm.put("m69",orderItem.getReceiveOrderReceiveemail()); //在途信息接收邮箱
                lm.put("m70",orderItem.getReceiveOrderMail()); //到站通知提货邮箱
                lm.put("m71",orderItem.getConsignorc()); //中文发货人
                lm.put("m72",orderItem.getEconsignorstate()); //发货人声明
                lm.put("m73",orderItem.getEtxCompany()); //实际收货人
                lm.put("m74",orderItem.getEtxName()); //承担监管区费用的公司或个人
                lm.put("m75",orderItem.getEduty()); //税号
                lm.put("m76",orderItem.getLineName()); //口岸
                Date classDateItem = orderItem.getClassDate(); //班列日期
                lm.put("m77",StringUtils.isNull(classDateItem)?"":dateFormat.format(classDateItem));
                lm.put("m78",orderItem.getOrderClassBh()); //班列编号
                Long totalturncountavg = orderItem.getTotalturncountavg(); //转待总次数
                lm.put("m79",StringUtils.isNull(totalturncountavg)?"0":String.valueOf(totalturncountavg));
                listMap.add(lm);
            }
            map.put("maplist", listMap);
            Workbook workbook = ExcelExportUtil.exportExcel(params, map);
            try {
                String downloadPath = SzhblConfig.getDownloadPath() + fileName+".xls";
                File desc = new File(downloadPath);
                if (!desc.getParentFile().exists()) {
                    desc.getParentFile().mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(downloadPath);
                workbook.write(fos);
                fos.close();
            }catch (Exception e) {
                log.error("托书汇总表导出失败",e.toString(),e.getStackTrace());
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
            return AjaxResult.success(fileName+".xls");
        }
    }


    /**
     * 十天内托书推送
     * http://127.0.0.1:8083/order/shipOrder/orderPush?dateType=6
     * http://42.228.11.179:8083/order/shipOrder/orderPush?dateType=6
     */
    @GetMapping("/orderPush")
    public void orderPush(String dateType){
        if(StringUtils.isNotEmpty(dateType)){
            List<String> orderIdList = busiShipOrderMapper.orderIdListT(dateType);
            if(orderIdList.size()>0){
                for(String orderItem :orderIdList){
                    //发送消息队列
                    String orderId = orderItem;
//                    if(StringUtils.isNotEmpty(orderId)){
//                        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
//                        if(StringUtils.isNotNull(orderInfoRabbmq)){
//                            String messagetype = "7";//托书修改
//                            try {
//                                commonService.orderInfoMQ(orderInfoRabbmq,messagetype);
//                            } catch (JsonProcessingException e) {
//                                log.error("批量同步托书修改失败",e.toString(),e.getStackTrace());
//                            }
//                        }
//                    }
                }
            }
        }
    }




}


