package com.szhbl.project.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.order.domain.BusiShippingorderGoods;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.track.*;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiShippingorderGoodsMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.mapper.BusiZyInfoMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.mapper.BusiClassesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiGoodsTrackMapper;
import com.szhbl.project.order.service.IBusiGoodsTrackService;

/**
 * 运踪_货物状态Service业务层处理
 * 
 * @author dps
 * @date 2020-04-09
 */
@Slf4j
@Service
public class BusiGoodsTrackServiceImpl implements IBusiGoodsTrackService 
{
    @Autowired
    private BusiGoodsTrackMapper busiGoodsTrackMapper;

    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    @Autowired
    private BusiZyInfoMapper busiZyInfoMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BusiClassesMapper busiClassesMapper;

    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;

    @Autowired
    private BusiShippingorderGoodsMapper busiShippingorderGoodsMapper;

    /**
     * 订舱组进站查看—货物状态列表
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态
     */
    @Override
    public List<BusiGoodsTrack> selectBusiGoodsTrackList(BusiGoodsTrack busiGoodsTrack)
    {
        Date classDateEnd = busiGoodsTrack.getClassDateEnd(); //班列日期
        Date actualClassDateValueEnd = busiGoodsTrack.getActualClassDateValueEnd(); //发运日期
        if(StringUtils.isNotNull(classDateEnd)){
            busiGoodsTrack.setClassDateEnd(DateUtils.dataChangeAdd(classDateEnd,1));
        }
        if(StringUtils.isNotNull(actualClassDateValueEnd)){
            busiGoodsTrack.setActualClassDateValueEnd(DateUtils.dataChangeAdd(actualClassDateValueEnd,1));
        }
        //列表权限
        String readType = null;
        String deptCode = busiGoodsTrack.getDeptCode();  //登录者部门编号
        if(StringUtils.isNotEmpty(deptCode)){
            if (deptCode.contains("YWB")) {  //业务部账号登录
                readType = "3"; //默认按照自己部门编号模糊查询
                if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    readType = "4";
                }
                if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    readType = "5";
                }
                if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    readType = "6";
                }
                if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    readType = "7";
                }
                if (deptCode.length() > 15) {  //部门编号大于15位
                    readType = "8";   //业务部普通人员，根据自己推荐人id查询
                }
                busiGoodsTrack.setReadType(readType);
            }
        }
        return busiGoodsTrackMapper.selectBusiGoodsTrackList(busiGoodsTrack);
    }

    /**
     * 订舱组进站查看—箱量统计
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态
     */
    @Override
    public List<Map> selectAmount(BusiGoodsTrack busiGoodsTrack)
    {
        Date classDateEnd = busiGoodsTrack.getClassDateEnd(); //班列日期
        Date actualClassDateValueEnd = busiGoodsTrack.getActualClassDateValueEnd(); //发运日期
        if(StringUtils.isNotNull(classDateEnd)){
            busiGoodsTrack.setClassDateEnd(DateUtils.dataChangeAdd(classDateEnd,1));
        }
        if(StringUtils.isNotNull(actualClassDateValueEnd)){
            busiGoodsTrack.setActualClassDateValueEnd(DateUtils.dataChangeAdd(actualClassDateValueEnd,1));
        }
        //列表权限
        String readType = null;
        String deptCode = busiGoodsTrack.getDeptCode();  //登录者部门编号
        if(StringUtils.isNotEmpty(deptCode)){
            if (deptCode.contains("YWB")) {  //业务部账号登录
                readType = "3"; //默认按照自己部门编号模糊查询
                if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    readType = "4";
                }
                if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    readType = "5";
                }
                if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    readType = "6";
                }
                if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    readType = "7";
                }
                if (deptCode.length() > 15) {  //部门编号大于15位
                    readType = "8";   //业务部普通人员，根据自己推荐人id查询
                }
                busiGoodsTrack.setReadType(readType);
            }
        }
        return busiGoodsTrackMapper.selectAmount(busiGoodsTrack);
    }
    @Override
    public BusiGoodsTrack selectAmountVW(BusiGoodsTrack busiGoodsTrack)
    {
        Date classDateEnd = busiGoodsTrack.getClassDateEnd(); //班列日期
        Date actualClassDateValueEnd = busiGoodsTrack.getActualClassDateValueEnd(); //发运日期
        if(StringUtils.isNotNull(classDateEnd)){
            busiGoodsTrack.setClassDateEnd(DateUtils.dataChangeAdd(classDateEnd,1));
        }
        if(StringUtils.isNotNull(actualClassDateValueEnd)){
            busiGoodsTrack.setActualClassDateValueEnd(DateUtils.dataChangeAdd(actualClassDateValueEnd,1));
        }
        return busiGoodsTrackMapper.selectAmountVW(busiGoodsTrack);
    }

    /**
     * 订舱组进站查看———发运时间编辑
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    @Override
    public int updateBusiGoodsTrack(BusiGoodsTrack busiGoodsTrack)
    {
        int result = 0;
        try{
            Long[] idsArr = busiGoodsTrack.getIdsArr();
            Date actualClassDateValue = busiGoodsTrack.getActualClassDateValue(); //发运日期
            String isClassAdd = busiGoodsTrack.getIsClassAdd(); //是否加开
            if(StringUtils.isNotNull(actualClassDateValue)){
                //实际班列id
                BusiGoodsTrack actualClasses = busiGoodsTrackMapper.selectActualClassesId(busiGoodsTrack);
                if(StringUtils.isNotNull(actualClasses)){
                    busiGoodsTrack.setActualClassId(actualClasses.getActualClassId());
                }else{
                    return 2;
                }
                //获取发运日期拼接是否加开的字符串
                String mon=String .format("%tm", actualClassDateValue); //月份
                String day=String .format("%td", actualClassDateValue);
                String actualClassDate = mon+'.'+day+isClassAdd;
                busiGoodsTrack.setActualClassDate(actualClassDate);
                //发运状态 0未发运 1已发运
                busiGoodsTrack.setIsRun("1");
            }
            busiGoodsTrack.setUpdateTime(DateUtils.getNowDate());
            busiGoodsTrack.setActualClassDateValue(actualClassDateValue);
            busiGoodsTrack.setIsClassAdd(isClassAdd);
            int resultTrack = busiGoodsTrackMapper.updateTrackById(busiGoodsTrack);
            //更新中亚表列数
            BusiZyInfo zyadd = new BusiZyInfo();
            zyadd.setTrackIdsArr(idsArr);
            zyadd.setLieshu(busiGoodsTrack.getLieshu());
            zyadd.setCreateusername(busiGoodsTrack.getUpdateBy());
            zyadd.setCreatedate(DateUtils.getNowDate());
            int resultZy = busiZyInfoMapper.updateBusiZyInfoByTrackId(zyadd);
            //发送消息队列
            for(int i=0;i<idsArr.length;i++){
                GoodsTrackMq orderQuery = busiGoodsTrackMapper.selectOrderIdByTrackId(idsArr[i].intValue());
                if(StringUtils.isNotNull(orderQuery)){
                    commonService.trackInfoMQ(orderQuery.getOrderId(),orderQuery.getBoxNum());
                }
            }
            if(resultTrack>0 && resultZy>0){
                result = 1;
            }
        }catch (Exception e){
            log.error("发运时间编辑失败",e.toString(),e.getStackTrace());
        }
        return result;
    }

    /**
     * 订舱组进站查看—申请代码编辑
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    @Override
    public int updateApplyCode(BusiGoodsTrack busiGoodsTrack){
        busiGoodsTrack.setUpdateTime(DateUtils.getNowDate());
        return busiGoodsTrackMapper.updateBusiGoodsTrack(busiGoodsTrack);
    }

    /**
     * 订舱组进站查看—导出进站跟踪列表
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态
     */
    @Override
    public List<ExportTrack> selectExportTrackList(ExportTrack busiGoodsTrack)
    {
        Date classDateEnd = busiGoodsTrack.getClassDateEnd(); //班列日期
        Date actualClassDateValueEnd = busiGoodsTrack.getActualClassDateValueEnd(); //发运日期
        if(StringUtils.isNotNull(classDateEnd)){
            busiGoodsTrack.setClassDateEnd(DateUtils.dataChangeAdd(classDateEnd,1));
        }
        if(StringUtils.isNotNull(actualClassDateValueEnd)){
            busiGoodsTrack.setActualClassDateValueEnd(DateUtils.dataChangeAdd(actualClassDateValueEnd,1));
        }
        //列表权限
        String readType = null;
        String deptCode = busiGoodsTrack.getDeptCode();  //登录者部门编号
        if(StringUtils.isNotEmpty(deptCode)){
            if (deptCode.contains("YWB")) {  //业务部账号登录
                readType = "3"; //默认按照自己部门编号模糊查询
                if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    readType = "4";
                }
                if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    readType = "5";
                }
                if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    readType = "6";
                }
                if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    readType = "7";
                }
                if (deptCode.length() > 15) {  //部门编号大于15位
                    readType = "8";   //业务部普通人员，根据自己推荐人id查询
                }
                busiGoodsTrack.setReadType(readType);
            }
        }
        return busiGoodsTrackMapper.selectExportTrackList(busiGoodsTrack);
    }

    /**
     * 订舱组进站查看—导出进站跟踪列表(多联)
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态
     */
    @Override
    public List<ExportTrackDl> selectExportTrackDlList(ExportTrackDl busiGoodsTrack)
    {
        Date classDateEnd = busiGoodsTrack.getClassDateEnd(); //班列日期
        Date actualClassDateValueEnd = busiGoodsTrack.getActualClassDateValueEnd(); //发运日期
        if(StringUtils.isNotNull(classDateEnd)){
            busiGoodsTrack.setClassDateEnd(DateUtils.dataChangeAdd(classDateEnd,1));
        }
        if(StringUtils.isNotNull(actualClassDateValueEnd)){
            busiGoodsTrack.setActualClassDateValueEnd(DateUtils.dataChangeAdd(actualClassDateValueEnd,1));
        }
        //列表权限
        String readType = null;
        String deptCode = busiGoodsTrack.getDeptCode();  //登录者部门编号
        if(StringUtils.isNotEmpty(deptCode)){
            if (deptCode.contains("YWB")) {  //业务部账号登录
                readType = "3"; //默认按照自己部门编号模糊查询
                if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    readType = "4";
                }
                if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    readType = "5";
                }
                if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    readType = "6";
                }
                if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    readType = "7";
                }
                if (deptCode.length() > 15) {  //部门编号大于15位
                    readType = "8";   //业务部普通人员，根据自己推荐人id查询
                }
                busiGoodsTrack.setReadType(readType);
            }
        }
        return busiGoodsTrackMapper.selectExportTrackDlList(busiGoodsTrack);
    }

    /**
     * 订舱组进站查看—发运时间导入
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    @Override
    public int updateTrackTime(ImportTrackTime busiGoodsTrack)
    {
        busiGoodsTrack.setUpdateTime(DateUtils.getNowDate());
        return busiGoodsTrackMapper.updateTrackTime(busiGoodsTrack);
    }

    /**
     * 订舱组进站查看/订舱组界面—删除货物状态
     *
     * @param idsArr 需要删除的运踪_货物状态ID
     * @return 结果
     */
    @Override
    public int deleteBusiGoodsTrackByIds(Long[] idsArr)
    {
        int result = 1;
        //更新货物状态表删除状态，同时删除中亚表数据
        if(idsArr.length>0){
            for(int i=0;i<idsArr.length;i++){
                Long id = idsArr[i];
                OrderGoodsTrackDel trackInfo = busiGoodsTrackMapper.selectOrderIdByTrack(id.intValue());//查询删除记录的托书id和箱号
                int deltrack = busiGoodsTrackMapper.deleteBusiGoodsTrackByIdUpd(id.intValue());
                if(deltrack==1){
                    busiZyInfoMapper.deleteZyInfoByTrackId(id.intValue());
                    //发送消息队列
                    if(StringUtils.isNotNull(trackInfo)){
                        try {
                            commonService.orderTrackDeleteMQ(trackInfo,"0");
                        } catch (JsonProcessingException e) {
                            log.error("删除推送失败"+id,e.toString(),e.getStackTrace());
                        }
                    }
                }else{
                    result = 0;
                }
            }
        }
        return result;
    }

    /**
     * 订舱组进站查看/订舱组界面—货物状态详细信息
     * 
     * @param id 运踪_货物状态ID
     * @return 运踪_货物状态
     */
    @Override
    public BusiGoodsTrack selectBusiGoodsTrackById(Integer id)
    {
        return busiGoodsTrackMapper.selectBusiGoodsTrackById(id);
    }
    @Override
    public ImportTrackTime selectBusiGoodsTrackByIdImport(String orderNumber)
    {
        return busiGoodsTrackMapper.selectBusiGoodsTrackByIdImport(orderNumber);
    }

    /**
     * 订舱组进站查看/订舱组界面—班列号编辑/订舱备注编辑
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    @Override
    public int updateClassBhAdd(BusiGoodsTrack busiGoodsTrack){
        int result = 1;
        try{
            Long[] idsArr = busiGoodsTrack.getIdsArr();
            String classzyNo = busiGoodsTrack.getClasszyNo(); //班列号
            if(idsArr.length>0){
                for(int i=0;i<idsArr.length;i++){
                    Long trackId = idsArr[i];
                    BusiZyInfo zyinfo = busiZyInfoMapper.selectBusiZyInfoByTrack(trackId);
                    if(StringUtils.isNotNull(zyinfo)){
                        String lineTypeId = zyinfo.getLineTypeid(); //0中欧 2中亚 3中越 4中俄
                        BusiZyInfo zyupd = new BusiZyInfo();
                        String classzyNoBack = zyinfo.getClasszyNo();
                        if(StringUtils.isNotNull(zyinfo.getClasszynoTime())){
                            if(!StringUtils.equals(classzyNo,classzyNoBack)){
                                String record = "";
                                record = "<th>修改人："+busiGoodsTrack.getUpdateBy()+",修改时间："+DateUtils.parseStr(DateUtils.getNowDate())+"</th>";
                                if("2".equals(lineTypeId)){
                                    record = record + "班列号由" +classzyNoBack+ "改为：" +classzyNo+ "<###>";
                                }else{
                                    record = record + "订舱组备注由" +classzyNoBack+ "改为：" +classzyNo+ "<###>";
                                }
                                record = StringUtils.replace(record,"null","空");
                                zyupd.setClasszynoRemark(record);
                            }
                        }
                        zyupd.setClasszyNo(classzyNo);
                        if(StringUtils.isEmpty(classzyNo)){
                            zyupd.setClasszyNoS("1");
                        }
                        zyupd.setClasszynoTime(DateUtils.getNowDate());
                        zyupd.setCreateusername(busiGoodsTrack.getUpdateBy());
                        zyupd.setCreatedate(DateUtils.getNowDate());
                        zyupd.setOrderId(zyinfo.getOrderId());
                        zyupd.setXianghao(zyinfo.getXianghao());
                        int resultZy = busiZyInfoMapper.updateBusiZyInfoByTrackOrder(zyupd);
                        if(resultZy==0){
                            return 0;
                        }
                        //推送消息队列
                        commonService.trackInfoMQ(zyinfo.getOrderId(),zyinfo.getXianghao());
                    }
                }
            }else{
                result=0;
            }
        }catch (Exception e){
            log.error("班列号编辑失败",e.toString(),e.getStackTrace());
        }
        return result;
    }

    /**
     * 订舱组进站查看/订舱组界面—发送消息队列
     */
    @Override
    public GoodsTrackMq selectTrackInfoToMq(String orderId,String boxNum){
        return busiGoodsTrackMapper.selectTrackInfoToMq(orderId,boxNum);
    }

    /**
     * 订舱组界面—货物状态列表
     */
    @Override
    public List<GoodsTrackDcz> selectBusiGoodsTrackDczList(GoodsTrackDcz busiGoodsTrack)
    {
        return busiGoodsTrackMapper.selectBusiGoodsTrackDczList(busiGoodsTrack);
    }

    /**
     * 订舱组界面—箱量统计
     */
    @Override
    public List<Map> selectAmountDcz(GoodsTrackDcz busiGoodsTrack)
    {
        return busiGoodsTrackMapper.selectAmountDcz(busiGoodsTrack);
    }
    @Override
    public List<Map> selectAmountVWDcz(GoodsTrackDcz busiGoodsTrack)
    {
        return busiGoodsTrackMapper.selectAmountVWDcz(busiGoodsTrack);
    }

    /**
     * 订舱组界面—舱位号批量修改
     */
    @Override
    public int orderBlEdit(BusiGoodsTrack busiGoodsTrack){
        int result = 1;
        try{
            String classBh = busiGoodsTrack.getClassBh();
            String[] orderIdsArr = busiGoodsTrack.getOrderIdsArr();
            if(orderIdsArr.length>0){
                BusiClasses classesInfo = busiClassesMapper.selectBusiClassesByBh(classBh);
                if(StringUtils.isNotNull(classesInfo)){
                    //托书修改内容
                    BusiShippingorders orderupd = new BusiShippingorders();
                    orderupd.setClassId(classesInfo.getClassId()); //班列id
                    orderupd.setClassDate(classesInfo.getClassStime()); //发车时间
                    orderupd.setOrderClassBh(classesInfo.getClassBh());//班列编号
                    orderupd.setClassNumber(classesInfo.getClassNumber());//班列号
                    orderupd.setCreatedate(DateUtils.getNowDate());//日期
                    orderupd.setCreateusername(busiGoodsTrack.getUpdateBy());
                    orderupd.setTjTime(DateUtils.getNowDate());
                    for(int i=0;i<orderIdsArr.length;i++){
                        String orderId = orderIdsArr[i];
                        //修改记录
                        BusiShippingorders orderInfo = busiShippingorderMapper.selectBusiShippingorderByIdOld(orderId);  //原托书信息
                        String title = "<th>修改人："+busiGoodsTrack.getUpdateBy()+",修改时间："+DateUtils.parseStr(DateUtils.getNowDate())+"</th>";
                        String editrecord = "";
                        if(StringUtils.isNotNull(orderInfo)){
                            if(!StringUtils.equals(DateUtils.parseStr(orderInfo.getClassDate()),DateUtils.parseStr(classesInfo.getClassStime()))){
                                editrecord = editrecord+"班列日期：由"+DateUtils.parseStr(orderInfo.getClassDate())+"修改为："+DateUtils.parseStr(classesInfo.getClassStime())+"<td>";
                            }
                            if(!StringUtils.equals(orderInfo.getOrderClassBh(),classesInfo.getClassBh())){
                                editrecord = editrecord+"班列编号：由"+orderInfo.getOrderClassBh()+"修改为"+classesInfo.getClassBh()+"<td>";
                            }
                            if(!StringUtils.equals(orderInfo.getClassNumber(),classesInfo.getClassNumber())){
                                editrecord = editrecord+"班列号：由"+orderInfo.getClassNumber()+"修改为"+classesInfo.getClassNumber()+"<td>";
                            }
                            if(StringUtils.isNotEmpty(editrecord)){
                                editrecord = title + editrecord +"<###>";
                            }
                        }
                        //更新托书
                        orderupd.setOrderId(orderId);
                        if(StringUtils.isNotEmpty(editrecord)){
                            orderupd.setIsupdate("1");
                        }
                        int orderResult = busiShippingorderMapper.updateBusiShippingorder(orderupd);
                        if(orderResult==1){
                            //更新修改记录
                            if(StringUtils.isNotEmpty(editrecord)){
                                BusiShippingorderGoods goodsinfo = busiShippingorderGoodsMapper.selectBusiShippingorderGoodsByOrderId(orderId);//商品表数据
                                if(StringUtils.isNotNull(goodsinfo)){
                                    String editrecordTotal = goodsinfo.getGoodsHistoryEditrecord()+editrecord;
                                    BusiShippingorderGoods goodsupd = new BusiShippingorderGoods();
                                    goodsupd.setOrderId(orderId);
                                    goodsupd.setGoodsHistoryEditrecord(editrecordTotal);
                                    busiShippingorderGoodsMapper.updateBusiShippingorderGoods(goodsupd);
                                }
                            }
                            //更新订舱组操作时间
                            BusiZyInfo zyupd = new BusiZyInfo();
                            zyupd.setOrderId(orderId);
                            zyupd.setCreatedate(DateUtils.getNowDate());
                            busiZyInfoMapper.updateZyInfoByOrder(zyupd);
                            //发送消息队列
                            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                            if(StringUtils.isNotNull(orderInfoRabbmq)){
                                String messagetype = "7";//托书修改
                                commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                            }
                        }else{
                            result = 0;
                        }
                    }
                }else{
                    result = 2; //班列不存在
                }
            }else{
                result = 0; //操作错误
            }
        }catch (Exception e){
            log.error("订舱组界面-舱位号编辑失败",e.toString(),e.getStackTrace());
        }
        return result;
    }










    /**
     * 新增运踪_货物状态
     * 
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    @Override
    public int insertBusiGoodsTrack(BusiGoodsTrack busiGoodsTrack)
    {
        busiGoodsTrack.setCreateTime(DateUtils.getNowDate());
        return busiGoodsTrackMapper.insertBusiGoodsTrack(busiGoodsTrack);
    }

    /**
     * 删除运踪_货物状态信息
     * 
     * @param id 运踪_货物状态ID
     * @return 结果
     */
    @Override
    public int deleteBusiGoodsTrackById(Integer id)
    {
        return busiGoodsTrackMapper.deleteBusiGoodsTrackById(id);
    }
}
