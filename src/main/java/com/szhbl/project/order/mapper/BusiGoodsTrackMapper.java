package com.szhbl.project.order.mapper;

import com.szhbl.project.order.domain.track.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 运踪_货物状态Mapper接口
 * 
 * @author dps
 * @date 2020-04-09
 */
@Repository
public interface BusiGoodsTrackMapper 
{
    /**
     * 订舱组进站查看—货物状态列表
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态集合
     */
    public List<BusiGoodsTrack> selectBusiGoodsTrackList(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看—箱量统计
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态
     */
    public List<Map> selectAmount(BusiGoodsTrack busiGoodsTrack);
    public BusiGoodsTrack selectAmountVW(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看———发运时间编辑,根据id
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateTrackById(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看———发运时间编辑,根据托书id,和箱号
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateTrackByOrder(ImportTrackTime busiGoodsTrack);

    /**
     * 订舱组进站查看———查询准确班列id
     *
     * @param busiGoodsTrack 运踪_货物状态ID
     * @return 运踪_货物状态
     */
    public BusiGoodsTrack selectActualClassesId(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看———查询准确班列id（导入）
     *
     * @param busiGoodsTrack 运踪_货物状态ID
     * @return 运踪_货物状态
     */
    public ImportTrackTime selectActualClassesIdImport(ImportTrackTime busiGoodsTrack);

    /**
     * 修改运踪_货物状态（订舱组进站查看—申请代码编辑）
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateBusiGoodsTrack(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看—导出进站跟踪列表
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态集合
     */
    public List<ExportTrack> selectExportTrackList(ExportTrack busiGoodsTrack);

    /**
     * 订舱组进站查看—导出进站跟踪列表(多联)
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 运踪_货物状态集合
     */
    public List<ExportTrackDl> selectExportTrackDlList(ExportTrackDl busiGoodsTrack);

    /**
     * 订舱组进站查看—发运时间导入
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateTrackTime(ImportTrackTime busiGoodsTrack);

    /**
     * 删除运踪_货物状态
     *
     * @param id 运踪_货物状态ID
     * @return 结果
     */
    public int deleteBusiGoodsTrackById(Integer id);
    public int deleteBusiGoodsTrackByIdUpd(Integer id);
    public OrderGoodsTrackDel selectOrderIdByTrack(Integer id); //查询删除记录的托书id和箱号

    /**
     * 订舱组进站查看/订舱组界面—货物状态详细信息
     * 
     * @param id 运踪_货物状态ID
     * @return 运踪_货物状态
     */
    public BusiGoodsTrack selectBusiGoodsTrackById(Integer id);
    public ImportTrackTime selectBusiGoodsTrackByIdImport(String orderNumber);
    public GoodsTrackMq selectOrderIdByTrackId(Integer id);

    /**
     * 订舱组进站查看/订舱组界面—发送消息队列
     */
    public GoodsTrackMq selectTrackInfoToMq(String orderId,String boxNum);

    /**
     * 订舱组界面—货物状态列表
     */
    public List<GoodsTrackDcz> selectBusiGoodsTrackDczList(GoodsTrackDcz busiGoodsTrack);

    /**
     * 订舱组界面—箱量统计
     */
    public List<Map> selectAmountDcz(GoodsTrackDcz busiGoodsTrack);
    public List<Map> selectAmountVWDcz(GoodsTrackDcz busiGoodsTrack);






    /**
     * 新增运踪_货物状态
     * 
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int insertBusiGoodsTrack(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看/订舱组界面—删除货物状态
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiGoodsTrackByIds(Integer[] ids);

}
