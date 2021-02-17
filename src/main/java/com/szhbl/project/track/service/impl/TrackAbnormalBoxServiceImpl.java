package com.szhbl.project.track.service.impl;

import java.io.File;
import java.util.*;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.email.MailUtils;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.system.domain.EmailSet;
import com.szhbl.project.system.domain.SysUser;
import com.szhbl.project.system.mapper.EmailSetMapper;
import com.szhbl.project.system.mapper.SysUserMapper;
import com.szhbl.project.track.domain.vo.AbnormalBoxEmailsVo;
import com.szhbl.project.track.domain.vo.AbnormalDay;
import com.szhbl.project.track.domain.vo.TrackAbnormalBoxVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.track.mapper.TrackAbnormalBoxMapper;
import com.szhbl.project.track.domain.TrackAbnormalBox;
import com.szhbl.project.track.service.ITrackAbnormalBoxService;
/**
 * 运踪_异常箱Service业务层处理
 *
 * @author lzd
 * @date 2020-03-30
 */
@Service
public class TrackAbnormalBoxServiceImpl implements ITrackAbnormalBoxService
{
    @Autowired
    private TrackAbnormalBoxMapper trackAbnormalBoxMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private EmailSetMapper emailSetMapper;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;


    /**
     * 查询订单表的异常箱
     *
     * @param trackAbnormalBoxVo 异常箱查询vo
     * @return
     */
    @Override
    public List<TrackAbnormalBoxVo> selectOrderAbnormalBoxList(TrackAbnormalBoxVo trackAbnormalBoxVo)
    {
        return trackAbnormalBoxMapper.selectOrderAbnormalBoxList(trackAbnormalBoxVo);
    }



    /**
     * 查询运踪_异常箱
     *
     * @param id 运踪_异常箱ID
     * @return 运踪_异常箱
     */
    @Override
    public TrackAbnormalBox selectTrackAbnormalBoxById(Integer id)
    {
        return trackAbnormalBoxMapper.selectTrackAbnormalBoxById(id);
    }

    /**
     * 查询运踪_异常箱列表
     *
     * @param trackAbnormalBox 运踪_异常箱
     * @return 运踪_异常箱
     */
    @Override
    public List<TrackAbnormalBox> selectTrackAbnormalBoxList(TrackAbnormalBox trackAbnormalBox)
    {
        return trackAbnormalBoxMapper.selectTrackAbnormalBoxList(trackAbnormalBox);
    }

    /**
     * 新增运踪_异常箱
     *
     * @param trackAbnormalBox 运踪_异常箱
     * @return 结果
     */
    @Override
    public int insertTrackAbnormalBox(TrackAbnormalBox trackAbnormalBox)
    {
        trackAbnormalBox.setCreateTime(DateUtils.getNowDate());
        trackAbnormalBox.setUpdateTime(DateUtils.getNowDate());
        trackAbnormalBox.setDelFlag(0);
        String orderId=trackAbnormalBox.getOrderId();
        String[] orderIds= orderId.split(",");
        for(int i=0;i<orderIds.length;i++){
            trackAbnormalBox.setOrderId(orderIds[i]);
            trackAbnormalBoxMapper.insertTrackAbnormalBox(trackAbnormalBox);
        }
        trackAbnormalBox.setOrderId(orderId);
        return orderIds.length;
    }

    /**
     * 修改运踪_异常箱
     *
     * @param trackAbnormalBox 运踪_异常箱
     * @return 结果
     */
    @Override
    public int updateTrackAbnormalBox(TrackAbnormalBox trackAbnormalBox)
    {
        trackAbnormalBox.setUpdateTime(DateUtils.getNowDate());
        return trackAbnormalBoxMapper.updateTrackAbnormalBox(trackAbnormalBox);
    }

    /**
     * 批量删除运踪_异常箱
     *
     * @param ids 需要删除的运踪_异常箱ID
     * @return 结果
     */
    @Override
    public int deleteTrackAbnormalBoxByIds(Integer[] ids)
    {
        return trackAbnormalBoxMapper.deleteTrackAbnormalBoxByIds(ids);
    }

    /**
     * 删除运踪_异常箱信息
     *
     * @param id 运踪_异常箱ID
     * @return 结果
     */
    @Override
    public int deleteTrackAbnormalBoxById(Integer id)
    {
        return trackAbnormalBoxMapper.deleteTrackAbnormalBoxById(id);
    }

    /**
     * 根据箱号和班列日期查询最新一条记录
     * @return 结果
     */
    @Override
    public TrackAbnormalBox selectByBoxNumAndClassDate(TrackAbnormalBox trackAbnormalBox)
    {
        return trackAbnormalBoxMapper.selectByBoxNumAndClassDate(trackAbnormalBox);
    }

    //获取智能运踪
    @Override
    public String selectAbnormalInformation(String classNum) {
        return trackAbnormalBoxMapper.selectAbnormalInformation(classNum);
    }

    //获取业务，跟单，客户 需要抄送的领导、相关部门邮箱
    @Override
    public AbnormalBoxEmailsVo getEmails(String orderId,String isBcc){
        List<String> emailList=new ArrayList<String>();
        List<String> bccEmailList=new ArrayList<String>();
        List<String> leaderList=null;
        String[] deptIds=null;
        String[] leaders=null;
        AbnormalBoxEmailsVo abVo=null;
        String merchandiserId=null;
        String isFull=null;
        String goCome=null;
        String[] orderIds=orderId.split(",");
        for(int i=0;i<orderIds.length;i++){
            abVo=trackAbnormalBoxMapper.selectAbVo(orderIds[i]);//获取业务需要抄送的领导id和业务自己邮箱
            //业务领导的邮箱
           /* if(abVo.getAncestors()!=null){
                deptIds=abVo.getAncestors().replaceAll("，",",").split(",");
                leaderList=trackAbnormalBoxMapper.selectLeaders(deptIds);
                leaders= StringUtils.join(leaderList,",").replaceAll(" ",",").replaceAll("，",",").split(",");//获取领导名字
                emailList=trackAbnormalBoxMapper.selectLeaderEmails(leaders);
            }*/
            //业务邮箱
            emailList.add(abVo.getYewuEmail());
            //跟单邮箱
            if("0".equals(abVo.getGoCome())){//0去程西向跟单员  去程提取下货站
                goCome="0";
                merchandiserId=abVo.getWMerchandiserId();
            }else if("1".equals(abVo.getGoCome())){//1回程东向跟单员  回程提取上货站
                goCome="1";
                merchandiserId=abVo.getEMerchandiserId();
            }

            if(merchandiserId!=null){
                String[] mcsIds=merchandiserId.replaceAll(" ",",").replaceAll("，",",").replaceAll("\\|",",").split(",");
                for(int j=0;j<mcsIds.length;j++){
                    SysUser sysUser=sysUserMapper.selectUserByDcId(mcsIds[j]);//dc_user_id
                    if(sysUser!=null){
                        emailList.add(sysUser.getEmail());
                    }
                }
            }
            isFull=abVo.getIsFull();
            //客户邮箱
            if("0".equals(isBcc)){//跟踪信息
                emailList.add(abVo.getBccMails().replaceAll(",",";").replaceAll("，",";").replaceAll("\\|",";"));
            }else if("1".equals(isBcc)){//拼箱编辑
                bccEmailList.add(abVo.getBccMails().replaceAll(",",";").replaceAll("，",";").replaceAll("\\|",";"));
            }
        }
        //固定邮箱
        //拼箱异常箱：邮箱分为收件邮箱和密送邮箱，收件邮箱发送业务邮箱+跟单邮箱+固定邮箱（quyr@zih718.com;liangcc@zih718.com;caozuo2@zih718.com;
        // lcl_operate@zih718.com;xiangguan@zih718.com;baoguan2@zih718.com）；
        //密送邮箱提取所有仓位号托书在途信息接收邮箱

        //整柜异常箱：收件邮箱提取托书在途信息接收邮箱+业务邮箱+跟单邮箱+固定邮箱（quyr@zih718.com;liangcc@zih718.com;caozuo2@zih718.com;
        // lcl_operate@zih718.com;xiangguan@zih718.com;baoguan2@zih718.com）

     /*   if("0".equals(isFull)){
            emailList.add( "quyr@zih718.com;liangcc@zih718.com;caozuo2@zih718.com;lcl_operate@zih718.com;xiangguan@zih718.com;baoguan2@zih718.com");
        }else if("1".equals(isFull)){
            emailList.add( "quyr@zih718.com;liangcc@zih718.com;caozuo2@zih718.com;lcl_operate@zih718.com;xiangguan@zih718.com;baoguan2@zih718.com");
        }*/
       // emailList.add( "quyr@zih718.com;liangcc@zih718.com;caozuo2@zih718.com;lcl_operate@zih718.com;xiangguan@zih718.com;baoguan2@zih718.com");
        emailList.add( "liangcc@zih718.com;caozuo2@zih718.com;lcl_operate@zih718.com;baoguan2@zih718.com");
       /* if("0".equals(isFull)&&"0".equals(goCome)){
            emailList.add( "quyr@zih718.com;lijm@zih718.com;caozuo2@zih718.com;lcl_operate@zih718.com");
        }else if("0".equals(isFull)&&"1".equals(goCome)){
            emailList.add( "quyr@zih718.com;lijm@zih718.com;caozuo2@zih718.com;kab@zih718.com");
        }else if("1".equals(isFull)&&"0".equals(goCome)){
            emailList.add( "quyr@zih718.com;lijm@zih718.com;caozuo2@zih718.com;lcl_operate@zih718.com;xiangguan@zih718.com");
        }else if("1".equals(isFull)&&"1".equals(goCome)){
            emailList.add( "quyr@zih718.com;lijm@zih718.com;caozuo2@zih718.com;kab@zih718.com;xiangguan@zih718.com;baoguan2@zih718.com");
        }*/
        emailList=  getEmail((StringUtils.join(emailList,";").split(";")));
        bccEmailList= getEmail((StringUtils.join(bccEmailList,";").split(";")));
        abVo=new AbnormalBoxEmailsVo();
        abVo.setSendMails(StringUtils.join(new ArrayList<String>(new TreeSet<String>(emailList)),";"));
        abVo.setBccMails(StringUtils.join(new ArrayList<String>(new TreeSet<String>(bccEmailList)),";"));
        return abVo;
    }

    //获取日报导出数据
    @Override
    public List<AbnormalDay> selectDayAbnormalBox(TrackAbnormalBoxVo trackAbnormalBoxVo)
    {
        List<AbnormalDay> abDayList=trackAbnormalBoxMapper.selectDayAbnormalBox(trackAbnormalBoxVo);
        for(int i=0;i<abDayList.size();i++){
            String eastAndWest=abDayList.get(i).getEastAndWest();
            String site=null;
            String merchandiser="";
            String merchandiserId=null;
            if("0".equals(eastAndWest)){//0去程西向跟单员  去程提取下货站
                merchandiserId=abDayList.get(i).getWMerchandiserId();
                site=abDayList.get(i).getUnloadSite();
                abDayList.get(i).setClassDate(abDayList.get(i).getClassDate().replaceAll("-",".").substring(5,10)+
                        getIsP(abDayList.get(i).getClassBh())+getAdressStart(abDayList.get(i).getEndBh()));
            }else if("1".equals(eastAndWest)){//1回程东向跟单员  回程提取上货站
                merchandiserId=abDayList.get(i).getEMerchandiserId();
                site=abDayList.get(i).getUploadSite();
                abDayList.get(i).setClassDate(abDayList.get(i).getClassDate().replaceAll("-",".").substring(5,10)+
                        getIsP(abDayList.get(i).getClassBh())+getAdressStart(abDayList.get(i).getStartBh()));
            }
            if(merchandiserId!=null){
                String[] mcsIds=merchandiserId.replaceAll(" ",",").replaceAll("，",",").replaceAll("\\|",",").split(",");
                for(int j=0;j<mcsIds.length;j++){
                    SysUser sysUser=sysUserMapper.selectUserByDcId(mcsIds[j]);
                    if(sysUser!=null){
                        if("".equals(merchandiser)){
                            merchandiser=sysUser.getNickName();
                        }else{
                            merchandiser=merchandiser+","+sysUser.getNickName();
                        }
                    }
                }
            }
            String days=DateUtils.getDays2(abDayList.get(i).getUnloadTime(),abDayList.get(i).getSolveTime());
            if(StringUtils.isNotEmpty(days)&&StringUtils.isNotEmpty(abDayList.get(i).getAbnormalType())){
                abDayList.get(i).setSolveTimeCompare(getTimeCompare(days,abDayList.get(i).getAbnormalType()));
            }
            abDayList.get(i).setMerchandiser(merchandiser);
            abDayList.get(i).setSite(site);
        }
        return abDayList;
    }

    //新增发送
    @Override
    public int addAndSend(TrackAbnormalBox trackAbnormalBox){
        int i=insertTrackAbnormalBox(trackAbnormalBox);
        sendMails(trackAbnormalBox);
        return i;
    };
    //编辑发送
    @Override
    public int editAndSend(TrackAbnormalBox trackAbnormalBox){
        int i=trackAbnormalBoxMapper.updateTrackAbnormalBox(trackAbnormalBox);
        sendMails(trackAbnormalBox);
        return i;
    };

    public void sendMails(TrackAbnormalBox tab) {
        String[] orderIds= tab.getOrderId().split(",");
        List<String> orderNumList=new ArrayList<>();
        ShippingOrder shippingOrder=null;
        Date classDate=null;
        for(int i=0;i<orderIds.length;i++){
            shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderIds[i]);
            orderNumList.add(shippingOrder.getOrderNumber());
            classDate=shippingOrder.getClassDate();
        }
        String orderNum=StringUtils.join(orderNumList,"、");
        EmailSet emailSet=new EmailSet();
        emailSet.setLineType(2);
        emailSet=emailSetMapper.selectEmailSet(emailSet);
        String gocome=tab.getGoCome();
        String isSeparate="异常";
        if(tab.getIsSeparate()!=null&&tab.getIsSeparate()==0){
            isSeparate=isSeparate+"下货";
        }
        if("0".equals(gocome)){
            gocome="去程";
        }else if("1".equals(gocome)){
            gocome="回程";
        }
        if(classDate!=null){
            tab.setClassDate(DateUtils.parseDateToStr("yyyy-MM-dd",classDate));
        }
        String subject=tab.getClassDate().substring(5,7)+"月"+tab.getClassDate().substring(8,10)+"日"+gocome+"班列,"+tab.getBoxNum()+","+isSeparate;
        StringBuilder content= new StringBuilder();
        content.append("Dear customer," + "<br />" + "Nice day to you!");
        content.append("<br />" + "舱位编号/Booking number："  +orderNum);
        content.append("<br />" + "异常原因/Abnormal cause：" + tab.getUnloadReason() + "<br />" + "下货地点/Unloading point：" + tab.getUnloadSite() + "<br />");
        content.append("运踪信息/Tracing info：" + "<br />" + tab.getAbnormalInformation() + "<br />" + "So sorry for the inconvenience to you, please understand.");
        content.append("<br />");
        File file=tab.getFilePath()==null?null:new File(tab.getFilePath());
        String[] SecretEmails=null;
        if(tab.getSecretEmails()!=null && tab.getSecretEmails() != ""){
            SecretEmails=tab.getSecretEmails().split(";");
        }
        MailUtils.sendAttachmentMail(emailSet.getSmtpSever(),emailSet.getEmailAuthentication().toString(),emailSet.getAccount(),emailSet.getPassword(),tab.getReceiveEmails().split(";"),
                SecretEmails,subject,content.toString(),file);
//        if(tab.getFilePath() != null){
//            FileUtils.deleteFile(tab.getFilePath());
//        }
    }

    //获取对应地址的大写开头字母
    public String getAdressStart(String adressBh) {
        String adressStart = "";
        if("32_20182".equals(adressBh)){//列日
            adressStart = "-L";
        }else if("49_80200".equals(adressBh)){//慕尼黑
            adressStart = "-M";
        }else if("48_21500".equals(adressBh)){//马拉
            adressStart = "-Ma";
        }else if("32_20183".equals(adressBh)){//亨克
            adressStart = "-G";
        }else if("358_00002".equals(adressBh)){//赫尔辛基
            adressStart = "-H";
        }
        return adressStart;
    }

    //获取对应地址的大写开头字母
    public String getIsP(String classBh) {
        String isP = "";
        if(StringUtils.isNotEmpty(classBh)&&classBh.toLowerCase().contains("p")){//列日
            isP = "P";
        }
        return isP;
    }

    //获取对应考核时效
    public String getTimeCompare(String days,String abnormalType) {

        String solveTimeCompare="";
        double useDays= Double.valueOf(days);
        if (abnormalType.contains("车板")) {
            double day = useDays - 5;
            if (day > 0){
                solveTimeCompare= "慢" + String.format("%.2f", day) + "天";
            }else{
                solveTimeCompare= "快" + String.format("%.2f", Math.abs(day)) + "天";
            }
        }else if (abnormalType.contains("箱损")) {
            double day = useDays - 7;
            if (day > 0){
                solveTimeCompare= "慢" + String.format("%.2f", day) + "天";
            }else{
                solveTimeCompare= "快" + String.format("%.2f", Math.abs(day)) + "天";
            }
        }else if (abnormalType.contains("查验")||abnormalType.contains("单证")) {
            double day = useDays - 2;
            if (day > 0){
                solveTimeCompare= "慢" + String.format("%.2f", day) + "天";
            }else{
                solveTimeCompare= "快" +String.format("%.2f", Math.abs(day)) + "天";
            }
        }
        return solveTimeCompare;
    }

    //去除数据空字符串
    public List<String> getEmail(String[] oldEmail) {
        List<String> newEmail = new ArrayList<>();
        for(int i=0;i<oldEmail.length;i++){
            if(StringUtils.isNotEmpty(oldEmail[i])){
                newEmail.add(oldEmail[i]);
            }
        }
        return newEmail;
    }

}
