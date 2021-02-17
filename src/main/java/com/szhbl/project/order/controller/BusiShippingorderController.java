package com.szhbl.project.order.controller;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.utils.AutoOrderNumber;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.ExportExcel;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExportWordUtils;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.basic.domain.BaseGoodsnote;
import com.szhbl.project.basic.domain.DangerousGoods;
import com.szhbl.project.basic.mapper.BaseGoodsnoteMapper;
import com.szhbl.project.basic.mapper.DangerousGoodsMapper;
import com.szhbl.project.basic.service.IBusiCustomersignService;
import com.szhbl.project.cabinarrangement.mapper.BusiShippingorderPcMapper;
import com.szhbl.project.client.mapper.BusiClientsMapper;
import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.documentcenter.service.IBusiStationService;
import com.szhbl.project.inquiry.domain.BookingInquiryGoodsDetails;
import com.szhbl.project.order.domain.BusiZyOrder;
import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.domain.track.ExportTrackDl;
import com.szhbl.project.order.domain.vo.*;
import com.szhbl.project.order.mapper.BusiShipOrderMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.mapper.BusiZyOrderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IShippingorderExaminfoService;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.domain.BusiLinesite;
import com.szhbl.project.trains.domain.BusiSite;
import com.szhbl.project.trains.service.IBusiClassesService;
import com.szhbl.project.trains.service.IBusiLinesiteService;
import com.szhbl.project.trains.service.IBusiSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单Controller
 *
 * @author dps
 * @date 2020-01-21
 */
@Slf4j
@RestController
@RequestMapping("/order/shippingorder")
@Api(tags = "订舱管理")
public class BusiShippingorderController extends BaseController
{
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private BusiShipOrderMapper busiShipOrderMapper;
    @Autowired
    private IBusiClassesService busiClassesService;
    @Autowired
    private IBusiSiteService busiSiteService;
    @Autowired
    private IShippingorderExaminfoService shippingorderExaminfoService;
    @Autowired
    private IBusiCustomersignService busiCustomersignService;
    @Autowired
    private DangerousGoodsMapper dangerousGoodsMapper;
    @Autowired
    private BaseGoodsnoteMapper baseGoodsnoteMapper;
    @Autowired
    private BusiShippingorderPcMapper busiShippingorderPcMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiZyOrderMapper busiZyOrderMapper;
    @Autowired
    private BusiClientsMapper busiClientsMapper;

    /**
     * 查询订单列表
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:list')")
    @GetMapping("/orderList")
    @ApiOperation("订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "登录者部门编号",name = "deptCode",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "登录者ID",name = "userid",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo list(ShippingOrderList busiShippingorder)
    {
        startPage();
        if(StringUtils.isNotEmpty(busiShippingorder.getOrderNumber())){
            busiShippingorder.setOrderNumber((busiShippingorder.getOrderNumber()).trim());
        }
        List<ShippingOrderList> list = busiShippingorderService.selectBusiShippingorderList(busiShippingorder);
        List<String> signList = busiCustomersignService.selectListName();
        if(signList.size()>0){
            for(ShippingOrderList orderItem:list){
                String clientUnit = orderItem.getClientUnit();
                if(signList.contains(clientUnit)){
                    orderItem.setIsClientSign("1");
                }else{
                    orderItem.setIsClientSign("0");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     *
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/boxAmountTotal")
    @ApiOperation("箱量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "登录者部门编号",name = "deptCode",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult boxAmountTotal(ShippingOrderList busiShippingorder){
        Map<String,Object> boxAmount = new HashMap();
        if(StringUtils.isEmpty(busiShippingorder.getOrganizationCode()) && StringUtils.isEmpty(busiShippingorder.getOrderNumber()) && StringUtils.isEmpty(busiShippingorder.getClientTjr())
        && StringUtils.isEmpty(busiShippingorder.getOrderMerchandiser()) && StringUtils.isEmpty(busiShippingorder.getClientUnit()) && StringUtils.isNull(busiShippingorder.getClassDate())
        && StringUtils.isEmpty(busiShippingorder.getClassNumber()) && StringUtils.isEmpty(busiShippingorder.getOrderClassBh()) && StringUtils.isEmpty(busiShippingorder.getIsexamline())
        && StringUtils.isEmpty(busiShippingorder.getIsconsolidation()) && StringUtils.isEmpty(busiShippingorder.getClassEastandwest()) && StringUtils.isNull(busiShippingorder.getLineTypeid())
        && StringUtils.isEmpty(busiShippingorder.getStation()) && StringUtils.isEmpty(busiShippingorder.getYuyan()) && StringUtils.isEmpty(busiShippingorder.getIsphone())
        && StringUtils.isEmpty(busiShippingorder.getShipOrderBinningway()) && StringUtils.isEmpty(busiShippingorder.getDcGaidanState()) && StringUtils.isEmpty(busiShippingorder.getGoodsName())
        && StringUtils.isEmpty(busiShippingorder.getSiteCode()) && StringUtils.isEmpty(busiShippingorder.getYwNumber())){
            boxAmount.put("20t","0");
            boxAmount.put("40t","0");
            boxAmount.put("45t","0");
            boxAmount.put("v","0");
            boxAmount.put("w","0");
        }else{
            String isconsolidation = busiShippingorder.getIsconsolidation();  //0整柜 1拼箱
            StringBuffer t = new StringBuffer();
            StringBuffer f = new StringBuffer();
            StringBuffer ff = new StringBuffer();
            List<Map> maps = busiShippingorderService.selectAmount(busiShippingorder);
            maps.forEach(map -> {
                if (map.get("boxType").equals("20")) {
                    t.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("40")) {
                    f.append(map.get("boxAmountSecond"));
                } else if (map.get("boxType").equals("45")) {
                    ff.append(map.get("boxAmountSecond"));
                }
            });
            ShippingOrderList amountVW = busiShippingorderService.selectAmountVW(busiShippingorder);
            if(StringUtils.isEmpty(isconsolidation)){
                boxAmount.put("20t",t.length() == 0? "0" : t);
                boxAmount.put("40t",f.length() == 0? "0" : f);
                boxAmount.put("45t",ff.length() == 0? "0" : ff);
                if(StringUtils.isNotNull(amountVW)){
                    boxAmount.put("v",amountVW.getTotalVolumn());
                    boxAmount.put("w",amountVW.getTotalWeight());
                }else{
                    boxAmount.put("v","0");
                    boxAmount.put("w","0");
                }
            }
            else if(isconsolidation.equals("0")){
                boxAmount.put("20t",t.length() == 0? "0" : t);
                boxAmount.put("40t",f.length() == 0? "0" : f);
                boxAmount.put("45t",ff.length() == 0? "0" : ff);
                boxAmount.put("v","0");
                boxAmount.put("w","0");
            }
            else if(isconsolidation.equals("1")){
                boxAmount.put("20t","0");
                boxAmount.put("40t","0");
                boxAmount.put("45t","0");
                if(StringUtils.isNotNull(amountVW)){
//                boxAmount.put("v",String.valueOf(amountVW.get("totalVolumn")));
//                boxAmount.put("w",String.valueOf(amountVW.get("totalWeight")));
                    boxAmount.put("v",amountVW.getTotalVolumn());
                    boxAmount.put("w",amountVW.getTotalWeight());
                }else{
                    boxAmount.put("v","0");
                    boxAmount.put("w","0");
                }
            }
        }
        return AjaxResult.success(boxAmount);
    }


    /**
     * 获取订单详细信息
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping(value = "/{orderId}")
    @ApiOperation("订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfo(@PathVariable("orderId") String orderId) throws ParseException {
        ShippingOrder orderInfo = busiShippingorderService.selectBusiShippingorderById(orderId);
        if(StringUtils.isNotNull(orderInfo)){
            String classEastandwest = orderInfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
            //跟单
            if("0".equals(classEastandwest)){  //西向
                orderInfo.setMerchandiserIdW(orderInfo.getOrderMerchandiserId());
                orderInfo.setMerchandiserW(orderInfo.getOrderMerchandiser());
            }
            String clientTjr = "";
            if(StringUtils.isNotEmpty(orderInfo.getClientTjr())){
                clientTjr += orderInfo.getClientTjr();
                if(clientTjr.contains("/")){
                    clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                }
            }
            //推荐人邮箱
            String tjrId = orderInfo.getClientTjrId();
            if(StringUtils.isNotEmpty(tjrId)){
                String tjrEmail = busiShippingorderService.selectOrderTjrEmail(tjrId);
                if(StringUtils.isNotEmpty(tjrEmail)){
                    clientTjr += "/";
                    clientTjr += tjrEmail;
                }
            }
            //跟单邮箱
            List<String> merchandiserEmails;
            String merchandiserIds = "";
            String[] merchandiserId;
            if(StringUtils.isNotEmpty(classEastandwest)){
                if(classEastandwest.equals("0")){ //西向跟单
                    merchandiserIds = orderInfo.getMerchandiserIdW();
                }
                if(classEastandwest.equals("1")){ //东向跟单
                    merchandiserIds = orderInfo.getOrderMerchandiserId();
                }
                if(StringUtils.isNotEmpty(merchandiserIds)){
                    merchandiserId = merchandiserIds.trim().split("\\|");
                    merchandiserEmails = busiShippingorderService.selectOrderMerEmail(merchandiserId);
                    if(merchandiserEmails.size()>0){
                        clientTjr += ";";
                        clientTjr += StringUtils.join(merchandiserEmails.toArray(),";");
                    }
                }
            }
            orderInfo.setClientTjr(clientTjr);
            //箱型
            String containerType = "";
            if(StringUtils.isNotEmpty(orderInfo.getContainerType())){
                containerType = commonService.containerTypeName(orderInfo.getContainerType());
                if(StringUtils.isNotEmpty(containerType)){
                    orderInfo.setContainerTypeval(containerType);
                }
            }
            //危险品备注
            String goodsReport = "";
            String goodsClearance = "";
            if("0".equals(classEastandwest)){ //去程
                goodsReport = orderInfo.getGoodsInReport();
                goodsClearance = orderInfo.getGoodsOutClearance();
            }else{ //回程
                goodsReport = orderInfo.getGoodsReport();
                goodsClearance = orderInfo.getGoodsClearance();
            }
            String nameColor = "0";
            String nameRemark = "";
            //危险品名
            String goodsName = orderInfo.getGoodsName();
            String goodsEnName = orderInfo.getGoodsEnName();
            if(StringUtils.isNotEmpty(goodsName)){
                if(goodsName.contains("混合") || goodsName.contains("液") || goodsName.contains("剂") || goodsName.contains("粉")){
                    nameColor = "1"; //边框blue,5px,字体red,16px;
                }
                List<DangerousGoods> goodsDanList = dangerousGoodsMapper.selectDanGoodsByName(goodsName,goodsEnName);
                if(goodsDanList.size()>0){
                    nameColor = "1";
                    for(int i=0;i<goodsDanList.size();i++){
                        nameRemark = nameRemark+goodsDanList.get(i).getGoodsName()+":"+goodsDanList.get(i).getSpecialremark()+";";
                    }
                }
            }
            //危险报关编码
            if(StringUtils.isEmpty(nameRemark)){
                if(StringUtils.isNotEmpty(goodsReport)){
                    List<DangerousGoods> bgHsDanList = dangerousGoodsMapper.selectDanGoodsByReportHs(goodsReport);
                    if(bgHsDanList.size()>0){
                        nameColor = "1";
                        for(int i=0;i<bgHsDanList.size();i++){
                            nameRemark = nameRemark+bgHsDanList.get(i).getGoodsReport()+":"+bgHsDanList.get(i).getSpecialremark()+";";
                        }
                    }
                }
            }
            //危险清关编码
            if(StringUtils.isEmpty(nameRemark)){
                if(StringUtils.isNotEmpty(goodsClearance)){
                    List<DangerousGoods> qgHsDanList = dangerousGoodsMapper.selectDanGoodsByClearanceHs(goodsClearance);
                    if(qgHsDanList.size()>0){
                        nameColor = "1";
                        for(int i=0;i<qgHsDanList.size();i++){
                            nameRemark = nameRemark+qgHsDanList.get(i).getGoodsClearance()+":"+qgHsDanList.get(i).getSpecialremark()+";";
                        }
                    }
                }
            }
            orderInfo.setNameColor(nameColor);
            orderInfo.setNameRemark(nameRemark);
            //特殊单证备注
            String noteColor = "0";
            if(StringUtils.isNotEmpty(orderInfo.getHsbz())){
                noteColor = "1"; // 边框blue,5px,字体red,16px;
            }
            //hs编码备注
            orderInfo.setNoteRemark(orderInfo.getHsbz());
            //货物特殊备注 orderInfo.getGoodsbz()
            //有色金属
            if(StringUtils.isNotEmpty(goodsReport)){
                String lineTypeId = orderInfo.getLineTypeid();
                if("2".equals(lineTypeId) || "0".equals(lineTypeId)){
                    BaseGoodsnote note = baseGoodsnoteMapper.selectBaseGoodsnoteListByHs(goodsReport);
                    if(StringUtils.isNotNull(note)){
                        noteColor = "2"; // 字体green,16px;
                    }
                    if(goodsReport.length()>5){
                        String report_t = goodsReport.substring(0,2);
                        String report_f = goodsReport.substring(0,4);
                        String report_ff = goodsReport.substring(0,5);
                        if(report_t.equals("78") ||report_t.equals("79") || report_t.equals("80") || report_f.equals("7106") || report_f.equals("7107") || report_f.equals("7108") ||
                                report_f.equals("7109") || report_f.equals("7110") || report_f.equals("7111") || report_f.equals("7112") || report_f.equals("7115") || report_f.equals("8302")
                                || report_f.equals("8309") || report_f.equals("8311") || report_ff.equals("83079") || report_f.equals("85481") || report_f.equals("28049")
                                || report_f.equals("28054") || report_f.equals("32121")){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("81") && (!report_ff.equals("81052"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("76") && (!report_f.equals("7615"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("75") && (!report_f.equals("7501"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("74") && (!report_f.equals("7418")) && (!report_f.equals("7418"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                    }
                }
            }
            orderInfo.setNoteColor(noteColor);
        }
        return AjaxResult.success(orderInfo);
    }

    /**
     * 获取订单详细信息（转待审核暂存表）
     */
    @GetMapping("/getInfoTem")
    @ApiOperation("订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfoTem(String orderId)
    {
        ShippingOrder orderInfo = busiShippingorderService.selectBusiShippingorderTemById(orderId);
        if(StringUtils.isNotNull(orderInfo)){
            //箱型
            String containerType = "";
            if(StringUtils.isNotEmpty(orderInfo.getContainerType())){
                containerType = commonService.containerTypeName(orderInfo.getContainerType());
                if(StringUtils.isNotEmpty(containerType)){
                    orderInfo.setContainerTypeval(containerType);
                }
            }
            String classEastandwest = orderInfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
            //危险品
            String nameColor = "0";
            String goodsName = orderInfo.getGoodsName();
            if(StringUtils.isNotEmpty(goodsName)){
                if(goodsName.contains("混合") || goodsName.contains("液") || goodsName.contains("剂") || goodsName.contains("粉")){
                    nameColor = "1"; //边框blue,5px,字体red,16px;
                }
                DangerousGoods dangoods = dangerousGoodsMapper.selectDangerousGoodsByName(goodsName);
                if(StringUtils.isNotNull(dangoods)){
                    nameColor = "1";
                    orderInfo.setNameRemark(dangoods.getSpecialremark());
                }
            }
            orderInfo.setNameColor(nameColor);
            //特殊单证
            String noteColor = "0";
            String goodsReport = "";
            if("0".equals(classEastandwest)){ //去程
                goodsReport = orderInfo.getGoodsInReport();
            }else{ //回程
                goodsReport = orderInfo.getGoodsReport();
            }
            if(StringUtils.isNotEmpty(orderInfo.getHsbz())){
                noteColor = "1"; // 边框blue,5px,字体red,16px;
            }
            //hs编码备注
            orderInfo.setNoteRemark(orderInfo.getHsbz());
            //货物特殊备注 orderInfo.getGoodsbz()
            //有色金属
            if(StringUtils.isNotEmpty(goodsReport)){
                String lineTypeId = orderInfo.getLineTypeid();
                if("2".equals(lineTypeId) || "0".equals(lineTypeId)){
                    BaseGoodsnote note = baseGoodsnoteMapper.selectBaseGoodsnoteListByHs(goodsReport);
                    if(StringUtils.isNotNull(note)){
                        noteColor = "2"; // 字体green,16px;
                    }
                    if(goodsReport.length()>5){
                        String report_t = goodsReport.substring(0,2);
                        String report_f = goodsReport.substring(0,4);
                        String report_ff = goodsReport.substring(0,5);
                        if(report_t.equals("78") ||report_t.equals("79") || report_t.equals("80") || report_f.equals("7106") || report_f.equals("7107") || report_f.equals("7108") ||
                                report_f.equals("7109") || report_f.equals("7110") || report_f.equals("7111") || report_f.equals("7112") || report_f.equals("7115") || report_f.equals("8302")
                                || report_f.equals("8309") || report_f.equals("8311") || report_ff.equals("83079") || report_f.equals("85481") || report_f.equals("28049")
                                || report_f.equals("28054") || report_f.equals("32121")){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("81") && (!report_ff.equals("81052"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("76") && (!report_f.equals("7615"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("75") && (!report_f.equals("7501"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                        if(report_t.equals("74") && (!report_f.equals("7418")) && (!report_f.equals("7418"))){
                            noteColor = "2"; // 字体green,16px;
                        }
                    }
                }
            }
            orderInfo.setNoteColor(noteColor);
        }
        return AjaxResult.success(orderInfo);
    }

    /**
     * 查询托书编号是否重复
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/getInfoByBh")
    @ApiOperation("订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单编号",name = "orderNumber",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getInfoByBh(String orderNumber)
    {
        String numFlag = "0"; //0不重复 1重复
        List<ShippingOrderList> orderList = busiShippingorderMapper.selectOrderByOrderNumberLike(orderNumber);
        if(orderList.size()>0){
            for(int i=0;i<orderList.size();i++){
                ShippingOrderList orderItem = orderList.get(i);
                String num = orderItem.getOrderNumber().trim();
                if(StringUtils.equals(orderNumber,num)){
                    numFlag = "1";
                }
                if(num.contains("-")){
                    num = num.substring(0,num.indexOf("-"));
                }
                if(StringUtils.equals(orderNumber,num)){
                    numFlag = "1";
                }
            }
        }
        if("1".equals(numFlag)){
            return AjaxResult.error("舱位号已存在");
        }else{
            return AjaxResult.success("舱位号可用");
        }
    }

    /**
     * 获取订单商品信息(规格)
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/goodsInfo")
    @ApiOperation("订单商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "询价结果id",name = "inquiryRecordId",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo goodsInfo(String inquiryRecordId){
        List<BookingInquiryGoodsDetails> goodsList = busiShippingorderService.selectGoodsInfo(inquiryRecordId);
        return getDataTable(goodsList);
    }

    /**
     * 获取订单询价信息
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/inquiryInfo")
    @ApiOperation("订单询价信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "询价结果id",name = "inquiryRecordId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult inquiryInfo(String inquiryRecordId){
        return AjaxResult.success(busiShippingorderService.selectInquiryInfo(inquiryRecordId));
    }

    /**
     * 修改、审核托书
     */
//    @PreAuthorize("@ss.hasPermi('classOrder:orders:editOrder')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/orderEdit")
    @ApiOperation("订单修改")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作类型 0审核 1编辑",name = "operateType",dataType = "String",required = true),
            @ApiImplicitParam(value = "订单状态：1审核通过 2审核失败 0待审核",name = "isexamline",dataType = "String",required = true)
    })
    public AjaxResult edit(@RequestBody BusiShippingorders busiShippingorder)
    {
        String orderId = busiShippingorder.getOrderId();
        String language = busiShippingorder.getLanguage();
        //插入排舱托书表
        if(StringUtils.isNotEmpty(orderId)){
            BusiZyOrder isZyOrder = busiZyOrderMapper.selectBusiZyOrderByOrderId(orderId);
            if(StringUtils.isNull(isZyOrder)){
                BusiZyOrder zyOrder = new BusiZyOrder();
                zyOrder.setOrderId(orderId);
                zyOrder.setCreateTime(DateUtils.getNowDate());
                busiZyOrderMapper.insertBusiZyOrder(zyOrder);
            }
        }
        //托书编号是否重复
        String orderNumber = busiShippingorder.getOrderNumber();
        if(StringUtils.isNotEmpty(orderNumber)){
            String numFlag = "0"; //0不重复 1重复
            List<ShippingOrderList> orderList = busiShippingorderMapper.selectOrderByOrderNumberLike(orderNumber);
            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++){
                    ShippingOrderList orderItem = orderList.get(i);
                    if(!StringUtils.equals(orderId,orderItem.getOrderId())){
                        String num = orderItem.getOrderNumber().trim();
                        if(StringUtils.equals(orderNumber,num)){
                            numFlag = "1";
                        }
                        if(num.contains("-")){
                            num = num.substring(0,num.indexOf("-"));
                            num = num.trim();
                        }
                        if(StringUtils.equals(orderNumber,num)){
                            numFlag = "1";
                        }
                    }
                }
                if("1".equals(numFlag)){
                    return AjaxResult.error("舱位号重复，请修改或重新生成");
                }
            }
        }
        //查询是否包含危险品
        String goodsName = busiShippingorder.getGoodsName();
        String goodsEnName = busiShippingorder.getGoodsEnName();
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
        //查询是否包含危险hs编码
        String goodsReport = "";
        String goodsClearance = "";
        if("0".equals(busiShippingorder.getClassEastandwest())){
            goodsReport = busiShippingorder.getGoodsInReport();
            goodsClearance = busiShippingorder.getGoodsOutClearance();
        }
        if("1".equals(busiShippingorder.getClassEastandwest())){
            goodsReport = busiShippingorder.getGoodsReport();
            goodsClearance = busiShippingorder.getGoodsClearance();
        }
        if(StringUtils.isNotEmpty(goodsReport)){
            DangerousGoods danReportHs = dangerousGoodsMapper.selectReportHsByNameByLevel(goodsReport);
            if(StringUtils.isNotNull(danReportHs)){
                if("en".equals(language)){
                    return AjaxResult.error(danReportHs.getSpecialEnRemark());
                }else{
                    return AjaxResult.error(danReportHs.getSpecialremark());
                }
            }
        }
        if(StringUtils.isNotEmpty(goodsClearance)){
            DangerousGoods danClearanceHs = dangerousGoodsMapper.selectClearanceHsByNameByLevel(goodsClearance);
            if(StringUtils.isNotNull(danClearanceHs)){
                if("en".equals(language)){
                    return AjaxResult.error(danClearanceHs.getSpecialEnRemark());
                }else{
                    return AjaxResult.error(danClearanceHs.getSpecialremark());
                }
            }
        }
        //编辑审核
        BusiShippingorders orderinfo = busiShippingorderService.selectBusiShippingorderByIdOld(busiShippingorder.getOrderId());
        String operateType = busiShippingorder.getOperateType();
        if(StringUtils.isNotNull(orderinfo)){
            //订舱时间小于提货时间
            if(!("0".equals(busiShippingorder.getOperateType()))){
                String binningway = busiShippingorder.getShipOrderBinningway(); //委托ZIH提货 0是 1否
                if(StringUtils.isNotEmpty(binningway)){
                    Date orderTime = null;
                    if(StringUtils.isNotEmpty(orderinfo.getOrderNumber())){
                        orderTime = busiShippingorder.getOrderAuditCreatedate(); //订舱时间
                    }else{
                        orderTime = new Date();
                    }
                    Date arriveTime = null;
                    if("0".equals(binningway)){
                        arriveTime = busiShippingorder.getShipOrderUnloadtime();
                    }
                    if("1".equals(binningway)){
                        arriveTime = busiShippingorder.getShipOrderSendtime();
                    }
                    if(StringUtils.isNotNull(orderTime) && StringUtils.isNotNull(arriveTime)){
                        int comto = orderTime.compareTo(arriveTime);//o小于a返回-1，o大于a返回1，相等返回0
                        if(comto>0){
                            return AjaxResult.error("提货时间/自送货时间不能早于订舱时间");
                        }
                    }
                }
            }
            //0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败
            String isExamline = orderinfo.getIsexamline();
            //审核 0审核 1编辑
            if(operateType.equals("0")){
                if(!isExamline.equals("0")){
                    return AjaxResult.error("未审核托书可进行审核操作");
                }
            }
            //编辑
            if(operateType.equals("1")){
                if(!("0".equals(isExamline) || "2".equals(isExamline) || "3".equals(isExamline) || "5".equals(isExamline) || "8".equals(isExamline))){
                    return AjaxResult.error("待审核、审核失败、草稿、取消委托状态下托书可编辑");
                }
            }
        }else{
            return AjaxResult.error("该托书不存在，请重新选择");
        }
        int result = 0;
        String msg = "";
        try {
            //提还箱地、提还箱费用
            if(StringUtils.isEmpty(busiShippingorder.getShipFhSite())){busiShippingorder.setShipFhSiteS("1");}
            if(StringUtils.isEmpty(busiShippingorder.getShipHyd())){busiShippingorder.setShipHydS("1");}
            if(StringUtils.isEmpty(busiShippingorder.getReceiveHxAddress())){busiShippingorder.setReceiveHxAddressS("1");}
            if(StringUtils.isEmpty(busiShippingorder.getPickUpBoxFee())){busiShippingorder.setPickUpBoxFeeS("1");}
            if(StringUtils.isEmpty(busiShippingorder.getReturnBoxFee())){busiShippingorder.setReturnBoxFeeS("1");}
            if(StringUtils.isEmpty(busiShippingorder.getRemark())){busiShippingorder.setRemarkS("1");}
            if(StringUtils.isEmpty(busiShippingorder.getEconsignorstate())){busiShippingorder.setEconsignorstateS("1");}
            result = busiShippingorderService.updateBusiShippingorder(busiShippingorder);
            if(result == 4){
                if("en".equals(language)){
                    return AjaxResult.error("Insufficient space");
                }else{
                    return AjaxResult.error("箱量与班列信息不匹配，请重新选择班列");
                }
            }
            if(result == 1){
                if("en".equals(language)){
                    msg = "success";
                }else{
                    msg = "操作成功";
                }
            }else{
                if("en".equals(language)){
                    msg = "error";
                }else{
                    msg = "操作失败，请重试";
                }
            }
        } catch (JsonProcessingException e) {
            log.error("托书修改失败",e.toString(),e.getStackTrace());
            return toAjax(0);
        }
        return AjaxResult.success(msg);
    }

    /**
     * 获取订单品名特殊备注
     */
    @GetMapping("/goodsSpecialRemark")
    @ApiOperation("品名特殊备注")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "上货站",name = "orderUploadsite",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "班列id",name = "classId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "品名",name = "goodsName",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult goodsSpecialRemark(BusiShippingorders busiShippingorder){
        String msg = "";
        msg = commonService.goodsSpecialRemark(busiShippingorder);
        return AjaxResult.success(msg);
    }

    /**
     * 获取订单hs特殊备注
     */
    @GetMapping("/hsSpecialRemark")
    @ApiOperation("hs编码特殊备注")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "上下货站",name = "orderUnloadsite",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "班列id",name = "classId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "hs编码",name = "goodsReport",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult hsSpecialRemark(BusiShippingorders busiShippingorder){
        String msg = "";
        msg = commonService.hsSpecialRemark(busiShippingorder);
        return AjaxResult.success(msg);
    }

    /**
     * 平台端转待审核申请
     */
    @PreAuthorize("@ss.hasPermi('classOrder:orders:zdApply')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/orderCheckApply")
    @ApiOperation("平台端转待审核申请")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "申请转待原因",name = "examInfo",dataType = "String",required = true),
            @ApiImplicitParam(value = "申请转待类别",name = "examType",dataType = "String",required = true),
            @ApiImplicitParam(value = "是否有改单费(是/否)",name = "dcGaidanState",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult orderCheckApply(@RequestBody BusiShippingorders busiShippingorder) throws JsonProcessingException {
        BusiShippingorders orderinfo = busiShippingorderService.selectBusiShippingorderByIdOld(busiShippingorder.getOrderId());
        if(StringUtils.isNotNull(orderinfo)){
            //0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败
            String isExamline = orderinfo.getIsexamline();
            Long turncount = orderinfo.getTurncount();
            Long totalturncount = orderinfo.getTotalturncount();
            Long totalturncountavg = StringUtils.isNotNull(orderinfo.getTotalturncountavg())?orderinfo.getTotalturncountavg():0;//订单的去程N-4 12:00之后，回程N-5 12:00之后转待审核次数
            String isAddZd = commonService.isZdCount(orderinfo.getClassDate(),orderinfo.getClassEastandwest()); //是否增加一次 0否 1是
            if(!isExamline.equals("1")){
                return AjaxResult.error("审核通过托书才能申请转待审核");
            }
            if(turncount>1){
                return AjaxResult.error("转待审核申请每日不能超过两次");
            }
            //重新赋值转待次数
            busiShippingorder.setTurncount(turncount+1);
            busiShippingorder.setTotalturncount(totalturncount+1);
            if("1".equals(isAddZd)){
                busiShippingorder.setTotalturncountavg(totalturncountavg+1);
            }
            busiShippingorder.setClientId(orderinfo.getClientId());
            int result = busiShippingorderService.orderCheckApply(busiShippingorder);
            return toAjax(result);
        }else{
            return AjaxResult.error("该托书不存在,请重新选择");
        }
    }

    /**
     * 转待审核(涉及询价)
     */
    @PreAuthorize("@ss.hasPermi('classOrder:orders:zdApply')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/orderCheckApplyXj")
    @ApiOperation("转待审核申请")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "申请转待原因",name = "examInfo",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult orderCheckApplyXj(@RequestBody BusiShippingorders busiShippingorder) throws JsonProcessingException {
        int result = busiShippingorderService.orderCheckApplyXj(busiShippingorder);
        if(result == 4){
            return AjaxResult.error("网络异常，请重试");
        }
        if(result == 0){
            return AjaxResult.error("数据异常，请重试");
        }
        return toAjax(result);
    }

    /**
     * 转待审核订单列表
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:list')")
    @GetMapping("/checkList")
    @ApiOperation("转待审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "登录者部门编号",name = "deptCode",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "登录者ID",name = "userid",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo checkList(CheckOrderList checkorder){
        startPage();
        checkorder.setIsexamline("4");
        if(StringUtils.isNotEmpty(checkorder.getOrderNumber())){
            checkorder.setOrderNumber((checkorder.getOrderNumber()).trim());
        }
        List<CheckOrderList> list = busiShippingorderService.selectCheckOrderList(checkorder);
        return getDataTable(list);
    }

    @GetMapping("/orderSiteCode")
    @ApiOperation("托书最新上货站")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public String orderSiteCode(String orderId){
        String uploadCode = "";
        uploadCode = busiShipOrderMapper.orderSiteCode(orderId);
        return uploadCode;
    }

    /**
     * 转待审核修改记录
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/checkEditRecord")
    @ApiOperation("转待审核修改记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult checkEditRecord(String orderId){
        ShippingorderExaminfo orderExaminfo = shippingorderExaminfoService.selectShippingorderExaminfoByOrderId(orderId);
        BusiShippingorders orderinfo = busiShippingorderService.selectBusiShippingorderByIdOld(orderId);
        String isconsolidation = "";
                RecordObj recordObj = new RecordObj();
        if(StringUtils.isNotNull(orderinfo)){
            isconsolidation = orderinfo.getIsconsolidation(); //0整柜 1拼箱
        }
        if(StringUtils.isNotNull(orderExaminfo)){
            String editRecord = orderExaminfo.getEditRecord();
            if(StringUtils.isNotEmpty(editRecord)){
                //截去末尾符号
                editRecord = StringUtils.substring(editRecord,0,-9);
                String title = editRecord.substring(editRecord.indexOf("<th>")+4, editRecord.lastIndexOf("</th>")); //标题
                String listStr = editRecord.substring(title.length()+9, editRecord.length());; //修改记录
                String[] recordS = listStr.split("<td>");
                List<RecordObjCon> contentList = new ArrayList<RecordObjCon>();
                for(int a=0;a<recordS.length;a++){
                    RecordObjCon objCon = new RecordObjCon();
                    objCon.setRecord(recordS[a]);
                    objCon.setFlag(recordFlag(recordS[a],isconsolidation));
                    contentList.add(objCon);
                }
                recordObj.setTitle(title);
                recordObj.setList(recordS);
                recordObj.setContentList(contentList);
            }
        }
        return AjaxResult.success(recordObj);
    }

    /**
     * 转待审核操作(涉及询价)
     */
    @PreAuthorize("@ss.hasPermi('classOrder:goAudited:check')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/orderCheckReply")
    @ApiOperation("转待审核操作")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作状态（同意:1，不同意:5）",name = "isexamline",dataType = "String",required = true),
            @ApiImplicitParam(value = "修改后的托书编号",name = "orderNumberNew",dataType = "String",required = true),
            @ApiImplicitParam(value = "是否有改单费(是/否)",name = "dcGaidanState",dataType = "String",required = true),
            @ApiImplicitParam(value = "申请转待失败原因",name = "examInfo",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult orderCheckReply(@RequestBody BusiShippingorders busiShippingorder) throws JsonProcessingException {
        int result = busiShippingorderService.orderCheckReplyXj(busiShippingorder);
        if(result == 0){
            return AjaxResult.error("数据异常，不能通过审核");
        }
        return toAjax(result);
    }

    /**
     * 修改舱位号
     */
    @PreAuthorize("@ss.hasPermi('classOrder:orders:checkCangWeiNum')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/orderSpaceEdit")
    @ApiOperation("修改舱位号")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "修改后托书编号",name = "orderNumberEdit",dataType = "String",required = true),
            @ApiImplicitParam(value = "修改后班列编号",name = "orderClassBh",dataType = "String",required = true),
            @ApiImplicitParam(value = "回程场站",name = "stationid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult orderSpaceEdit(@RequestBody BusiShippingorders busiShippingorder) throws JsonProcessingException {
        try{
            int result = busiShippingorderService.orderSpaceEdit(busiShippingorder);
            if(result==11){
                return AjaxResult.error("不存在该班列");
            }
            if(result==12){
                return AjaxResult.error("输入的托书编号重复");
            }
            if(result==13){
                return AjaxResult.error("没有内容更新");
            }
            if(result==14){
                return AjaxResult.error("回程托书可修改场站地址");
            }
            return toAjax(result);
        }catch (JsonProcessingException e) {
            log.error("舱位号修改失败",e.toString(),e.getStackTrace());
            return toAjax(0);
        }
    }

    //平台端取消订单
//    @PreAuthorize("@ss.hasPermi('classOrder:orders:delOrder')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/orderCancel")
    @ApiOperation("取消托书")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作状态（传定值3）",name = "isexamline",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult cancel(@RequestBody BusiShippingorders busiShippingorder) throws JsonProcessingException {
        ShippingOrder orderinfo = busiShippingorderService.selectBusiShippingorderById(busiShippingorder.getOrderId());
        if(StringUtils.isNotNull(orderinfo)){
            //0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败
            String isExamline = orderinfo.getIsexamline();
            if("1".equals(isExamline)){
                return AjaxResult.error("审核通过托书，不能取消");
            }
            if("3".equals(isExamline)){
                return AjaxResult.error("已取消托书，不能重复取消");
            }
            if("4".equals(isExamline) || "7".equals(isExamline) || "9".equals(isExamline) || "10".equals(isExamline) || "11".equals(isExamline) || "12".equals(isExamline)){
                return AjaxResult.error("转待审核中托书，不能取消");
            }
        }else{
            return AjaxResult.error("该托书不存在,请重新选择");
        }
        return toAjax(busiShippingorderService.cancelBusiShippingorder(busiShippingorder));
    }

    //客户端取消订单申请(撤舱)
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/orderCancelApply")
    @ApiOperation("申请撤舱")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作状态（传定值13）",name = "isexamline",dataType = "String",required = true),
            @ApiImplicitParam(value = "撤舱原因",name = "examInfo",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult orderCancelApply(@RequestBody BusiShippingorders busiShippingorder){
        ShippingOrder orderinfo = busiShippingorderService.selectBusiShippingorderById(busiShippingorder.getOrderId());
        if(StringUtils.isNotNull(orderinfo)){
            String isExamline = orderinfo.getIsexamline();
            if(!("1".equals(isExamline))){
                return AjaxResult.error("审核通过托书可申请撤舱");
            }
        }else{
            return AjaxResult.error("该托书不存在,请重新选择");
        }
        return toAjax(busiShippingorderService.orderCancelApply(busiShippingorder));
    }

    //撤舱申请审核列表
    @GetMapping("/cancelCheckList")
    @ApiOperation("转待审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "登录者部门编号",name = "deptCode",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "登录者ID",name = "userid",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo cancelCheckList(CheckOrderList checkorder){
        startPage();
        checkorder.setIsexamline("13");
        if(StringUtils.isNotEmpty(checkorder.getOrderNumber())){
            checkorder.setOrderNumber((checkorder.getOrderNumber()).trim());
        }
        List<CheckOrderList> list = busiShippingorderService.selectCheckOrderList(checkorder);
        return getDataTable(list);
    }

    //客户端撤舱申请操作
    @PreAuthorize("@ss.hasPermi('classOrder:goCancel:check')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelCheckReply")
    @ApiOperation("转待审核操作")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作状态（同意:3，不同意:5）",name = "isexamline",dataType = "String",required = true),
            @ApiImplicitParam(value = "审核失败原因",name = "examInfo",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人id",name = "createuserid",dataType = "String",required = true),
            @ApiImplicitParam(value = "操作人姓名",name = "createusername",dataType = "String",required = true)
    })
    public AjaxResult cancelCheckReply(@RequestBody BusiShippingorders busiShippingorder){
        int result = busiShippingorderService.cancelCheckReply(busiShippingorder);
        if(result == 0){
            return AjaxResult.error("数据异常，请重试");
        }
        return toAjax(result);
    }

    /**
     * 批量删除订单
     */
    @PreAuthorize("@ss.hasPermi('classOrder:orders:delete')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    @ApiOperation("订单删除")
    public AjaxResult remove(@PathVariable String[] orderIds) throws JsonProcessingException {
        for(int i=0;i<orderIds.length;i++){
            BusiShippingorders orderinfo = busiShippingorderService.selectBusiShippingorderByIdOld(orderIds[i]);
            //0未审核 1已审核通过  2已审核未通过 3已取消的委托 4转待审核  5草稿 6转待失败
            String isExamline = orderinfo.getIsexamline();
            if(StringUtils.isNotEmpty(isExamline)){
                if(!isExamline.equals("3")){
                    return AjaxResult.error("存在未完成托书，不能删除");
                }
            }
        }
        return toAjax(busiShippingorderService.deleteBusiShippingorderByIds(orderIds));
    }

    /**
     * 获取托书编号
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/getOrderNumber")
    @ApiOperation("获取托书编号")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getOrderNumber(String orderId){
        //BusiShippingorders orderinfo = busiShippingorderService.selectBusiShippingorderByIdOld(orderId);
        ShippingOrder orderinfo = busiShippingorderMapper.selectBusiShippingorderById(orderId);
        String classBh = orderinfo.getOrderClassBh();  //班列编号
        String zp = orderinfo.getIsconsolidation();  //整拼箱
        String shipOrederName = orderinfo.getShipOrederName(); //发货方名称
        //site 去0(西向)为下货站，回1(东向)为上货站
        String site = "";
        if("0".equals(orderinfo.getClassEastandwest())){
            site = orderinfo.getOrderUnloadcode();
        }
        if("1".equals(orderinfo.getClassEastandwest())){
            site = orderinfo.getOrderUploadcode();
        }
        int orderType = 2;
        if("陆港拼箱".equals(shipOrederName)){
            orderType = 1;
        }
        //全流程测试用户
        int isPower = 1; //1不是全流程客户 0是全流程客户
//        if(StringUtils.isNotEmpty(orderinfo.getClientId())){
//            String powerFlag = busiClientsMapper.getPowerById(orderinfo.getClientId());
//            if("0".equals(powerFlag)){
//                isPower = 0;
//            }
//        }
        String orderNumberRule = AutoOrderNumber.orderNumber(classBh,zp,site,orderType,isPower);
        if(StringUtils.isNotEmpty(orderNumberRule)){
            //判断新托书编号是否重复
            String numFlag = "0"; //0不重复 1重复
            List<ShippingOrderList> orderList = busiShippingorderMapper.selectOrderByOrderNumberLike(orderNumberRule);
            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++){
                    ShippingOrderList orderItem = orderList.get(i);
                    String num = orderItem.getOrderNumber().trim();
                    if(StringUtils.equals(orderNumberRule,num)){
                        numFlag = "1";
                    }
                    if(num.contains("-")){
                        num = num.substring(0,num.indexOf("-"));
                        num = num.trim();
                    }
                    if(StringUtils.equals(orderNumberRule,num)){
                        numFlag = "1";
                    }
                }
            }
            if("1".equals(numFlag)){
                return getOrderNumber(orderId);
            }else{
                return AjaxResult.success(orderNumberRule);
            }
        } else {
            return AjaxResult.success(null);
        }
    }

    /**
     * 查询订单修改记录
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/historyEditRecord")
    @ApiOperation("查询订单修改记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo historyEditRecord(String orderId)
    {
        BusiShippingorders orderInfo = busiShippingorderService.selectHistoryEditRecord(orderId);
        String isconsolidation = orderInfo.getIsconsolidation(); //0整柜 1拼箱
        List<RecordObj> historyList = new ArrayList<RecordObj>();
        if(StringUtils.isNotNull(orderInfo)){
            String editRecord = orderInfo.getGoodsHistoryEditrecord();
            if(StringUtils.isNotEmpty(editRecord)){
                if(!editRecord.equals("null")){
                    //截去末尾符号
                    editRecord = StringUtils.substring(editRecord,0,-5);
                    String[] recordF = editRecord.split("<###>");
                    for(int i = 0;i <recordF.length; i++){
                        String recordEvery = recordF[i];
                        String title = recordEvery.substring(recordEvery.indexOf("<th>")+4, recordEvery.lastIndexOf("</th>")); //标题
                        String listStr = null;
                        if(i==0){
                            listStr = recordEvery.substring(title.length()+13, recordEvery.length()); //修改记录
                        }else{
                            listStr = recordEvery.substring(title.length()+9, recordEvery.length()); //修改记录
                        }
                        //截去末尾<td>
                        listStr = StringUtils.substring(listStr,0,-4);
                        String[] recordS = listStr.split("<td>");
                        List<RecordObjCon> contentList = new ArrayList<RecordObjCon>();
                        for(int a=0;a<recordS.length;a++){
                            RecordObjCon objCon = new RecordObjCon();
                            objCon.setRecord(recordS[a]);
                            objCon.setFlag(recordFlag(recordS[a],isconsolidation));
                            contentList.add(objCon);
                        }
                        RecordObj recordObj = new RecordObj();
                        recordObj.setTitle(title);
                        recordObj.setList(recordS);
                        recordObj.setContentList(contentList);
                        historyList.add(recordObj);
                    }
                }
            }
        }
        return getDataTable(historyList);
    }

    /**
     * 判断特殊标识
     */
    public String recordFlag(String content,String isconsolidation){
        String flag = "0"; //0不标记 1标记
        if(StringUtils.isNotEmpty(content)){
            String[] zgTitle = {"委托书编号","箱型","箱量","由ZIH提货","提货时间","提货联系人","提货联系方式","提货地址","详细地址","货品中文名称","最外层包装数量","总体积","总重量"};
            String[] pxTitle = {"委托书编号","由ZIH提货","提货时间","提货联系人","提货联系方式","提货地址","详细地址","货品中文名称","最外层包装数量","总体积","总重量","报价编号","装箱方式","托书备注","可推叠(货物属性)"};
            if("0".equals(isconsolidation)){
                for(int i=0;i<zgTitle.length;i++){
                    if(content.contains(zgTitle[i])){
                        flag = "1";
                    }
                }
            }
            if("1".equals(isconsolidation)){
                for(int i=0;i<pxTitle.length;i++){
                    if(content.contains(pxTitle[i])){
                        flag = "1";
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 重新询价时判断箱量是否满足当前班列
     */
    @GetMapping("/boxNumberMatch")
    @ApiOperation("重新询价时判断箱量是否满足当前班列")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "托书id",name = "orderId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "箱型",name = "containerType",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "箱量",name = "containerBoxamount",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult boxNumberMatch(String orderId,String containerType,String containerBoxamount){
        if(StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(containerType) && StringUtils.isNotEmpty(containerBoxamount)){
            BusiShippingorders orderInfo = busiShippingorderService.selectBusiShippingorderByIdOld(orderId);
            int result = 1;
            if(StringUtils.isNotNull(orderInfo)){
                String isconsolidation = orderInfo.getIsconsolidation(); //0整柜 1拼箱
                if("0".equals(isconsolidation)){
                    //最新询价箱量
                    Double boxamount = 0.0;
                    if(containerType.contains("20")){
                        boxamount = Double.valueOf(containerBoxamount)/2;
                    }else{
                        boxamount = Double.valueOf(containerBoxamount);
                    }
                    //托书原箱量
                    Double boxamountback = 0.0;
                    String boxtypeback = orderInfo.getContainerType(); //托书原箱型
                    if(StringUtils.isNotEmpty(orderInfo.getContainerBoxamount())){
                        if(StringUtils.isNotEmpty(boxtypeback)){
                            if(boxtypeback.contains("20")){
                                boxamountback = Double.valueOf(orderInfo.getContainerBoxamount())/2;
                            }else{
                                boxamountback = Double.valueOf(orderInfo.getContainerBoxamount());
                            }
                        }
                    }
                    Double isup = boxamount - boxamountback; //新增或减少的箱量
                    if(isup > 0){
                        //班列信息
                        String classId = orderInfo.getClassId();
                        BusiClasses classInfo = busiClassesService.selectBusiClassesById(classId);
                        if(StringUtils.isNotNull(classInfo)){
                            Long classSpacenumber = classInfo.getClassSpacenumber(); //舱位总数
                            Long zxcnt = classInfo.getZxcnt(); //整箱舱位数
                            Integer zg = busiShippingorderPcMapper.zgCount(classId);
                            if(zg == null){
                                zg = 0;
                            }
                            Double boxleft = zxcnt - Double.valueOf(zg); //剩余整箱舱位数
                            if(boxleft<isup || zg>classSpacenumber){
                                result = 0; //舱位不足
                            }
                        }else{
                            result = 0;
                        }
                    }
                }
            }
            if(result == 1){
                return AjaxResult.success("原班列可选");
            }else{
                return AjaxResult.error("当前班列无剩余舱位");
            }
        }else{
            return AjaxResult.error("缺少必填参数");
        }
    }

    /**
     * 根据订单id查询上下货站列表
     */
//    @PreAuthorize("@ss.hasPermi('order:shippingorder:query')")
    @GetMapping("/getSiteByOrderId")
    @ApiOperation("托书上下货站列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "班列id",name = "classId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "订单id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult getSiteByOrderId(String classId,String orderId){
        Map mapSite = new HashMap();
        BusiShippingorders orderInfo = busiShippingorderService.selectBusiShippingorderByIdOld(orderId);
        if(StringUtils.isNotNull(orderInfo)){
            String isconsolidation = orderInfo.getIsconsolidation();  //0整柜 1拼箱
            String classEastandwest = orderInfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
            BusiClasses classesInfo = busiClassesService.selectBusiClassesById(classId);//查询到班列信息
            if(StringUtils.isNotNull(classesInfo)){
                String departureNum = classesInfo.getClassStationofdeparture(); //始发站编号
                String destinationNum = classesInfo.getClassStationofdestination();//目的站编号
                if(classEastandwest.equals("0")){
                    //去程整柜/拼箱上货站
                    List<BusiSite> siteObj = busiSiteService.listBySiteCodes(departureNum);
                    mapSite.put("sitelistup",siteObj);
                    if(isconsolidation.equals("0")){
                        //去程整柜下货站
                        String siteList = classesInfo.getReceiveSiteFull();//整柜随机接货站点编号
                        List<BusiSite> sitelistdown = busiSiteService.listBySiteCodes(siteList);
                        mapSite.put("sitelistdown",sitelistdown);
                    }
                    if(isconsolidation.equals("1")){
                        //去程拼箱下货站
                        String siteList = classesInfo.getReceiveSiteLcl();//拼箱随机接货站点编号
                        List<BusiSite> sitelistdown = busiSiteService.listBySiteCodes(siteList);
                        mapSite.put("sitelistdown",sitelistdown);
                    }
                }
                if(classEastandwest.equals("1")){
                    if(isconsolidation.equals("0")){
                        //回程整柜上货站
                        String siteList = classesInfo.getReceiveSiteFull();//整柜随机接货站点编号
                        List<BusiSite> sitelistup = busiSiteService.listBySiteCodes(siteList);
                        mapSite.put("sitelistup",sitelistup);
                    }
                    if(isconsolidation.equals("1")){
                        //回程拼箱上货站
                        String siteList = classesInfo.getReceiveSiteLcl();//整柜随机接货站点编号
                        List<BusiSite> sitelistup = busiSiteService.listBySiteCodes(siteList);
                        mapSite.put("sitelistup",sitelistup);
                    }
                    //回程整柜/拼箱下货站
                    List<BusiSite> siteObj = busiSiteService.listBySiteCodes(destinationNum);
                    mapSite.put("sitelistdown",siteObj);
                }
            }
        }
        return AjaxResult.success(mapSite);
    }

    /**
     * 回程场站列表
     */
    @GetMapping("/stationList")
    @ApiOperation("场站列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "0：整柜，1：拼箱",name = "isconsolidation",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "上货站唯一码",name = "orderUploadcode",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo stationList(String isconsolidation,String orderUploadcode){
        List<BusiStation> list = busiShippingorderService.selectStationList(isconsolidation,orderUploadcode );
        return getDataTable(list);
    }

    /**
     * 导出托书
     */
    //@PreAuthorize("@ss.hasPermi('order:shippingorder:export')")
    @GetMapping("/orderExport")
    @ApiOperation("托书导出")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "托书id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult orderExport(HttpServletRequest request, HttpServletResponse response, String orderId){
        ShippingOrder orderInfo = busiShippingorderService.selectBusiShippingorderById(orderId);
        AjaxResult result = null;
        String strtime = "yyy-MM-dd HH:mm:ss";
        String strdate = "yyy-MM-dd";
        SimpleDateFormat sdftime = new SimpleDateFormat(strtime);
        SimpleDateFormat sdfdate = new SimpleDateFormat(strdate);
        if(StringUtils.isNotNull(orderInfo)){
            String classEastandwest = orderInfo.getClassEastandwest(); //0为去程(西向) 1为回程(东向)
            String isconsolidation = orderInfo.getIsconsolidation(); //0整柜 1拼箱
            String orderNumber = orderInfo.getOrderNumber();
            String eastandwest = (classEastandwest.equals("1")?"东向":"西向");
            String boxtype = (isconsolidation.equals("1")?"拼箱":"整柜");
            String fileName = orderNumber+'-'+eastandwest+boxtype+"托书.docx";
            //箱型
            String containerType = "暂无";
            if(StringUtils.isNotEmpty(orderInfo.getContainerType())){
                containerType = commonService.containerTypeName(orderInfo.getContainerType());
            }
            Map<String,Object> params = new HashMap<>();
            params.put("title",eastandwest+boxtype);
            //班列信息
            String numberInfo = "";
            if(StringUtils.isNotEmpty(orderNumber)){
                numberInfo += orderNumber;
            }else{
                numberInfo += "暂无托书编号";
            }
            String eaw = orderInfo.getClassEastandwest(); //0去程 1回程
            if(StringUtils.isNotEmpty(eaw)){
                if(eaw.equals("0")){
                    if(StringUtils.isNotEmpty(orderInfo.getMerchandiserW())){
                        numberInfo += "-";
                        numberInfo += orderInfo.getMerchandiserW();
                    }
                }
                if(eaw.equals("1")){
                    if(StringUtils.isNotEmpty(orderInfo.getOrderMerchandiser())){
                        numberInfo += "-";
                        numberInfo += orderInfo.getOrderMerchandiser();
                    }
                }
            }
            params.put("orderNumber",StringUtils.isNotEmpty(numberInfo)?numberInfo:"暂无");
            params.put("classNumber",StringUtils.isNotEmpty(orderInfo.getClassNumber())?orderInfo.getClassNumber():"暂无");
            params.put("classEastandwest",eastandwest);
            params.put("classDate",StringUtils.isNotNull(orderInfo.getClassDate())?sdfdate.format(orderInfo.getClassDate()):"暂无");
            params.put("lineName",StringUtils.isNotEmpty(orderInfo.getLineName())?orderInfo.getLineName():"暂无");
            params.put("orderUploadsite",StringUtils.isNotEmpty(orderInfo.getOrderUploadsite())?orderInfo.getOrderUploadsite():"暂无");
            params.put("orderUnloadsite",StringUtils.isNotEmpty(orderInfo.getOrderUnloadsite())?orderInfo.getOrderUnloadsite():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getOrderIsticket())){
                params.put("orderIsticket",(orderInfo.getOrderIsticket()).equals("0")?"否":"是");
            }else{
                params.put("orderIsticket","暂无");
            }
            params.put("dictName",StringUtils.isNotEmpty(orderInfo.getDictName())?orderInfo.getDictName():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getOrderAuditBelongto())){
                params.put("orderAuditBelongto",(orderInfo.getOrderAuditBelongto()).equals("0")?"委托ZIH":(orderInfo.getOrderAuditBelongto()).equals("1")?"自备":(orderInfo.getOrderAuditBelongto()).equals("2")?"自备铁路箱":"自备非铁路箱");
            }else{
                params.put("orderAuditBelongto","暂无");

            }
            params.put("clientTjr",StringUtils.isNotEmpty(orderInfo.getClientTjr())?orderInfo.getClientTjr():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getSitecost())){
                if(StringUtils.isNotEmpty(orderInfo.getSitecostCurrency())){
                    params.put("sitecost",orderInfo.getSitecost()+orderInfo.getSitecostCurrency());
                }else{
                    params.put("sitecost",orderInfo.getSitecost());
                }
            }else{
                params.put("sitecost","暂无");
            }
            //订舱方
            params.put("clientUnit",StringUtils.isNotEmpty(orderInfo.getClientUnit())?orderInfo.getClientUnit():"暂无");
            params.put("clientAddress",StringUtils.isNotEmpty(orderInfo.getClientAddress())?orderInfo.getClientAddress():"暂无");
            params.put("clientContacts",StringUtils.isNotEmpty(orderInfo.getClientContacts())?orderInfo.getClientContacts():"暂无");
            params.put("clientTel",StringUtils.isNotEmpty(orderInfo.getClientTel())?orderInfo.getClientTel():"暂无");
            params.put("clientEmail",StringUtils.isNotEmpty(orderInfo.getClientEmail())?orderInfo.getClientEmail():"暂无");
            params.put("containerBoxamount",StringUtils.isNotEmpty(orderInfo.getContainerBoxamount())?orderInfo.getContainerBoxamount():"暂无");
            params.put("containerType",containerType);
            if(StringUtils.isNotEmpty(orderInfo.getGoodsCannotpileup())){
                params.put("goodsCannotpileup",(orderInfo.getGoodsCannotpileup()).equals("1")?"是":"否");
            }else{
                params.put("goodsCannotpileup","暂无");
            }
            if(StringUtils.isNotEmpty(orderInfo.getGoodsFragile())){
                params.put("goodsFragile",(orderInfo.getGoodsFragile()).equals("1")?"是":"否");
            }else{
                params.put("goodsFragile","暂无");
            }
            if(StringUtils.isNotEmpty(orderInfo.getGoodsGeneral())){
                params.put("goodsGeneral",(orderInfo.getGoodsGeneral()).equals("1")?"是":"否");
            }else{
                params.put("goodsGeneral","暂无");
            }
            params.put("clientOrderBindingaddress",StringUtils.isNotEmpty(orderInfo.getClientOrderBindingaddress())?orderInfo.getClientOrderBindingaddress():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getClientOrderBindingway())){
                params.put("clientOrderBindingway",(orderInfo.getClientOrderBindingway()).equals("0")?"是":"否");
            }else{
                params.put("clientOrderBindingway","暂无");
            }
            params.put("clientBgCost",StringUtils.isNotEmpty(orderInfo.getClientBgCost())?orderInfo.getClientBgCost():"暂无");
            params.put("clientOrderRemarks",StringUtils.isNotEmpty(orderInfo.getClientOrderRemarks())?orderInfo.getClientOrderRemarks():"暂无");
            //发货方
            params.put("shipOrederName",StringUtils.isNotEmpty(orderInfo.getShipOrederName())?orderInfo.getShipOrederName():"暂无");
            params.put("shipOrederContacts",StringUtils.isNotEmpty(orderInfo.getShipOrederContacts())?orderInfo.getShipOrederContacts():"暂无");
            params.put("shipOrederPhone",StringUtils.isNotEmpty(orderInfo.getShipOrederPhone())?orderInfo.getShipOrederPhone():"暂无");
            params.put("shipOrederEmail",StringUtils.isNotEmpty(orderInfo.getShipOrederEmail())?orderInfo.getShipOrederEmail():"暂无");
            params.put("shipOrederAddress",StringUtils.isNotEmpty(orderInfo.getShipOrederAddress())?orderInfo.getShipOrederAddress():"暂无");
            if(classEastandwest.equals("0")){  //去程
                params.put("shipFhSite",StringUtils.isNotEmpty(orderInfo.getShipHyd())?orderInfo.getShipHyd():"暂无");
            }
            if(classEastandwest.equals("1")){ //回程
                params.put("shipFhSite",StringUtils.isNotEmpty(orderInfo.getShipFhSite())?orderInfo.getShipFhSite():"暂无");
            }
            if(StringUtils.isNotEmpty(orderInfo.getShipOrderBinningway())){
                params.put("shipOrderBinningway",(orderInfo.getShipOrderBinningway()).equals("0")?"委托ZIH提货":"自送货");
            }else{
                params.put("shipOrderBinningway","暂无");
            }
            if(StringUtils.isNotEmpty(orderInfo.getShipOrderBinningway())){
                if((orderInfo.getShipOrderBinningway()).equals("0")){  //委托zih
                    if(StringUtils.isNotEmpty(orderInfo.getShipThTypeId())){
                        params.put("shipZsType",(orderInfo.getShipThTypeId()).equals("0")?"整箱到车站":"散货到堆场");
                    }else{
                        params.put("shipZsType","暂无");
                    }
                    params.put("shipOrderUnloadtime",StringUtils.isNotNull(orderInfo.getShipOrderUnloadtime())?sdftime.format(orderInfo.getShipOrderUnloadtime()):"暂无");
                    params.put("shipOrderUnloadcontacts",StringUtils.isNotEmpty(orderInfo.getShipOrderUnloadcontacts())?orderInfo.getShipOrderUnloadcontacts():"暂无");
                    params.put("shipOrderUnloadway",StringUtils.isNotEmpty(orderInfo.getShipOrderUnloadway())?orderInfo.getShipOrderUnloadway():"暂无");
                    params.put("shipOrderUnloadaddress",StringUtils.isNotEmpty(orderInfo.getShipOrderUnloadaddress())?orderInfo.getShipOrderUnloadaddress()+orderInfo.getDetailedAddress():"暂无");
                    params.put("shipOrderSendtime","暂无");
                }
                if((orderInfo.getShipOrderBinningway()).equals("1")){  //自送货
                    if(StringUtils.isNotEmpty(orderInfo.getShipZsTypeId())){
                        params.put("shipZsType",(orderInfo.getShipZsTypeId()).equals("0")?"散货到堆场":"整箱到车站");
                    }else{
                        params.put("shipZsType","暂无");
                    }
                    params.put("shipOrderUnloadtime","暂无");
                    params.put("shipOrderUnloadcontacts","暂无");
                    params.put("shipOrderUnloadway","暂无");
                    params.put("shipOrderUnloadaddress","暂无");
                    params.put("shipOrderSendtime",StringUtils.isNotNull(orderInfo.getShipOrderSendtime())?sdftime.format(orderInfo.getShipOrderSendtime()):"暂无");
                }
            }

            if(StringUtils.isNotEmpty(orderInfo.getShipThCost())){
                if(StringUtils.isNotEmpty(orderInfo.getZxThcostCurrency())){
                    params.put("shipThCost",orderInfo.getShipThCost()+((orderInfo.getZxThcostCurrency()).equals("0")?"美金":"人民币"));
                }else{
                    params.put("shipThCost",orderInfo.getShipThCost());
                }
            }else{
                params.put("shipThCost","暂无");
            }
            params.put("shipThCostNo",StringUtils.isNotEmpty(orderInfo.getShipThCostNo())?orderInfo.getShipThCostNo():"暂无");
            //收货方
            params.put("receiveOrderName",StringUtils.isNotEmpty(orderInfo.getReceiveOrderName())?orderInfo.getReceiveOrderName():"暂无");
            params.put("receiveOrderMail",StringUtils.isNotEmpty(orderInfo.getReceiveOrderMail())?orderInfo.getReceiveOrderMail():"暂无");
            params.put("receiveOrderContacts",StringUtils.isNotEmpty(orderInfo.getReceiveOrderContacts())?orderInfo.getReceiveOrderContacts():"暂无");
            params.put("receiveOrderPhone",StringUtils.isNotEmpty(orderInfo.getReceiveOrderPhone())?orderInfo.getReceiveOrderPhone():"暂无");
            params.put("receiveOrderEnName",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEnName())?orderInfo.getReceiveOrderEnName():"暂无");
            params.put("receiveOrderEnContacts",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEnContacts())?orderInfo.getReceiveOrderEnContacts():"暂无");
            params.put("receiveOrderEnAddress",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEnAddress())?orderInfo.getReceiveOrderEnAddress():"暂无");
            params.put("receiveOrderEnEmail",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEnEmail())?orderInfo.getReceiveOrderEnEmail():"暂无");
            params.put("receiveOrderEname",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEname())?orderInfo.getReceiveOrderEname():"暂无"); //公司名称 俄线
            params.put("receiveOrderEcontacts",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEcontacts())?orderInfo.getReceiveOrderEcontacts():"暂无");
            params.put("receiveOrderEaddress",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEaddress())?orderInfo.getReceiveOrderEaddress():"暂无");
            params.put("receiveOrderEemail",StringUtils.isNotEmpty(orderInfo.getReceiveOrderEemail())?orderInfo.getReceiveOrderEemail():"暂无");
            params.put("receiveHyd",StringUtils.isNotEmpty(orderInfo.getReceiveHyd())?orderInfo.getReceiveHyd():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getReceiveOrderIsclearance())){
                params.put("receiveOrderIsclearance",(orderInfo.getReceiveOrderIsclearance()).equals("1")?"是" : "否");
            }else{
                params.put("receiveOrderIsclearance","暂无");
            }
            if(StringUtils.isNotEmpty(orderInfo.getReceiveQgCost())){
                params.put("receiveQgCost",orderInfo.getReceiveQgCost()+orderInfo.getQgCostcurrency());
            }else{
                params.put("receiveQgCost","暂无");
            }
            params.put("receiveOrderAddress",StringUtils.isNotEmpty(orderInfo.getReceiveOrderAddress())?orderInfo.getReceiveOrderAddress():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getReceiveOrderIspart())){
                params.put("receiveOrderIspart",(orderInfo.getReceiveOrderIspart()).equals("1")?"是" : "否");
            }else{
                params.put("receiveOrderIspart","暂无");
            }
            params.put("receiveOrderZihcontacts",StringUtils.isNotEmpty(orderInfo.getReceiveOrderZihcontacts())?orderInfo.getReceiveOrderZihcontacts():"暂无");
            params.put("receiveOrderZihtel",StringUtils.isNotEmpty(orderInfo.getReceiveOrderZihtel())?orderInfo.getReceiveOrderZihtel():"暂无");
            params.put("receiveOrderPartaddress",StringUtils.isNotEmpty(orderInfo.getReceiveOrderPartaddress())?orderInfo.getReceiveOrderPartaddress()+orderInfo.getDetailedAddress():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getReceiveShCost())){
                if(StringUtils.isNotEmpty(orderInfo.getShCostcurrency())){
                    params.put("receiveShCost",orderInfo.getReceiveShCost()+orderInfo.getShCostcurrency());
                }else{
                    params.put("receiveShCost",orderInfo.getReceiveShCost());
                }
            }else{
                params.put("receiveShCost","暂无");
            }
            params.put("receiveShCostId",StringUtils.isNotEmpty(orderInfo.getReceiveShCostId())?orderInfo.getReceiveShCostId():"暂无");
            String xgCost = "";
            if(StringUtils.isNotEmpty(orderInfo.getPickUpBoxFee())){  //提箱费
                xgCost = xgCost+"提箱费:"+orderInfo.getPickUpBoxFee()+";";
            }
            if(StringUtils.isNotEmpty(orderInfo.getReturnBoxFee())){ //还箱费
                xgCost = xgCost+"还箱费:"+orderInfo.getReturnBoxFee();
            }
            params.put("receiveXgCost",StringUtils.isNotEmpty(xgCost)?xgCost:"暂无"); //箱管费
            params.put("receiveHxAddress",StringUtils.isNotEmpty(orderInfo.getReceiveHxAddress())?orderInfo.getReceiveHxAddress():"暂无");
            params.put("receiveOrderReceiveemail",StringUtils.isNotEmpty(orderInfo.getReceiveOrderReceiveemail())?orderInfo.getReceiveOrderReceiveemail():"暂无");
            params.put("receiveOrderMail",StringUtils.isNotEmpty(orderInfo.getReceiveOrderMail())?orderInfo.getReceiveOrderMail():"暂无");
            params.put("etxName",StringUtils.isNotEmpty(orderInfo.getEtxName())?orderInfo.getEtxName():"暂无");
            params.put("eduty",StringUtils.isNotEmpty(orderInfo.getEduty())?orderInfo.getEduty():"暂无");
            params.put("电话","11");
            params.put("邮箱","11");
            //货物
            params.put("goodsMark",StringUtils.isNotEmpty(orderInfo.getGoodsMark())?orderInfo.getGoodsMark():"暂无");
            params.put("goodsName",StringUtils.isNotEmpty(orderInfo.getGoodsName())?orderInfo.getGoodsName():"暂无");
            params.put("goodsEnName",StringUtils.isNotEmpty(orderInfo.getGoodsEnName())?orderInfo.getGoodsEnName():"暂无");
            if(StringUtils.isNotEmpty(classEastandwest)){
                if(classEastandwest.equals("0")){ //西向
                    params.put("goodsReportTitle","国内报关HS");
                    params.put("goodsClearanceTitle","国外清关HS");
                    params.put("goodsReport",StringUtils.isNotEmpty(orderInfo.getGoodsInReport())?orderInfo.getGoodsInReport():"暂无");
                    params.put("goodsClearance",StringUtils.isNotEmpty(orderInfo.getGoodsOutClearance())?orderInfo.getGoodsOutClearance():"暂无");
                }
                if(classEastandwest.equals("1")){ //东向
                    params.put("goodsReportTitle","国外报关HS");
                    params.put("goodsClearanceTitle","国内清关HS");
                    params.put("goodsReport",StringUtils.isNotEmpty(orderInfo.getGoodsReport())?orderInfo.getGoodsReport():"暂无");
                    params.put("goodsClearance",StringUtils.isNotEmpty(orderInfo.getGoodsClearance())?orderInfo.getGoodsClearance():"暂无");
                }
            }else{
                params.put("goodsReportTitle","报关HS");
                params.put("goodsClearanceTitle","清关HS");
                params.put("goodsReport","暂无");
                params.put("goodsClearance","暂无");
            }
            params.put("goodsPacking",StringUtils.isNotEmpty(orderInfo.getGoodsPacking())?orderInfo.getGoodsPacking():"暂无");
            params.put("goodsNumber",StringUtils.isNotEmpty(orderInfo.getGoodsNumber())?orderInfo.getGoodsNumber():"暂无");
            params.put("goodsCbm",StringUtils.isNotEmpty(orderInfo.getGoodsCbm())?orderInfo.getGoodsCbm():"暂无");
            params.put("goodsStandard",StringUtils.isNotEmpty(orderInfo.getGoodsStandard())?orderInfo.getGoodsStandard():"暂无");
            params.put("goodsKgs",StringUtils.isNotEmpty(orderInfo.getGoodsKgs())?orderInfo.getGoodsKgs():"暂无");
            if(StringUtils.isNotEmpty(orderInfo.getGoodsIsscheme())){
                params.put("goodsIsscheme",(orderInfo.getGoodsIsscheme()).equals("0")?"否" : "是");
            }else{
                params.put("goodsIsscheme","暂无");
            }
            if(StringUtils.isNotEmpty(orderInfo.getShipOrderIsdispatch())){
                params.put("shipOrderIsdispatch",(orderInfo.getShipOrderIsdispatch()).equals("1")?"是" : "否");
            }else{
                params.put("shipOrderIsdispatch","暂无");
            }
            params.put("shipJzCost",StringUtils.isNotEmpty(orderInfo.getShipJzCost())?orderInfo.getShipJzCost():"暂无");
            params.put("remark",StringUtils.isNotEmpty(orderInfo.getRemark())?orderInfo.getRemark():"暂无");
            if("1".equals(isconsolidation)){  //拼箱
                InputStream is = this.getClass().getResourceAsStream("/templates/orderexport/px.docx");
                result = ExportWordUtils.exportWord(is,"D:/wordexport",fileName,params,request,response);
            }
            if("0".equals(isconsolidation) && "1".equals(classEastandwest)){  //东向整柜
                InputStream is = this.getClass().getResourceAsStream("/templates/orderexport/eastzg.docx");
                result = ExportWordUtils.exportWord(is,"D:/wordexport",fileName,params,request,response);
            }
            if("0".equals(isconsolidation) && "0".equals(classEastandwest)){  //西向整柜
                InputStream is = this.getClass().getResourceAsStream("/templates/orderexport/westzg.docx");
                result = ExportWordUtils.exportWord(is,"D:/wordexport",fileName,params,request,response);
            }
        }
        return result;
    }

    /**
     * 委托书汇总订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:shippingorder:listCollect')")
    @GetMapping("/listCollect")
    @ApiOperation("委托书汇总订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "登录者部门编号",name = "deptCode",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "登录者ID",name = "userid",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo listCollect(OrderImportList busiShippingorder)
    {
        startPage();
        List<OrderImportList> list;
        if(StringUtils.isEmpty(busiShippingorder.getOrderNumber()) && StringUtils.isEmpty(busiShippingorder.getClassNumber()) && StringUtils.isNull(busiShippingorder.getClassDate()) && StringUtils.isNull(busiShippingorder.getClassDateStart())
        && StringUtils.isNull(busiShippingorder.getClassDateEnd()) && StringUtils.isEmpty(busiShippingorder.getOrderClassBh()) && StringUtils.isEmpty(busiShippingorder.getOrderUploadcode())
        && StringUtils.isEmpty(busiShippingorder.getOrderUnloadcode()) && StringUtils.isEmpty(busiShippingorder.getLineTypeid()) && StringUtils.isEmpty(busiShippingorder.getIsexamline())
        && StringUtils.isEmpty(busiShippingorder.getIsconsolidation()) && StringUtils.isEmpty(busiShippingorder.getClassEastandwest()) && StringUtils.isEmpty(busiShippingorder.getStation()) && StringUtils.isEmpty(busiShippingorder.getOrderMerchandiser())
        && StringUtils.isEmpty(busiShippingorder.getOrderMerchandiserNumber()) && StringUtils.isEmpty(busiShippingorder.getClientTjr()) && StringUtils.isEmpty(busiShippingorder.getClientUnit())
        && StringUtils.isEmpty(busiShippingorder.getShipOrederName()) && StringUtils.isEmpty(busiShippingorder.getReceiveOrderName()) && StringUtils.isEmpty(busiShippingorder.getGoodsName())
        && StringUtils.isEmpty(busiShippingorder.getShipOrderBinningway()) && StringUtils.isEmpty(busiShippingorder.getReceiveOrderIspart()) && StringUtils.isNull(busiShippingorder.getPutoffClass())
        && StringUtils.isEmpty(busiShippingorder.getShipFhSite()) && StringUtils.isEmpty(busiShippingorder.getReceiveHxAddress()) && StringUtils.isEmpty(busiShippingorder.getYuyan())){
            list = new ArrayList<>();
        }else{
            if(StringUtils.isNotEmpty(busiShippingorder.getOrderNumber())){
                busiShippingorder.setOrderNumber((busiShippingorder.getOrderNumber()).trim());
            }
            busiShippingorder.setType("0");//列表查询
            list = busiShippingorderService.selectOrderImportList(busiShippingorder);
            for(OrderImportList orderItem:list){
                //推荐人
                String clientTjr = orderItem.getClientTjr();
                if(StringUtils.isNotEmpty(clientTjr)){
                    if(clientTjr.contains("/")){
                        clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                    }
                    orderItem.setClientTjr(clientTjr);
                }
                //20尺箱算半个
                if("0".equals(orderItem.getIsconsolidation())){
                    String boxType = orderItem.getContainerType();
                    String boxAmount = orderItem.getContainerBoxamount();
                    if(StringUtils.isNotEmpty(boxType) && StringUtils.isNotEmpty(boxAmount)){
                        if(boxType.contains("20")){
                            Double boxAmountI = Double.valueOf(boxAmount)/2;
                            orderItem.setContainerBoxamount(String.valueOf(boxAmountI));
                        }
                    }
                }
                //箱型
                String containerType = "";
                if(StringUtils.isNotEmpty(orderItem.getContainerType())){
                    containerType = commonService.containerTypeName(orderItem.getContainerType());
                    if(StringUtils.isNotEmpty(containerType)){
                        orderItem.setContainerType(containerType);
                    }
                }
                //分拨方式
                if("1".equals(orderItem.getReceiveOrderIspart())){
                    String distributionType = orderItem.getDistributionType();
                    distributionType = StringUtils.isEmpty(distributionType)?"":"0".equals(distributionType)?"整柜派送":"散货派送";
                    orderItem.setDistributionType(distributionType);
                }else{
                    orderItem.setDistributionType("");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 委托书汇总订单列表导出
     */
    @PreAuthorize("@ss.hasPermi('order:shippingorder:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OrderImportList busiShippingorder)
    {
        List<OrderImportList> list = null;
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
            if(StringUtils.isNotEmpty(busiShippingorder.getOrderNumber())){
                busiShippingorder.setOrderNumber((busiShippingorder.getOrderNumber()).trim());
            }
            busiShippingorder.setType("1");//导出查询
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String classNumber = "";  //班列号
            String classDate = "";   //班列日期
            list = busiShippingorderService.selectOrderImportList(busiShippingorder);
            if(list.size()>0){
                OrderImportList selectOne = list.get(0);
                classNumber = selectOne.getClassNumber();
                if(StringUtils.isNotEmpty(classNumber) && classNumber.contains("/")){
                    classNumber = StringUtils.replace(classNumber,"/","-");
                }
                classDate = StringUtils.isNotNull(selectOne.getClassDate())? dateFormat.format(selectOne.getClassDate()):"";
            }
            for(OrderImportList orderItem:list){
                String classEastandwest = orderItem.getClassEastandwest(); //0去程 1回程
                String isconsolidation = orderItem.getIsconsolidation(); //0整柜 1拼箱
                //托书状态
                String isexamline = orderItem.getIsexamline();
                if(StringUtils.isNotEmpty(isexamline)){
                    isexamline = "0".equals(isexamline)?"待审核":"1".equals(isexamline)?"审核通过":"2".equals(isexamline)?"审核失败":"3".equals(isexamline)?"取消委托":"4".equals(isexamline)?"转待审核中":"5".equals(isexamline)?"草稿":"6".equals(isexamline)?"转待审核失败":"7".equals(isexamline)?"箱管部审核中": "8".equals(isexamline)?"箱管部审核失败":"9".equals(isexamline)? "报价中":"10".equals(isexamline)? "客户确认中":"11".equals(isexamline)?"公路审核中":"12".equals(isexamline)?"集疏审核中":"13".equals(isexamline)?"撤舱审核中":"14".equals(isexamline)?"订舱报价中":"订舱确认中";
                }
                orderItem.setIsexamline(isexamline);
                //20尺箱算半个
                if("0".equals(orderItem.getIsconsolidation())){
                    String boxType = orderItem.getContainerType();
                    String boxAmount = orderItem.getContainerBoxamount();
                    if(StringUtils.isNotEmpty(boxType) && StringUtils.isNotEmpty(boxAmount)){
                        if(boxType.contains("20")){
                            Double boxAmountI = Double.valueOf(boxAmount)/2;
                            orderItem.setContainerBoxamount(String.valueOf(boxAmountI));
                        }
                    }
                }
                //箱型
                String containerType = "";
                if(StringUtils.isNotEmpty(orderItem.getContainerType())){
                    containerType = commonService.containerTypeName(orderItem.getContainerType());
                    if(StringUtils.isNotEmpty(containerType)){
                        orderItem.setContainerType(containerType);
                    }
                }
                //拼箱箱量
                if("1".equals(orderItem.getIsconsolidation())){
                    orderItem.setContainerBoxamount("0");
                }
                //提货地、送货地详细地址
                if("1".equals(classEastandwest)){ //回程
                    orderItem.setDetailedAddressFb(orderItem.getDetailedAddress());
                    orderItem.setDetailedAddress("");
                }
                //提货费、送货费,铁路运费
                if(StringUtils.isNotEmpty(orderItem.getShipThCost())){
                    orderItem.setShipThCost(orderItem.getShipThCost()+orderItem.getZxThcostCurrency());
                }
                if(StringUtils.isNotEmpty(orderItem.getReceiveShCost())){
                    orderItem.setReceiveShCost(orderItem.getReceiveShCost()+orderItem.getShCostcurrency());
                }
                if(StringUtils.isNotEmpty(orderItem.getSitecost())){
                    orderItem.setSitecost(orderItem.getSitecost()+orderItem.getSitecostCurrency());
                }
                //提货时间 装箱方式
                if(StringUtils.isNotEmpty(orderItem.getShipOrderBinningway())){
                    if(!"0".equals(orderItem.getShipOrderBinningway())){
                        orderItem.setShipOrderUnloadtime(orderItem.getShipOrderSendtime());
                    }
                    if((!"0".equals(orderItem.getShipOrderBinningway())) && "0".equals(isconsolidation)){ //自送货且是整柜
                        if(StringUtils.isNotEmpty(orderItem.getShipZsTypeId())){
                            orderItem.setShipThTypeId("0".equals(orderItem.getShipZsTypeId())?"1":"0");
                        }else{
                            orderItem.setShipThTypeId("");
                        }
                    }
                }
                //箱管费用
                String xgCost = "";
                if(StringUtils.isNotEmpty(orderItem.getPickUpBoxFee())){
                    xgCost += orderItem.getPickUpBoxFee();
                }
                if(StringUtils.isNotEmpty(orderItem.getReturnBoxFee())){
                    if(StringUtils.isNotEmpty(xgCost)){
                        xgCost += "+";
                    }
                    xgCost += orderItem.getReturnBoxFee();
                }
                orderItem.setPickUpBoxFee(xgCost);
                //推荐人
                String clientTjr = orderItem.getClientTjr();
                if(StringUtils.isNotEmpty(clientTjr)){
                    if(clientTjr.contains("/")){
                        clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                    }
                    if(clientTjr.contains("-")){
                        clientTjr = clientTjr.substring(clientTjr.lastIndexOf("-")+1);
                    }
                    orderItem.setClientTjr(clientTjr);
                }
                //分拨方式
                if("1".equals(orderItem.getReceiveOrderIspart())){
                    String distributionType = orderItem.getDistributionType();
                    distributionType = StringUtils.isEmpty(distributionType)?"":"0".equals(distributionType)?"整柜派送":"散货派送";
                    orderItem.setDistributionType(distributionType);
                }else{
                    orderItem.setDistributionType("");
                }
                //转待次数
                Long zdCount = StringUtils.isNotNull(orderItem.getTotalturncountavg())?orderItem.getTotalturncountavg():0;
                orderItem.setTotalturncountavg(zdCount);
            }
            ExcelUtil<OrderImportList> util = new ExcelUtil<OrderImportList>(OrderImportList.class);
            return util.exportExcel(list, classNumber+"班列客户托书信息汇总表"+classDate);
        }
    }

    /**
     * 委托书汇总随车托书导出
     */
    @PreAuthorize("@ss.hasPermi('order:shippingorder:scExport')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/scExport")
    public AjaxResult scExport(HttpServletResponse response,OrderImportList busiShippingorder){
        List<OrderImportList> list = null;
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
            String[] colNames = {"ZIH推荐人","订舱方","发货方","装箱方式","装箱方式","收货方","上货站","下货站","箱量","箱型","货物中文品名","货物英文品名","上货站报关HS","下货站清关HS","最外层包装形式","最外层包装数量","总体积","总毛重","委托书编号","跟单员(报关员)","特殊要求备注"};
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String classNumber = "";  //班列号
            String classDate = "";   //班列日期
            if(StringUtils.isNotEmpty(busiShippingorder.getOrderNumber())){
                busiShippingorder.setOrderNumber((busiShippingorder.getOrderNumber()).trim());
            }
            busiShippingorder.setType("1");//导出查询
            list = busiShippingorderService.selectOrderImportList(busiShippingorder);
            if(list.size()<1){
                return AjaxResult.error("未查询到数据");
            }else{
                OrderImportList selectOne = list.get(0);
                classNumber = selectOne.getClassNumber();
                classDate = StringUtils.isNotNull(selectOne.getClassDate())? dateFormat.format(selectOne.getClassDate()):"";
                List<String[]> listCells = new ArrayList<String[]>();
                for(OrderImportList orderItem:list){
                    String[] cells = new String[21];
                    //推荐人
                    String clientTjr = orderItem.getClientTjr();
                    if(StringUtils.isNotEmpty(clientTjr)){
                        if(clientTjr.contains("/")){
                            clientTjr=clientTjr.substring(0,clientTjr.indexOf("/"));
                        }
                    }
                    cells[0] = clientTjr;
                    cells[1] = orderItem.getClientUnit(); //订舱方
                    cells[2] = orderItem.getShipOrederName(); //发货方
                    //装箱方式
                    String binningway = orderItem.getShipOrderBinningway();
                    String shipThTypeId = orderItem.getShipThTypeId(); //委托陆港 0整箱到车站,1散货到堆场
                    String shipZsTypeId = orderItem.getShipZsTypeId(); //自送货 0散货到堆场，1整箱到车站
                    binningway = StringUtils.isEmpty(binningway)?"":"0".equals(binningway)?"委托ZIH提货":"自送货";
                    cells[3] = binningway;
                    if("0".equals(orderItem.getShipOrderBinningway())){
                        shipThTypeId = StringUtils.isEmpty(shipThTypeId)?"":"0".equals(shipThTypeId)?"整箱到车站":"散货到堆场";
                        cells[4] = shipThTypeId;
                    }
                    if("1".equals(orderItem.getShipOrderBinningway())){
                        shipZsTypeId = StringUtils.isEmpty(shipZsTypeId)?"":"0".equals(shipZsTypeId)?"散货到堆场":"整箱到车站";
                        cells[4] = shipZsTypeId;
                    }
                    cells[5] = orderItem.getReceiveOrderName();  //收货方
                    cells[6] = orderItem.getOrderUploadsite(); //上货站
                    cells[7] = orderItem.getOrderUnloadsite(); //下货站
                    //箱型箱量
                    if("0".equals(orderItem.getIsconsolidation())){
                        String boxType = orderItem.getContainerType();
                        String boxAmount = orderItem.getContainerBoxamount();
                        if(StringUtils.isNotEmpty(boxType) && StringUtils.isNotEmpty(boxAmount)){
                            if(boxType.contains("20")){
                                Double boxAmountI = Double.valueOf(boxAmount)/2;
                                orderItem.setContainerBoxamount(String.valueOf(boxAmountI));
                                cells[8] = String.valueOf(boxAmountI);
                            }else{
                                cells[8] = orderItem.getContainerBoxamount();
                            }
                        }
                    }
                    if("1".equals(orderItem.getIsconsolidation())){
                        cells[8] = "0";
                    }
                    String containerType = orderItem.getContainerType();
                    if(StringUtils.isNotEmpty(containerType)){
                        containerType = commonService.containerTypeName(orderItem.getContainerType());
                        if(StringUtils.isNotEmpty(containerType)){
                            orderItem.setContainerType(containerType);
                        }
                    }
                    cells[9] = containerType;
                    cells[10] = orderItem.getGoodsName(); //货物中文品名
                    cells[11] = orderItem.getGoodsEnName(); //货物英文品名
                    cells[12] = orderItem.getGoodsReport(); //上货站报关HS
                    cells[13] = orderItem.getGoodsClearance(); //下货站清关hs
                    cells[14] = orderItem.getGoodsPacking(); //最外层包装形式
                    cells[15] = orderItem.getGoodsNumber(); //最外层包装数量
                    cells[16] = orderItem.getGoodsCbm(); //体积
                    cells[17] = orderItem.getGoodsKgs(); //重量
                    cells[18] = orderItem.getOrderNumber(); //委托书编号
                    cells[19] = orderItem.getOrderMerchandiser(); //跟单员
                    cells[20] = orderItem.getRemark(); //特殊要求备注
                    listCells.add(cells);
                }
                ExportExcel.arrageExcel(classNumber+"班列客户托书信息汇总表("+classDate+")",classNumber+"班列客户托书信息汇总表","托书汇总表",colNames, listCells, response);
                return AjaxResult.success();
            }
        }
    }




}
