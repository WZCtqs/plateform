package com.szhbl.project.track.service.impl;

import java.util.*;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.documentcenter.domain.vo.BusiShippingorder;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import com.szhbl.project.track.domain.*;
import com.szhbl.project.track.domain.vo.*;
import com.szhbl.project.track.mapper.*;
import com.szhbl.project.track.service.ITrackGoodsStatusService;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.service.IBusiClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.szhbl.project.track.service.ITrackOneLevelService;

/**
 * 运踪一级节点Service业务层处理
 * 
 * @author lzd
 * @date 2020-03-23
 */
@Service
public class TrackOneLevelServiceImpl implements ITrackOneLevelService 
{
    @Autowired
    private TrackOneLevelMapper trackOneLevelMapper;

    @Autowired
    private TrackRunTimeCensusMapper trackRunTimeCensusMapper;

    @Autowired
    private TrackTrainMapper trackTrainMapper;

    @Autowired
    private TrackAbroadMapper trackAbroadMapper;

    @Autowired
    private TrackGoodsStatusMapper trackGoodsStatusMapper;

    @Autowired
    private IBusiClassesService busiClassesService;

    @Autowired
    private TrackAbnormalBoxMapper trackAbnormalBoxMapper;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private ITrackGoodsStatusService trackGoodsStatusService;

    @Autowired
    private IBusiZyInfoService busiZyInfoService;
    /**
     * 查询运踪一级节点
     * 
     * @param id 运踪一级节点ID
     * @return 运踪一级节点
     */
    @Override
    public TrackOneLevel selectTrackOneLevelById(Long id)
    {
        return trackOneLevelMapper.selectTrackOneLevelById(id);
    }

    /**
     * 查询运踪一级节点列表
     * 
     * @param trackOneLevel 运踪一级节点
     * @return 运踪一级节点
     */
    @Override
    public List<TrackOneLevel> selectTrackOneLevelList(TrackOneLevel trackOneLevel)
    {
        return trackOneLevelMapper.selectTrackOneLevelList(trackOneLevel);
    }

    /**
     * 新增运踪一级节点
     * 
     * @param trackOneLevel 运踪一级节点
     * @return 结果
     */
    @Override
    public int insertTrackOneLevel(TrackOneLevel trackOneLevel)
    {
        trackOneLevel.setCreateTime(DateUtils.getNowDate());
        trackOneLevel.setUpdateTime(DateUtils.getNowDate());
        int i =trackOneLevelMapper.insertTrackOneLevel(trackOneLevel);
        updateName(trackOneLevel.getOrderId());
        return i;
    }

    /**
     * 修改运踪一级节点
     * 
     * @param trackOneLevel 运踪一级节点
     * @return 结果
     */
    @Override
    public int updateTrackOneLevel(TrackOneLevel trackOneLevel)
    {
        trackOneLevel.setUpdateTime(DateUtils.getNowDate());
        return trackOneLevelMapper.updateTrackOneLevel(trackOneLevel);
    }

    /**
     * 批量删除运踪一级节点
     * 
     * @param ids 需要删除的运踪一级节点ID
     * @return 结果
     */
    @Override
    public int deleteTrackOneLevelByIds(Long[] ids)
    {
        return trackOneLevelMapper.deleteTrackOneLevelByIds(ids);
    }

    /**
     * 删除运踪一级节点信息
     * 
     * @param id 运踪一级节点ID
     * @return 结果
     */
    @Override
    public int deleteTrackOneLevelById(Long id)
    {
        TrackOneLevel tol =trackOneLevelMapper.selectTrackOneLevelById(id);
        int i=trackOneLevelMapper.deleteTrackOneLevelById(id);
        updateName(tol.getOrderId());
        return i;
    }

    //根据orderid删除
    @Override
    public int deleteTrackOneLevelByOrderId(String orderId){
        int i=trackOneLevelMapper.deleteTrackOneLevelByOrderId(orderId);
        updateName(orderId);
        return i;
    }
    /**
     * 运踪查询
     *
     * @param trackVo 运踪查询vo
     * @return
     */
    @Override
    public List<TrackQueryVo> selectTrackList(TrackQueryVo trackVo)
    {
        String deptCode = trackVo.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (trackVo.getDeptCode().contains("YWB")) {
                if (trackVo.getDeptCode().length() > 15) {
                    trackVo.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    trackVo.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    trackVo.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    trackVo.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    trackVo.setReadType("5");
                } else {
                    trackVo.setReadType("1");
                }
            }
        }
        List<TrackQueryVo> list=trackOneLevelMapper.selectTrackList(trackVo);
        //异常箱运踪返回
        for(int i=0;i<list.size();i++){
            if("班列运踪".equals(list.get(i).getTrackState())&&StringUtils.isNotEmpty(list.get(i).getBoxNum())){
                TrackGoodsStatus trackGoodsStatus=new TrackGoodsStatus();
                trackGoodsStatus.setBoxNum(list.get(i).getBoxNum());
                trackGoodsStatus.setOrderId(list.get(i).getOrderId());
                TrackGoodsStatus checkTgs=trackGoodsStatusMapper.checkTgs(trackGoodsStatus);
                if(1==checkTgs.getIsNormal()){
                    list.get(i).setTrackState("异常箱运踪");
                }
            }
        }
        return list;
    }

    //根据orderId获取一级运踪
    @Override
    public Map<String, Object> selectTolByOrderId(String orderId,String boxNum)
    {
        Map<String, Object> map = new HashMap<>();
        List<TrackOneLevel> list=trackOneLevelMapper.selectTolByOrderId(orderId);
        map.put("abnormal",0);
        if(StringUtils.isNotEmpty(boxNum)&&!"null".equals(boxNum)){
            TrackGoodsStatus trackGoodsStatus=new TrackGoodsStatus();
            trackGoodsStatus.setBoxNum(boxNum);
            trackGoodsStatus.setOrderId(orderId);
            TrackGoodsStatus checkTgs=trackGoodsStatusMapper.checkTgs(trackGoodsStatus);
            if(1==checkTgs.getIsNormal()&&null!=list.get(3).getState()&&1==list.get(3).getState()){//是否正常箱0是1否
                list.get(3).setState(2);//班列运踪异常
                list.get(3).setNameZh("异常箱运踪");
                //添加异常箱运踪信息
                TrackAbnormalBox trackAbnormalBox=new TrackAbnormalBox();
                trackAbnormalBox.setOrderId(orderId);
                trackAbnormalBox.setBoxNum(boxNum);
                List<TrackAbnormalBox> abnormalList = trackAbnormalBoxMapper.selectTrackAbnormalBoxList(trackAbnormalBox);
                map.put("abnormalBox",abnormalList);
                map.put("abnormal",1);
            }
        }
        ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
        //中亚去程只显示到班列运踪，已进站添加计划班列号备注
        if("0".equals(shippingOrder.getClassEastandwest())&&"2".equals(shippingOrder.getLineTypeid())){
            list=list.subList(0,4);
            //获取计划班列号
            BusiZyInfo zyInfo=busiZyInfoService.selectZyInfoByOrder(orderId,boxNum);
            //tol.id,tol.order_id,IFNULL(tol.name_zh,ti.name_zh) name_zh,IFNULL(tol.name_en,ti.name_en) name_en,ti.sort,tol.state,DATE_FORMAT(tol.time,'%Y-%m-%d') time,ti.icon
            if(list.get(2).getState()!=null){
                list.get(2).setRemark(StringUtils.isNotEmpty(list.get(2).getRemark())?list.get(2).getRemark():""+"计划"+zyInfo.getClasszyNo());
            }
        }else if("1".equals(shippingOrder.getClassEastandwest())&&"2".equals(shippingOrder.getLineTypeid())){
            list.remove(1);
            list.remove(1);
        }
        map.put("oneNode",list);
        return map;
    }

    //根据orderId查询发货地和收货地
    @Override
    public TrackOneLevelVo selectAddrssByOrderId(String orderId)
    {
        TrackOneLevelVo trackOneLevelVo=trackOneLevelMapper.selectAddrssByOrderId(orderId);
        //trackOneLevelVo.setStartAddress(trackRunTimeCensusMapper.selectBusiSiteVo(trackOneLevelVo.getStartAddress()).getNameCn());
        //trackOneLevelVo.setEndAddress(trackRunTimeCensusMapper.selectBusiSiteVo(trackOneLevelVo.getEndAddress()).getNameCn());
        return trackOneLevelVo;
    }

    //根据classId查询班列运踪
    @Override
    public Map<String, Object> selectTrainListByClassId(String classId,String orderId,String boxNum)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("abroad",0);
        ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
        if("2".equals(shippingOrder.getLineTypeid())&&"1".equals(shippingOrder.getClassEastandwest())){
            map.put("abroad",2);
        }
        List<TrackTrainResultVo> ttrvoList=new ArrayList<>();
        //获取实际班列日期
        TrackGoodsStatus tgs=new TrackGoodsStatus();
        tgs.setOrderId(orderId);
        tgs.setBoxNum(boxNum);
        TrackGoodsStatus testTgs =trackGoodsStatusService.checkTgs(tgs);
        String actualDate=null;
        /*String batchTime=null;
        String abnormalTime=null;*/
        if(testTgs!=null){
            actualDate=testTgs.getActualClassDate();
            testTgs.setActualClassId(classId);
            /*if(testTgs.getAbnormalTime()!=null){
                abnormalTime=DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",testTgs.getAbnormalTime());
            }*/
        }

        //实际班列日期为空班列运踪为空
        if(StringUtils.isEmpty(actualDate)||"/".equals(actualDate)){
            map.put("trainList",ttrvoList);
            return map;
        }else{
            //带x,显示这个仓位号和箱号在改为10.22p-x之前10.22p所有的班列运踪+改之后10.22p-x的班列运踪，获取修改成X的时间
           /* if(actualDate.contains("X")||actualDate.contains("x")){
                Date batTime= testTgs.getBatchTime();
                batchTime=DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",batTime);
            }*/
            //不带x,显示不带x的所有班列运踪
            //ttrvoList=trackOneLevelMapper.selectTrainListByClassId(classId,actualDate,batchTime,abnormalTime);
            ttrvoList=trackOneLevelMapper.selectTrainListByTgs(testTgs);
        }

        //中亚去程有境外运踪，中亚去程获取出境日期，回程没有
        if("2".equals(shippingOrder.getLineTypeid())&&"0".equals(shippingOrder.getClassEastandwest())){
    /*        if(ttrvoList!=null&&ttrvoList.size()!=0) {
                String gocome = "1";//"0"到达"1"驶离"2"在
                String chPort = ttrvoList.get(0).getChPort();
                String enPort = ttrvoList.get(0).getEnPort();
                for (int i = 0; i < ttrvoList.size(); i++) {//state "0"到达"1"驶离"2"在
                    if (StringUtils.isEmpty(chPort)) {
                        chPort = "0";
                    }
                    if (StringUtils.isEmpty(enPort)) {
                        enPort = "0";
                    }
                    if ((chPort.contains(StringUtils.isNotEmpty(ttrvoList.get(i).getCurrentLocation()) ? ttrvoList.get(i).getCurrentLocation().toLowerCase() : "") ||
                            enPort.contains(StringUtils.isNotEmpty(ttrvoList.get(i).getCurrentLocation()) ? ttrvoList.get(i).getCurrentLocation().toLowerCase() : ""))
                            && gocome.equals(ttrvoList.get(i).getState())) {
                        trackTime = ttrvoList.get(i).getTrackTime();
                    }
                }
            }*/
            TrackAbroad trackAbroad = new TrackAbroad();
            trackAbroad.setOrderId(orderId);
            trackAbroad.setBoxNum(boxNum);
            List<TrackAbroad> trackAbroadList = trackAbroadMapper.selectTrackAbroadList(trackAbroad);
          /*  if (trackAbroadList != null && trackAbroadList.size() != 0) {
                for (int n = 0; n < trackAbroadList.size(); n++) {
                    trackAbroadList.get(n).setPortDate(trackAbroadList.get(n).getLeaveTime());
                }
            }*/
            map.put("abroadList",trackAbroadList);
            map.put("abroad",1);
        }
        //欧线去程班列上有多个下货站的货物，需要判断一下到布列斯特（布列斯特，莫斯科，明斯克，圣彼得堡）、马拉（马拉，华沙，布拉格，布达佩斯）下货的货物，之后的运踪不再显示
        if("0".equals(shippingOrder.getLineTypeid())&&"0".equals(shippingOrder.getClassEastandwest())){
            List<TrackTrainResultVo> actualList=new ArrayList<>();
            List<String> emptyList=new ArrayList<>();
            emptyList.add("0");
            emptyList.add("/");
            List<String> brestUnLoadsite=new ArrayList<>();
            brestUnLoadsite.add("布列斯特");
            brestUnLoadsite.add("莫斯科");
            brestUnLoadsite.add("明斯克");
            brestUnLoadsite.add("圣彼得堡");
            //布列斯特下货
            if(brestUnLoadsite.contains(shippingOrder.getOrderUnloadsite())){
                //已经经过或者在布列斯特,布列斯特之后运踪不显示
                if(emptyList.contains(ttrvoList.get(0).getStationTwoDistance())){
                    int n=0;
                    for(int i=0;i<ttrvoList.size();i++){
                        if("0".equals(ttrvoList.get(i).getState())&&(ttrvoList.get(i).getCurrentLocation().contains("布列斯特")||ttrvoList.get(i).getCurrentLocation().contains("Brest")
                                ||ttrvoList.get(i).getCurrentLocation().contains("brest"))){
                            n=i;
                            break;
                        }
                    }
                    //从n到size-1
                    for(int m=n;m<ttrvoList.size();m++){
                        actualList.add(ttrvoList.get(m));
                    }
                    ttrvoList=actualList;
                }
            }
            List<String> malaUnLoadsite=new ArrayList<>();
            malaUnLoadsite.add("马拉");
            malaUnLoadsite.add("华沙");
            malaUnLoadsite.add("布拉格");
            malaUnLoadsite.add("布达佩斯");
            //马拉下货
            if(malaUnLoadsite.contains(shippingOrder.getOrderUnloadsite())){
                 //已经经过或者在马拉,马拉之后运踪不显示
                if(emptyList.contains(ttrvoList.get(0).getStationThrDistance())){
                    int n=0;
                    for(int i=0;i<ttrvoList.size();i++){
                        if("0".equals(ttrvoList.get(i).getState())&&(ttrvoList.get(i).getCurrentLocation().contains("马拉")||ttrvoList.get(i).getCurrentLocation().contains("Mala")
                                ||ttrvoList.get(i).getCurrentLocation().contains("mala"))){
                            n=i;
                            break;
                        }
                    }
                    //从n到size-1
                    for(int m=n;m<ttrvoList.size();m++){
                        actualList.add(ttrvoList.get(m));
                    }
                    ttrvoList=actualList;
                }
            }
        }
        map.put("trainList",ttrvoList);
        return map;
    }

    //邮件发送记录查询
    @Override
    public List<EmailRecordsVo> selectEmailRecords(String orderNum){
        return trackOneLevelMapper.selectEmailRecords(orderNum);
    }

    //根据sort和orderId查询一级节点id
    @Override
    public Long selectOneId(TrackOneLevel trackOneLevel)
    {
        return trackOneLevelMapper.selectOneId(trackOneLevel);
    }

    //更新运踪名字
    public int updateName(String orderId) {
        //根据订单id获取最新运踪
        TrackOneLevel tol=trackOneLevelMapper.getMaxTol(orderId);
        //更新运踪
        if(tol==null){
            tol=new TrackOneLevel();
        }
        return trackOneLevelMapper.updateOrder(tol);
    }

    //@Scheduled(cron="23 37 20 * * ?")
    public void autoLogout() {
        //所有审核通过订单id
        List<String> sList=trackOneLevelMapper.getorderid();
        List<Long> list=new ArrayList<>();
        TrackOneLevel trackOneLevel=null;
        for(String s :sList){
            //查询运踪表一级节点 orderid sort
            trackOneLevel=new TrackOneLevel();
            trackOneLevel.setOrderId(s);
            for(int m=0;m<8;m++){
                trackOneLevel.setSort(m);
                List<Long> oneId0 =trackOneLevelMapper.selectOneIdList(trackOneLevel);
                if(oneId0.size()>1){
                    for(Long l :oneId0){
                        list.add(l);
                    }
                }
            }
        }
        for(Long l :list){
        System.out.println(l);
        }
    }

    //@Scheduled(cron="23 56 21 * * ?")
    public void getTrack() {
        //所有审核通过订单id
        List<String> sList=trackOneLevelMapper.getorderid();
        for(String s : sList){
            updateName(s);
        }
        System.out.println("更新完毕---------------");
    }

    //@Scheduled(cron="21 07 23 * * ?")
    public void handleTgs() {
        //获取delflag=0,fromsystem不为null的货物状态表数据
       List<TrackGoodsStatus> tgsList=trackGoodsStatusMapper.getTgs();
       System.out.println("*/*//////----------"+tgsList.size());
        TrackGoodsStatus trackGoodsStatus=null;
        for(int i=0;i<tgsList.size();i++){
            System.out.println("tgsList.get(i)---------"+tgsList.get(i));
            //根据箱号和舱位号查数据
            trackGoodsStatus=new TrackGoodsStatus();
            trackGoodsStatus.setBoxNum(tgsList.get(i).getBoxNum());
            trackGoodsStatus.setOrderId(tgsList.get(i).getOrderId());
            System.out.println("trackGoodsStatus---------"+trackGoodsStatus);
            List<TrackGoodsStatus> checkTgsList=trackGoodsStatusMapper.checkTgsList(trackGoodsStatus);
            List<TrackGoodsStatus> list=null;
            if(checkTgsList!=null&&checkTgsList.size()>0){
                list=new ArrayList<>();
                //循环，获取有fromsystem的，把其余的班列日期加到有fromsystem的，然后把其余的删除,fromsystem升序排序
                for(int j=0;j<checkTgsList.size();j++){
                    if(j>0&&StringUtils.isEmpty(checkTgsList.get(j-1).getFromSystem())&&StringUtils.isNotEmpty(checkTgsList.get(j).getFromSystem())){
                        checkTgsList.get(j).setActualClassDate(checkTgsList.get(j-1).getActualClassDate());
                        trackGoodsStatusMapper.updateTgs( checkTgsList.get(j));
                        checkTgsList.remove(j);
                        list=checkTgsList;
                        break;
                    }
                }
                for(int n=0;n<list.size();n++){
                    list.get(n).setDelFlag(1);
                    trackGoodsStatusMapper.updateTgs( list.get(n));
                }
            }
        }
        System.out.println("更新完毕---------------");
    }
}
