package com.szhbl.project.order.service;

import com.szhbl.project.order.domain.track.*;

import java.util.List;
import java.util.Map;

/**
 * 运踪_货物状态Service接口
 * 
 * @author dps
 * @date 2020-04-09
 */
public interface IBusiGoodsTrackService 
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
     * @return 运踪_货物状态集合
     */
    public List<Map> selectAmount(BusiGoodsTrack busiGoodsTrack);
    public BusiGoodsTrack selectAmountVW(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看———发运时间编辑
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateBusiGoodsTrack(BusiGoodsTrack busiGoodsTrack);

    /**
     * 订舱组进站查看—申请代码编辑
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateApplyCode(BusiGoodsTrack busiGoodsTrack);

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
     * 订舱组进站查看/订舱组界面—删除货物状态
     *
     * @param idsArr 需要删除的运踪_货物状态ID
     * @return 结果
     */
    public int deleteBusiGoodsTrackByIds(Long[] idsArr);

    /**
     * 订舱组进站查看/订舱组界面—货物状态详细信息
     * 
     * @param id 运踪_货物状态ID
     * @return 运踪_货物状态
     */
    public BusiGoodsTrack selectBusiGoodsTrackById(Integer id);
    public ImportTrackTime selectBusiGoodsTrackByIdImport(String orderNumber);

    /**
     * 订舱组进站查看/订舱组界面—班列号编辑/订舱备注编辑
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateClassBhAdd(BusiGoodsTrack busiGoodsTrack);

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
     * 订舱组界面—舱位号批量修改
     */
    public int orderBlEdit(BusiGoodsTrack busiGoodsTrack);










    /**
     * 新增运踪_货物状态
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int insertBusiGoodsTrack(BusiGoodsTrack busiGoodsTrack);

    /**
     * 删除运踪_货物状态信息
     * 
     * @param id 运踪_货物状态ID
     * @return 结果
     */
    public int deleteBusiGoodsTrackById(Integer id);

    /**
     * 导入发运时间
     *
     * @param busiGoodsTrack 运踪_货物状态
     * @return 结果
     */
    public int updateTrackTime(ImportTrackTime busiGoodsTrack);
}
