package com.szhbl.project.track.mapper;


import com.szhbl.project.track.domain.TrackGoodsStatus;
import com.szhbl.project.track.domain.vo.OrderGoodsVo;

import java.util.List;

/**
 * 货物状态表Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-08
 */
public interface TrackGoodsStatusMapper
{
    /**
     * 更新货物状态表
     *
     * @param Tgs 货物状态实体类
     * @return
     */
    public int updateTgs(TrackGoodsStatus Tgs);

    /**
     * 新增货物状态表
     *
     * @param Tgs 货物状态实体类
     * @return
     */
    public int insertTgs(TrackGoodsStatus Tgs);

    /**
     * 根据查询vo查询班货物状态表
     *
     * @param Tgs 货物状态实体类
     * @return
     */
    public List<TrackGoodsStatus> selectGoodsStatusList(TrackGoodsStatus Tgs);

    /**
     * 根据id进行货物状态表数据查询
     *
     * @param id 货物状态实体类
     * @return
     */
    public TrackGoodsStatus selectById(Long id);

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
    //增加箱舱匹配数据
    public int addTgs(TrackGoodsStatus Tgs);

    public List<TrackGoodsStatus>  getTgs();
    public List<TrackGoodsStatus>  checkTgsList(TrackGoodsStatus trackGoodsStatus);
}
