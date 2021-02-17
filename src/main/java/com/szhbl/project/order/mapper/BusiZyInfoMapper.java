package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.track.GoodsTrackMq;
import com.szhbl.project.order.domain.track.ZyInfoClients;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 中亚信息Mapper接口
 * 
 * @author dps
 * @date 2020-04-27
 */
@Repository
public interface BusiZyInfoMapper 
{
    /**
     * 客户端中亚运单列表-托书列表
     *
     * @param busiZyInfo 中亚信息
     * @return 中亚信息集合
     */
    public List<ZyInfoClients> selectZyOrderList(ZyInfoClients busiZyInfo);

    /**
     * 客户端中亚运单列表-托书列表
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
    public GoodsTrackMq selectBusiZyInfoByZyId(String costId);

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
    public BusiZyInfo selectZyInfoByOrder(@Param("orderId") String orderId,@Param("xianghao")String xianghao);

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
    public int updateZmdcByXh(BusiZyInfo busiZyInfo);

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
    public int deleteZyInfoByTrackId(Integer trackId);

    /**
     * 删除中亚信息
     * 
     * @param costId 中亚信息ID
     * @return 结果
     */
    public int deleteBusiZyInfoById(String costId);

    /**
     * 批量删除中亚信息
     * 
     * @param costIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiZyInfoByIds(String[] costIds);

    /**
     * 大口岸中亚运单推送
     */
    public List<String> selectZyFyCostId();

    //订舱组查看-通过托书id更新中亚表
    public int updateZyInfoByOrder(BusiZyInfo busiZyInfo);

}
