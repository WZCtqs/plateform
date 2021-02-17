package com.szhbl.project.track.mapper;

import com.szhbl.project.track.domain.TrackAbnormalBox;
import com.szhbl.project.track.domain.vo.AbnormalBoxEmailsVo;
import com.szhbl.project.track.domain.vo.AbnormalDay;
import com.szhbl.project.track.domain.vo.TrackAbnormalBoxVo;

import java.util.List;

/**
 * 运踪_异常箱Mapper接口
 * 
 * @author lzd
 * @date 2020-03-30
 */
public interface TrackAbnormalBoxMapper 
{
    /**
     * 查询订单表的异常箱
     *
     * @param trackAbnormalBoxVo 异常箱查询vo
     * @return
     */
    public List<TrackAbnormalBoxVo> selectOrderAbnormalBoxList(TrackAbnormalBoxVo trackAbnormalBoxVo);



    /**
     * 查询运踪_异常箱
     * 
     * @param id 运踪_异常箱ID
     * @return 运踪_异常箱
     */
    public TrackAbnormalBox selectTrackAbnormalBoxById(Integer id);

    /**
     * 查询运踪_异常箱列表
     * 
     * @param trackAbnormalBox 运踪_异常箱
     * @return 运踪_异常箱集合
     */
    public List<TrackAbnormalBox> selectTrackAbnormalBoxList(TrackAbnormalBox trackAbnormalBox);

    /**
     * 新增运踪_异常箱
     * 
     * @param trackAbnormalBox 运踪_异常箱
     * @return 结果
     */
    public int insertTrackAbnormalBox(TrackAbnormalBox trackAbnormalBox);

    /**
     * 修改运踪_异常箱
     * 
     * @param trackAbnormalBox 运踪_异常箱
     * @return 结果
     */
    public int updateTrackAbnormalBox(TrackAbnormalBox trackAbnormalBox);

    /**
     * 删除运踪_异常箱
     * 
     * @param id 运踪_异常箱ID
     * @return 结果
     */
    public int deleteTrackAbnormalBoxById(Integer id);

    /**
     * 批量删除运踪_异常箱
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrackAbnormalBoxByIds(Integer[] ids);

    /**
     * 根据箱号和班列日期查询最新一条记录
     * @return 结果
     */
    public TrackAbnormalBox selectByBoxNumAndClassDate(TrackAbnormalBox trackAbnormalBox);

    //获取日报导出数据
    public List<AbnormalDay> selectDayAbnormalBox(TrackAbnormalBoxVo trackAbnormalBoxVo);

    //获取智能运踪
    public String selectAbnormalInformation(String classNum);

    //获取业务需要抄送的领导id和业务自己邮箱
    public AbnormalBoxEmailsVo selectAbVo(String orderId);

    //获取业务需要抄送的领导id对应的负责人名字
    public List<String> selectLeaders( String[] deptIds);

    //获取业务需要抄送的领导id对应的负责人名字
    public List<String> selectLeaderEmails( String[] leaders);

}
