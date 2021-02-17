package com.szhbl.project.client.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhbl.common.utils.ClientEmailUtil;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.email.MailUtils;
import com.szhbl.framework.config.rabbit.client.CustomerRabbitConfig;
import com.szhbl.framework.email.IMailService;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.client.domain.BusiClientsByTjr;
import com.szhbl.project.client.domain.BusiClientsMq;
import com.szhbl.project.client.form.ClientForm;
import com.szhbl.project.client.form.EmailForm;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiShipOrderMapper;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.system.domain.SysUser;
import com.szhbl.project.system.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.szhbl.project.client.mapper.BusiClientsMapper;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.service.IBusiClientsService;

import javax.mail.internet.MimeMessage;

/**
 * 客户管理Service业务层处理
 * 
 * @author jhm
 * @date 2020-01-06
 */
@Slf4j
@Service
public class BusiClientsServiceImpl implements IBusiClientsService 
{
    @Autowired
    private BusiClientsMapper busiClientsMapper;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private BusiShipOrderMapper busiShipOrderMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;

    /**
     * 查询客户管理
     * 
     * @param clientId 客户管理ID
     * @return 客户管理
     */
    @Override
    public BusiClients selectBusiClientsById(String clientId)
    {
        BusiClients clients = busiClientsMapper.selectBusiClientsById(clientId);
        if(StringUtils.isNull(clients.getClientValiditydate())){
            Date clientValiditydate = clients.getCreatedate();
            if(StringUtils.isNotNull(clientValiditydate)){
                Calendar cal = Calendar.getInstance();
                cal.setTime(clientValiditydate);
                cal.add(Calendar.YEAR, 1);//增加一年
                Date clientDisableddate = cal.getTime();
                clients.setClientDisableddate(clientDisableddate);
            }
            clients.setClientValiditydate(clientValiditydate); //有效日期开始时间
        }
        return clients;
    }

    /**
     * 查询客户管理
     *
     * @param userName 客户登录账号
     * @return 客户管理
     */
    @Override
    public BusiClients selectBusiClientsByName(String userName) {
        return busiClientsMapper.selectBusiClientsByName(userName);
    }

    /**
     * 查询客户管理
     *
     * @param userName 客户登录账号
     * @return 客户管理
     */
    @Override
    public List<BusiClients> selectBusiClientsListByName(String userName) {
        return busiClientsMapper.selectBusiClientsListByName(userName);
    }

    /**
     * 查询客户管理列表
     * 
     * @param busiClients 客户管理
     * @return 客户管理
     */
    @Override
    public List<BusiClients> selectBusiClientsList(BusiClients busiClients) {
        String deptCode = busiClients.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (busiClients.getDeptCode().contains("YWB")) {
                if (busiClients.getDeptCode().length() > 15) {
                    busiClients.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("5");
                } else {
                    busiClients.setReadType("1");
                }
            }
        }
        /*List<BusiClients> busiClientsList=busiClientsMapper.selectBusiClientsList(busiClients);
        for(int i=0;i<busiClientsList.size();i++){
            if(StringUtils.isEmpty(busiClientsList.get(i).getClientExamperson())){
                busiClientsList.get(i).setClientExamperson(SecurityUtils.getUsername());
            }
        }*/
        return busiClientsMapper.selectBusiClientsList(busiClients);
    }

    /**
     * 根据账号查询客户管理列表
     */
    @Override
    public List<BusiClients> selectBusiClientsListByUser(String clientLoginuser){
        return busiClientsMapper.selectBusiClientsListByUser(clientLoginuser);
    }

    /**
     * 新增客户管理
     * 
     * @param busiClients 客户管理
     * @return 结果
     */
    @Override
    public int insertBusiClients(BusiClients busiClients)
    {
        return busiClientsMapper.insertBusiClients(busiClients);
    }

    /**
     * 修改客户管理
     * 
     * @param busiClients 客户管理
     * @return 结果
     */
    @Override
    public int updateBusiClients(BusiClients busiClients) throws JsonProcessingException {
        //判断账号是否重复
        String clientId = busiClients.getClientId();
        //判断是否修改了推荐人信息
        String isEditTjr = "0"; //0否1是
        if(StringUtils.isNotEmpty(busiClients.getClientTjrId()) && StringUtils.isNotEmpty(busiClients.getClientTjr())){
            BusiClients clientsInfo = busiClientsMapper.selectBusiClientsById(clientId); //原客户信息
            if(StringUtils.isNotNull(clientsInfo)){
                if(!StringUtils.equals(clientsInfo.getClientTjrId(),busiClients.getClientTjrId()) && (!StringUtils.equals(clientsInfo.getClientTjr(),busiClients.getClientTjr()))){
                    isEditTjr = "1";
                }
            }
        }
        int i = busiClientsMapper.updateBusiClients(busiClients);
        if(i ==1){
            //修改之后发送消息队列
            BusiClients clients = busiClientsMapper.selectBusiClientsById(clientId);
            if(StringUtils.isNotNull(clients)){
                clients.setClientTjr(getEmail(clients));
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                MessageProperties header = new MessageProperties();
                header.getHeaders().put("__TypeId__","Object");
                ObjectMapper objectMapper = new ObjectMapper();
                Message message = new Message(objectMapper.writeValueAsBytes(clients), header);
                rabbitTemplate.convertAndSend(CustomerRabbitConfig.DC_SYSTEM__CLIENT_EXCHANGE,"dc.system.topic.client.add",message,correlationData);
            }
            //修改推荐人推送托书消息队列
            if("1".equals(isEditTjr)){
                List<String> orderIdList = busiShipOrderMapper.orderIdListByClient(clientId);
                if(orderIdList.size()>0){
                    for(String orderItem :orderIdList){
                        //发送消息队列
                        String orderId = orderItem;
                        if(StringUtils.isNotEmpty(orderId)){
                            ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                            if(StringUtils.isNotNull(orderInfoRabbmq)){
                                String messagetype = "7";//托书修改
                                try {
                                    commonService.orderInfoMQ(orderInfoRabbmq,messagetype);
                                } catch (JsonProcessingException e) {
                                    log.error("批量同步托书修改失败",e.toString(),e.getStackTrace());
                                }
                            }
                        }
                    }
                }
            }
        }
        return i;
    }

    /**
     * 批量删除客户管理
     * 
     * @param clientIds 需要删除的客户管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiClientsByIds(String[] clientIds)
    {
        return busiClientsMapper.deleteBusiClientsByIds(clientIds);
    }

    /**
     * 删除客户管理信息
     * 
     * @param clientId 客户管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiClientsById(String clientId)
    {
        return busiClientsMapper.deleteBusiClientsById(clientId);
    }

    @Override
    public List<BusiClients> selectClientsExamineList(BusiClients busiClients) {
        return busiClientsMapper.selectClientsExamineList(busiClients);
    }

    @Override
    public AjaxResult simpleEmail(BusiClients busiClients) {

        String clientEmail = busiClients.getClientEmail();
        //发送邮件
        if(StringUtils.isNotEmpty(busiClients.getEngChinese())) {
            if (busiClients.getEngChinese().equals("1")) {
                iMailService.sendSimpleMail(clientEmail, "Booking system user audit information", "Registered failure reason：" + busiClients.getExaminfo());
            } else {
                iMailService.sendSimpleMail(clientEmail, "订舱系统用户审核信息", "注册失败原因：" + busiClients.getExaminfo());
            }
            return AjaxResult.success();
        }else {
            return AjaxResult.error("发送失败！");
        }
    }

    @Override
    public AjaxResult sendHtmlMail(BusiClients busiClients) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String clientUnit = busiClients.getClientUnit();
        String account = busiClients.getClientLoginuser();
        String clientValidityDate = sdf.format(busiClients.getClientValiditydate());
        String clientDisableDate = sdf.format(busiClients.getClientDisableddate());
        String clientEmail = busiClients.getClientEmail();
        String password = busiClients.getClientLoginpwd();//密码
        String isexamline = busiClients.getIsexamline();
        String examInfo = busiClients.getExaminfo();
        //发送邮件
        if(StringUtils.isNotEmpty(busiClients.getEngChinese())) {

            if (busiClients.getEngChinese().equals("1")) {
                //国外客户
                if (StringUtils.isNotEmpty(busiClients.getClientEmail())) {
                    //客户端邮箱不为空的情况下
                    try {
                        iMailService.sendHtmlMail(clientEmail, "User registration successful for booking system", "Individual user informationn：" + ClientEmailUtil.enEmailContent(account, clientValidityDate, clientDisableDate,password).toString());
                    } catch (Exception e) {
                        log.error("客户邮件发送失败",e.toString(),e.getStackTrace());
                        return AjaxResult.error("客户邮件发送失败");
                    }

                }
                SysUser u = userMapper.selectUserByTjrId(busiClients.getClientTjrId());
                if (StringUtils.isNull(u)) {
                    return AjaxResult.error("推荐人不存在，推荐人邮箱发送失败");
                }
                //发送给对应的推荐人
                String referenceEmails = u.getEmail();
                StringBuilder sb1 = new StringBuilder();
                sb1.append(account + "&nbsp;&nbsp;" + "<br/>" + "有效时间:" + clientValidityDate + "&nbsp;&nbsp;无效时间:" + clientDisableDate + "<br/>");
                if (StringUtils.isNotEmpty(referenceEmails)) {
                    String[] refeEmils = referenceEmails.split(",");
                    iMailService.sendHtmlMail(refeEmils, "订舱系统用户注册成功", "订舱系统个人用户信息：" + clientUnit + sb1.toString());
                }
                return AjaxResult.success();
            } else {
                //国内客户
                if (StringUtils.isNotBlank(busiClients.getClientEmail())) {
                    //客户端邮箱不为空的情况下
                    try {
                        iMailService.sendHtmlMail(clientEmail, "订舱系统用户注册成功", "订舱系统个人用户信息：" + ClientEmailUtil.chEmailContent(account, clientValidityDate, clientDisableDate,password).toString());
                    } catch (Exception e) {
                        log.error("客户邮件发送失败",e.toString(),e.getStackTrace());
                        return AjaxResult.error("客户邮件发送失败");
                    }

                }
                SysUser u = userMapper.selectUserByTjrId(busiClients.getClientTjrId());
                if (StringUtils.isNull(u)) {
                    return AjaxResult.error("推荐人不存在，推荐人邮箱发送失败");
                }
                String referenceEmails = u.getEmail();
                //发送给对应的推荐人
                StringBuilder sb1 = new StringBuilder();
                sb1.append(account + "&nbsp;&nbsp;" + "<br/>" + "有效时间:" + clientValidityDate + "&nbsp;&nbsp;无效时间:" + clientDisableDate + "<br/>");
                if (StringUtils.isNotBlank(referenceEmails) && referenceEmails.contains(";")) {
                    String[] refeEmils = referenceEmails.split(";");
                    try {
                        iMailService.sendHtmlMail(refeEmils, "订舱系统用户注册成功", "订舱系统个人用户信息：" + clientUnit + sb1.toString());
                    } catch (Exception e) {
                        log.error("推荐人邮件发送失败",e.toString(),e.getStackTrace());
                        return AjaxResult.error("推荐人邮件发送失败");
                    }
                } else {
                    try {
                        iMailService.sendHtmlMail(referenceEmails, "订舱系统用户注册成功", "订舱系统个人用户信息：" + clientUnit + sb1.toString());
                    } catch (Exception e) {
                        log.error("推荐人邮件发送失败",e.toString(),e.getStackTrace());
                        return AjaxResult.error("推荐人邮件发送失败");
                    }

                }
                return AjaxResult.success();
            }
        }else{
            return AjaxResult.error("请确认客户是否是国内外客户");
        }
    }

    /**
     * 批量修改跟单员（根据推荐人）
     * @param busiClients
     * @return
     */
    @Override
    public int updateWMerchandiserByTjr(BusiClientsByTjr busiClients){
        int result = 1;
        try{
            String clientIds = busiClients.getClientIds();
            String eastAndWest = busiClients.getEastAndWest(); //0为西向 1为东向
            if(StringUtils.isNotEmpty(clientIds) && StringUtils.isNotEmpty(eastAndWest)){
                BusiClientsByTjr clientUpd = new BusiClientsByTjr();
                if("0".equals(eastAndWest)){
                    clientUpd.setWMerchandiserId(busiClients.getNewMerchandiserId());
                    clientUpd.setWMerchandiser(busiClients.getNewMerchandiser());
                }
                if("1".equals(eastAndWest)){
                    clientUpd.setEMerchandiserId(busiClients.getNewMerchandiserId());
                    clientUpd.setEMerchandiser(busiClients.getNewMerchandiser());
                }
                String[] clientIdsArr = clientIds.split(",");
                for(int i=0;i<clientIdsArr.length;i++){
                    clientUpd.setClientId(clientIdsArr[i]);
                    int isupd = busiClientsMapper.updateWMerchandiserByTjr(clientUpd);
                    if(isupd == 0){
                        return 0;
                    }else{
                        //推送消息队列
                        BusiClients busiClientsNew = busiClientsMapper.selectBusiClientsById(clientIdsArr[i]);
                        if(StringUtils.isNotNull(busiClientsNew)){
                            busiClientsNew.setClientTjr(getEmail(busiClientsNew));
                            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                            MessageProperties header = new MessageProperties();
                            header.getHeaders().put("__TypeId__","Object");
                            ObjectMapper objectMapper = new ObjectMapper();
                            Message message = new Message(objectMapper.writeValueAsBytes(busiClientsNew), header);
                            rabbitTemplate.convertAndSend(CustomerRabbitConfig.DC_SYSTEM__CLIENT_EXCHANGE,"dc.system.topic.client.add",message,correlationData);
                        }
                    }
                }
            }else{
                result = 0;
            }
        }catch (Exception e) {
            log.error("批量修改跟单失败",e.toString(),e.getStackTrace());
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public AjaxResult updateWMerchandiser(ClientForm clientForm) throws JsonProcessingException {
        //西向跟单员，更新西向跟单员
        //判断西乡跟单员id字段是否包含此id,如果包含就替换，不包含不进行更新操作
        int count = 0;
//        String eMerchandiserId = clientForm.getEMerchandiserId();
        String[] clientids = clientForm.getClientIds().split(",");
        StringBuilder sb = new StringBuilder();//记录未更新信息
        for(int i=0;i<clientids.length;i++){
           //循环比较西向跟单员是否与修改的一致
            BusiClients busiClients = busiClientsMapper.selectBusiClientsById(clientids[i]);
            String i1 = busiClients.getwMerchandiserId();
            String i2 = clientForm.getOldWMerchandiserId();
            String i3 = clientForm.getNewWMerchandiserId();
            String m1 = busiClients.getwMerchandiser();
            String m2 = clientForm.getOldWMerchandiser().trim();
            String m3 = clientForm.getNewWMerchandiser();
            String n1 = busiClients.getwMerchandiserNumber();
            String n2 = clientForm.getOldWMerchandiserNumber();
            String n3 = clientForm.getNewWMerchandiserNumber();
            if(StringUtils.isEmpty(i1)){
                sb.append(busiClients.getClientUnit()+"原跟单员不存在，无法更新，请确认之后更新,");
                continue;
            }
            if(i1.contains(i2) && !i1.contains(i3)){
                count ++;
                String newIds =  editMore(i1,i2,i3);
                busiClients.setwMerchandiserId(newIds);//替换id
                busiClients.setwMerchandiser(editMore(m1,m2,m3)); //替换姓名
                busiClients.setwMerchandiserNumber(editMore(n1,n2,n3));  //替换工号
                //busiClients.setCreatedate(DateUtils.getNowDate());
                busiClientsMapper.updateWMerchandiser(busiClients);
                //修改之后发送消息队列
                BusiClients busiClientsNew = busiClientsMapper.selectBusiClientsById(clientids[i]);
                if(StringUtils.isNotNull(busiClientsNew)){
                    busiClientsNew.setClientTjr(getEmail(busiClientsNew));
                    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                    MessageProperties header = new MessageProperties();
                    header.getHeaders().put("__TypeId__","Object");
                    ObjectMapper objectMapper = new ObjectMapper();
                    Message message = new Message(objectMapper.writeValueAsBytes(busiClientsNew), header);
                    rabbitTemplate.convertAndSend(CustomerRabbitConfig.DC_SYSTEM__CLIENT_EXCHANGE,"dc.system.topic.client.add",message,correlationData);
                }
            } else{
                sb.append(busiClients.getClientUnit()+"被替换的跟单员不存在或者新替换的跟单员已经存在了，无法更新，请确认之后更新,");
                continue;
            }

        }
        if(count>0){
            return   AjaxResult.success("西向跟单员替换成功,");
        }else{
            return  AjaxResult.error("失败或部分更新");
        }
//        if(count == clientids.length){
//            return   AjaxResult.success("西向跟单员替换成功,");
//        }else if(count>0 && count < clientids.length){
//            return  AjaxResult.success("西向跟单员部分替换成功,"+sb.toString());
//        }else{
//            return  AjaxResult.error("所勾选的被替换的跟单员不存在，无法更新,请确认之后更新,");
//        }


    }

    @Override
    public AjaxResult updateEMerchandiser(ClientForm clientForm) throws JsonProcessingException {
        //东向跟单员
        //判断东向跟单员id字段是否包含此id,如果包含就替换，不包含不进行更新操作
        int count = 0;
       // return busiClientsMapper.updateEMerchandiser(clientForm);
        String[] clientids = clientForm.getClientIds().split(",");
        StringBuilder sb = new StringBuilder();//记录未更新信息
        for(int i=0;i<clientids.length;i++){
            //循环比较西向跟单员是否与修改的一致
            BusiClients busiClients = busiClientsMapper.selectBusiClientsById(clientids[i]);
            /*String A= busiClients.geteMerchandiserId();
            String b = clientForm.getOldEMerchandiserId();
            Boolean c = busiClients.geteMerchandiserId().contains(clientForm.getOldEMerchandiserId());
            if(busiClients.geteMerchandiserId().contains(clientForm.getOldEMerchandiserId())){
                count ++;
                busiClients.seteMerchandiserId(busiClients.geteMerchandiserId().replace(clientForm.getOldWMerchandiserId(),clientForm.getNewWMerchandiserId())); ;//替换id
                busiClients.seteMerchandiserNumber(busiClients.geteMerchandiserNumber().replace(clientForm.getOldEMerchandiserNumber(),clientForm.getNewWMerchandiserNumber()));                                                                               //替换工号
                busiClients.seteMerchandiser(busiClients.geteMerchandiser().replace(clientForm.getOldEMerchandiser(),clientForm.getNewEMerchandiser()));//替换姓名
                busiClientsMapper.updateEMerchandiser(busiClients);
            }else{
                sb.append(busiClients.getClientUnit()+"被替换的跟单员不存在，无法更新，请确认之后更新,");
            }*/
            String i1 = busiClients.geteMerchandiserId();
            String i2 = clientForm.getOldEMerchandiserId();
            String i3 = clientForm.getNewEMerchandiserId();
            String m1 = busiClients.geteMerchandiser();
            String m2 = clientForm.getOldEMerchandiser().trim();
            String m3 = clientForm.getNewEMerchandiser();
            String n1 = busiClients.geteMerchandiserNumber();
            String n2 = clientForm.getOldEMerchandiserNumber();
            String n3 = clientForm.getNewEMerchandiserNumber();
            if(StringUtils.isEmpty(i1)){
                sb.append(busiClients.getClientUnit()+"原跟单员不存在，无法更新，请确认之后更新,");
                continue;
            }
            if(i1.contains(i2) && !i1.contains(i3)){
                count ++;
                String newIds =  editMore(i1,i2,i3);
                busiClients.seteMerchandiserId(newIds);//替换id
                busiClients.seteMerchandiser(editMore(m1,m2,m3)); //替换姓名
                busiClients.seteMerchandiserNumber(editMore(n1,n2,n3));  //替换工号
                //busiClients.setCreatedate(DateUtils.getNowDate());
                busiClientsMapper.updateEMerchandiser(busiClients);
//                //修改之后发送消息队列
                BusiClients busiClientsNew = busiClientsMapper.selectBusiClientsById(clientids[i]);
                if(StringUtils.isNotNull(busiClientsNew)){
                    busiClientsNew.setClientTjr(getEmail(busiClientsNew));
                    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                    MessageProperties header = new MessageProperties();
                    header.getHeaders().put("__TypeId__","Object");
                    ObjectMapper objectMapper = new ObjectMapper();
                    Message message = new Message(objectMapper.writeValueAsBytes(busiClientsNew), header);
                    rabbitTemplate.convertAndSend(CustomerRabbitConfig.DC_SYSTEM__CLIENT_EXCHANGE,"dc.system.topic.client.add",message,correlationData);
                }
            }else{
                sb.append(busiClients.getClientUnit()+"被替换的跟单员不存在或者新替换的跟单员已经存在了，无法更新，请确认之后更新");
                continue;
            }
        }
        if(count>0){
            return   AjaxResult.success("东向跟单员替换成功,");
        }else{
            return  AjaxResult.error("失败或部分更新");
        }
//        if(count == clientids.length){
//            return   AjaxResult.success("东向跟单员替换成功");
//        }else if(count>0 && count < clientids.length){
//            return  AjaxResult.success("东向跟单员部分替换成功"+sb.toString());
//        }else{
//            return  AjaxResult.error("所勾选的被替换的跟单员不存在，无法更新,请确认之后更新");
//        }
    }

    @Override
    public AjaxResult selectBusiClientWithClientIds(String type,String clientIds) {
        String[] clientArray = clientIds.split(",");

    List<BusiClients> busiClientsList = busiClientsMapper.selectBusiClientsWithIds(clientArray);
    List<String> merchandisers = new ArrayList<>();
    String[] mechanids;
    for(BusiClients busiClients:busiClientsList){
        if(type.equals("0")){
        //西向跟单员id,姓名
            merchandisers.add(busiClients.getwMerchandiserId());
    }else{
            merchandisers.add(busiClients.geteMerchandiserId());
        }

    }

    if(merchandisers.size()>0){
        String firstIds = merchandisers.get(0);
        for(String ids : merchandisers){
            if(!ids.equals(firstIds)){

                return AjaxResult.error("所选客户跟单员存在不一致的情况，请确定之后再更改");
            }
        }
        List<SysUser> sysUserList = new ArrayList<>();
        if(firstIds!=null&& firstIds.contains("|")){
            sysUserList = userMapper.selectUserWithIds(firstIds.replace("|",","));

        }else{
            sysUserList = userMapper.selectUserWithIds(firstIds);

        }
        return AjaxResult.success(sysUserList);


    }
        return AjaxResult.error("所选客户跟单员存在为空的情况，请重新选择");
    }

    @Override
    public AjaxResult deleteMoreWMerchandiser(ClientForm clientForm) {
        int count = 0;
        String a = clientForm.getClientIds();
        String[] clientIds = a.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<clientIds.length;i++){
            BusiClients busiClients = busiClientsMapper.selectBusiClientsById(clientIds[i]);
            String deleteId = clientForm.getOldWMerchandiserId();
            String c = busiClients.getwMerchandiserId();
            String delWnum = clientForm.getOldWMerchandiserNumber();
            String nums = busiClients.getwMerchandiserNumber();
            String delName = clientForm.getOldWMerchandiser();
            String names = busiClients.getwMerchandiser();
            if(c.contains(deleteId)){
                count++;
                String newWMerchandiserIds = removeId(c,deleteId);//删除跟单员id
                String newWMerchandNums =  removeId(nums,delWnum);//删除跟单员工号
                String newWnames = removeName(names,delName);//删除跟单员姓名
                busiClients.setwMerchandiserId(newWMerchandiserIds);
                busiClients.setwMerchandiserNumber(newWMerchandNums);
                busiClients.setwMerchandiser(newWnames);
                //更新
                busiClientsMapper.updateWMerchandiser(busiClients);
//                //修改之后发送消息队列
//                BusiClients busiClientsNew = busiClientsMapper.selectBusiClientsById(clientids[i]);
//                String messageProperties = JSONObject.toJSONString(busiClientsNew, SerializerFeature.WriteMapNullValue);
//                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//                rabbitTemplate.convertAndSend(CustomerRabbitConfig.DC_SYSTEM__CLIENT_EXCHANGE,"dc.system.topic.client.add",messageProperties,correlationData);
            }else{
                stringBuilder.append(busiClients.getClientUnit()+"中，跟目前所删西向跟单员不一致，请确认之后重新勾选。");
            }


        }
        if(count>0){
            return   AjaxResult.success("西向跟单员删除成功,");
        }else{
            return  AjaxResult.error("失败或部分删除");
        }
//            if(count == clientIds.length){
//                   return AjaxResult.success("西向跟单员删除成功");
//            }else if(count>0 && count < clientIds.length){
//                return  AjaxResult.error("西向跟单员部分删除成功"+stringBuilder.toString());
//            }else{
//                return AjaxResult.error("西向跟单员删除失败");
//            }
    }

    @Override
    public AjaxResult deleteMoreEMerchandiser(ClientForm clientForm) {
        int count = 0;
        String a = clientForm.getClientIds();
        String[] clientIds = a.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<clientIds.length;i++){
            BusiClients busiClients = busiClientsMapper.selectBusiClientsById(clientIds[i]);
            String deleteId = clientForm.getOldEMerchandiserId();
            String c = busiClients.geteMerchandiserId();
            String delWnum = clientForm.getOldEMerchandiserNumber();
            String nums = busiClients.geteMerchandiserNumber();
            String delName = clientForm.getOldEMerchandiser();
            String names = busiClients.geteMerchandiser();
            if(c.contains(deleteId)){
                count++;
                String newEMerchandiserIds = removeId(c,deleteId);//删除跟单员id
                String newEMerchandNums =  removeId(nums,delWnum);//删除跟单员工号
                String newEnames = removeName(names,delName);//删除跟单员姓名
                busiClients.seteMerchandiserId(newEMerchandiserIds);
                busiClients.seteMerchandiserNumber(newEMerchandNums);
                busiClients.seteMerchandiser(newEnames);
                //更新
                busiClientsMapper.updateEMerchandiser(busiClients);
//                //修改之后发送消息队列
//                BusiClients busiClientsNew = busiClientsMapper.selectBusiClientsById(clientids[i]);
//                String messageProperties = JSONObject.toJSONString(busiClientsNew, SerializerFeature.WriteMapNullValue);
//                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//                rabbitTemplate.convertAndSend(CustomerRabbitConfig.DC_SYSTEM__CLIENT_EXCHANGE,"dc.system.topic.client.add",messageProperties,correlationData);
            }else{
                stringBuilder.append(busiClients.getClientUnit()+"中，跟目前所删东向跟单员不一致，请确认之后重新勾选。");
            }


        }
//        if(count == clientIds.length){
//            return AjaxResult.success("东向跟单员删除成功");
//        }else if(count>0 && count < clientIds.length){
//            return  AjaxResult.error("东向跟单员部分删除成功"+stringBuilder.toString());
//        }else{
//            return AjaxResult.error("东向跟单员删除失败");
//        }
        if(count>0){
            return   AjaxResult.success("东向跟单员删除成功,");
        }else{
            return  AjaxResult.error("失败或部分删除");
        }
    }

    @Override
    public List<BusiClients> selectBusiClientsListWithValidate(String registerStartTime, String registerEndTime) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("registerStartTime",registerStartTime);
        map.put("registerEndTime",registerEndTime);
        return busiClientsMapper.selectBusiClientsListWithValidate(map);
    }

    @Override
    public AjaxResult sendEmailWithSender(List<BusiClients> busiClientsList,EmailForm emailForm) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder successBuilder = new StringBuilder();
        for(BusiClients busiClients:busiClientsList){
            String clientEmail  = busiClients.getClientEmail();
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth","true");
                senderImpl.setHost("smtp.qiye.163.com");
                senderImpl.setUsername(emailForm.getSendUserEmaill());
                senderImpl.setPassword(emailForm.getEmailPass());
                senderImpl.setJavaMailProperties(props);
                MimeMessage mimeMessge = senderImpl.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessge, true,"UTF-8");
                mimeMessageHelper.setTo(clientEmail);
                mimeMessageHelper.setFrom(emailForm.getSendUserEmaill());
                mimeMessageHelper.setSubject(emailForm.getTitle());
                mimeMessageHelper.setText(emailForm.getContent(), true);
             //   FileSystemResource img = new FileSystemResource(new File("E:/active1.png"));
                senderImpl.send(mimeMessge);
                count ++;
                successBuilder.append(busiClients.getClientUnit()+"、");
            } catch (Exception e) {
                sb.append(busiClients.getClientUnit()+"邮件发送失败，请检查邮箱地址是否正确。");
                log.error("sendEmailWithSender失败：{}",e.toString(),e.getStackTrace());
            }
        }

if(count == busiClientsList.size()){
       return AjaxResult.success("发送成功")  ;
}else if(count>0 && count<busiClientsList.size()){
    return AjaxResult.error("部分发送失败"+sb.toString())  ;
}else {
    return AjaxResult.error("全部送失败，请检查发送人邮箱、密码和接收人邮箱地址是否正确");
}

    }

    @Override
    public int selectBusiClientsGNCountNum(BusiClients busiClients) {
        String deptCode= busiClients.getDeptCode();

        if (StringUtils.isNotEmpty(deptCode)) {
            if (busiClients.getDeptCode().contains("YWB")) {
                if (busiClients.getDeptCode().length() > 15) {
                    busiClients.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("5");
                } else {
                    busiClients.setReadType("1");
                }

            }
        }
        return busiClientsMapper.selectBusiClientsGNCountNum(busiClients);
    }

    @Override
    public int selectBusiClientsGWCountNum(BusiClients busiClients) {
        String deptCode= busiClients.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (busiClients.getDeptCode().contains("YWB")) {
                if (busiClients.getDeptCode().length() > 15) {
                    busiClients.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    busiClients.setReadType("5");
                } else {
                    busiClients.setReadType("1");
                }
            }
        }
        return busiClientsMapper.selectBusiClientsGWCountNum(busiClients);
    }

    /**
     * olds原表存的跟单员ids
     * olded 被修改的跟单员id
     * newM 新跟单员id
     * 循环修改跟单id,工号
     */
    public String editMore(String olds,String olded,String newM){
        if(StringUtils.isEmpty(olds)){
        return newM;
        }
        String[] a = olds.trim().split("\\|");//原表存的数据
        String d = "";
        for(int i=0;i<a.length;i++){
            String s = a[i];
            String ss = newM;
            d+= a[i].equals(olded)?"|"+newM:"|"+a[i];
        }
        return d.length()>0?d.trim().substring(1).toString():"";
    }
    /**
     * 删除循环遍历数据判断是否相等
     */
    public String removeId(String ids,String delId){
        String b = "";
        if(StringUtils.isNotEmpty(ids)){
            String[] a =null;

            a  = ids.trim().split("\\|");
            for(int i=0;i<a.length;i++){
                if(a[i].equals(delId)){

                }else{
                    b+=a[i]+"|";
                }
                System.out.println("=="+b);
            }
            System.out.println("=="+b.length());
        }
        return b.length()>0?b.substring(0, b.length()-1).toString():"";
    }

    /**
     *循环比较姓名， 删除姓名
     */
    public String removeName(String names,String delName){
        String b = "";
        if(StringUtils.isNotEmpty(names)){
            String[] n = names.split("[|]");//中文用此分割
            for(int i =0;i<n.length;i++){
                if(n[i].equals(delName)){

                }else{
                    b+=n[i]+"|";
                }

            }
        }
        return b.length()>0?b.substring(0, b.length()-1).toString():"";

    }

    /**
     *获取客户推荐人邮箱和跟单邮箱
     */
    public String getEmail(BusiClients busiClients){
        String clientTjr = "";
        if(StringUtils.isNotEmpty(busiClients.getClientTjr())){
            clientTjr += busiClients.getClientTjr();
        }
        //推荐人邮箱
        String tjrId = busiClients.getClientTjrId();
        if(StringUtils.isNotEmpty(tjrId)){
            String tjrEmail = busiShippingorderService.selectOrderTjrEmail(tjrId);
            if(StringUtils.isNotEmpty(tjrEmail)){
                clientTjr += "/";
                clientTjr += tjrEmail;
            }
        }
        //跟单邮箱
        List<String> merchandiserEmails;
        String[] merchandiserId;
        String merchandiserIds = (StringUtils.isNotEmpty(busiClients.getwMerchandiserId())?busiClients.getwMerchandiserId():"")+"|"+(StringUtils.isNotEmpty(busiClients.geteMerchandiserId())?busiClients.geteMerchandiserId():"");
        if(!"|".equals(merchandiserIds)){
            merchandiserId = merchandiserIds.trim().split("\\|");
            merchandiserEmails = busiShippingorderService.selectOrderMerEmail(merchandiserId);
            if(merchandiserEmails.size()>0){
                clientTjr += ";";
                clientTjr += StringUtils.join(merchandiserEmails.toArray(),";");
            }
        }
        return clientTjr;
    }

    //自动注销客户
    //A、已经发过货的客户，6个月时间未询价的，注销；B、未发过货的客户，3个月未询价的
    //@Scheduled(cron="1 0 0 * * ?")
    public void autoLogout() {
        //查询已审核通过的启用和锁定的客户
        List<BusiClients> clientList = busiClientsMapper.selectClinent();
        BusiClients busiClients=null;
        for(int i=0;i<clientList.size();i++){
            //查询是否下过订单
            int aob = busiClientsMapper.selectAorB(clientList.get(i).getClientId());
            //查询未询价天数
            int days=busiClientsMapper.selectDays(clientList.get(i).getClientId());
            //符合条件注销账户
            if((aob==0&&days>=90)||(aob>0&&days>=180)){
                busiClients=new BusiClients();
                busiClients.setClientId(clientList.get(i).getClientId());
                busiClients.setCancelaccount("3");
                busiClientsMapper.updateBusiClients(busiClients);
            }
        }
    }

    @Override
    public List<String> getPowerClients(Integer powerType) {
        return busiClientsMapper.getPowerClients(powerType);
    }

    @Scheduled(cron = "1 0 0 * * ?")
    //自动更新状态和发送提醒邮件
    public void updateContactStatusAndSendMail() {
        //获取客户
        List<BusiClients> clientList = busiClientsMapper.selectClinent();
        BusiClients busiClients=null;
        for(int i=0;i<clientList.size();i++){

            if(clientList.get(i).getSignDisableddate()!=null){
                //到期时间比较,到期时间早于今天,到期，更新客户合同状态
                if(clientList.get(i).getSignDisableddate().before(new Date())){
                    busiClients=new BusiClients();
                    busiClients.setClientId(clientList.get(i).getClientId());
                    busiClients.setContractStatus(2);
                    busiClientsMapper.updateBusiClients(busiClients);
                }
                //到期时间比较,到期时间晚于今天,提醒时间早于今天，更新客户合同状态
                else{
                    busiClients=new BusiClients();
                    if(StringUtils.isEmpty(clientList.get(i).getRemindTime())){
                        clientList.get(i).setRemindTime(DateUtils.parseStr(DateUtils.getNextDay(DateUtils.parseDate(clientList.get(i).getSignDisableddate()),-90)));
                    }
                    busiClients.setRemindTime(clientList.get(i).getRemindTime());
                    Date remindTime =DateUtils.parseDate(busiClients.getRemindTime());
                    if(remindTime.before(new Date())){
                        busiClients.setClientId(clientList.get(i).getClientId());
                        busiClients.setContractStatus(1);
                        busiClientsMapper.updateBusiClients(busiClients);
                    }else{
                        busiClients.setClientId(clientList.get(i).getClientId());
                        busiClients.setContractStatus(0);
                        busiClientsMapper.updateBusiClients(busiClients);
                    }
                }
            }
            //到期提醒邮件发送,有提醒时间的按提醒时间提醒,没有的默认三个月
            if((StringUtils.isNotEmpty(clientList.get(i).getRemindTime())&&"0.00".equals(DateUtils.getDays2(DateUtils.parseDateToStr("yyyy-MM-dd",
                    DateUtils.parseDate(clientList.get(i).getRemindTime())),DateUtils.parseDateToStr("yyyy-MM-dd",new Date()))))||
                    (StringUtils.isEmpty(clientList.get(i).getRemindTime())&&clientList.get(i).getSignDisableddate()!=null&&"90.00".equals(DateUtils.getDays2(DateUtils.parseDateToStr
                            ("yyyy-MM-dd",new Date()), DateUtils.parseDateToStr("yyyy-MM-dd", DateUtils.parseDate(clientList.get(i).getSignDisableddate())))))
            ){
                //获取客户和业务邮箱，发送邮件
                String khEmail=clientList.get(i).getClientEmail();
                SysUser su =new SysUser();
                su.setTjrId(clientList.get(i).getClientTjrId());
                String ywEmail=userMapper.selectUserList(su).get(0).getEmail();
                String content=clientList.get(i).getClientUnit()+": \n"+
                        "您好！您与郑州聚通国际货运代理有限公司签订的《国际运输物流服务合同》（以下简称“物流合同”）即将到期，为避免双方后续合作受到影响，请及时联系我司业务员办理续签物流合同。";
                //XXX公司：
                //     您好！您与郑州聚通国际货运代理有限公司签订的《国际运输物流服务合同》（以下简称“物流合同”）即将到期，为避免双方后续合作受到影响，请及时联系我司业务员办理续签物流合同。
                MailUtils.mailByAll("smtp.qiye.163.com","0","notice@zih718.com","Lgzih718",khEmail,"合同即将到期提醒", content);
                MailUtils.mailByAll("smtp.qiye.163.com","0","notice@zih718.com","Lgzih718",ywEmail,"合同即将到期提醒", content);
            }
        }
    }
}
