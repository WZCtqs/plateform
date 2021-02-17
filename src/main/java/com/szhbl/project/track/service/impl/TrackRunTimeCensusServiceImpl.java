package com.szhbl.project.track.service.impl;

import java.util.Date;
import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.track.domain.TrackAbroad;
import com.szhbl.project.track.domain.TrackTrain;
import com.szhbl.project.track.domain.vo.BusiSiteVo;
import com.szhbl.project.track.domain.vo.TrackRunTimeCensusVo;
import com.szhbl.project.track.mapper.TrackAbroadMapper;
import com.szhbl.project.track.mapper.TrackTrainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.track.mapper.TrackRunTimeCensusMapper;
import com.szhbl.project.track.domain.TrackRunTimeCensus;
import com.szhbl.project.track.service.ITrackRunTimeCensusService;

/**
 * 运踪_班列运行时间统计Service业务层处理
 * 
 * @author lzd
 * @date 2020-04-08
 */
@Service
public class TrackRunTimeCensusServiceImpl implements ITrackRunTimeCensusService 
{
    @Autowired
    private TrackRunTimeCensusMapper trackRunTimeCensusMapper;

    @Autowired
    private TrackTrainMapper trackTrainMapper;

    @Autowired
    private TrackAbroadMapper trackAbroadMapper;

    /**
     * 查询运踪_班列运行时间统计
     * 
     * @param id 运踪_班列运行时间统计ID
     * @return 运踪_班列运行时间统计
     */
    @Override
    public TrackRunTimeCensus selectTrackRunTimeCensusById(Long id)
    {
        return trackRunTimeCensusMapper.selectTrackRunTimeCensusById(id);
    }

    /**
     * 查询运踪_班列运行时间统计列表
     * 
     * @param trackRunTimeCensus 运踪_班列运行时间统计
     * @return 运踪_班列运行时间统计
     */
    @Override
    public List<TrackRunTimeCensus> selectTrackRunTimeCensusList(TrackRunTimeCensus trackRunTimeCensus)
    {
        return trackRunTimeCensusMapper.selectTrackRunTimeCensusList(trackRunTimeCensus);
    }

    /**
     * 新增运踪_班列运行时间统计
     * 
     * @param trackRunTimeCensus 运踪_班列运行时间统计
     * @return 结果
     */
    @Override
    public int insertTrackRunTimeCensus(TrackRunTimeCensus trackRunTimeCensus)
    {
        trackRunTimeCensus.setCreateTime(DateUtils.getNowDate());
        trackRunTimeCensus.setIsEdit(1);
        System.out.println("trackRunTimeCensus----------"+trackRunTimeCensus);
        return trackRunTimeCensusMapper.insertTrackRunTimeCensus(trackRunTimeCensus);
    }

    /**
     * 修改运踪_班列运行时间统计
     * 
     * @param trackRunTimeCensus 运踪_班列运行时间统计
     * @return 结果
     */
    @Override
    public int updateTrackRunTimeCensus(TrackRunTimeCensus trackRunTimeCensus)
    {
        trackRunTimeCensus.setUpdateTime(DateUtils.getNowDate());
        return trackRunTimeCensusMapper.updateTrackRunTimeCensus(trackRunTimeCensus);
    }

    /**
     * 批量删除运踪_班列运行时间统计
     * 
     * @param ids 需要删除的运踪_班列运行时间统计ID
     * @return 结果
     */
    @Override
    public int deleteTrackRunTimeCensusByIds(Long[] ids)
    {
        return trackRunTimeCensusMapper.deleteTrackRunTimeCensusByIds(ids);
    }

    /**
     * 删除运踪_班列运行时间统计信息
     * 
     * @param id 运踪_班列运行时间统计ID
     * @return 结果
     */
    @Override
    public int deleteTrackRunTimeCensusById(Long id)
    {
        return trackRunTimeCensusMapper.deleteTrackRunTimeCensusById(id);
    }

    /**
     * 从班列表查询运行统计时间
     *
     * @param trtcVo 运踪_班列运行时间Vo
     * @return 运踪_班列运行时间统计Vo集合
     */
    @Override
    public List<TrackRunTimeCensusVo> selectTrtcVoList(TrackRunTimeCensusVo trtcVo)
    {
        List<TrackRunTimeCensusVo> trtcVoList=trackRunTimeCensusMapper.selectTrtcVoList(trtcVo);
        if(trtcVoList!=null){
            BusiSiteVo bsVo=null;
            for(int i=0;i<trtcVoList.size();i++){
                bsVo=trackRunTimeCensusMapper.selectBusiSiteVo(trtcVoList.get(i).getClassStationofdestinationName());
                trtcVoList.get(i).setClassStationofdestinationName(bsVo.getNameCn());
                trtcVoList.get(i).setEndNameEn(bsVo.getNameEn());
                bsVo=trackRunTimeCensusMapper.selectBusiSiteVo(trtcVoList.get(i).getStartStation());
                trtcVoList.get(i).setStartStation(bsVo.getNameCn());
            }
        }
        return trtcVoList;
    }

    /**
     * 根据班列id查询运行统计时间详情
     *
     * @param trtcVo
     * @return 班列运行时间统计详情
     * redisTemplate.opsForValue().set("fileType::"+docDocumentsType.getFileTypeKey(),docDocumentsType);
     */
    @Override
    public TrackRunTimeCensus selectTrtc(TrackRunTimeCensusVo trtcVo)
    {
        TrackRunTimeCensus trackRunTimeCensus=null;
        String classId=trtcVo.getClassId();
        if(trtcVo.getIsEdit()==1){
            trackRunTimeCensus= trackRunTimeCensusMapper.selectTrtcByClassId(classId);
        }else{
            //2020-04-10
            String Stime=trtcVo.getClassSTime().substring(0,10);
            String endes=trtcVo.getEndNameEn().toLowerCase();
            String chdes=trtcVo.getClassStationofdestinationName();
            trackRunTimeCensus=new TrackRunTimeCensus();
            trackRunTimeCensus.setClassDate(Stime);//班列日期
            Date prTime=DateUtils.dateTime("yyyy-MM-dd",Stime);
            trackRunTimeCensus.setClassEastAndWest(trtcVo.getClassEastAndWest());
            TrackTrain trackTrain=new TrackTrain();
            trackTrain.setClassId(classId);
            List<TrackTrain>  TrackTrainList=trackTrainMapper.selectTrackTrainList(trackTrain);
            StringBuilder delayReason=new StringBuilder();
            String trackTime=null;
            String arriveOneTime=null;
            String departOneTime=null;
            String arriveTwoTime=null;
            String departTwoTime=null;
            String arriveThrTime=null;
            String departThrTime=null;
            String arriveFouTime=null;
            String departFouTime=null;
            String destinationTime=null;

            String domesticDay=null;
            String oneStayDay=null;
            String oneTwoDay=null;
            String twoStayDay=null;
            String twoThrDay=null;
            String thrStayDay=null;
            String thrFouDay=null;
            String fouStayDay=null;
            String lastDestinationDate=null;
            String territoryDay=null;
            String totalDay=null;
            if("0".equals(trtcVo.getClassEastAndWest())){//去,西
                trackRunTimeCensus.setPlanRunTime(DateUtils.parseDateToStr("yyyy-MM-dd",DateUtils.dataChangeAdd(prTime,1))+" 03:26:00");//计划发车
                switch (trtcVo.getClassBlockTrain()){
                    case "郑欧班列-山口":
                    case "郑州-山口-马拉":
                        trackRunTimeCensus.setPort("山口");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0){
                            trackRunTimeCensus.setActualRunTime(TrackTrainList.get(TrackTrainList.size()-1).getTrackTime());//实际发车
                            dealTime(TrackTrainList,arriveOneTime,departOneTime,arriveTwoTime,
                                    departTwoTime, arriveThrTime,departThrTime, arriveFouTime, departFouTime,
                                    destinationTime,"阿拉山口","alashankou","多斯特克","dostyk",
                                    "布列斯特","brest","马拉舍维奇", "malaszewicze",
                                    chdes, endes,delayReason);
                            domesticDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveOneTime);
                            oneStayDay=getDays(arriveOneTime,departOneTime);
                            oneTwoDay=getDays(departOneTime,arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime,departTwoTime);
                            twoThrDay=getDays(departTwoTime,arriveThrTime);
                            thrStayDay=getDays(arriveThrTime,departThrTime);
                            thrFouDay=getDays(departThrTime,arriveFouTime);
                            fouStayDay=getDays(arriveFouTime,departFouTime);
                            lastDestinationDate=getDays(departFouTime,destinationTime);
                            territoryDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveTwoTime);
                            totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                        }
                        break;
                    case "郑欧班列-二连":
                        trackRunTimeCensus.setPort("二连");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0){
                            trackRunTimeCensus.setActualRunTime(TrackTrainList.get(TrackTrainList.size()-1).getTrackTime());//实际发车
                            dealTime(TrackTrainList,arriveOneTime,departOneTime,arriveTwoTime,
                                    departTwoTime, arriveThrTime,departThrTime, arriveFouTime, departFouTime,
                                    destinationTime,"二连浩特","erenhot","扎门乌德","zamyn-uud",
                                    "布列斯特","brest","马拉舍维奇", "malaszewicze",
                                    chdes, endes,delayReason);
                            domesticDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveOneTime);
                            oneStayDay=getDays(arriveOneTime,departOneTime);
                            oneTwoDay=getDays(departOneTime,arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime,departTwoTime);
                            twoThrDay=getDays(departTwoTime,arriveThrTime);
                            thrStayDay=getDays(arriveThrTime,departThrTime);
                            thrFouDay=getDays(departThrTime,arriveFouTime);
                            fouStayDay=getDays(arriveFouTime,departFouTime);
                            lastDestinationDate=getDays(departFouTime,destinationTime);
                            territoryDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveTwoTime);
                            totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                        }
                        break;
                    case "郑欧班列-绥芬河":
                        trackRunTimeCensus.setPort("绥芬河");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0){
                            trackRunTimeCensus.setActualRunTime(TrackTrainList.get(TrackTrainList.size()-1).getTrackTime());//实际发车
                            dealTime(TrackTrainList,arriveOneTime,departOneTime,arriveTwoTime,
                                    departTwoTime, arriveThrTime,departThrTime, arriveFouTime, departFouTime,
                                    destinationTime,"绥芬河","suifenhe","格罗捷克沃","grodekovo",
                                    "布列斯特","brest","马拉舍维奇", "malaszewicze",
                                    chdes, endes,delayReason);
                            domesticDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveOneTime);
                            oneStayDay=getDays(arriveOneTime,departOneTime);
                            oneTwoDay=getDays(departOneTime,arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime,departTwoTime);
                            twoThrDay=getDays(departTwoTime,arriveThrTime);
                            thrStayDay=getDays(arriveThrTime,departThrTime);
                            thrFouDay=getDays(departThrTime,arriveFouTime);
                            fouStayDay=getDays(arriveFouTime,departFouTime);
                            lastDestinationDate=getDays(departFouTime,destinationTime);
                            territoryDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveTwoTime);
                            totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                        }
                        break;
                    case "郑欧班列-霍尔果斯":
                        trackRunTimeCensus.setPort("霍尔果斯");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0){
                            trackRunTimeCensus.setActualRunTime(TrackTrainList.get(TrackTrainList.size()-1).getTrackTime());//实际发车
                            dealTime(TrackTrainList,arriveOneTime,departOneTime,arriveTwoTime,
                                    departTwoTime, arriveThrTime,departThrTime, arriveFouTime, departFouTime,
                                    destinationTime,"霍尔果斯","khorgos","阿腾科里","altynkol",
                                    "布列斯特","brest","马拉舍维奇", "malaszewicze",
                                    chdes, endes,delayReason);
                            domesticDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveOneTime);
                            oneStayDay=getDays(arriveOneTime,departOneTime);
                            oneTwoDay=getDays(departOneTime,arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime,departTwoTime);
                            twoThrDay=getDays(departTwoTime,arriveThrTime);
                            thrStayDay=getDays(arriveThrTime,departThrTime);
                            thrFouDay=getDays(departThrTime,arriveFouTime);
                            fouStayDay=getDays(arriveFouTime,departFouTime);
                            lastDestinationDate=getDays(departFouTime,destinationTime);
                            territoryDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveTwoTime);
                            totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                        }
                        break;
                    case "中亚班列":
                        trackRunTimeCensus.setPort("霍尔果斯");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0) {
                            String actualTime = null;
                            for (int i = 0; i < TrackTrainList.size(); i++) {//state "0"到达"1"驶离"2"在
                                trackTime = getTrackTime("郑州圃田", "zhengzhou.putian", "1", TrackTrainList.get(i));
                                actualTime = trackTime == null ? actualTime : trackTime;
                                trackTime = getTrackTime("霍尔果斯", "khorgos", "0", TrackTrainList.get(i));
                                arriveOneTime = trackTime == null ? arriveOneTime : trackTime;
                                trackTime = getTrackTime("霍尔果斯", "khorgos", "1", TrackTrainList.get(i));
                                departOneTime = trackTime == null ? departOneTime : trackTime;
                                if (TrackTrainList.get(i).getRemark() != null) {
                                    delayReason.append(TrackTrainList.get(i).getRemark() + "\n");
                                }
                            }
                            trackRunTimeCensus.setActualRunTime(actualTime);//实际发车
                            TrackAbroad trackAbroad=new TrackAbroad();
                            trackAbroad.setClassId(classId);
                            List<TrackAbroad>  trackAbroadList=trackAbroadMapper.selectTrackAbroadList(trackAbroad);
                            String changeTime=null;
                            for(int j = 0; j < trackAbroadList.size(); j++){
                                if(trackAbroadList.get(j).getAbroadContents().contains("阿腾科里")){
                                    arriveTwoTime=trackAbroadList.get(j).getTrackTime();
                                    changeTime=trackAbroadList.get(j).getChangeTime();
                                    if(StringUtils.isNotEmpty(arriveTwoTime)){
                                    arriveTwoTime=DateUtils.getCurrentYear()+"-"+arriveTwoTime.replaceAll(".","-");
                                    }
                                    if(StringUtils.isNotEmpty(changeTime)){
                                        changeTime=DateUtils.getCurrentYear()+"-"+changeTime.split("/")[1].replaceAll(".","-");
                                    }
                                }
                            }
                            trackRunTimeCensus.setChangeDate(changeTime);
                            domesticDay = getDays(trackRunTimeCensus.getActualRunTime(), arriveOneTime);
                            oneStayDay = getDays(arriveOneTime, departOneTime);
                            oneTwoDay = getDays(departOneTime, arriveTwoTime+"00:00:00");
                            territoryDay = getDays(trackRunTimeCensus.getActualRunTime(), arriveTwoTime+"00:00:00");
                        }
                        break;
                    case "越南":
                        trackRunTimeCensus.setPort("凭祥");
                        trackRunTimeCensus.setActualRunTime(trtcVo.getClassSTime());//实际发车
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0) {
                            for (int i = 0; i < TrackTrainList.size(); i++) {//state "0"到达"1"驶离"2"在
                                trackTime = getTrackTime("凭祥", "pinxiang", "0", TrackTrainList.get(i));
                                arriveOneTime = trackTime == null ? arriveOneTime : trackTime;
                                trackTime = getTrackTime("凭祥", "pinxiang", "1", TrackTrainList.get(i));
                                departOneTime = trackTime == null ? departOneTime : trackTime;
                                trackTime = getTrackTime("河内", "hanoi", "0", TrackTrainList.get(i));
                                arriveTwoTime = trackTime == null ? arriveTwoTime : trackTime;
                                if (TrackTrainList.get(i).getRemark() != null) {
                                    delayReason.append(TrackTrainList.get(i).getRemark() + "\n");
                                }
                            }
                            domesticDay = getDays(trackRunTimeCensus.getActualRunTime(), arriveOneTime);
                            oneStayDay = getDays(arriveOneTime, departOneTime);
                            oneTwoDay = getDays(departOneTime, arriveTwoTime);
                            territoryDay = getDays(trackRunTimeCensus.getActualRunTime(), arriveOneTime);
                            totalDay=getDays(trackRunTimeCensus.getActualRunTime(),arriveTwoTime);
                        }
                        break;
                }
            }else if("1".equals(trtcVo.getClassEastAndWest())){//回
                trackRunTimeCensus.setPlanRunTime(DateUtils.parseDateToStr("yyyy-MM-dd",DateUtils.dataChangeAdd(prTime,1))+" 12:00:00");//计划发车
                switch (trtcVo.getClassBlockTrain()){
                    case "郑欧班列-山口":
                        trackRunTimeCensus.setPort("山口");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0){
                            trackRunTimeCensus.setActualRunTime(getActualTime(TrackTrainList));//实际发车
                            dealTime(TrackTrainList,arriveOneTime,departOneTime,arriveTwoTime,
                                    departTwoTime, arriveThrTime,departThrTime, arriveFouTime, departFouTime,
                                    destinationTime,"马拉舍维奇","malaszewicze","布列斯特","brest",
                                    "多斯特克","dostyk","阿拉山口","alashankou",
                                    "郑州圃田", "zhengzhou.putian",delayReason);
                            lastDestinationDate=getDays(trackRunTimeCensus.getActualRunTime(),arriveOneTime);//始发站到第一个站点时间
                            oneStayDay=getDays(arriveOneTime,departOneTime);
                            oneTwoDay=getDays(departOneTime,arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime,departTwoTime);
                            twoThrDay=getDays(departTwoTime,arriveThrTime);
                            thrStayDay=getDays(arriveThrTime,departThrTime);
                            thrFouDay=getDays(departThrTime,arriveFouTime);
                            fouStayDay=getDays(arriveFouTime,departFouTime);
                            domesticDay=getDays(departFouTime,destinationTime);
                            territoryDay=getDays(arriveFouTime,destinationTime);
                            if(trackRunTimeCensus.getActualRunTime()!=null&&trackRunTimeCensus.getPlanRunTime()!=null){
                                if(trackRunTimeCensus.getActualRunTime().compareTo(trackRunTimeCensus.getPlanRunTime())<=0){
                                    totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                                }else{
                                    totalDay=getDays(trackRunTimeCensus.getPlanRunTime(),destinationTime);
                                }
                            }
                        }
                        break;
                    case "郑欧班列-二连":
                        trackRunTimeCensus.setPort("二连");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0){
                            trackRunTimeCensus.setActualRunTime(getActualTime(TrackTrainList));//实际发车
                            dealTime(TrackTrainList,arriveOneTime,departOneTime,arriveTwoTime,
                                    departTwoTime, arriveThrTime,departThrTime, arriveFouTime, departFouTime,
                                    destinationTime,"马拉舍维奇","malaszewicze","布列斯特","brest",
                                    "扎门乌德","zamyn-uud","二连浩特","erenhot",
                                    "郑州圃田", "zhengzhou.putian",delayReason);
                            lastDestinationDate=getDays(trackRunTimeCensus.getActualRunTime(),arriveOneTime);//始发站到第一个站点时间
                            oneStayDay=getDays(arriveOneTime,departOneTime);
                            oneTwoDay=getDays(departOneTime,arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime,departTwoTime);
                            twoThrDay=getDays(departTwoTime,arriveThrTime);
                            thrStayDay=getDays(arriveThrTime,departThrTime);
                            thrFouDay=getDays(departThrTime,arriveFouTime);
                            fouStayDay=getDays(arriveFouTime,departFouTime);
                            domesticDay=getDays(departFouTime,destinationTime);
                            territoryDay=getDays(arriveFouTime,destinationTime);
                            if(trackRunTimeCensus.getActualRunTime()!=null&&trackRunTimeCensus.getPlanRunTime()!=null){
                                if(trackRunTimeCensus.getActualRunTime().compareTo(trackRunTimeCensus.getPlanRunTime())<=0){
                                    totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                                }else{
                                    totalDay=getDays(trackRunTimeCensus.getPlanRunTime(),destinationTime);
                                }
                            }
                        }
                        break;
                    case "郑欧班列-绥芬河":
                        trackRunTimeCensus.setPort("绥芬河");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0){
                            trackRunTimeCensus.setActualRunTime(getActualTime(TrackTrainList));//实际发车
                            dealTime(TrackTrainList,arriveOneTime,departOneTime,arriveTwoTime,
                                    departTwoTime, arriveThrTime,departThrTime, arriveFouTime, departFouTime,
                                    destinationTime,"马拉舍维奇","malaszewicze","布列斯特","brest",
                                    "格罗捷克沃","grodekovo","绥芬河","suifenhe",
                                    "郑州圃田", "zhengzhou.putian",delayReason);
                            lastDestinationDate=getDays(trackRunTimeCensus.getActualRunTime(),arriveOneTime);//始发站到第一个站点时间
                            oneStayDay=getDays(arriveOneTime,departOneTime);
                            oneTwoDay=getDays(departOneTime,arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime,departTwoTime);
                            twoThrDay=getDays(departTwoTime,arriveThrTime);
                            thrStayDay=getDays(arriveThrTime,departThrTime);
                            thrFouDay=getDays(departThrTime,arriveFouTime);
                            fouStayDay=getDays(arriveFouTime,departFouTime);
                            domesticDay=getDays(departFouTime,destinationTime);
                            territoryDay=getDays(arriveFouTime,destinationTime);
                            if(trackRunTimeCensus.getActualRunTime()!=null&&trackRunTimeCensus.getPlanRunTime()!=null){
                                if(trackRunTimeCensus.getActualRunTime().compareTo(trackRunTimeCensus.getPlanRunTime())<=0){
                                    totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                                }else{
                                    totalDay=getDays(trackRunTimeCensus.getPlanRunTime(),destinationTime);
                                }
                            }
                        }
                        break;
                    case "中亚班列":
                        trackRunTimeCensus.setPort("霍尔果斯");
                        if(TrackTrainList!=null&&TrackTrainList.size()!=0) {
                            trackRunTimeCensus.setActualRunTime(trtcVo.getClassSTime());//实际发车
                            for (int i = 0; i < TrackTrainList.size(); i++) {//state "0"到达"1"驶离"2"在
                                trackTime = getTrackTime("霍尔果斯", "khorgos", "0", TrackTrainList.get(i));
                                arriveTwoTime = trackTime == null ? arriveTwoTime : trackTime;
                                trackTime = getTrackTime("霍尔果斯", "khorgos", "1", TrackTrainList.get(i));
                                departTwoTime = trackTime == null ? departTwoTime : trackTime;
                                trackTime = getTrackTime("郑州圃田", "zhengzhou.putian", "0", TrackTrainList.get(i));
                                destinationTime = trackTime == null ? destinationTime : trackTime;
                                if (TrackTrainList.get(i).getRemark() != null) {
                                    delayReason.append(TrackTrainList.get(i).getRemark() +"\n");
                                }
                            }
                            TrackAbroad trackAbroad=new TrackAbroad();
                            trackAbroad.setClassId(classId);
                            List<TrackAbroad>  trackAbroadList=trackAbroadMapper.selectTrackAbroadList(trackAbroad);

                            for(int j = 0; j < trackAbroadList.size(); j++){
                                if(trackAbroadList.get(j).getAbroadContents().contains("阿腾科里")){
                                    arriveOneTime=trackAbroadList.get(j).getTrackTime();
                                    if(StringUtils.isNotEmpty(arriveTwoTime)){
                                        arriveTwoTime=DateUtils.getCurrentYear()+"-"+arriveTwoTime.replaceAll(".","-");
                                    }
                                }
                            }
                            domesticDay = getDays(departTwoTime,destinationTime);
                            oneStayDay = getDays(arriveOneTime+"00:00:00", arriveOneTime+"00:00:00");
                            oneTwoDay = getDays(arriveOneTime+"00:00:00", arriveTwoTime);
                            twoStayDay=getDays(arriveTwoTime, departTwoTime);
                            territoryDay = getDays(arriveOneTime+"00:00:00", destinationTime);
                            totalDay=getDays(trackRunTimeCensus.getActualRunTime(),destinationTime);
                        }
                        break;
                }
            }
            trackRunTimeCensus.setArriveOneTime(arriveOneTime);
            trackRunTimeCensus.setArriveTwoTime(arriveTwoTime);
            trackRunTimeCensus.setArriveThrTime(arriveThrTime);
            trackRunTimeCensus.setArriveFouTime(arriveFouTime);
            trackRunTimeCensus.setDepartOneTime(departOneTime);
            trackRunTimeCensus.setDepartTwoTime(departTwoTime);
            trackRunTimeCensus.setDepartThrTime(departThrTime);
            trackRunTimeCensus.setDepartFouTime(departFouTime);
            trackRunTimeCensus.setDestinationTime(destinationTime);
            trackRunTimeCensus.setDomesticDay(domesticDay);
            trackRunTimeCensus.setOneStayDay(oneStayDay);
            trackRunTimeCensus.setTwoStayDay(twoStayDay);
            trackRunTimeCensus.setThrStayDay(thrStayDay);
            trackRunTimeCensus.setFouStayDay(fouStayDay);
            trackRunTimeCensus.setOneTwoDay(oneTwoDay);
            trackRunTimeCensus.setTwoThrDay(twoThrDay);
            trackRunTimeCensus.setThrFouDay(thrFouDay);
            trackRunTimeCensus.setLastDestinationDate(lastDestinationDate);
            trackRunTimeCensus.setTerritoryDay(territoryDay);
            trackRunTimeCensus.setTotalDay(totalDay);
            trackRunTimeCensus.setClassId(classId);
        }
        trackRunTimeCensus.setClassEastAndWest(trtcVo.getClassEastAndWest());
        trackRunTimeCensus.setClassBlockTrain(trtcVo.getClassBlockTrain());
        return trackRunTimeCensus;
    }

    //获取回程实际发车时间
    public String getActualTime(List<TrackTrain>  TrackTrainList){
        String actualTime =null;
        String trackTime =null;
       for(int i = 0; i < TrackTrainList.size(); i++){
           trackTime = getTrackTime("列日", "liege", "1", TrackTrainList.get(i));
           actualTime = trackTime == null ? actualTime : trackTime;
           trackTime = getTrackTime("慕尼黑", "munich", "1", TrackTrainList.get(i));
           actualTime = trackTime == null ? actualTime : trackTime;
           trackTime = getTrackTime("汉堡", "hamburg", "1", TrackTrainList.get(i));
           actualTime = trackTime == null ? actualTime : trackTime;
       }
        return actualTime;
    }

    //处理运踪时间
    public void dealTime(  List<TrackTrain>  TrackTrainList,String arriveOneTime, String departOneTime, String arriveTwoTime, String departTwoTime,
                           String arriveThrTime, String departThrTime, String arriveFouTime, String departFouTime, String destinationTime,
                           String chAddressOne, String enAddressOne, String chAddressTwo, String enAddressTwo, String chAddressThr, String enAddressThr,
                           String chAddressFou, String enAddressFou, String chAddressFiv, String enAddressFiv, StringBuilder delayReason
                          ) {
            String trackTime=null;
            for (int i = 0; i < TrackTrainList.size(); i++) {//state "0"到达"1"驶离"2"在
                trackTime = getTrackTime(chAddressOne, enAddressOne, "0", TrackTrainList.get(i));
                arriveOneTime = trackTime == null ? arriveOneTime : trackTime;
                trackTime = getTrackTime(chAddressOne, enAddressOne, "1", TrackTrainList.get(i));
                departOneTime = trackTime == null ? departOneTime : trackTime;
                trackTime = getTrackTime(chAddressTwo, enAddressTwo, "0", TrackTrainList.get(i));
                arriveTwoTime = trackTime == null ? arriveTwoTime : trackTime;
                trackTime = getTrackTime(chAddressTwo, enAddressTwo, "1", TrackTrainList.get(i));
                departTwoTime = trackTime == null ? departTwoTime : trackTime;
                trackTime = getTrackTime(chAddressThr, enAddressThr, "0", TrackTrainList.get(i));
                arriveThrTime = trackTime == null ? arriveThrTime : trackTime;
                trackTime = getTrackTime(chAddressThr, enAddressThr, "1", TrackTrainList.get(i));
                departThrTime = trackTime == null ? departThrTime : trackTime;
                trackTime = getTrackTime(chAddressFou, enAddressFou, "0", TrackTrainList.get(i));
                arriveFouTime = trackTime == null ? arriveFouTime : trackTime;
                trackTime = getTrackTime(chAddressFou, enAddressFou, "1", TrackTrainList.get(i));
                departFouTime = trackTime == null ? departFouTime : trackTime;
                trackTime = getTrackTime(chAddressFiv, enAddressFiv, "0", TrackTrainList.get(i));
                destinationTime = trackTime == null ? destinationTime : trackTime;
                if(TrackTrainList.get(i).getRemark()!=null){
                    delayReason.append(TrackTrainList.get(i).getRemark()+"\n");
                }
        }
    }

    //获取到达或驶离指定地点的时间
    public String getTrackTime(String chName,String enName,String state,TrackTrain trackTrain){
        String trackTime =null;
        if((chName.contains(trackTrain.getCurrentLocation().toLowerCase())||enName.contains(trackTrain.getCurrentLocation().toLowerCase()))
                &&state.equals(trackTrain.getStateValue())) {
            trackTime = trackTrain.getTrackTime();
        }
        return trackTime;
    }

    //获取两个时间相差天数
    public String getDays(String startTime,String endTime){
        String days =null;
        if(startTime!=null&&endTime!=null){
            days=DateUtils.getDays(startTime,endTime);
        }
        return days;
    }

}
