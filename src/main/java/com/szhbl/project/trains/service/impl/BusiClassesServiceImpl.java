package com.szhbl.project.trains.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.trains.mapper.BusiClassesMapper;
import com.szhbl.project.trains.domain.BusiClasses;
import com.szhbl.project.trains.service.IBusiClassesService;

/**
 * 班列Service业务层处理
 * 
 * @author dps
 * @date 2020-01-13
 */
@Service
public class BusiClassesServiceImpl implements IBusiClassesService 
{
    @Autowired
    private BusiClassesMapper busiClassesMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 查询班列
     * 
     * @param classId 班列ID
     * @return 班列
     */
    @Override
    public BusiClasses selectBusiClassesById(String classId)
    {
        return busiClassesMapper.selectBusiClassesById(classId);
    }

    /**
     * 根据班列编号查询班列
     * 
     * @param classBh 班列编号
     * @return 班列
     */
    @Override
    public BusiClasses selectBusiClassesByBh(String  classBh)
    {
        return busiClassesMapper.selectBusiClassesByBh(classBh);
    }

    /**
     * 查询班列列表
     * 
     * @param busiClasses 班列
     * @return 班列
     */
    @Override
    public List<BusiClasses> selectBusiClassesList(BusiClasses busiClasses)
    {
        return busiClassesMapper.selectBusiClassesList(busiClasses);
    }

    /**
     * 新增班列
     * 
     * @param busiClasses 班列
     * @return 结果
     */
    @Override
    public int insertBusiClasses(BusiClasses busiClasses)
    {
        busiClasses.setCreatedate(DateUtils.getNowDate());
        busiClasses.setClassId(IdUtils.randomUUID());
        String[] receiveSiteFullArr = busiClasses.getReceiveSiteFullArr();
        String[] receiveSiteLclArr = busiClasses.getReceiveSiteLclArr();
        if(StringUtils.isNotNull(receiveSiteFullArr)){
            busiClasses.setReceiveSiteFull(StringUtils.join(receiveSiteFullArr, ","));
        }
        if(StringUtils.isNotNull(receiveSiteLclArr)){
            busiClasses.setReceiveSiteLcl(StringUtils.join(receiveSiteLclArr, ","));
        }
        int result = busiClassesMapper.insertBusiClasses(busiClasses);
        if(result ==1){
            //推送消息队列
            String classId = busiClasses.getClassId();
            BusiClasses classesInfo = busiClassesMapper.selectBusiClassesById(classId);
            classesInfo.setIsDelete("0"); //是否删除 0否 1是
//            String messageProperties = JSONObject.toJSONString(classesInfo, SerializerFeature.WriteMapNullValue);
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_CLASS_TOPIC_EXCHANGE, "order.class.message", classesInfo,correlationData);
        }
        return result;
    }

    /**
     * 修改班列
     * 
     * @param busiClasses 班列
     * @return 结果
     */
    @Override
    public int updateBusiClasses(BusiClasses busiClasses)
    {
        String[] receiveSiteFullArr = busiClasses.getReceiveSiteFullArr();
        String[] receiveSiteLclArr = busiClasses.getReceiveSiteLclArr();
        if(StringUtils.isNotNull(receiveSiteFullArr)){
            busiClasses.setReceiveSiteFull(StringUtils.join(receiveSiteFullArr, ","));
        }
        if(StringUtils.isNotNull(receiveSiteLclArr)){
            busiClasses.setReceiveSiteLcl(StringUtils.join(receiveSiteLclArr, ","));
        }
        busiClasses.setCreatedate(DateUtils.getNowDate());
        int result = busiClassesMapper.updateBusiClasses(busiClasses);
        if(result==1){
            //发送消息队列
            String classId = busiClasses.getClassId();
            BusiClasses classesInfo = busiClassesMapper.selectBusiClassesById(classId);
            classesInfo.setIsDelete("0"); //是否删除 0否 1是
//            String messageProperties = JSONObject.toJSONString(classesInfo, SerializerFeature.WriteMapNullValue);
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_CLASS_TOPIC_EXCHANGE, "order.class.message", classesInfo,correlationData);
        }
        return result;
    }

    /**
     * 批量删除班列
     * 
     * @param classIds 需要删除的班列ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesByIds(String[] classIds)
    {
        int result = busiClassesMapper.deleteBusiClassesByIds(classIds);
//        if(result > 0){
//            BusiClasses classesInfo = new BusiClasses();
//            classesInfo.setIsDelete("1"); //是否删除 0否 1是
//            String deleteIdStr = StringUtils.join(classIds,",");
//            classesInfo.setDeleteIdStr(deleteIdStr);
////            String messageProperties = JSONObject.toJSONString(classesInfo, SerializerFeature.WriteMapNullValue);
//            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//            rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_CLASS_TOPIC_EXCHANGE, "order.class.message", classesInfo,correlationData);
//        }
        return result;
    }

    /**
     * 删除班列信息
     * 
     * @param classId 班列ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesById(String classId)
    {
        int result = busiClassesMapper.deleteBusiClassesById(classId);
        if(result==1){
            BusiClasses classesInfo = new BusiClasses();
            classesInfo.setIsDelete("1"); //是否删除 0否 1是
            classesInfo.setDeleteIdStr(String.valueOf(classId));
//            String messageProperties = JSONObject.toJSONString(classesInfo, SerializerFeature.WriteMapNullValue);
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_CLASS_TOPIC_EXCHANGE, "order.class.message", classesInfo,correlationData);
        }
        return result;
    }

    /**
     * 校验班列编号是否唯一
     *
     * @param classBh 班列编号
     * @return 结果
     */
    @Override
    public String checkclassBhUnique(String classBh)
    {
        int count = busiClassesMapper.checkclassBhUnique(classBh);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验班列编号是否唯一(更新)
     *
     * @param busiClasses 班列编号
     * @return 结果
     */
    @Override
    public String checkclassBhUniqueUpd(BusiClasses busiClasses)
    {
        String classId = busiClasses.getClassId();
        BusiClasses info = busiClassesMapper.checkclassBhUniqueUpd(busiClasses.getClassBh());
        if (StringUtils.isNotNull(info) && !StringUtils.equals(info.getClassId(),classId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 修改是否可修改状态
     *
     * @param classId
     * @param pxentryState 线路站点
     * @return 结果
     */
    @Override
    public int updatePxStatus(String classId,Long pxentryState)
    {
        int result = busiClassesMapper.updatePxStatus(classId,pxentryState);
        if(result==1){
            //发送消息队列
            BusiClasses classesInfo = busiClassesMapper.selectBusiClassesById(classId);
            classesInfo.setIsDelete("0"); //是否删除 0否 1是
//            String messageProperties = JSONObject.toJSONString(classesInfo, SerializerFeature.WriteMapNullValue);
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_CLASS_TOPIC_EXCHANGE, "order.class.message", classesInfo,correlationData);
        }
        return result;
    }

    /**
     * 查看是否有模板下班列
     *
     * @param classTId 模板id
     * @return 结果
     */
    @Override
    public boolean hasChildByTemId(String classTId)
    {
        int result = busiClassesMapper.hasChildByTemId(classTId);
        return result > 0 ? true : false;
    }

    /**
     * 更新班列状态
     * @param nowDate
     */
    @Override
    public void updateClassState(Date nowDate){
        List<String> updClassesList = busiClassesMapper.updClassesList(nowDate);
        busiClassesMapper.updateClassState(nowDate);
        if(updClassesList.size()>0){
            for(String item:updClassesList){
                String classId = item;
                BusiClasses classesInfo = busiClassesMapper.selectBusiClassesById(classId);
                classesInfo.setIsDelete("0"); //是否删除 0否 1是
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend(ShipOrderRabbitmq.ORDER_CLASS_TOPIC_EXCHANGE, "order.class.message", classesInfo,correlationData);
            }
        }
    }
}
