package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.documentcenter.domain.DocGwczSettlement;
import com.szhbl.project.documentcenter.mapper.DocGwczSettlementMapper;
import com.szhbl.project.documentcenter.service.IDocGwczSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * gwczSettlementService业务层处理
 *
 * @author szhbl
 * @date 2020-12-07
 */
@Service
public class DocGwczSettlementServiceImpl implements IDocGwczSettlementService {
    @Autowired
    private DocGwczSettlementMapper docGwczSettlementMapper;

    /**
     * 查询gwczSettlement
     *
     * @param id gwczSettlementID
     * @return gwczSettlement
     */
    @Override
    public DocGwczSettlement selectDocGwczSettlementById(Long id) {
        return docGwczSettlementMapper.selectDocGwczSettlementById(id);
    }

    @Override
    public DocGwczSettlement selectSettlementByOrderIdAndConNo(String orderId, String containerNo) {
        return docGwczSettlementMapper.selectSettlementByOrderIdAndConNo(orderId, containerNo);
    }

    /**
     * 查询gwczSettlement列表
     *
     * @param docGwczSettlement gwczSettlement
     * @return gwczSettlement
     */
    @Override
    public List<DocGwczSettlement> selectDocGwczSettlementList(DocGwczSettlement docGwczSettlement) {
        return docGwczSettlementMapper.selectDocGwczSettlementList(docGwczSettlement);
    }

    /**
     * 新增gwczSettlement
     *
     * @param docGwczSettlement gwczSettlement
     * @return 结果
     */
    @Override
    public int insertDocGwczSettlement(DocGwczSettlement docGwczSettlement) {
        docGwczSettlement.setCreateTime(DateUtils.getNowDate());
        return docGwczSettlementMapper.insertDocGwczSettlement(docGwczSettlement);
    }

    /**
     * 修改gwczSettlement
     *
     * @param docGwczSettlement gwczSettlement
     * @return 结果
     */
    @Override
    public int updateDocGwczSettlement(DocGwczSettlement docGwczSettlement) {
        docGwczSettlement.setUpdateTime(DateUtils.getNowDate());
        return docGwczSettlementMapper.updateDocGwczSettlement(docGwczSettlement);
    }

    /**
     * 批量删除gwczSettlement
     *
     * @param ids 需要删除的gwczSettlementID
     * @return 结果
     */
    @Override
    public int deleteDocGwczSettlementByIds(Long[] ids) {
        return docGwczSettlementMapper.deleteDocGwczSettlementByIds(ids);
    }

    /**
     * 删除gwczSettlement信息
     *
     * @param id gwczSettlementID
     * @return 结果
     */
    @Override
    public int deleteDocGwczSettlementById(Long id) {
        return docGwczSettlementMapper.deleteDocGwczSettlementById(id);
    }
}
