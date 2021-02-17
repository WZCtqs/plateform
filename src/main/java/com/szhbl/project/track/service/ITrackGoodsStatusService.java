package com.szhbl.project.track.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.domain.vo.OrderGoodsVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 货物状态表Service接口
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public interface ITrackGoodsStatusService
{
   /**
     * 货物状态表数据导入
     * 
     * @param file 导入文件
     * @return AjaxResult
     */
    public AjaxResult importData(MultipartFile file)  throws Exception;

    /**
     * 货物状态表数据查询
     *
     * @param tgs 查询条件
     * @return AjaxResult
     */
    public List<TrackGoodsStatus>  selectGoodsStatusList(TrackGoodsStatus tgs);

    /**
     * 根据id进行货物状态表数据查询
     *
     * @param id 查询条件
     * @return AjaxResult
     */
    public TrackGoodsStatus  selectById(Long id);

    /**
     * 货物状态表数据修改
     *
     * @param Tgs 修改条件
     * @return AjaxResult
     */
    public int updateTgs(TrackGoodsStatus Tgs);

    /**
     * 货物品名查询
     *
     * @param ogv 查询条件
     * @return AjaxResult
     */
    public List<OrderGoodsVo> selectGoodsList(OrderGoodsVo ogv);

    //根据订单id和箱号查看数据库是否有该条数据
    public TrackGoodsStatus checkTgs(TrackGoodsStatus trackGoodsStatus);
    //新增箱舱匹配数据
    public int insertXcppTgs(TrackGoodsStatus trackGoodsStatus);
    //删除箱舱匹配数据
    public int deleteXcppTgs(TrackGoodsStatus trackGoodsStatus);

    //增加箱舱匹配数据
    public int addTgs(TrackGoodsStatus trackGoodsStatus);

}
