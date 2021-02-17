package com.szhbl.project.backclient.service;

import com.szhbl.project.backclient.domain.DocumentsRequired;
import java.util.List;

/**
 * 单证要求Service接口
 * 
 * @author bxt
 * @date 2020-01-07
 */
public interface IDocumentsRequiredService 
{
    /**
     * 查询单证要求
     * 
     * @param id 单证要求ID
     * @return 单证要求
     */
    public DocumentsRequired selectDocumentsRequiredById(Long id);

    /**
     * 查询单证要求列表
     * 
     * @param documentsRequired 单证要求
     * @return 单证要求集合
     */
    public List<DocumentsRequired> selectDocumentsRequiredList(DocumentsRequired documentsRequired);

    /**
     * 新增单证要求
     * 
     * @param documentsRequired 单证要求
     * @return 结果
     */
    public int insertDocumentsRequired(DocumentsRequired documentsRequired);

    /**
     * 修改单证要求
     * 
     * @param documentsRequired 单证要求
     * @return 结果
     */
    public int updateDocumentsRequired(DocumentsRequired documentsRequired);

    /**
     * 批量删除单证要求
     * 
     * @param ids 需要删除的单证要求ID
     * @return 结果
     */
    public int deleteDocumentsRequiredByIds(Long[] ids);

    /**
     * 删除单证要求信息
     * 
     * @param id 单证要求ID
     * @return 结果
     */
    public int deleteDocumentsRequiredById(Long id);
}
