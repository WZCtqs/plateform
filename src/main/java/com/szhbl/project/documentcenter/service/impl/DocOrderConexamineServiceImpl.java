package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.DocOrderConexamine;
import com.szhbl.project.documentcenter.mapper.DocOrderConexamineMapper;
import com.szhbl.project.documentcenter.service.IDocOrderConexamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 大口岸箱号随车审核Service业务层处理
 *
 * @author szhbl
 * @date 2020-08-19
 */
@Service
public class DocOrderConexamineServiceImpl implements IDocOrderConexamineService {
    @Autowired
    private DocOrderConexamineMapper docOrderConexamineMapper;

    /**
     * 查询大口岸箱号随车审核
     *
     * @param id 大口岸箱号随车审核ID
     * @return 大口岸箱号随车审核
     */
    @Override
    public DocOrderConexamine selectDocOrderConexamineById(Long id) {
        return docOrderConexamineMapper.selectDocOrderConexamineById(id);
    }

    /**
     * 查询大口岸箱号随车审核列表
     *
     * @param docOrderConexamine 大口岸箱号随车审核
     * @return 大口岸箱号随车审核
     */
    @Override
    public List<DocOrderConexamine> selectDocOrderConexamineList(DocOrderConexamine docOrderConexamine) {
        return docOrderConexamineMapper.selectDocOrderConexamineList(docOrderConexamine);
    }

    /**
     * 新增大口岸箱号随车审核
     *
     * @param docOrderConexamine 大口岸箱号随车审核
     * @return 结果
     */
    @Override
    public int insertDocOrderConexamine(DocOrderConexamine docOrderConexamine) {
        return docOrderConexamineMapper.insertDocOrderConexamine(docOrderConexamine);
    }

    /**
     * 修改大口岸箱号随车审核
     *
     * @param docOrderConexamine 大口岸箱号随车审核
     * @return 结果
     */
    @Override
    public int updateDocOrderConexamine(DocOrderConexamine docOrderConexamine) {
        return docOrderConexamineMapper.updateDocOrderConexamine(docOrderConexamine);
    }

    /**
     * 批量删除大口岸箱号随车审核
     *
     * @param ids 需要删除的大口岸箱号随车审核ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderConexamineByIds(Long[] ids) {
        return docOrderConexamineMapper.deleteDocOrderConexamineByIds(ids);
    }

    /**
     * 删除大口岸箱号随车审核信息
     *
     * @param id 大口岸箱号随车审核ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderConexamineById(Long id) {
        return docOrderConexamineMapper.deleteDocOrderConexamineById(id);
    }

    @Override
    public int deleteDocOrderConexamineByOrderId(String orderId) {
        return docOrderConexamineMapper.deleteDocOrderConexamineByOrderId(orderId);
    }
}
