package com.szhbl.project.track.service.impl;

import java.util.*;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.email.MailUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.system.domain.EmailSet;
import com.szhbl.project.system.mapper.EmailSetMapper;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.mapper.TrackGoodsStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.track.mapper.TrackAbroadMapper;
import com.szhbl.project.track.domain.TrackAbroad;
import com.szhbl.project.track.service.ITrackAbroadService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 运踪_中亚境外Service业务层处理
 * 
 * @author lzd
 * @date 2020-03-26
 */
@Service
public class TrackAbroadServiceImpl implements ITrackAbroadService 
{
    @Autowired
    private TrackAbroadMapper trackAbroadMapper;
    @Autowired
    private TrackGoodsStatusMapper trackGoodsStatusMapper;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private EmailSetMapper emailSetMapper;

    /**
     * 查询运踪_中亚境外
     * 
     * @param id 运踪_中亚境外ID
     * @return 运踪_中亚境外
     */
    @Override
    public TrackAbroad selectTrackAbroadById(Integer id)
    {
        return trackAbroadMapper.selectTrackAbroadById(id);
    }

    /**
     * 查询运踪_中亚境外列表
     * 
     * @param trackAbroad 运踪_中亚境外
     * @return 运踪_中亚境外
     */
    @Override
    public List<TrackAbroad> selectTrackAbroadList(TrackAbroad trackAbroad)
    {
        return trackAbroadMapper.selectTrackAbroadList(trackAbroad);
    }

    /**
     * 新增运踪_中亚境外
     * 
     * @param trackAbroad 运踪_中亚境外
     * @return 结果
     */
   /* @Override
    public int insertTrackAbroad(TrackAbroad trackAbroad)
    {
        trackAbroad.setCreateTime(DateUtils.getNowDate());
        return trackAbroadMapper.insertTrackAbroad(trackAbroad);
    }*/

    /**
     * 修改运踪_中亚境外
     * 
     * @param trackAbroad 运踪_中亚境外
     * @return 结果
     */
    @Override
    public int updateTrackAbroad(TrackAbroad trackAbroad)
    {
        trackAbroad.setUpdateTime(DateUtils.getNowDate());
        return trackAbroadMapper.updateTrackAbroad(trackAbroad);
    }

    /**
     * 批量删除运踪_中亚境外
     * 
     * @param ids 需要删除的运踪_中亚境外ID
     * @return 结果
     */
    @Override
    public int deleteTrackAbroadByIds(Integer[] ids)
    {
        return trackAbroadMapper.deleteTrackAbroadByIds(ids);
    }

    /**
     * 删除运踪_中亚境外信息
     * 
     * @param id 运踪_中亚境外ID
     * @return 结果
     */
    @Override
    public int deleteTrackAbroadById(Integer id)
    {
        return trackAbroadMapper.deleteTrackAbroadById(id);
    }


    /**
     * 运踪_中亚境外列表导入
     *
     * @param file 导入文件
     * @return
     */
    @Override
    public AjaxResult importData(MultipartFile file)  throws Exception {
        ExcelUtil<TrackAbroad> excelUtil = new ExcelUtil<>(TrackAbroad.class);
        List<TrackAbroad> list = excelUtil.importExcel(file.getInputStream());
        int error=0;
        int total=list.size();
        for(int i=0;i<total;i++){
            String departureDate=list.get(i).getDepartureDate();
            String boxNum=list.get(i).getBoxNum();
            if(StringUtils.isEmpty(departureDate)||StringUtils.isEmpty(boxNum)){
                error+=1;
            }
        }
        if(error==0){
            TrackAbroad trackAbroad=null;
            List<TrackAbroad> trackAbroadList=new ArrayList<>();
            Map<String, String> orderIdMap = new HashMap<String, String>();
            int t=0;
            for(int n=0;n<total;n++){
                String departureDate=list.get(n).getDepartureDate();
                String boxNum=list.get(n).getBoxNum();
                TrackGoodsStatus tgs=new TrackGoodsStatus();
                tgs.setActualClassDate(departureDate);
                tgs.setBoxNum(boxNum);
                List<TrackGoodsStatus> goodsStatusList =trackGoodsStatusMapper.selectGoodsStatusList(tgs);
                for(int j=0;j<goodsStatusList.size();j++) {
                    trackAbroad = new TrackAbroad();
                    trackAbroad.setOrderId(goodsStatusList.get(j).getOrderId());
                    trackAbroad.setBoxNum(boxNum);
                    trackAbroad.setDepartureDate(departureDate);
                    trackAbroad.setLeaveTime(list.get(n).getLeaveTime());
                    trackAbroad.setChangeTime(list.get(n).getChangeTime());
                    trackAbroad.setChangeNum(list.get(n).getChangeNum());
                    trackAbroad.setAbroadContents(list.get(n).getAbroadContents());
                    String trackTime=list.get(n).getAbroadContents().split("位于")[0];
                    trackAbroad.setTrackTime(trackTime);
                    trackAbroad.setCreateTime(new Date());
                    trackAbroad.setUpdateTime(new Date());
                    trackAbroad.setBoxType(goodsStatusList.get(j).getBoxType());
                    trackAbroad.setGoodsName(goodsStatusList.get(j).getGoodsName());
                    trackAbroadList.add(trackAbroad);
                    orderIdMap.put(trackAbroad.getOrderId(),departureDate);
                    t+=trackAbroadMapper.insertTrackAbroad(trackAbroad);
                }
            }
            sendEmails(trackAbroadList,orderIdMap);
            return AjaxResult.success("成功导入"+t+"条数据");
        }else{
            return AjaxResult.error("数据导入失败，请输入箱号或者发车日期");
        }
    }

    //Tracing info of 11.05  West-Bound ZZ-train—舱位号
    //尊敬的客户，您好/Dear customer：
    //舱位编号/Booking number：ZIHWBZY201112FAta1481
    //
    //箱号：XINU8155099
    //箱型：40HC
    //货物品名：面料
    //目的地：阿拉木图1
    //驶离山口(霍尔果斯)时间：12.05
    //换装时间：暂无
    //车板号：94304128
    //运踪/Tracing info：12.16位于AЛM-1/阿拉木图1（已到站）
    //导入中亚班列发送邮件
    public void sendEmails(List<TrackAbroad> trackAbroadList,Map<String, String> orderIdMap) {
        EmailSet emailSet=new EmailSet();
        emailSet.setLineType(1);
        emailSet=emailSetMapper.selectEmailSet(emailSet);
        ShippingOrder shippingOrder=null;
        String classDate=null;
        String gocome=null;
        String content ="";
        String subject="";
        //舱位号获取
        for(Map.Entry<String, String> map : orderIdMap.entrySet()){
            String orderId=map.getKey();
            //获取实际班列日期
            classDate=map.getValue();
            //获取去回程和舱位号
            shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
            gocome="0".equals(shippingOrder.getClassEastandwest())?"West":"East";
            subject="Tracing info of "+classDate+" "+gocome+"-Bound ZZ-train——"+shippingOrder.getOrderNumber();
            content = "尊敬的客户，您好/Dear customer：\n"+
            "舱位编号/Booking number："  +shippingOrder.getOrderNumber()+"\n\n";
            //根据仓位号获取箱号
            for(int j=0;j<trackAbroadList.size();j++){
                if(orderId.equals(trackAbroadList.get(j).getOrderId())){
                    content=content+"箱号："  +trackAbroadList.get(j).getBoxNum()+"\n";
                    if(StringUtils.isNotEmpty(trackAbroadList.get(j).getBoxType())){
                        content=content+"箱型："  +trackAbroadList.get(j).getBoxType()+"\n";
                    }
                    content=content+"货物品名："  +trackAbroadList.get(j).getGoodsName()+"\n"+
                            "目的地："  +shippingOrder.getOrderUnloadsite()+"\n"+
                            "驶离山口(霍尔果斯)时间："  +trackAbroadList.get(j).getLeaveTime()+"\n"+
                            "换装时间："  +trackAbroadList.get(j).getChangeTime()+"\n"+
                            "车板号："  +trackAbroadList.get(j).getChangeNum()+"\n"+
                            "运踪/Tracing info："  +trackAbroadList.get(j).getAbroadContents()+"\n\n\n";
                }
            }
            //发送邮件
            MailUtils.mailByAll(emailSet.getSmtpSever(),emailSet.getEmailAuthentication().toString(),emailSet.getAccount(),
                    emailSet.getPassword(),shippingOrder.getReceiveOrderReceiveemail(),subject,content);
        }
    }
}
