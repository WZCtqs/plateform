package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.DocOrderConexamine;

import java.util.List;

/**
 * 大口岸箱号随车审核Mapper接口
 *
 * @author szhbl
 * @date 2020-08-19
 */
public interface DocOrderConexamineMapper {
    /**
     * 查询大口岸箱号随车审核
     *
     * @param id 大口岸箱号随车审核ID
     * @return 大口岸箱号随车审核
     */
    public DocOrderConexamine selectDocOrderConexamineById(Long id);

    /**
     * 查询大口岸箱号随车审核列表
     *
     * @param docOrderConexamine 大口岸箱号随车审核
     * @return 大口岸箱号随车审核集合
     */
    public List<DocOrderConexamine> selectDocOrderConexamineList(DocOrderConexamine docOrderConexamine);

    /**
     * 新增大口岸箱号随车审核
     *
     * @param docOrderConexamine 大口岸箱号随车审核
     * @return 结果
     */
    public int insertDocOrderConexamine(DocOrderConexamine docOrderConexamine);

    /**
     * 修改大口岸箱号随车审核
     *
     * @param docOrderConexamine 大口岸箱号随车审核
     * @return 结果
     */
    public int updateDocOrderConexamine(DocOrderConexamine docOrderConexamine);

    /**
     * 删除大口岸箱号随车审核
     *
     * @param id 大口岸箱号随车审核ID
     * @return 结果
     */
    public int deleteDocOrderConexamineById(Long id);

    /**
     * 批量删除大口岸箱号随车审核
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocOrderConexamineByIds(Long[] ids);

    int deleteDocOrderConexamineByOrderId(String orderId);
}
