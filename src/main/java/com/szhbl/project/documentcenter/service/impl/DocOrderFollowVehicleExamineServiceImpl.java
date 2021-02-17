package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.documentcenter.domain.DocOrderFollowVehicleExamine;
import com.szhbl.project.documentcenter.mapper.DocOrderFollowVehicleExamineMapper;
import com.szhbl.project.documentcenter.service.IDocOrderFollowVehicleExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 大口岸随车审核Service业务层处理
 *
 * @author szhbl
 * @date 2020-08-19
 */
@Service
public class DocOrderFollowVehicleExamineServiceImpl implements IDocOrderFollowVehicleExamineService {
    @Autowired
    private DocOrderFollowVehicleExamineMapper docOrderFollowVehicleExamineMapper;

    /**
     * 查询大口岸随车审核
     *
     * @param id 大口岸随车审核ID
     * @return 大口岸随车审核
     */
    @Override
    public DocOrderFollowVehicleExamine selectDocOrderFollowVehicleExamineById(Long id) {
        return docOrderFollowVehicleExamineMapper.selectDocOrderFollowVehicleExamineById(id);
    }

    /**
     * 查询大口岸随车审核列表
     *
     * @param docOrderFollowVehicleExamine 大口岸随车审核
     * @return 大口岸随车审核
     */
    @Override
    public List<DocOrderFollowVehicleExamine> selectDocOrderFollowVehicleExamineList(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine) {
        return docOrderFollowVehicleExamineMapper.selectDocOrderFollowVehicleExamineList(docOrderFollowVehicleExamine);
    }

    /**
     * 新增大口岸随车审核
     *
     * @param docOrderFollowVehicleExamine 大口岸随车审核
     * @return 结果
     */
    @Override
    public int insertDocOrderFollowVehicleExamine(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine) {
        return docOrderFollowVehicleExamineMapper.insertDocOrderFollowVehicleExamine(docOrderFollowVehicleExamine);
    }

    /**
     * 修改大口岸随车审核
     *
     * @param docOrderFollowVehicleExamine 大口岸随车审核
     * @return 结果
     */
    @Override
    public int updateDocOrderFollowVehicleExamine(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine) {
        docOrderFollowVehicleExamine.setUpdateTime(DateUtils.getNowDate());
        return docOrderFollowVehicleExamineMapper.updateDocOrderFollowVehicleExamine(docOrderFollowVehicleExamine);
    }

    /**
     * 批量删除大口岸随车审核
     *
     * @param ids 需要删除的大口岸随车审核ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderFollowVehicleExamineByIds(Long[] ids) {
        return docOrderFollowVehicleExamineMapper.deleteDocOrderFollowVehicleExamineByIds(ids);
    }

    /**
     * 删除大口岸随车审核信息
     *
     * @param id 大口岸随车审核ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderFollowVehicleExamineById(Long id) {
        return docOrderFollowVehicleExamineMapper.deleteDocOrderFollowVehicleExamineById(id);
    }

    @Override
    public int deleteDocOrderFollowVehicleExamineByOrderId(String orderId) {
        return docOrderFollowVehicleExamineMapper.deleteDocOrderFollowVehicleExamineByOrderId(orderId);
    }
}
