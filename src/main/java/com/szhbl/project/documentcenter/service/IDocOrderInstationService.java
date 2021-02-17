package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocOrderInstation;
import java.util.List;

/**
 * 回程进站统计表Service接口
 *
 * @author szhbl
 * @date 2020-05-25
 */
public interface IDocOrderInstationService
{
    /**
     * 查询回程进站统计表
     *
     * @param orderId 回程进站统计表ID
     * @return 回程进站统计表
     */
    public DocOrderInstation selectDocOrderInstationByOrderId(String orderId);

    /**
     * 查询回程进站统计表列表
     *
     * @param docOrderInstation 回程进站统计表
     * @return 回程进站统计表集合
     */
    public List<DocOrderInstation> selectDocOrderInstationList(DocOrderInstation docOrderInstation);

    /**
     * 新增回程进站统计表
     *
     * @param docOrderInstation 回程进站统计表
     * @return 结果
     */
    public int insertDocOrderInstation(DocOrderInstation docOrderInstation);

    /**
     * 修改回程进站统计表
     *
     * @param docOrderInstation 回程进站统计表
     * @return 结果
     */
    public int updateDocOrderInstation(DocOrderInstation docOrderInstation);

    /**
     * 批量删除回程进站统计表
     *
     * @param ids 需要删除的回程进站统计表ID
     * @return 结果
     */
    public int deleteDocOrderInstationByIds(Long[] ids);

    /**
     * 删除回程进站统计表信息
     *
     * @param orderId 回程进站统计表ID
     * @return 结果
     */
    public int deleteDocOrderInstationByOrderId(String orderId);
}
