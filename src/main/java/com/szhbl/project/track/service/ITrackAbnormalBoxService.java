package com.szhbl.project.track.service;

import com.szhbl.project.track.domain.TrackAbnormalBox;
import com.szhbl.project.track.domain.vo.AbnormalBoxEmailsVo;
import com.szhbl.project.track.domain.vo.AbnormalDay;
import com.szhbl.project.track.domain.vo.TrackAbnormalBoxVo;

import java.util.List;

/**
 * 运踪_异常箱Service接口
 * 
 * @author lzd
 * @date 2020-03-30
 */
public interface ITrackAbnormalBoxService 
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
     * 批量删除运踪_异常箱
     * 
     * @param ids 需要删除的运踪_异常箱ID
     * @return 结果
     */
    public int deleteTrackAbnormalBoxByIds(Integer[] ids);

    /**
     * 删除运踪_异常箱信息
     * 
     * @param id 运踪_异常箱ID
     * @return 结果
     */
    public int deleteTrackAbnormalBoxById(Integer id);

    /**
     * 根据箱号和班列日期查询最新一条记录
     * @return 结果
     */
    public TrackAbnormalBox selectByBoxNumAndClassDate(TrackAbnormalBox trackAbnormalBox);

    //获取日报导出数据
    public List<AbnormalDay> selectDayAbnormalBox(TrackAbnormalBoxVo trackAbnormalBoxVo);

    //获取智能运踪
    public String selectAbnormalInformation(String classNum);
    //获取业务，跟单，客户 需要抄送的领导、相关部门邮箱
    public AbnormalBoxEmailsVo getEmails(String orderId,String isBcc);
    //新增发送
    public int addAndSend(TrackAbnormalBox trackAbnormalBox);
    //编辑发送
    public int editAndSend(TrackAbnormalBox trackAbnormalBox);

}
