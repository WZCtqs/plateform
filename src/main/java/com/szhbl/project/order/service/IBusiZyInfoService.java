package com.szhbl.project.order.service;

import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.track.ZyInfoClients;

import java.util.List;

/**
 * 中亚信息Service接口
 * 
 * @author dps
 * @date 2020-04-27
 */
public interface IBusiZyInfoService 
{

    /**
     * 客户端中亚运单列表-托书列表
     *
     * @param busiZyInfo 中亚信息
     * @return 中亚信息集合
     */
    public List<ZyInfoClients> selectZyOrderList(ZyInfoClients busiZyInfo);

    /**
     * 客户端中亚运单列表-箱子列表
     */
    public List<ZyInfoClients> selectZyBoxList(String orderId);

    /**
     * 客户端中亚运单-上传运单信息
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    public int zyUpload(ZyInfoClients busiZyInfo);

    /**
     * 平台端中亚运单列表
     */
    public List<ZyInfoClients> selectZyBoxListPf(ZyInfoClients busiZyInfo);


    /**
     * 查询中亚信息列表
     *
     * @param busiZyInfo 中亚信息
     * @return 中亚信息集合
     */
    public List<BusiZyInfo> selectBusiZyInfoList(BusiZyInfo busiZyInfo);

    /**
     * 查询中亚信息
     * 
     * @param costId 中亚信息ID
     * @return 中亚信息
     */
    public BusiZyInfo selectBusiZyInfoById(String costId);

    /**
     * 通过货物状态id查询中亚信息
     *
     * @param trackId 中亚信息ID
     * @return 中亚信息
     */
    public BusiZyInfo selectBusiZyInfoByTrack(Long trackId);

    /**
     * 判断是否存在中亚信息
     */
    public BusiZyInfo selectZyInfoByOrder(String orderId,String xianghao);

    /**
     * 新增中亚信息
     * 
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    public int insertBusiZyInfo(BusiZyInfo busiZyInfo);

    /**
     * 修改中亚信息（通过订单id和箱号）
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    public int updateBusiZyInfoByXh(BusiZyInfo busiZyInfo);

    /**
     * 进站跟踪-通过货物状态表id更新中亚表
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    public int updateBusiZyInfoByTrackId(BusiZyInfo busiZyInfo);

    /**
     * 进站跟踪-通过托书id、箱号更新中亚表
     *
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    public int updateBusiZyInfoByTrackOrder(BusiZyInfo busiZyInfo);

    /**
     * 修改中亚信息
     * 
     * @param busiZyInfo 中亚信息
     * @return 结果
     */
    public int updateBusiZyInfo(BusiZyInfo busiZyInfo);

    /**
     * 根据货物状态表id删除中亚表信息
     * @param orderId  xianghao货物状态ID
     * @return 结果
     */
    public int deleteZyInfoByTrack(String orderId,String xianghao);

    /**
     * 批量删除中亚信息
     * 
     * @param costIds 需要删除的中亚信息ID
     * @return 结果
     */
    public int deleteBusiZyInfoByIds(String[] costIds);

    /**
     * 删除中亚信息信息
     * 
     * @param costId 中亚信息ID
     * @return 结果
     */
    public int deleteBusiZyInfoById(String costId);



}
