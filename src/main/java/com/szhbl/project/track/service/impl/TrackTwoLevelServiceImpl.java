package com.szhbl.project.track.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.track.mapper.TrackTwoLevelMapper;
import com.szhbl.project.track.domain.TrackTwoLevel;
import com.szhbl.project.track.service.ITrackTwoLevelService;

/**
 * 运踪二级节点Service业务层处理
 * 
 * @author lzd
 * @date 2020-03-23
 */
@Service
public class TrackTwoLevelServiceImpl implements ITrackTwoLevelService 
{
    @Autowired
    private TrackTwoLevelMapper trackTwoLevelMapper;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    /**
     * 查询运踪二级节点
     * 
     * @param id 运踪二级节点ID
     * @return 运踪二级节点
     */
    @Override
    public TrackTwoLevel selectTrackTwoLevelById(Long id)
    {
        return trackTwoLevelMapper.selectTrackTwoLevelById(id);
    }

    /**
     * 查询运踪二级节点列表
     * 
     * @param trackTwoLevel 运踪二级节点
     * @return 运踪二级节点
     */
    @Override
    public List<TrackTwoLevel> selectTrackTwoLevelList(TrackTwoLevel trackTwoLevel)
    {
        return trackTwoLevelMapper.selectTrackTwoLevelList(trackTwoLevel);
    }

    /**
     * 新增运踪二级节点
     * 
     * @param trackTwoLevel 运踪二级节点
     * @return 结果
     */
    @Override
    public int insertTrackTwoLevel(TrackTwoLevel trackTwoLevel)
    {
        trackTwoLevel.setCreateTime(DateUtils.getNowDate());
        trackTwoLevel.setUpdateTime(DateUtils.getNowDate());
        //如已提箱，将预计提箱时间 隐藏
        if("已提箱".equals(trackTwoLevel.getNameZh())){
            TrackTwoLevel delTwoLevel=new TrackTwoLevel();
            delTwoLevel.setOrderId(trackTwoLevel.getOrderId());
            delTwoLevel.setSort(25);
            Long id=trackTwoLevelMapper.selectTwoId(delTwoLevel);
            if(id!=null){
                trackTwoLevelMapper.deleteTrackTwoLevelById(id);
            }
        }
        return trackTwoLevelMapper.insertTrackTwoLevel(trackTwoLevel);
    }

    /**
     * 修改运踪二级节点
     * 
     * @param trackTwoLevel 运踪二级节点
     * @return 结果
     */
    @Override
    public int updateTrackTwoLevel(TrackTwoLevel trackTwoLevel)
    {
        trackTwoLevel.setUpdateTime(DateUtils.getNowDate());
        return trackTwoLevelMapper.updateTrackTwoLevel(trackTwoLevel);
    }

    /**
     * 批量删除运踪二级节点
     * 
     * @param ids 需要删除的运踪二级节点ID
     * @return 结果
     */
    @Override
    public int deleteTrackTwoLevelByIds(Long[] ids)
    {
        return trackTwoLevelMapper.deleteTrackTwoLevelByIds(ids);
    }

    /**
     * 删除运踪二级节点信息
     * 
     * @param id 运踪二级节点ID
     * @return 结果
     */
    @Override
    public int deleteTrackTwoLevelById(Long id)
    {
        return trackTwoLevelMapper.deleteTrackTwoLevelById(id);
    }
    //根据orderid删除
    @Override
    public int deleteTrackTwoLevelByOrderId(String orderId){
        return trackTwoLevelMapper.deleteTrackTwoLevelByOrderId(orderId);
    }
    //根据orderId获取所有二级节点，传参数orderId
    @Override
    public List<TrackTwoLevel> detailByOrderId(String orderId,String boxNum)
    {
        List<TrackTwoLevel>  ttlList=trackTwoLevelMapper.detailByOrderId(orderId);
        //中亚查询去程班列运踪之后不要，回程已订舱-班列运踪
        ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
        //中亚去程只显示到班列运踪
        if("0".equals(shippingOrder.getClassEastandwest())&&"2".equals(shippingOrder.getLineTypeid())){
            //获取计划班列号
            BusiZyInfo zyInfo=busiZyInfoService.selectZyInfoByOrder(orderId,boxNum);
            for(int i=0;i<ttlList.size();i++){
                if(ttlList.get(i).getSort()>23){
                    ttlList.remove(i);
                }
                if("已进站".equals(ttlList.get(i).getNameZh())){
                    ttlList.get(i).setRemark(StringUtils.isNotEmpty(ttlList.get(i).getRemark())?ttlList.get(i).getRemark():""+"计划"+zyInfo.getClasszyNo());
                }
            }
        }else if("1".equals(shippingOrder.getClassEastandwest())&&"2".equals(shippingOrder.getLineTypeid())){
            for(int i=0;i<ttlList.size();i++){
                if(ttlList.get(i).getSort()>7&&ttlList.get(i).getSort()<18){
                    ttlList.remove(i);
                }
            }
        }

        return ttlList;
    }

    //根据orderid和sort获取二级节点id
    @Override
    public Long selectTwoId(TrackTwoLevel trackTwoLevel)
    {
        return trackTwoLevelMapper.selectTwoId(trackTwoLevel);
    }
}
