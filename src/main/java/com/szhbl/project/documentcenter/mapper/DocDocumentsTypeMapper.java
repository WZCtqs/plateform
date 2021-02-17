package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.DocDocumentsType;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 单证类型Mapper接口
 *
 * @author szhbl
 * @date 2020-01-13
 */
public interface DocDocumentsTypeMapper
{
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
    public List<DocDocumentsType> selectAllDocDocumentsTypeList(DocDocumentsType docDocumentsType);

    public List<DocDocumentsType> selectParentDocTypesList(DocDocumentsType docDocumentsType);

    /**
     * 新增单证类型
     *
     * @param docDocumentsType 单证类型
     * @return 结果
     */
    public int insertDocDocumentsType(DocDocumentsType docDocumentsType);

    /**
     * 修改单证类型
     *
     * @param docDocumentsType 单证类型
     * @return 结果
     */
    public int updateDocDocumentsType(DocDocumentsType docDocumentsType);

    /**
     * 删除单证类型
     *
     * @param id 单证类型ID
     * @return 结果
     */
    public int deleteDocDocumentsTypeById(Long id);

    /**
     * 批量删除单证类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocDocumentsTypeByIds(Long[] ids);

    /**
     * 父级分类新增时查询未分配父级分类的 单证信息列表
     * @return
     */
    public List<DocDocumentsType> selectNoParentDocumentType();

    /**
     * 根据父级分类查询所有单证信息
     * @param fileOrderStage 父级id
     * @return
     */
    public List<DocDocumentsType> selectDocumentTypeByStage(Long fileOrderStage);

    /**
     * 批量更新二级单证类型的父级id
     * @param fileOrderStage
     * @param docTypeIds
     * @param updateBy
     * @param updateTime
     * @return
     */
    public int updateDocTypeStage(@Param("fileOrderStage") Long fileOrderStage,
                                  @Param("docTypeIds") Long[] docTypeIds,
                                  @Param("updateBy")String updateBy,
                                  @Param("updateTime") Date updateTime);

    /**
     * 根据单证作用域查询所有单证信息
     * @param activeArea 单证作用域
     * @return
     */
    public List<DocDocumentsType> selectByActiveArea(@Param("activeArea")Integer activeArea, @Param("fclLcl")Integer fclLcl);

    /**
     * 清空父级id
     * @param fileOrderStage
     * @return
     */
    public int clearStageByStage(Long fileOrderStage);
}
