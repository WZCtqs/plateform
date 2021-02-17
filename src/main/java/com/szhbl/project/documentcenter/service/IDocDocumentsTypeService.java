package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocDocumentsType;

import java.util.List;

/**
 * 单证类型Service接口
 *
 * @author szhbl
 * @date 2020-01-13
 */
public interface IDocDocumentsTypeService {
    /**
     * 查询单证类型
     *
     * @param id 单证类型ID
     * @return 单证类型
     */
    public DocDocumentsType selectDocDocumentsTypeById(Long id);

    public DocDocumentsType getTypeByTypeKey(String fileTypeKey);

    /**
     * 查询单证类型列表
     *
     * @param docDocumentsType 单证类型
     * @return 单证类型集合
     */
    public List<DocDocumentsType> selectDocDocumentsTypeList(DocDocumentsType docDocumentsType);

    public List<DocDocumentsType> selectParentList(DocDocumentsType docDocumentsType);

    /**
     * 新增单证一级类型
     *
     * @param docDocumentsType 单证类型
     * @return 结果
     */
    public int insertDocDocumentsType(DocDocumentsType docDocumentsType);

    /**
     * 编辑单证一级类型
     *
     * @param docDocumentsType
     * @return
     */
    public int updateDocType(DocDocumentsType docDocumentsType);

    /**
     * 修改单证类型
     *
     * @param docDocumentsType 单证类型
     * @return 结果
     */
    public int updateDocDocumentsType(DocDocumentsType docDocumentsType);

    /**
     * 批量删除单证类型
     *
     * @param ids 需要删除的单证类型ID
     * @return 结果
     */
    public int deleteDocDocumentsTypeByIds(Long[] ids);

    /**
     * 删除单证类型信息
     *
     * @param id 单证类型ID
     * @return 结果
     */
    public int deleteDocDocumentsTypeById(Long id);

    /**
     * 父级分类新增时查询未分配父级分类的 单证信息列表
     *
     * @return
     */
    public List<DocDocumentsType> selectNoParentDocumentType();

    /**
     * 根据父级分类查询所有单证信息
     *
     * @param fileOrderStage 父级id
     * @return
     */
    public List<DocDocumentsType> selectDocumentTypeByStage(Long fileOrderStage);
}
