package com.szhbl.project.order.service.impl;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.config.rabbit.trackzy.PlatformTrackRabbitmqConfig;
import com.szhbl.project.order.domain.track.GoodsTrackMq;
import com.szhbl.project.order.domain.track.ZyInfoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiZyInfoMapper;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.service.IBusiZyInfoService;


/**
 * 中亚信息Service业务层处理
 * 
 * @author dps
 * @date 2020-04-27
 */
@Slf4j
@Service
public class BusiZyInfoServiceImpl implements IBusiZyInfoService 
{
    @Autowired
    private BusiZyInfoMapper busiZyInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 客户端中亚运单列表-托书列表
     *
     * @param busiZyInfo 中亚信息
     * @return 中亚信息集合
     */
    @Override
    public List<ZyInfoClients> selectZyOrderList(ZyInfoClients busiZyInfo)
    {
        return busiZyInfoMapper.selectZyOrderList(busiZyInfo);
    }

    /**
     * 客户端中亚运单列表-箱子列表
     */
    @Override
    public List<ZyInfoClients> selectZyBoxList(String orderId)
    {
        return busiZyInfoMapper.selectZyBoxList(orderId);
    }

    /**
     * 客户端中亚运单-上传运单信息
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    @Override
    public int zyUpload(ZyInfoClients busiZyInfo)
    {
        int result = 0;
        try{
            busiZyInfo.setZyunitTime(DateUtils.getNowDate());
            busiZyInfo.setIsApplynum("1");
            result = busiZyInfoMapper.zyUpload(busiZyInfo);
            if(result==1){
                //发送消息队列
                String costId = busiZyInfo.getCostId();
                if(StringUtils.isNotEmpty(costId)){
                    GoodsTrackMq zyinfo = busiZyInfoMapper.selectBusiZyInfoByZyId(costId);
                    if(StringUtils.isNotNull(zyinfo)){
                        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                        MessageProperties header = new MessageProperties();
                        header.getHeaders().put("__TypeId__","Object");
                        ObjectMapper objectMapper = new ObjectMapper();
                        Message message = new Message(objectMapper.writeValueAsBytes(zyinfo), header);
                        rabbitTemplate.convertAndSend(PlatformTrackRabbitmqConfig.PLATFORM_GOODSSTATUS_TOPIC_EXCHANGE, "platform.goods.status.zyinfo", message,correlationData);
                    }
                }
            }
        }catch (Exception e){
            log.error("中亚运单更新失败",e.toString(),e.getStackTrace());
        }
        return result;
    }

    /**
     * 平台端中亚运单列表
     */
    @Override
    public List<ZyInfoClients> selectZyBoxListPf(ZyInfoClients busiZyInfo)
    {
        return busiZyInfoMapper.selectZyBoxListPf(busiZyInfo);
    }

    /**
     * 查询中亚信息列表
     *
     * @param busiZyInfo 中亚信息
     * @return 中亚信息
     */
    @Override
    public List<BusiZyInfo> selectBusiZyInfoList(BusiZyInfo busiZyInfo)
    {
        return busiZyInfoMapper.selectBusiZyInfoList(busiZyInfo);
    }

    /**
     * 查询中亚信息
     * 
     * @param costId 中亚信息ID
     * @return 中亚信息
     */
    @Override
    public BusiZyInfo selectBusiZyInfoById(String costId)
    {
        return busiZyInfoMapper.selectBusiZyInfoById(costId);
    }

    /**
     * 通过货物状态id查询中亚信息
     *
     * @param trackId 中亚信息ID
     * @return 中亚信息
     */
    @Override
    public BusiZyInfo selectBusiZyInfoByTrack(Long trackId)
    {
        return busiZyInfoMapper.selectBusiZyInfoByTrack(trackId);
    }

    /**
     * 判断是否存在中亚信息
     */
    @Override
    public BusiZyInfo selectZyInfoByOrder(String orderId,String xianghao){
        return busiZyInfoMapper.selectZyInfoByOrder(orderId,xianghao);
    }

    /**
     * 新增中亚信息
     * 
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    @Override
    public int insertBusiZyInfo(BusiZyInfo busiZyInfo)
    {
        busiZyInfo.setCostId(IdUtils.randomUUID());
        busiZyInfo.setCreatedate(DateUtils.getNowDate());
        return busiZyInfoMapper.insertBusiZyInfo(busiZyInfo);
    }

    /**
     * 修改中亚信息（通过订单id和箱号）
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    @Override
    public int updateBusiZyInfoByXh(BusiZyInfo busiZyInfo)
    {
        busiZyInfo.setCreatedate(DateUtils.getNowDate());
        return busiZyInfoMapper.updateBusiZyInfoByXh(busiZyInfo);
    }

    /**
     * 进站跟踪-通过货物状态表id更新中亚表
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    @Override
    public int updateBusiZyInfoByTrackId(BusiZyInfo busiZyInfo)
    {
        busiZyInfo.setCreatedate(DateUtils.getNowDate());
        return busiZyInfoMapper.updateBusiZyInfoByTrackId(busiZyInfo);
    }

    /**
     * 进站跟踪-通过托书id、箱号更新中亚表
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    @Override
    public int updateBusiZyInfoByTrackOrder(BusiZyInfo busiZyInfo){
        busiZyInfo.setCreatedate(DateUtils.getNowDate());
        return busiZyInfoMapper.updateBusiZyInfoByTrackOrder(busiZyInfo);
    }

    /**
     * 修改中亚信息
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    @Override
    public int updateBusiZyInfo(BusiZyInfo busiZyInfo)
    {
        return busiZyInfoMapper.updateBusiZyInfo(busiZyInfo);
    }

    /**
     * 根据货物状态表id删除中亚表信息
     * @param orderId  xianghao货物状态ID
     * @return 结果
     */
    @Override
    public int deleteZyInfoByTrack(String orderId,String xianghao){
        return busiZyInfoMapper.deleteZyInfoByTrack(orderId,xianghao);
    }

    /**
     * 批量删除中亚信息
     * 
     * @param costIds 需要删除的中亚信息ID
     * @return 结果
     */
    @Override
    public int deleteBusiZyInfoByIds(String[] costIds)
    {
        return busiZyInfoMapper.deleteBusiZyInfoByIds(costIds);
    }

    /**
     * 删除中亚信息信息
     * 
     * @param costId 中亚信息ID
     * @return 结果
     */
    @Override
    public int deleteBusiZyInfoById(String costId)
    {
        return busiZyInfoMapper.deleteBusiZyInfoById(costId);
    }

}
