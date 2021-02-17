package com.szhbl.project.documentcenter.service.impl;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.documentcenter.domain.BusiStation;
import com.szhbl.project.documentcenter.domain.vo.BusiSiteDto;
import com.szhbl.project.documentcenter.mapper.BusiStationMapper;
import com.szhbl.project.documentcenter.service.IBusiStationService;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import com.szhbl.project.order.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 场站地址Service业务层处理
 *
 * @author szhbl
 * @date 2020-01-08
 */
@Slf4j
@Service
public class BusiStationServiceImpl implements IBusiStationService
{
    @Resource
    private BusiStationMapper busiStationMapper;
    @Autowired
    private BusiShippingorderMapper busiShippingorderMapper;
    @Autowired
    private CommonService commonService;

    /**
     * 查询场站地址
     *
     * @param stationId 场站地址ID
     * @return 场站地址
     */
    @Override
    public BusiStation selectBusiStationById(String stationId)
    {
        return busiStationMapper.selectBusiStationById(stationId);
    }

    /**
     * 查询场站地址列表
     *
     * @param busiStation 场站地址
     * @return 场站地址
     */
    @Override
    public List<BusiStation> selectBusiStationList(BusiStation busiStation)
    {
        return busiStationMapper.selectBusiStationList(busiStation);
    }

    @Override
    public List<BusiSiteDto> selectBusiSiteList() {
        return busiStationMapper.selectBusiSiteList();
    }

    /**
     * 新增场站地址
     *
     * @param busiStation 场站地址
     * @return 结果
     */
    @Override
    public int insertBusiStation(BusiStation busiStation)
    {
        busiStation.setStationId(IdUtils.randomUUID());
        return busiStationMapper.insertBusiStation(busiStation);
    }

    /**
     * 修改场站地址
     *
     * @param busiStation 场站地址
     * @return 结果
     */
    @Override
    public int updateBusiStation(BusiStation busiStation)
    {
        int result = busiStationMapper.updateBusiStation(busiStation);
        if(result==1){
            List<String> orderIds = busiStationMapper.orderIdList(busiStation.getStationId());
            if(orderIds.size()>0){
                for(String idItem :orderIds){
                    String orderId = idItem;
                    if(StringUtils.isNotEmpty(orderId)){
                        //推送消息队列
                        ShippingOrder orderInfoRabbmq = busiShippingorderMapper.selectBusiShippingorderById(orderId);
                        if(StringUtils.isNotNull(orderInfoRabbmq)){
                            String messagetype = "7";//托书修改
                            try {
                                commonService.orderInfoMQ(orderInfoRabbmq,messagetype); //推送消息队列
                            } catch (JsonProcessingException e) {
                                log.error("堆场修改-托书更新失败",e.toString(),e.getStackTrace());
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 批量删除场站地址
     *
     * @param stationIds 需要删除的场站地址ID
     * @return 结果
     */
    @Override
    public int deleteBusiStationByIds(String[] stationIds)
    {
        return busiStationMapper.deleteBusiStationByIds(stationIds);
    }

    /**
     * 删除场站地址信息
     *
     * @param stationId 场站地址ID
     * @return 结果
     */
    @Override
    public int deleteBusiStationById(String stationId)
    {
        return busiStationMapper.deleteBusiStationById(stationId);
    }

}
