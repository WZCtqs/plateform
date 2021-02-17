package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.DocGwczSettlement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * gwczSettlementMapper接口
 *
 * @author szhbl
 * @date 2020-12-07
 */
public interface DocGwczSettlementMapper {
    /**
     * 查询gwczSettlement
     *
     * @param id gwczSettlementID
     * @return gwczSettlement
     */
    public DocGwczSettlement selectDocGwczSettlementById(Long id);

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
     * 删除gwczSettlement
     *
     * @param id gwczSettlementID
     * @return 结果
     */
    public int deleteDocGwczSettlementById(Long id);

    /**
     * 批量删除gwczSettlement
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocGwczSettlementByIds(Long[] ids);

    DocGwczSettlement selectSettlementByOrderIdAndConNo(@Param("orderId") String orderId, @Param("containerNo") String containerNo);
}
