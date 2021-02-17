package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocOrderArrivalExamine;

import java.util.List;

/**
 * 关检到货审核表Service接口
 *
 * @author szhbl
 * @date 2020-08-17
 */
public interface IDocOrderArrivalExamineService {
    /**
     * 查询关检到货审核表
     *
     * @param id 关检到货审核表ID
     * @return 关检到货审核表
     */
    public DocOrderArrivalExamine selectDocOrderArrivalExamineById(Long id);

    /**
     * 查询关检到货审核表列表
     *
     * @param docOrderArrivalExamine 关检到货审核表
     * @return 关检到货审核表集合
     */
    public List<DocOrderArrivalExamine> selectDocOrderArrivalExamineList(DocOrderArrivalExamine docOrderArrivalExamine);

    /**
     * 新增关检到货审核表
     *
     * @param docOrderArrivalExamine 关检到货审核表
     * @return 结果
     */
    public int insertDocOrderArrivalExamine(DocOrderArrivalExamine docOrderArrivalExamine);

    /**
     * 修改关检到货审核表
     *
     * @param docOrderArrivalExamine 关检到货审核表
     * @return 结果
     */
    public int updateDocOrderArrivalExamine(DocOrderArrivalExamine docOrderArrivalExamine);

    /**
     * 批量删除关检到货审核表
     *
     * @param ids 需要删除的关检到货审核表ID
     * @return 结果
     */
    public int deleteDocOrderArrivalExamineByIds(Long[] ids);

    /**
     * 删除关检到货审核表信息
     *
     * @param id 关检到货审核表ID
     * @return 结果
     */
    public int deleteDocOrderArrivalExamineById(Long id);

    int deleteDocOrderArrivalExamineByOrderId(String orderId);
}
