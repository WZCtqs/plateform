package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocGwczSettlement;

import java.util.List;

/**
 * gwczSettlementService接口
 *
 * @author szhbl
 * @date 2020-12-07
 */
public interface IDocGwczSettlementService {
    /**
     * 查询gwczSettlement
     *
     * @param id gwczSettlementID
     * @return gwczSettlement
     */
    public DocGwczSettlement selectDocGwczSettlementById(Long id);

    public DocGwczSettlement selectSettlementByOrderIdAndConNo(String orderId, String containerNo);

    /**
     * 查询gwczSettlement列表
     *
     * @param docGwczSettlement gwczSettlement
     * @return gwczSettlement集合
     */
    public List<DocGwczSettlement> selectDocGwczSettlementList(DocGwczSettlement docGwczSettlement);

    /**
     * 新增gwczSettlement
     *
     * @param docGwczSettlement gwczSettlement
     * @return 结果
     */
    public int insertDocGwczSettlement(DocGwczSettlement docGwczSettlement);

    /**
     * 修改gwczSettlement
     *
     * @param docGwczSettlement gwczSettlement
     * @return 结果
     */
    public int updateDocGwczSettlement(DocGwczSettlement docGwczSettlement);

    /**
     * 批量删除gwczSettlement
     *
     * @param ids 需要删除的gwczSettlementID
     * @return 结果
     */
    public int deleteDocGwczSettlementByIds(Long[] ids);

    /**
     * 删除gwczSettlement信息
     *
     * @param id gwczSettlementID
     * @return 结果
     */
    public int deleteDocGwczSettlementById(Long id);
}
