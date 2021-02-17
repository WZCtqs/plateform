package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocOrderPickconNo;

import java.util.List;

/**
 * 提箱号表Service接口
 * 
 * @author szhbl
 * @date 2020-07-04
 */
public interface IDocOrderPickconNoService 
{
    /**
     * 查询提箱号表
     * 
     * @param id 提箱号表ID
     * @return 提箱号表
     */
    public DocOrderPickconNo selectDocOrderPickconNoById(Long id);

    /**
     * 查询提箱号表列表
     * 
     * @param docOrderPickconNo 提箱号表
     * @return 提箱号表集合
     */
    public List<DocOrderPickconNo> selectDocOrderPickconNoList(DocOrderPickconNo docOrderPickconNo);

    /**
     * 新增提箱号表
     * 
     * @param docOrderPickconNo 提箱号表
     * @return 结果
     */
    public int insertDocOrderPickconNo(DocOrderPickconNo docOrderPickconNo);

    /**
     * 修改提箱号表
     * 
     * @param docOrderPickconNo 提箱号表
     * @return 结果
     */
    public int updateDocOrderPickconNo(DocOrderPickconNo docOrderPickconNo);

    /**
     * 批量删除提箱号表
     * 
     * @param ids 需要删除的提箱号表ID
     * @return 结果
     */
    public int deleteDocOrderPickconNoByIds(Long[] ids);

    /**
     * 删除提箱号表信息
     *
     * @param id 提箱号表ID
     * @return 结果
     */
    public int deleteDocOrderPickconNo(String classNum, String containerNum);
}
