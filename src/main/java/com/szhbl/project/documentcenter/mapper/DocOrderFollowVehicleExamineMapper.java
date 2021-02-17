package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.DocOrderFollowVehicleExamine;

import java.util.List;

/**
 * 大口岸随车审核Mapper接口
 *
 * @author szhbl
 * @date 2020-08-19
 */
public interface DocOrderFollowVehicleExamineMapper {
    /**
     * 查询大口岸随车审核
     *
     * @param id 大口岸随车审核ID
     * @return 大口岸随车审核
     */
    public DocOrderFollowVehicleExamine selectDocOrderFollowVehicleExamineById(Long id);

    /**
     * 查询大口岸随车审核列表
     *
     * @param docOrderFollowVehicleExamine 大口岸随车审核
     * @return 大口岸随车审核集合
     */
    public List<DocOrderFollowVehicleExamine> selectDocOrderFollowVehicleExamineList(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine);

    /**
     * 新增大口岸随车审核
     *
     * @param docOrderFollowVehicleExamine 大口岸随车审核
     * @return 结果
     */
    public int insertDocOrderFollowVehicleExamine(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine);

    /**
     * 修改大口岸随车审核
     *
     * @param docOrderFollowVehicleExamine 大口岸随车审核
     * @return 结果
     */
    public int updateDocOrderFollowVehicleExamine(DocOrderFollowVehicleExamine docOrderFollowVehicleExamine);

    /**
     * 删除大口岸随车审核
     *
     * @param id 大口岸随车审核ID
     * @return 结果
     */
    public int deleteDocOrderFollowVehicleExamineById(Long id);

    /**
     * 批量删除大口岸随车审核
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocOrderFollowVehicleExamineByIds(Long[] ids);

    int deleteDocOrderFollowVehicleExamineByOrderId(String orderId);
}
