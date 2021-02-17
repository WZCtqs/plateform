package com.szhbl.project.track.service.impl;

import java.util.*;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.email.MailUtils;
import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.system.domain.EmailSet;
import com.szhbl.project.system.mapper.EmailSetMapper;
import com.szhbl.project.track.domain.*;
import com.szhbl.project.track.domain.vo.GwTrackTrainVo;
import com.szhbl.project.track.domain.vo.TrackTrainResultVo;
import com.szhbl.project.track.domain.vo.TrackTrainVo;
import com.szhbl.project.track.mapper.TrackAbnormalBoxMapper;
import com.szhbl.project.track.mapper.TrackOneLevelMapper;
import com.szhbl.project.track.mapper.TrackRunTimeCensusMapper;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
import com.szhbl.project.track.service.ITrackOneLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.szhbl.project.track.mapper.TrackTrainMapper;
import com.szhbl.project.track.service.ITrackTrainService;

/**
 * 运踪_班列站到站Service业务层处理
 *
 * @author szhbl
 * @date 2020-03-16
 */
@Service
public class TrackTrainServiceImpl implements ITrackTrainService
{
    @Autowired
    private TrackTrainMapper trackTrainMapper;

    @Autowired
    private TrackOneLevelMapper trackOneLevelMapper;

    @Autowired
    private ITrackOneLevelService trackOneLevelService;

    @Autowired
    private TrackRunTimeCensusMapper trackRunTimeCensusMapper;

    @Autowired
    private EmailSetMapper emailSetMapper;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private ITrackGoodsStatusService trackGoodsStatusService;

    @Autowired
    private TrackAbnormalBoxMapper trackAbnormalBoxMapper;

    /**
     * 查询运踪_班列站到站
     *
     * @param id 运踪_班列站到站ID
     * @return 运踪_班列站到站
     */
    @Override
    public TrackTrain selectTrackTrainById(Integer id)
    {
        return trackTrainMapper.selectTrackTrainById(id);
    }

    /**
     * 查询运踪_班列站到站列表
     *
     * @param trackTrain 运踪_班列站到站
     * @return 运踪_班列站到站
     */
    @Override
    public List<TrackTrain> selectTrackTrainList(TrackTrain trackTrain)
    {
        return trackTrainMapper.selectTrackTrainList(trackTrain);
    }

    //查询导出列表
    @Override
    public List<TrackTrain> selectExportTrainList(TrackTrain trackTrain)
    {
         List<TrackTrain> list=trackTrainMapper.selectExportTrainList(trackTrain);
         for(int i=0;i<list.size();i++){
             if(StringUtils.isNotEmpty(list.get(i).getStationOneDistance())&&!"/".equals(list.get(i).getStationOneDistance())){
                 list.get(i).setStationOneDistance("距离"+list.get(i).getStationOneName()+list.get(i).getStationOneDistance()+"km/with "+list.get(i).getStationOneDistance()+"km until "+getEnStation(list.get(i).getStationOneName()));
             }
             if(StringUtils.isNotEmpty(list.get(i).getStationTwoDistance())&&!"/".equals(list.get(i).getStationTwoDistance())){
                 list.get(i).setStationTwoDistance("距离"+list.get(i).getStationTwoName()+list.get(i).getStationTwoDistance()+"km/with "+list.get(i).getStationTwoDistance()+"km until "+getEnStation(list.get(i).getStationTwoName()));
             }
             if(StringUtils.isNotEmpty(list.get(i).getStationThrDistance())&&!"/".equals(list.get(i).getStationThrDistance())){
                 list.get(i).setStationThrDistance("距离"+list.get(i).getStationThrName()+list.get(i).getStationThrDistance()+"km/with "+list.get(i).getStationThrDistance()+"km until "+getEnStation(list.get(i).getStationThrName()));
             }
             if(StringUtils.isNotEmpty(list.get(i).getStationFouDistance())&&!"/".equals(list.get(i).getStationFouDistance())){
                 list.get(i).setStationFouDistance("距离"+list.get(i).getStationFouName()+list.get(i).getStationFouDistance()+"km/with "+list.get(i).getStationFouDistance()+"km until "+getEnStation(list.get(i).getStationFouName()));
             }
             //-M为慕尼黑班列，-L为列日班列，-Ma为马拉专列，-G为亨克班列，-H为赫尔辛基班列
             if("0".equals(list.get(i).getLineTypeId())&& StringUtils.isNotEmpty(list.get(i).getClassBlockTrain())){
                 if(list.get(i).getClassBlockTrain().contains("慕尼黑")){
                     list.get(i).setActualClassDate(list.get(i).getActualClassDate()+"-M");
                 }else if(list.get(i).getClassBlockTrain().contains("列日")){
                     list.get(i).setActualClassDate(list.get(i).getActualClassDate()+"-L");
                 }else if(list.get(i).getClassBlockTrain().contains("马拉")){
                     list.get(i).setActualClassDate(list.get(i).getActualClassDate()+"-Ma");
                 }else if(list.get(i).getClassBlockTrain().contains("亨克")){
                     list.get(i).setActualClassDate(list.get(i).getActualClassDate()+"-G");
                 }else if(list.get(i).getClassBlockTrain().contains("赫尔辛基")){
                     list.get(i).setActualClassDate(list.get(i).getActualClassDate()+"-H");
                 }
             }
             //对比分析添加 用时间标准公式算出来的时间和录运踪的运踪时间-发车时间的时间比较出来一个结果作为那个导出字段的值
             list.get(i).setAnalyseResult(getAnalyseResult( list.get(i).getClassId()));
         }
         return list;
    }

    /**
     * 查询运踪_班列表
     *
     * @param trainVo 运踪_班列表
     * @return 运踪_班列表集合
     */
    @Override
    public List<TrackTrainVo> selectTrainsList(TrackTrainVo trainVo) {
        if(trainVo.getLineTypeid()!=null){
            trainVo.setLineTypeids(trainVo.getLineTypeid().split(","));
        }
        List<TrackTrainVo> trackTrainVoList=trackTrainMapper.selectTrainsList(trainVo);
        if(trackTrainVoList!=null){
            for(int i=0;i<trackTrainVoList.size();i++){
                trackTrainVoList.get(i).setClassStationOfDeparture(trackRunTimeCensusMapper.selectBusiSiteVo(trackTrainVoList.get(i).getClassStationOfDeparture()).getNameCn());
                trackTrainVoList.get(i).setClassStationOfDestination(trackRunTimeCensusMapper.selectBusiSiteVo(trackTrainVoList.get(i).getClassStationOfDestination()).getNameCn());
                //查询班列预计到站时间
                trackTrainVoList.get(i).setClassETime(trackTrainMapper.getArriveTime(trackTrainVoList.get(i).getClassId()));
            }
        }
        return trackTrainVoList;
    }

    /**
     * 新增运踪_班列站到站
     *
     * @param trackTrain 运踪_班列站到站
     * @return 结果
     */
    @Override
    public int insertTrackTrain(TrackTrain trackTrain)
    {
        TrackTrain tt=new TrackTrain();
        tt.setClassId(trackTrain.getClassId());
        List<TrackTrain> list = trackTrainMapper.selectTrackTrainList(tt);
        if(list.size()==0){//第一次添加班列运踪，查询班列下的仓位，进行运踪数据添加
            List<ShippingOrder> trackList=trackTrainMapper.selectShippingOrderByClassId(tt.getClassId());//查询班列下的仓位
            TrackOneLevel trackOneLevel=null;
            TrackTwoLevel trackTwoLevel=null;
            for(int i=0;i<trackList.size();i++){
                trackOneLevel=new TrackOneLevel();
                trackOneLevel.setOrderId(trackList.get(i).getOrderId());
                trackOneLevel.setSort(4);
                Long oneId=trackOneLevelMapper.selectOneId(trackOneLevel);
                trackOneLevel.setTime(trackTrain.getTrackTime());
                trackOneLevel.setUpdateTime(DateUtils.getNowDate());
                if(oneId!=null){
                    trackOneLevel.setId(oneId);
                    trackOneLevelMapper.updateTrackOneLevel(trackOneLevel);
                }else{
                    trackOneLevel.setNameZh("班列运踪");
                    trackOneLevel.setNameEn("Tracking");
                    trackOneLevel.setState(1);
                    trackOneLevel.setCreateTime(DateUtils.getNowDate());
                    trackOneLevelService.insertTrackOneLevel(trackOneLevel);
                }
              /*  trackTwoLevel=new TrackTwoLevel();
                trackTwoLevel.setOrderId(trackList.get(i).getOrderId());
                trackTwoLevel.setNameZh("已发车");
                trackTwoLevel.setNameEn("departed");
                trackTwoLevel.setState(1);
                trackTwoLevel.setIsCustom("1");
                trackTwoLevel.setTime(trackTrain.getTrackTime());
                //trackTwoLevel.setOneId(trackOneLevelService.selectOneId(trackOneLevel));
                if("0".equals(trackList.get(i).getClassEastandwest())){//去
                    trackTwoLevel.setSort(23);
                }else{
                    trackTwoLevel.setSort(18);
                }
                trackTwoLevel.setCreateTime(DateUtils.getNowDate());
                trackTwoLevel.setUpdateTime(DateUtils.getNowDate());
                trackTwoLevelMapper.insertTrackTwoLevel(trackTwoLevel);*/
            }
        }
        trackTrain.setCreateTime(DateUtils.getNowDate());
        int i= trackTrainMapper.insertTrackTrain(trackTrain);
        updateClass(trackTrain);
        //修改班列运踪当天状态
        trackTrainMapper.updateTrackState(trackTrain.getClassId());
        return i;
    }

    /**
     * 修改运踪_班列站到站
     *
     * @param trackTrain 运踪_班列站到站
     * @return 结果
     */
    @Override
    public int updateTrackTrain(TrackTrain trackTrain)
    {
        Integer firstId=trackTrainMapper.getFirstId(trackTrain.getClassId());//获取班列第一条数据
        if(firstId.equals(trackTrain.getId())){//班列运踪时间修改
            TrackTrain tt=trackTrainMapper.selectTrackTrainById(firstId);
            if(StringUtils.isNotEmpty(tt.getTrackTime())&&!tt.getTrackTime().equals(trackTrain.getTrackTime())){
                List<ShippingOrder> trackList=trackTrainMapper.selectShippingOrderByClassId(trackTrain.getClassId());//查询班列下的仓位
                TrackOneLevel trackOneLevel=null;
                TrackTwoLevel trackTwoLevel=null;
                for(int i=0;i<trackList.size();i++){
                    trackOneLevel=new TrackOneLevel();
                    trackOneLevel.setOrderId(trackList.get(i).getOrderId());
                    trackOneLevel.setSort(4);
                    Long oneId=trackOneLevelMapper.selectOneId(trackOneLevel);
                    trackOneLevel.setTime(trackTrain.getTrackTime());
                    trackOneLevel.setUpdateTime(DateUtils.getNowDate());
                    if(oneId!=null){
                        trackOneLevel.setId(oneId);
                        trackOneLevelMapper.updateTrackOneLevel(trackOneLevel);
                    }else{
                        trackOneLevel.setNameZh("班列运踪");
                        trackOneLevel.setNameEn("Tracking");
                        trackOneLevel.setState(1);
                        trackOneLevel.setSort(4);
                        trackOneLevel.setCreateTime(DateUtils.getNowDate());
                        trackOneLevelService.insertTrackOneLevel(trackOneLevel);
                    }

                 /*   trackTwoLevel=new TrackTwoLevel();
                    trackTwoLevel.setOrderId(trackList.get(i).getOrderId());
                    if("0".equals(trackList.get(i).getClassEastandwest())){//去
                        trackTwoLevel.setSort(23);
                    }else{
                        trackTwoLevel.setSort(18);
                    }
                    Long twoId=trackTwoLevelMapper.selectTwoId(trackTwoLevel);
                    trackTwoLevel.setTime(trackTrain.getTrackTime());
                    trackTwoLevel.setUpdateTime(DateUtils.getNowDate());
                    if(twoId!=null){
                        trackTwoLevel.setId(twoId);
                        trackTwoLevelMapper.updateTrackTwoLevel(trackTwoLevel);
                    }else{
                        //trackTwoLevel.setOneId(trackOneLevelService.selectOneId(trackOneLevel));
                        trackTwoLevel.setNameZh("已发车");
                        trackTwoLevel.setNameEn("departed");
                        trackTwoLevel.setState(1);
                        trackTwoLevel.setIsCustom("1");
                        trackTwoLevel.setTime(trackTrain.getTrackTime());
                        trackTwoLevel.setCreateTime(DateUtils.getNowDate());
                        trackTwoLevelMapper.insertTrackTwoLevel(trackTwoLevel);
                    }*/
                }
            }
        }
        int i= trackTrainMapper.updateTrackTrain(trackTrain);
        updateClass(trackTrain);
        return i;
    }

    /**
     * 批量删除运踪_班列站到站
     *
     * @param ids 需要删除的运踪_班列站到站ID
     * @return 结果
     */
    @Override
    public int deleteTrackTrainByIds(Integer[] ids)
    {

        for(int j=0;j<ids.length;j++){
            TrackTrain tt=trackTrainMapper.selectTrackTrainById(ids[j]);
            Integer firstId=trackTrainMapper.getFirstId(tt.getClassId());//获取班列第一条数据
            if(firstId.equals(ids[j])){
                List<ShippingOrder> trackList=trackTrainMapper.selectShippingOrderByClassId(tt.getClassId());//查询班列下的仓位
                TrackOneLevel trackOneLevel=null;
                //TrackTwoLevel trackTwoLevel=null;
                for(int i=0;i<trackList.size();i++){
                    trackOneLevel=new TrackOneLevel();
                    trackOneLevel.setOrderId(trackList.get(i).getOrderId());
                    trackOneLevel.setSort(4);
                    trackOneLevelService.deleteTrackOneLevelById(trackOneLevelMapper.selectOneId(trackOneLevel));

                    /*trackTwoLevel=new TrackTwoLevel();
                    trackTwoLevel.setOrderId(trackList.get(i).getOrderId());
                    if("0".equals(trackList.get(i).getClassEastandwest())){//去
                        trackTwoLevel.setSort(23);
                    }else{
                        trackTwoLevel.setSort(18);
                    }
                    trackTwoLevelMapper.deleteTrackTwoLevelById(trackTwoLevelMapper.selectTwoId(trackTwoLevel));*/
                }
            }
        }
        int m= trackTrainMapper.deleteTrackTrainByIds(ids);
        for(int j=0;j<ids.length;j++){
            TrackTrain tt=trackTrainMapper.selectTrackTrainById(ids[j]);
            //获取最新班列数据进行存储
            TrackTrain trackTrain=trackTrainMapper.selectTrackTrainById(trackTrainMapper.getFirstId(tt.getClassId()));
            List<ShippingOrder> trackList=trackTrainMapper.selectShippingOrderByClassId(tt.getClassId());//查询班列下的仓位
            TrackOneLevel trackOneLevel=null;
            //TrackTwoLevel trackTwoLevel=null;
            if(trackTrain!=null){
                for(int i=0;i<trackList.size();i++){
                    trackOneLevel=new TrackOneLevel();
                    trackOneLevel.setOrderId(trackList.get(i).getOrderId());
                    trackOneLevel.setSort(4);
                    Long oneId=trackOneLevelMapper.selectOneId(trackOneLevel);
                    trackOneLevel.setTime(trackTrain.getTrackTime());
                    trackOneLevel.setUpdateTime(DateUtils.getNowDate());
                    if(oneId!=null){
                        trackOneLevel.setId(oneId);
                        trackOneLevelMapper.updateTrackOneLevel(trackOneLevel);
                    }else{
                        trackOneLevel.setNameZh("班列运踪");
                        trackOneLevel.setNameEn("Tracking");
                        trackOneLevel.setState(1);
                        trackOneLevel.setCreateTime(DateUtils.getNowDate());
                        trackOneLevelService.insertTrackOneLevel(trackOneLevel);
                    }

               /* trackTwoLevel=new TrackTwoLevel();
                trackTwoLevel.setOrderId(trackList.get(i).getOrderId());
                trackTwoLevel.setNameZh("已发车");
                trackTwoLevel.setNameEn("departed");
                trackTwoLevel.setState(1);
                trackTwoLevel.setIsCustom("1");
                trackTwoLevel.setTime(trackTrain.getTrackTime());
                //trackTwoLevel.setOneId(trackOneLevelMapper.selectOneId(trackOneLevel));
                if("0".equals(trackList.get(i).getClassEastandwest())){//去
                    trackTwoLevel.setSort(23);
                }else{
                    trackTwoLevel.setSort(18);
                }
                trackTwoLevel.setCreateTime(DateUtils.getNowDate());
                trackTwoLevel.setUpdateTime(DateUtils.getNowDate());
                trackTwoLevelMapper.insertTrackTwoLevel(trackTwoLevel);*/
                }
            }
        }
        TrackTrain trackTrain=new TrackTrain();
        trackTrain.setId(ids[0]);
        updateClass(trackTrain);
        return m;
    }

    /**
     * 删除运踪_班列站到站信息
     *
     * @param id 运踪_班列站到站ID
     * @return 结果
     */
    @Override
    public int deleteTrackTrainById(Integer id)
    {
        int i= trackTrainMapper.deleteTrackTrainById(id);
        TrackTrain trackTrain=new TrackTrain();
        trackTrain.setId(id);
        updateClass(trackTrain);
        return i;
    }

    //新增保存发送全部或者发送VIP
    @Override
    public int addAndSend(TrackTrain trackTrain)  {
        int i =insertTrackTrain(trackTrain);
        sendMails(trackTrain);
        return i;
    }

    //编辑保存发送全部或者发送VIP
    @Override
    public int editAndSend(TrackTrain trackTrain) {
        int i =updateTrackTrain(trackTrain);
        sendMails(trackTrain);
        return i;
    }

    //查询关务班列运踪
    @Override
    public Map<String, Object> selectGwTrackTrainList(String orderNum) {
        Map<String, Object> map =new HashMap<>();
        String actualDate=null;
        String batchTime=null;
        String abnormalTime=null;
        List<GwTrackTrainVo> trackTrainList=new ArrayList<>();
        List<GwTrackTrainVo> trackAbnormalBoxList=new ArrayList<>();
        map.put("Status",200);
        map.put("Msg","请求成功");
        //根据仓位号获取班列id
        TrackGoodsStatus tgs=new TrackGoodsStatus();
        tgs.setOrderNum(orderNum);
        List<TrackGoodsStatus> tgsList =trackGoodsStatusService.selectGoodsStatusList(tgs);
        if(tgsList==null||tgsList.size()==0){
            map.put("Data",trackTrainList);
            map.put("DataAbnormal",trackAbnormalBoxList);
            return map;
        }
        actualDate=tgsList.get(0).getActualClassDate();
        if(StringUtils.isEmpty(actualDate)||"/".equals(actualDate)){
            map.put("Data",trackTrainList);
            map.put("DataAbnormal",trackAbnormalBoxList);
            return map;
        }
        if(tgsList.get(0).getAbnormalTime()!=null){
            abnormalTime=DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",tgsList.get(0).getAbnormalTime());
        }
        //带x,显示这个仓位号和箱号在改为10.22p-x之前10.22p所有的班列运踪+改之后10.22p-x的班列运踪，获取修改成X的时间
        if(actualDate.contains("X")||actualDate.contains("x")){
            Date batTime= tgsList.get(0).getBatchTime();
            batchTime=DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",batTime);
        }
        //不带x,显示不带x的所有班列运踪
        trackTrainList=trackTrainMapper.selectGwTrackTrainList(tgsList.get(0).getActualClassId(),actualDate,batchTime,abnormalTime);
        map.put("Data",trackTrainList);
        //添加异常箱运踪信息
        if(StringUtils.isNotEmpty(abnormalTime)){
            TrackAbnormalBox trackAbnormalBox=new TrackAbnormalBox();
            trackAbnormalBox.setOrderId(tgsList.get(0).getOrderId());
            trackAbnormalBox.setBoxNum(tgsList.get(0).getBoxNum());
            List<TrackAbnormalBox> abnormalList = trackAbnormalBoxMapper.selectTrackAbnormalBoxList(trackAbnormalBox);
            GwTrackTrainVo gwTrackTrainVo=null;
            for(int i=0;i<abnormalList.size();i++){
                gwTrackTrainVo=new GwTrackTrainVo();
                gwTrackTrainVo.setLOGGING_DATE(abnormalList.get(i).getInputTime());
                gwTrackTrainVo.setLOGGIN_ABNORMALINFO(abnormalList.get(i).getAbnormalInformation());
                gwTrackTrainVo.setUNLOAD_RE(abnormalList.get(i).getUnloadReason());
                trackAbnormalBoxList.add(gwTrackTrainVo);
            }
        }
        map.put("abnormalBox",trackAbnormalBoxList);
        return map;
    }

    /**
     * 班列表运踪时间更新
     *
     * @param trackTrain
     * @return
     */
    public int updateClass(TrackTrain trackTrain)
    {
        return trackTrainMapper.updateClass(trackTrain);
    }

    //获取需要发送客户的邮箱和舱位号编号，根据班列id和实际班列日期获取的所有托书的对应邮箱和舱位号编号并发送
    public void sendMails(TrackTrain trackTrain) {
        EmailSet emailSet=new EmailSet();
        List<TrackTrain> sendMessage=trackTrainMapper.getSendMessage(trackTrain);
        String content = "";
        String goCome = trackTrain.getClassEastAndWest();
        /** 线路类型：0中欧 2中亚 3中越 4中俄   0*/
        String lineTypeId = trackTrain.getLineTypeId();
        //"0"到达"1"驶离"2"在
        String stateValue = trackTrain.getStateValue();
        //班列编号 获取奇偶数
        String classBh = trackTrain.getClassBh();
        if ("0".equals(stateValue)) {
            stateValue = "到达/arrive in";
        } else if ("1".equals(stateValue)) {
            stateValue = "驶离/depart from";
        } else if ("2".equals(stateValue)) {
            stateValue = "在/in";
        }
        if("0".equals(goCome)){
            goCome="West";
        }else if("1".equals(goCome)){
            goCome="East";
        }
        String subject="Tracing info of "+goCome+"-Bound ZZ-train "+trackTrain.getClassDate().substring(0,10);
        //站点距离
        String StationOne="";
        String StationTwo="";
        String StationThr="";
        String StationFou="";
        List<String> emptyList=new ArrayList<>();
        emptyList.add("0");
        emptyList.add("/");
        if(!emptyList.contains(trackTrain.getStationOneDistance())){
            StationOne="距离" + trackTrain.getStationOneName() + "/distance until " + getEnStation(trackTrain.getStationOneName()) + "： " + trackTrain.getStationOneDistance() + "km\n" ;
        }
        if(!emptyList.contains(trackTrain.getStationTwoDistance())){
            StationTwo= "距离" + trackTrain.getStationTwoName() + "/distance until " + getEnStation(trackTrain.getStationTwoName()) + "： "  + trackTrain.getStationTwoDistance() + "km\n" ;
        }
        if(!emptyList.contains(trackTrain.getStationThrDistance())){
            StationThr="距离" + trackTrain.getStationThrName() + "/distance until " + getEnStation(trackTrain.getStationThrName()) + "： " + trackTrain.getStationThrDistance() + "km\n" ;
        }
        if(!emptyList.contains(trackTrain.getStationFouDistance())){
            StationFou="距离" + trackTrain.getStationFouName() + "/distance until " + getEnStation(trackTrain.getStationFouName()) + "： " + trackTrain.getStationFouDistance() + "km\n" ;
        }
        //预计时间
        String ETAString = "";
        if(StringUtils.isNotEmpty(trackTrain.getExceptEarlyTime())){
            String earlyMonth = getEnMonth(trackTrain.getExceptEarlyTime().split("-")[1]);
            String lastMonth = getEnMonth(trackTrain.getExceptLastTime().split("-")[1]);
            String earlyDay = Integer.valueOf(trackTrain.getExceptEarlyTime().split("-")[2]).toString();
            String lastDay = Integer.valueOf(trackTrain.getExceptLastTime().split("-")[2]).toString();
            ETAString = "预计到港时间/ETA time： "+earlyDay+" "+earlyMonth+"/"+lastDay+" " +lastMonth + "\n";
        }
        //运踪时间 没有录时间的统一显示成暂无
        String trackTime="";
        if(StringUtils.isNotEmpty(trackTrain.getTrackTime())){
            trackTime="运踪时间/Tracing time： " + trackTrain.getTrackTime() + "\n" ;
        }else{
            trackTime="运踪时间/Tracing time： 暂无"+ "\n" ;
        }
        //备注
        String remark="";
        if(StringUtils.isNotEmpty(trackTrain.getRemark())){
            remark="备注： " + trackTrain.getRemark() + "\n" ;
        }
        //结语
        String mailEnd="我司会根据班列的实时运行情况对ETA进行调整，请知悉。/we will adjust the ETA according to the real-time operation of the train, please be informed。\n";
  /*      String mailEnd="我司会根据班列的实时运行情况对ETA进行调整，请知悉。/we will adjust the ETA according to the real-time operation of the train, please be informed\n"
                +"<p></p>\n" +
                "<br /><br />\n" +
                "<p style=\"color:blue\">&nbsp;&nbsp;Best Regards</p>\n" +
                "<p></p><br /> \n" +
                "<p>&nbsp;&nbsp;郑州国际陆港开发建设有限公司   管理部 路务信息组</p>\n" +
                "<p>&nbsp;&nbsp;Zhengzhou International Hub Development and Construction Co, Ltd.</p>\n" +
                "<p></p>\n" +
                "<p style=\"color:blue\">&nbsp;&nbsp;Web: zxdc.zih718.com</p>\n" +
                "<p>&nbsp;&nbsp;Add:郑州市经济技术开发区经北四路与航海路第十八大街交叉口郑州国际陆港园区</p>\n" +
                "<p>&nbsp;&nbsp;Crossing of Jingbei 4th Road and 18th Ave.of Hanghai Road,Zhengzhou Economic and Technological Development Zone,Henan,China</p>\n" +
                "<p style=\"color:blue\">&nbsp;&nbsp;Tel:    +86（0）371-55171376 （ tracing info ） </p>\n" +
                "\n" +
                "<p style=\"color:blue\">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;     +86（0）371-55175609（ pick up issues）</p>";*/
        if("2".equals(lineTypeId)){//中亚去回程，境内
            emailSet.setLineType(1);
            emailSet=emailSetMapper.selectEmailSet(emailSet);
            if(sendMessage!=null&&sendMessage.size()!=0){
                for(int j=0;j<sendMessage.size();j++){
                    String[] boxNumArray=trackTrainMapper.getBoxNum(sendMessage.get(j).getOrderNum(),trackTrain.getActualClassDate());
                    String boxNum= StringUtils.join(boxNumArray,"、");
                    //业务编号
                    String businessNum="";
                    BusiShippingorders busiShippingorders=busiShippingorderService.selectBusiShippingorderByOrderNumber(sendMessage.get(j).getOrderNum());
                    if(StringUtils.isNotEmpty(busiShippingorders.getClientYwNumber())){
                        businessNum="业务编号/Service Number： "+busiShippingorders.getClientYwNumber()+"\n";
                    }
                    if( "West".equals(goCome)){
                        content="尊敬的客户，您好/Dear customer：\n" +
                                "舱位编号/Booking number： "+sendMessage.get(j).getOrderNum()+"\n" +
                                "箱号/Container_no： "+boxNum+"\n" +
                                businessNum+
                                trackTime +
                                "状态/Status： "+stateValue+" "+trackTrain.getCurrentLocation()+"\n" +
                                StationOne +
                                StationTwo +
                                StationThr+
                                ETAString +
                                remark+
                                mailEnd;
                    }else if ("East".equals(goCome)){
                        content="尊敬的客户，您好/Dear customer：\n" +
                                "舱位编号/Booking number： "+sendMessage.get(j).getOrderNum()+"\n" +
                                "箱号/Container_no： "+boxNum+"\n" +
                                businessNum+
                                trackTime +
                                "状态/Status： "+stateValue+" "+trackTrain.getCurrentLocation()+"\n" +
                                StationOne +
                                StationTwo +
                                ETAString +
                                remark+
                                mailEnd;
                    }
                    MailUtils.mailByAll(emailSet.getSmtpSever(),emailSet.getEmailAuthentication().toString(),emailSet.getAccount(),emailSet.getPassword(),sendMessage.get(j).getEmail(),subject,content);
                }
            }
        }else{
            if ("0".equals(lineTypeId)) {//中欧去程 回程
                emailSet.setLineType(0);
                emailSet.setIsCustom(1);
                if("West".equals(goCome)){
                    emailSet.setGoCome(0);
                }else if("East".equals(goCome)){
                    emailSet.setGoCome(1);
                }
                emailSet.setOddEven(getOddEven(classBh));
                if(sendMessage!=null&&sendMessage.size()!=0){
                    //欧线去程班列上有多个下货站的货物，需要判断一下到布列斯特（布列斯特，莫斯科，明斯克，圣彼得堡）、马拉（马拉，华沙，布拉格，布达佩斯）下货的货物，之后的运踪不再发送
                    boolean send=true;
                    for(int j=0;j<sendMessage.size();j++){
                        emailSet.setConsolidationType(Integer.valueOf(sendMessage.get(j).getConsolidationType()));
                        //欧线整柜发送邮件增加一行箱号
                        String boxNum="";
                        if("0".equals(sendMessage.get(j).getConsolidationType())){
                            String[] boxNumArray=trackTrainMapper.getBoxNum(sendMessage.get(j).getOrderNum(),trackTrain.getActualClassDate());
                            boxNum="箱号/Container_no： "+StringUtils.join(boxNumArray,"、")+"\n";
                        }
                        BusiShippingorders busiShippingorders=busiShippingorderService.selectBusiShippingorderByOrderNumber(sendMessage.get(j).getOrderNum());
                        //业务编号
                        String businessNum="";
                        if(StringUtils.isNotEmpty(busiShippingorders.getClientYwNumber())){
                            businessNum="业务编号/Service Number： "+busiShippingorders.getClientYwNumber()+"\n";
                        }
                        //是否发送
                        if("0".equals(busiShippingorders.getClassEastandwest())){
                            send=getSend(busiShippingorders,trackTrain);
                        }
                        if(send){
                            emailSet=emailSetMapper.selectEmailSet(emailSet);
                            content = "尊敬的客户，您好/Dear customer：\n" +
                                    "班列日期/Train date： " + trackTrain.getClassDate().substring(0, 10) + "\n" +
                                    "舱位编号/Booking number： " +sendMessage.get(j).getOrderNum()+ "\n" +
                                    boxNum +
                                    businessNum+
                                    trackTime +
                                    "状态/Status： " + stateValue + " " + trackTrain.getCurrentLocation() + "\n" +
                                    StationOne +
                                    StationTwo +
                                    StationThr +
                                    StationFou +
                                    ETAString +
                                    remark+
                                    mailEnd;
                            MailUtils.mailByAll(emailSet.getSmtpSever(),emailSet.getEmailAuthentication().toString(),emailSet.getAccount(),emailSet.getPassword(),sendMessage.get(j).getEmail(),subject,content);
                        }
                    }
                }
            } else if ("4".equals(lineTypeId) && "West".equals(goCome)) {//中俄去程
                emailSet.setLineType(1);
                emailSet=emailSetMapper.selectEmailSet(emailSet);
                if(sendMessage!=null&&sendMessage.size()!=0){
                    for(int j=0;j<sendMessage.size();j++){
                        //业务编号
                        String businessNum="";
                        BusiShippingorders busiShippingorders=busiShippingorderService.selectBusiShippingorderByOrderNumber(sendMessage.get(j).getOrderNum());
                        if(StringUtils.isNotEmpty(busiShippingorders.getClientYwNumber())){
                            businessNum="业务编号/Service Number： "+busiShippingorders.getClientYwNumber()+"\n";
                        }
                        content = "尊敬的客户，您好/Dear customer：\n" +
                                "班列日期/Train date： " + trackTrain.getClassDate().substring(0, 10) + "\n" +
                                "舱位编号/Booking number： "+sendMessage.get(j).getOrderNum() + "\n" +
                                businessNum+
                                trackTime +
                                "状态/Status： " + stateValue + " " + trackTrain.getCurrentLocation() + "\n" +
                                StationOne +
                                StationTwo +
                                StationThr +
                                ETAString +
                                remark+
                                mailEnd;
                        MailUtils.mailByAll(emailSet.getSmtpSever(),emailSet.getEmailAuthentication().toString(),emailSet.getAccount(),emailSet.getPassword(),sendMessage.get(j).getEmail(),subject,content);
                    }
                }
            } else if ("4".equals(lineTypeId) && "East".equals(goCome)) {//中俄回程
                emailSet.setLineType(1);
                emailSet=emailSetMapper.selectEmailSet(emailSet);
                if(sendMessage!=null&&sendMessage.size()!=0){
                    for(int j=0;j<sendMessage.size();j++){
                        //业务编号
                        String businessNum="";
                        BusiShippingorders busiShippingorders=busiShippingorderService.selectBusiShippingorderByOrderNumber(sendMessage.get(j).getOrderNum());
                        if(StringUtils.isNotEmpty(busiShippingorders.getClientYwNumber())){
                            businessNum="业务编号/Service Number： "+busiShippingorders.getClientYwNumber()+"\n";
                        }
                        if (trackTrain.getClassBlockTrain().contains("二连")) {
                            content = "尊敬的客户，您好/Dear customer：\n" +
                                    "班列日期/Train date： " + trackTrain.getClassDate().substring(0, 10) + "\n" +
                                    "舱位编号/Booking number： "+sendMessage.get(j).getOrderNum() + "\n" +
                                    businessNum+
                                    trackTime +
                                    "状态/Status： " + stateValue + " " + trackTrain.getCurrentLocation() + "\n" +
                                    StationOne +
                                    StationTwo +
                                    StationThr+
                                    remark;
                        } else if (trackTrain.getClassBlockTrain().contains("绥芬河")) {
                            content = "尊敬的客户，您好/Dear customer：\n" +
                                    "班列日期/Train date： " + trackTrain.getClassDate().substring(0, 10) + "\n" +
                                    "舱位编号/Booking number： "+sendMessage.get(j).getOrderNum() + "\n" +
                                    businessNum+
                                    trackTime +
                                    "状态/Status： " + stateValue + " " + trackTrain.getCurrentLocation() + "\n" +
                                    StationThr +
                                    ETAString +
                                    remark+
                                    mailEnd;
                        }
                        MailUtils.mailByAll(emailSet.getSmtpSever(),emailSet.getEmailAuthentication().toString(),emailSet.getAccount(),emailSet.getPassword(),sendMessage.get(j).getEmail(),subject,content);
                    }
                }
            } else if ("3".equals(lineTypeId)) {//越南去 回程
                emailSet.setLineType(0);
                emailSet.setIsCustom(1);
                if("West".equals(goCome)){
                    emailSet.setGoCome(0);
                }else if("East".equals(goCome)){
                    emailSet.setGoCome(1);
                }
                emailSet.setOddEven(getOddEven(classBh));
                if(sendMessage!=null&&sendMessage.size()!=0){
                     for(int j=0;j<sendMessage.size();j++){
                        emailSet.setConsolidationType(Integer.valueOf(sendMessage.get(j).getConsolidationType()));
                        emailSet=emailSetMapper.selectEmailSet(emailSet);
                         //业务编号
                         String businessNum="";
                         BusiShippingorders busiShippingorders=busiShippingorderService.selectBusiShippingorderByOrderNumber(sendMessage.get(j).getOrderNum());
                         if(StringUtils.isNotEmpty(busiShippingorders.getClientYwNumber())){
                             businessNum="业务编号/Service Number： "+busiShippingorders.getClientYwNumber()+"\n";
                         }
                        content = "尊敬的客户，您好/Dear customer：\n" +
                                 "班列日期/Train date： " + trackTrain.getClassDate().substring(0, 10) + "\n" +
                                 "舱位编号/Booking number： "+sendMessage.get(j).getOrderNum() + "\n" +
                                 businessNum+
                                 trackTime +
                                 "状态/Status： " + stateValue + " " + trackTrain.getCurrentLocation() + "\n" +
                                 StationOne +
                                 StationTwo +
                                 ETAString +
                                remark+
                                mailEnd;
                        MailUtils.mailByAll(emailSet.getSmtpSever(),emailSet.getEmailAuthentication().toString(),emailSet.getAccount(),emailSet.getPassword(),sendMessage.get(j).getEmail(),subject,content);
                     }
                }
            }
        }
    }

    //获取中文名对应的英文名
    public String getEnStation(String chStation){
       Map<String, String> nameMap=new HashMap();
       nameMap.put("山口","Alashankou");
       nameMap.put("布列斯特","BREST");
       nameMap.put("马拉","Malaszewicze");
       nameMap.put("列日","Liege");
       nameMap.put("绥芬河","Suifenhe");
       nameMap.put("霍尔果斯","Khorgos");
       nameMap.put("满洲里","Manzhouli");
       nameMap.put("郑州圃田","Putian.Zhengzhou");
       nameMap.put("郑州","Putian.Zhengzhou");
       nameMap.put("二连","Erenhot");
       nameMap.put("扎门乌德","Zamyn-Uud");
       nameMap.put("格罗捷克沃","Grodekovo");
       nameMap.put("沃尔西诺","Vorsino");
       nameMap.put("后贝加尔斯克","Zabaikalsk");
       nameMap.put("河内","Hanoi");
       nameMap.put("凭祥","Pinxiang");
       nameMap.put("阿拉木图","Almaty");
       nameMap.put("塔什干","Tashkent");
       nameMap.put("丘库尔赛","Chukursay");
       nameMap.put("谢尔盖利","Sergeli");
       nameMap.put("汉堡","Hamburg");
       nameMap.put("慕尼黑","Munich");
       nameMap.put("丘诺亚尔","Chunoyal");
       nameMap.put("克拉斯诺亚尔斯克","Krasnoyarsk");
       nameMap.put("伊尔库茨克","Irkutsk");
       nameMap.put("赤塔","Chita");
       nameMap.put("亨克","Genk");
       return nameMap.get(chStation);
    }

    //获取数字对应英文名
    public String getEnMonth(String chMonth){
        Map<String, String> nameMap=new HashMap();
        nameMap.put("01","Jan.");
        nameMap.put("02","Feb.");
        nameMap.put("03","Mar.");
        nameMap.put("04","Apr.");
        nameMap.put("05","May.");
        nameMap.put("06","Jun.");
        nameMap.put("07","Jul.");
        nameMap.put("08","Aug.");
        nameMap.put("09","Sep.");
        nameMap.put("10","Oct.");
        nameMap.put("11","Nov.");
        nameMap.put("12","Dec.");
        return nameMap.get(chMonth);
    }

    //获取班列的奇偶数
    public Integer getOddEven(String classBh){
        String number="";
        if(classBh != null && !"".equals(classBh)) {
            for (int i = 0; i < classBh.length(); i++) {
                if (classBh.charAt(i) >= 48 && classBh.charAt(i) <= 57) {
                    number += classBh.charAt(i);
                }
            }
        }
        return Integer.valueOf(number.substring(number.length()-1,number.length()))%2;
    }

    //导出表的对比分析  用时间标准公式算出来的时间和录运踪的运踪时间-发车时间的时间比较出来一个结果作为那个导出字段的值
    public String getAnalyseResult(String classId){
        String analyseResult="";
        TrackTrain tt=new TrackTrain();
        tt.setClassId(classId);
        //获取班列运踪,倒序
        List<TrackTrain> trackTrainList=trackTrainMapper.selectTrackTrainList(tt);
        //计算对比分析  去回程,线路0中欧 2中亚 3中越 4中俄 ,站点,换装站
        TrackTrain lastTrackTrain=trackTrainList.get(0);
        String goCome=lastTrackTrain.getClassEastAndWest();
        String lineType=lastTrackTrain.getLineTypeId();
        String site=lastTrackTrain.getClassBlockTrain();
        String currentLocation=lastTrackTrain.getCurrentLocation();
        //0=到达/arrive in,1=驶离/depart from,2=在/in
        String state=lastTrackTrain.getStateValue();
        //计算值初始化
        int actualDistance=0;
        int totalDistance=0;
        double expectTime=0;
        double totalTime=0;
        double day=0;
        double changeValue=0;
        double doubleAnalyseResult=0;
        List<String> empList=new ArrayList<>();
        empList.add("0");
        empList.add("/");
        //欧线去程
        if("0".equals(lineType)&&"0".equals(goCome)&&!site.contains("赫尔辛基")){
            //圃田,始发站驶离时间
            String leaveTime=getTrackTime("郑州圃田","zhengzhou.putian","1",trackTrainList);
            if(StringUtils.isEmpty(leaveTime)||StringUtils.isEmpty(lastTrackTrain.getTrackTime())){
                return null;
            }
            //实际总时间
            String stringTotalTime=DateUtils.getDays(leaveTime,lastTrackTrain.getTrackTime());
            totalTime=Double.valueOf(stringTotalTime);
            //换装站
            String changePort="";
            for(int i=0;i<trackTrainList.size();i++){
                if(StringUtils.isNotEmpty(trackTrainList.get(i).getChangePort())){
                    changePort=trackTrainList.get(i).getChangePort();
                    break;
                }
            }
            //阿拉山口/霍尔果斯
            if(site.contains("阿拉山口")||site.contains("霍尔果斯")){
                //第一个站点距离不为空
                if(!empList.contains(lastTrackTrain.getStationOneDistance())){
                    actualDistance=Integer.valueOf(lastTrackTrain.getStationOneDistance());
                    if(site.contains("阿拉山口")){
                        totalDistance=3669;
                    }else{
                        totalDistance=3883;
                    }
                    expectTime= (2.5/totalDistance)*(totalDistance-actualDistance);
                }
                //第一个站点距离为空,第二个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationOneDistance())&&!empList.contains(lastTrackTrain.getStationTwoDistance())){
                    //站点名字为 阿拉山口/霍尔果斯
                    if(site.contains(currentLocation)){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=3.42;
                        }
                        //到达，在站点
                        else{
                            expectTime=2.5;
                        }
                    }
                    //站点名字为 多斯特克/阿腾科里
                    else if(currentLocation.contains("多斯特克")||currentLocation.contains("阿腾科里")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=4.5;
                        }
                        //到达，在站点
                        else{
                            expectTime=3.5;
                        }
                    }
                    //需要换装站
                    else{
                        actualDistance=Integer.valueOf(lastTrackTrain.getStationTwoDistance());
                        if("布列斯特".equals(changePort)){
                            changeValue=6.5;
                        }else if("马拉".equals(changePort)){
                            changeValue=6.25;
                        }
                        if(site.contains("阿拉山口")){
                            totalDistance=5466;
                        }else{
                            totalDistance=5391;
                        }
                        if(changeValue!=0){
                            expectTime= 4.5+(changeValue/totalDistance)*(totalDistance-actualDistance);
                        }
                    }
                }
                //第二个站点距离为空,第三个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationTwoDistance())&&!empList.contains(lastTrackTrain.getStationThrDistance())){
                    //站点为布列斯特
                    if(currentLocation.contains("布列斯特")||currentLocation.contains("Brest")||currentLocation.contains("brest")){
                        //驶离站点
                        if("1".equals(state)){
                            if("布列斯特".equals(changePort)){
                                expectTime=12.5;
                            }else if("马拉".equals(changePort)){
                                expectTime=11.25;
                            }
                        }
                        //到达，在站点
                        else{
                            if("布列斯特".equals(changePort)){
                                expectTime=11;
                            }else if("马拉".equals(changePort)){
                                expectTime=10.75;
                            }
                        }
                    }
                }
                //第三个站点距离为空,第四个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationThrDistance())&&lastTrackTrain.getStationFouDistance()!=null&&!empList.contains(lastTrackTrain.getStationFouDistance())){
                    //站点为马拉
                    if(currentLocation.contains("马拉")||currentLocation.contains("Mala")||currentLocation.contains("mala")){
                        //驶离站点
                        if("1".equals(state)){
                            if("布列斯特".equals(changePort)){
                                expectTime=13.25;
                            }else if("马拉".equals(changePort)){
                                expectTime=13;
                            }
                        }
                        //到达，在站点
                        else{
                            if("布列斯特".equals(changePort)){
                                expectTime=12.75;
                            }else if("马拉".equals(changePort)){
                                expectTime=11.5;
                            }
                        }
                    }
                    //马拉和终点站的运踪  汉堡/慕尼黑/列日/亨克
                    else{
                        actualDistance=Integer.valueOf(lastTrackTrain.getStationFouDistance());
                        if("布列斯特".equals(changePort)){
                            changeValue=1.75;
                            day=13.25;
                        }else if("马拉".equals(changePort)){
                            changeValue=2;
                            day=13;
                        }
                        if(site.contains("汉堡")){
                            totalDistance=1031;
                        }else if(site.contains("慕尼黑")){
                            totalDistance=1403;
                        }else if(site.contains("列日")){
                            totalDistance=1252;
                        }else if(site.contains("亨克")){
                            totalDistance=1410;
                        }
                        if(changeValue!=0){
                            expectTime= day+(changeValue/totalDistance)*(totalDistance-actualDistance);
                        }
                    }
                }
                //只有三个站点
                else if(empList.contains(lastTrackTrain.getStationThrDistance())&&lastTrackTrain.getStationFouDistance()==null){
                    if("布列斯特".equals(changePort)){
                        expectTime=12.75;
                    }else if("马拉".equals(changePort)){
                        expectTime=11.5;
                    }
                }
                //到达终点
                else if(lastTrackTrain.getStationFouDistance()!=null&&empList.contains(lastTrackTrain.getStationFouDistance())){
                    expectTime=15;
                }
            }
            //二连浩特/满洲里/绥芬河
            else if(site.contains("二连浩特")||site.contains("满洲里")||site.contains("绥芬河")){
                //第一个站点距离不为空
                if(!empList.contains(lastTrackTrain.getStationOneDistance())){
                    actualDistance=Integer.valueOf(lastTrackTrain.getStationOneDistance());
                    if(site.contains("二连浩特")){
                        totalDistance=1403;
                    }else if(site.contains("满洲里")){
                        totalDistance=2704;
                    }else if(site.contains("绥芬河")){
                        totalDistance=2593;
                    }
                    expectTime= (1.5/totalDistance)*(totalDistance-actualDistance);
                }
                //第一个站点距离为空,第二个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationOneDistance())&&!empList.contains(lastTrackTrain.getStationTwoDistance())){
                    //站点名字为 二连浩特/满洲里/绥芬河
                    if(site.contains(currentLocation)){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=2.29;
                        }
                        //到达，在站点
                        else{
                            expectTime=1.5;
                        }
                    }
                    //站点名字为 扎门乌德/后贝加尔斯克/格罗捷克沃
                    else if(currentLocation.contains("扎门乌德")||currentLocation.contains("后贝加尔斯克")||currentLocation.contains("格罗捷克沃")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=3;
                        }
                        //到达，在站点
                        else{
                            expectTime=2.5;
                        }
                    }
                    //需要换装站
                    else{
                        actualDistance=Integer.valueOf(lastTrackTrain.getStationTwoDistance());
                        if("布列斯特".equals(changePort)){
                            changeValue=9;
                        }else if("马拉".equals(changePort)){
                            changeValue=8.75;
                        }
                        if(site.contains("二连浩特")){
                            totalDistance=7983;
                        }else if(site.contains("满洲里")){
                            totalDistance=7800;
                        }else if(site.contains("绥芬河")){
                            totalDistance=10213;
                        }
                        if(changeValue!=0){
                            expectTime=3+(changeValue/totalDistance)*(totalDistance-actualDistance);
                        }
                    }
                }
                //第二个站点距离为空,第三个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationTwoDistance())&&!empList.contains(lastTrackTrain.getStationThrDistance())){
                    //站点为布列斯特
                    if(currentLocation.contains("布列斯特")||currentLocation.contains("Brest")||currentLocation.contains("brest")){
                        //驶离站点
                        if("1".equals(state)){
                            if("布列斯特".equals(changePort)){
                                expectTime=13.5;
                            }else if("马拉".equals(changePort)){
                                expectTime=12.25;
                            }
                        }
                        //到达，在站点
                        else{
                            if("布列斯特".equals(changePort)){
                                expectTime=12;
                            }else if("马拉".equals(changePort)){
                                expectTime=11.75;
                            }
                        }
                    }
                }
                //第三个站点距离为空,第四个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationThrDistance())&&lastTrackTrain.getStationFouDistance()!=null&&!empList.contains(lastTrackTrain.getStationFouDistance())){
                    //站点为马拉
                    if(currentLocation.contains("马拉")||currentLocation.contains("Mala")||currentLocation.contains("mala")){
                        //驶离站点
                        if("1".equals(state)){
                            if("布列斯特".equals(changePort)){
                                expectTime=14;
                            }else if("马拉".equals(changePort)){
                                expectTime=13.75;
                            }
                        }
                        //到达，在站点
                        else{
                            if("布列斯特".equals(changePort)){
                                expectTime=13.75;
                            }else if("马拉".equals(changePort)){
                                expectTime=12.5;
                            }
                        }
                    }
                    //马拉和终点站的运踪  汉堡/慕尼黑/列日/亨克
                    else{
                        actualDistance=Integer.valueOf(lastTrackTrain.getStationFouDistance());
                        if("布列斯特".equals(changePort)){
                            changeValue=2;
                            day=14;
                        }else if("马拉".equals(changePort)){
                            changeValue=2.25;
                            day=13.75;
                        }
                        if(site.contains("汉堡")){
                            totalDistance=1031;
                        }else if(site.contains("慕尼黑")){
                            totalDistance=1403;
                        }else if(site.contains("列日")){
                            totalDistance=1252;
                        }else if(site.contains("亨克")){
                            totalDistance=1410;
                        }
                        if(changeValue!=0){
                            expectTime= day+(changeValue/totalDistance)*(totalDistance-actualDistance);
                        }
                    }
                }
                //只有三个站点
                else if(empList.contains(lastTrackTrain.getStationThrDistance())&&lastTrackTrain.getStationFouDistance()==null){
                    if("布列斯特".equals(changePort)){
                        expectTime=13.75;
                    }else if("马拉".equals(changePort)){
                        expectTime=12.5;
                    }
                }
                //到达终点
                else if(lastTrackTrain.getStationFouDistance()!=null&&empList.contains(lastTrackTrain.getStationFouDistance())){
                    expectTime=16;
                }
            }
        }
        //欧线回程班列
        else if("0".equals(lineType)&&"1".equals(goCome)&&!site.contains("赫尔辛基")){
            //汉堡/慕尼黑/列日/亨克,始发站驶离时间
            String leaveTime=getTrackTime(site.split("-")[0],getEnStation(site.split("-")[0]),"1",trackTrainList);
            if(StringUtils.isEmpty(leaveTime)||StringUtils.isEmpty(lastTrackTrain.getTrackTime())){
                return null;
            }
            //实际总时间
            String stringTotalTime=DateUtils.getDays(leaveTime,lastTrackTrain.getTrackTime());
            totalTime=Double.valueOf(stringTotalTime);
            //阿拉山口/霍尔果斯
            if(site.contains("阿拉山口")||site.contains("霍尔果斯")){
                //第一个站点距离不为空
                if(!empList.contains(lastTrackTrain.getStationOneDistance())){
                    actualDistance=Integer.valueOf(lastTrackTrain.getStationOneDistance());
                    if(site.contains("汉堡")){
                        totalDistance=1031;
                    }else if(site.contains("慕尼黑")){
                        totalDistance=1403;
                    }else if(site.contains("列日")){
                        totalDistance=1252;
                    }else if(site.contains("亨克")){
                        totalDistance=1410;
                    }
                    expectTime= (2/totalDistance)*(totalDistance-actualDistance);
                }
                //第一个站点距离为空,第二个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationOneDistance())&&!empList.contains(lastTrackTrain.getStationTwoDistance())){
                    //站点名字为 阿拉山口/霍尔果斯
                    if(currentLocation.contains("马拉")||currentLocation.contains("Mala")||currentLocation.contains("mala")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=3;
                        }
                        //到达，在站点
                        else{
                            expectTime=2;
                        }
                    }
                }
                //第二个站点距离为空,第三个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationTwoDistance())&&!empList.contains(lastTrackTrain.getStationThrDistance())){
                    //站点为布列斯特
                    if(currentLocation.contains("布列斯特")||currentLocation.contains("Brest")||currentLocation.contains("brest")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=5;
                        }
                        //到达，在站点
                        else{
                            expectTime=3.5;
                            }
                    }
                    //站点名字为 多斯特克/阿腾科里
                    else if(currentLocation.contains("多斯特克")||currentLocation.contains("阿腾科里")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=12.88;
                        }
                        //到达，在站点
                        else{
                            expectTime=12.5;
                        }
                    }
                    //布列斯特和多斯特克/阿腾科里的运踪
                    else{
                        actualDistance=Integer.valueOf(lastTrackTrain.getStationThrDistance());
                        if(site.contains("山口")){
                            totalDistance=5466;
                        }else if(site.contains("霍尔果斯")){
                            totalDistance=5391;
                        }
                        expectTime= 5+(7.5/totalDistance)*(totalDistance-actualDistance);
                    }
                }
                //第三个站点距离为空,第四个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationThrDistance())&&!empList.contains(lastTrackTrain.getStationFouDistance())){
                   //站点名字为 阿拉山口/霍尔果斯
                   if(site.contains(currentLocation)){
                       //驶离站点
                       if("1".equals(state)){
                           expectTime=14.5;
                       }
                       //到达，在站点
                       else{
                           expectTime=13;
                       }
                   }else{
                       actualDistance=Integer.valueOf(lastTrackTrain.getStationFouDistance());
                       if(site.contains("山口")){
                           totalDistance=3669;
                       }else if(site.contains("霍尔果斯")){
                           totalDistance=3883;
                       }
                       expectTime= 13+(2.5/totalDistance)*(totalDistance-actualDistance);
                   }
                }
                //到达终点
                else if(lastTrackTrain.getStationFouDistance()!=null&&empList.contains(lastTrackTrain.getStationFouDistance())){
                    expectTime=17;
                }
            }
            //二连浩特/满洲里/绥芬河
            else if(site.contains("二连浩特")||site.contains("满洲里")||site.contains("绥芬河")){
                //第一个站点距离不为空
                if(!empList.contains(lastTrackTrain.getStationOneDistance())){
                    actualDistance=Integer.valueOf(lastTrackTrain.getStationOneDistance());
                    if(site.contains("汉堡")){
                        totalDistance=1031;
                    }else if(site.contains("慕尼黑")){
                        totalDistance=1403;
                    }else if(site.contains("列日")){
                        totalDistance=1252;
                    }else if(site.contains("亨克")){
                        totalDistance=1410;
                    }
                    expectTime= (2/totalDistance)*(totalDistance-actualDistance);
                }
                //第一个站点距离为空,第二个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationOneDistance())&&!empList.contains(lastTrackTrain.getStationTwoDistance())){
                    //站点名字为 阿拉山口/霍尔果斯
                    if(currentLocation.contains("马拉")||currentLocation.contains("Mala")||currentLocation.contains("mala")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=3;
                        }
                        //到达，在站点
                        else{
                            expectTime=2;
                        }
                    }
                }
                //第二个站点距离为空,第三个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationTwoDistance())&&!empList.contains(lastTrackTrain.getStationThrDistance())){
                    //站点为布列斯特
                    if(currentLocation.contains("布列斯特")||currentLocation.contains("Brest")||currentLocation.contains("brest")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=5;
                        }
                        //到达，在站点
                        else{
                            expectTime=3.5;
                        }
                    }
                    //站点名字为 扎门乌德/后贝加尔斯克/格罗捷克沃
                    else if(currentLocation.contains("扎门乌德")||currentLocation.contains("后贝加尔斯克")||currentLocation.contains("格罗捷克沃")){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=13.92;
                        }
                        //到达，在站点
                        else{
                            expectTime=13.71;
                        }
                    }
                    //布列斯特和扎门乌德/后贝加尔斯克/格罗捷克沃的运踪
                    else{
                        actualDistance=Integer.valueOf(lastTrackTrain.getStationThrDistance());
                        if(site.contains("二连浩特")){
                            totalDistance=7983;
                        }else if(site.contains("满洲里")){
                            totalDistance=7800;
                        }else if(site.contains("绥芬河")){
                            totalDistance=10213;
                        }
                        expectTime= 5+(8.71/totalDistance)*(totalDistance-actualDistance);
                    }
                }
                //第三个站点距离为空,第四个站点距离不为空
                else if(empList.contains(lastTrackTrain.getStationThrDistance())&&!empList.contains(lastTrackTrain.getStationFouDistance())){
                    //站点名字为 二连浩特/满洲里/绥芬河
                    if(site.contains(currentLocation)){
                        //驶离站点
                        if("1".equals(state)){
                            expectTime=15;
                        }
                        //到达，在站点
                        else{
                            expectTime=14;
                        }
                    }else{
                        actualDistance=Integer.valueOf(lastTrackTrain.getStationFouDistance());
                        if(site.contains("二连浩特")){
                            totalDistance=1403;
                        }else if(site.contains("满洲里")){
                            totalDistance=2704;
                        }else if(site.contains("绥芬河")){
                            totalDistance=2593;
                        }
                        expectTime= 15+(2/totalDistance)*(totalDistance-actualDistance);
                    }
                }
                //到达终点
                else if(lastTrackTrain.getStationFouDistance()!=null&&empList.contains(lastTrackTrain.getStationFouDistance())){
                    expectTime=17;
                }
            }

        }
        //俄线二连浩特去程班列
        else if("4".equals(lineType)&&"0".equals(goCome)&&site.contains("二连浩特")){
            //圃田,始发站驶离时间
            String leaveTime=getTrackTime("郑州圃田","zhengzhou.putian","1",trackTrainList);
            if(StringUtils.isEmpty(leaveTime)||StringUtils.isEmpty(lastTrackTrain.getTrackTime())){
                return null;
            }
            //实际总时间
            String stringTotalTime=DateUtils.getDays(leaveTime,lastTrackTrain.getTrackTime());
            totalTime=Double.valueOf(stringTotalTime);
            //第一个站点距离不为空
            if(!empList.contains(lastTrackTrain.getStationOneDistance())){
                actualDistance=Integer.valueOf(lastTrackTrain.getStationOneDistance());
                totalDistance=1403;
                expectTime= (1.5/totalDistance)*(totalDistance-actualDistance);
            }
            //第一个站点距离为空,第二个站点距离不为空
            else if(empList.contains(lastTrackTrain.getStationOneDistance())&&!empList.contains(lastTrackTrain.getStationTwoDistance())){
                //站点名字为 二连
                if(currentLocation.contains("二连")||currentLocation.contains("Erenhot")||currentLocation.contains("erenhot")){
                    //驶离站点
                    if("1".equals(state)){
                        expectTime=2.29;
                    }
                    //到达，在站点
                    else{
                        expectTime=1.5;
                    }
                }
            }
            //第二个站点距离为空,第三个站点距离不为空
            else if(empList.contains(lastTrackTrain.getStationTwoDistance())&&!empList.contains(lastTrackTrain.getStationThrDistance())){
                //站点为扎门乌德
                if(currentLocation.contains("扎门乌德")||currentLocation.contains("Zamyn-Uud")||currentLocation.contains("zamyn-uud")){
                    //驶离站点
                    if("1".equals(state)){
                        expectTime=3;
                    }
                    //到达，在站点
                    else{
                        expectTime=2.5;
                    }
                }else{
                    actualDistance=Integer.valueOf(lastTrackTrain.getStationThrDistance());
                    totalDistance=7983;
                    expectTime= 3+(9/totalDistance)*(totalDistance-actualDistance);
                }
            }
            //第三个站点距离为空,到达终点
            else if(empList.contains(lastTrackTrain.getStationThrDistance())){
                expectTime=12;
            }
        }
        //赫尔辛基二连浩特去程班列（欧线）
        else if("0".equals(lineType)&&"0".equals(goCome)&&site.contains("赫尔辛基")&&site.contains("二连浩特")){
            //圃田,始发站驶离时间
            String leaveTime=getTrackTime("郑州圃田","zhengzhou.putian","1",trackTrainList);
            if(StringUtils.isEmpty(leaveTime)||StringUtils.isEmpty(lastTrackTrain.getTrackTime())){
                return null;
            }
            //实际总时间
            String stringTotalTime=DateUtils.getDays(leaveTime,lastTrackTrain.getTrackTime());
            totalTime=Double.valueOf(stringTotalTime);
            //第一个站点距离不为空
            if(!empList.contains(lastTrackTrain.getStationOneDistance())){
                actualDistance=Integer.valueOf(lastTrackTrain.getStationOneDistance());
                totalDistance=1403;
                expectTime= (1.5/totalDistance)*(totalDistance-actualDistance);
            }
            //第一个站点距离为空,第二个站点距离不为空
            else if(empList.contains(lastTrackTrain.getStationOneDistance())&&!empList.contains(lastTrackTrain.getStationFouDistance())){
                //站点名字为 二连
                if(currentLocation.contains("二连")||currentLocation.contains("Erenhot")||currentLocation.contains("erenhot")){
                    //驶离站点
                    if("1".equals(state)){
                        expectTime=2.29;
                    }
                    //到达，在站点
                    else{
                        expectTime=1.5;
                    }
                }
                //站点为扎门乌德
                else if(currentLocation.contains("扎门乌德")||currentLocation.contains("Zamyn-Uud")||currentLocation.contains("zamyn-uud")){
                    //驶离站点
                    if("1".equals(state)){
                        expectTime=3;
                    }
                    //到达，在站点
                    else{
                        expectTime=2.5;
                    }
                }
                else{
                    actualDistance=Integer.valueOf(lastTrackTrain.getStationFouDistance());
                    totalDistance=7500;
                    expectTime= 3+(9/totalDistance)*(totalDistance-actualDistance);
                }
            }
            //第四个站点距离为空,到达终点
            else if(empList.contains(lastTrackTrain.getStationThrDistance())){
                expectTime=12;
            }
        }
        //计算时间
        doubleAnalyseResult=totalTime-expectTime;
        if(doubleAnalyseResult>0){
            analyseResult="慢"+String.format("%.2f", doubleAnalyseResult)+"天";
        }else if(doubleAnalyseResult<0){
            analyseResult="快"+String.format("%.2f", Math.abs(doubleAnalyseResult))+"天";
        }else{
            analyseResult=null;
        }
        return analyseResult;
    }

    //获取到达或驶离指定地点的时间
    public String getTrackTime(String chName,String enName,String state,List<TrackTrain>  trackTrainList){
        if(StringUtils.isEmpty(chName)||StringUtils.isEmpty(enName)){
            return null;
        }
        String trackTime =null;
        for(int i=0;i<trackTrainList.size();i++){
            if((chName.contains(trackTrainList.get(i).getCurrentLocation().toLowerCase())||enName.contains(trackTrainList.get(i).getCurrentLocation().toLowerCase()))
                    &&state.equals(trackTrainList.get(i).getStateValue())) {
                trackTime = trackTrainList.get(i).getTrackTime();
                break;
            }
        }
        return trackTime;
    }

    //欧线去程班列上有多个下货站的货物，需要判断一下到布列斯特（布列斯特，莫斯科，明斯克，圣彼得堡）、马拉（马拉，华沙，布拉格，布达佩斯）下货的货物，之后的运踪不再发送
    public boolean getSend(BusiShippingorders busiShippingorders,TrackTrain trackTrain){
        boolean send =true;
        List<String> emptyList=new ArrayList<>();
        emptyList.add("0");
        emptyList.add("/");
        List<String> brestUnLoadsite=new ArrayList<>();
        brestUnLoadsite.add("布列斯特");
        brestUnLoadsite.add("莫斯科");
        brestUnLoadsite.add("明斯克");
        brestUnLoadsite.add("圣彼得堡");
        //布列斯特下货
        if(brestUnLoadsite.contains(busiShippingorders.getOrderUnloadsite())){
            //布列斯特之前运踪发送
            if(!emptyList.contains(trackTrain.getStationTwoDistance())||(emptyList.contains(trackTrain.getStationTwoDistance())&&"0".equals(trackTrain.getStateValue())&&(trackTrain.getCurrentLocation().contains("布列斯特")
                    ||trackTrain.getCurrentLocation().contains("Brest") ||trackTrain.getCurrentLocation().contains("brest")))){
                send= true;
            }
            //布列斯特之后运踪不发送
            else{
                send= false;
            }
        }
        List<String> malaUnLoadsite=new ArrayList<>();
        malaUnLoadsite.add("马拉");
        malaUnLoadsite.add("华沙");
        malaUnLoadsite.add("布拉格");
        malaUnLoadsite.add("布达佩斯");
        //马拉下货
        if(malaUnLoadsite.contains(busiShippingorders.getOrderUnloadsite())){
            //马拉之前运踪发送
            if(!emptyList.contains(trackTrain.getStationThrDistance())||(emptyList.contains(trackTrain.getStationThrDistance())&&"0".equals(trackTrain.getStateValue())&&(trackTrain.getCurrentLocation().contains("马拉")
                    ||trackTrain.getCurrentLocation().contains("Mala") ||trackTrain.getCurrentLocation().contains("mala")))){
                send= true;
            }
            //马拉之后运踪不发送
            else{
                send= false;
            }
        }
        return send;
    }



    /**
     * 自动更新每天班列运踪状态
     */
    @Scheduled(cron="1 0 0 * * ?")
    public void autoUpdateTrackState(){
        trackTrainMapper.autoUpdateTrackState();
    }
}
