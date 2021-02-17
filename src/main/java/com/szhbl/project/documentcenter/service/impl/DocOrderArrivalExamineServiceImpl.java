package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.documentcenter.domain.DocOrderArrivalExamine;
import com.szhbl.project.documentcenter.mapper.DocOrderArrivalExamineMapper;
import com.szhbl.project.documentcenter.service.IDocOrderArrivalExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关检到货审核表Service业务层处理
 *
 * @author szhbl
 * @date 2020-08-17
 */
@Service
public class DocOrderArrivalExamineServiceImpl implements IDocOrderArrivalExamineService {
    @Autowired
    private DocOrderArrivalExamineMapper docOrderArrivalExamineMapper;

    /**
     * 查询关检到货审核表
     *
     * @param id 关检到货审核表ID
     * @return 关检到货审核表
     */
    @Override
    public DocOrderArrivalExamine selectDocOrderArrivalExamineById(Long id) {
        return docOrderArrivalExamineMapper.selectDocOrderArrivalExamineById(id);
    }

    /**
     * 查询关检到货审核表列表
     *
     * @param docOrderArrivalExamine 关检到货审核表
     * @return 关检到货审核表
     */
    @Override
    public List<DocOrderArrivalExamine> selectDocOrderArrivalExamineList(DocOrderArrivalExamine docOrderArrivalExamine) {
        return docOrderArrivalExamineMapper.selectDocOrderArrivalExamineList(docOrderArrivalExamine);
    }

    /**
     * 新增关检到货审核表
     *
     * @param docOrderArrivalExamine 关检到货审核表
     * @return 结果
     */
    @Override
    public int insertDocOrderArrivalExamine(DocOrderArrivalExamine docOrderArrivalExamine) {
        docOrderArrivalExamine.setCreateTime(DateUtils.getNowDate());
        return docOrderArrivalExamineMapper.insertDocOrderArrivalExamine(docOrderArrivalExamine);
    }

    /**
     * 修改关检到货审核表
     *
     * @param docOrderArrivalExamine 关检到货审核表
     * @return 结果
     */
    @Override
    public int updateDocOrderArrivalExamine(DocOrderArrivalExamine docOrderArrivalExamine) {
        docOrderArrivalExamine.setUpdateTime(DateUtils.getNowDate());
        return docOrderArrivalExamineMapper.updateDocOrderArrivalExamine(docOrderArrivalExamine);
    }

    /**
     * 批量删除关检到货审核表
     *
     * @param ids 需要删除的关检到货审核表ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderArrivalExamineByIds(Long[] ids) {
        return docOrderArrivalExamineMapper.deleteDocOrderArrivalExamineByIds(ids);
    }

    /**
     * 删除关检到货审核表信息
     *
     * @param id 关检到货审核表ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderArrivalExamineById(Long id) {
        return docOrderArrivalExamineMapper.deleteDocOrderArrivalExamineById(id);
    }

    @Override
    public int deleteDocOrderArrivalExamineByOrderId(String orderId) {
        return docOrderArrivalExamineMapper.deleteDocOrderArrivalExamineByOrderId(orderId);
    }
}
