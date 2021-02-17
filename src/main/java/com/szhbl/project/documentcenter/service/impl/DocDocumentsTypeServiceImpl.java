package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.documentcenter.domain.DocDocumentsType;
import com.szhbl.project.documentcenter.mapper.DocDocumentsTypeMapper;
import com.szhbl.project.documentcenter.service.IDocDocumentsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 单证类型Service业务层处理
 *
 * @author szhbl
 * @date 2020-01-13
 */
@Service
public class DocDocumentsTypeServiceImpl implements IDocDocumentsTypeService {
    @Resource
    private DocDocumentsTypeMapper docDocumentsTypeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询单证类型
     *
     * @param id 单证类型ID
     * @return 单证类型
     */
    @Override
    public DocDocumentsType selectDocDocumentsTypeById(Long id) {
        DocDocumentsType type = docDocumentsTypeMapper.selectDocDocumentsTypeById(id);
        List<DocDocumentsType> list = docDocumentsTypeMapper.selectDocumentTypeByStage(type.getId());
        type.setDocTypesList(list);
        return type;
    }

    @Override
    public DocDocumentsType getTypeByTypeKey(String fileTypeKey) {
        return docDocumentsTypeMapper.getTypeByTypeKey(fileTypeKey);
    }

    /**
     * 查询单证类型列表
     *
     * @param docDocumentsType 单证类型
     * @return 单证类型
     */
    @Override
    public List<DocDocumentsType> selectDocDocumentsTypeList(DocDocumentsType docDocumentsType) {
        // 查询出一级菜单和没有父级的二级菜单
        return docDocumentsTypeMapper.selectParentDocTypesList(docDocumentsType);
    }

    @Override
    public List<DocDocumentsType> selectParentList(DocDocumentsType docDocumentsType) {
        return docDocumentsTypeMapper.selectAllDocDocumentsTypeList(docDocumentsType);
    }

    /**
     * 新增单证一级类型
     *
     * @param docDocumentsType 单证类型
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDocDocumentsType(DocDocumentsType docDocumentsType) {
        // 新增一级单证分类
        docDocumentsType.setCreateTime(DateUtils.getNowDate());
        docDocumentsType.setUpdateTime(DateUtils.getNowDate());
        docDocumentsType.setFileNotice(null);
        docDocumentsType.setFileNoticeTime(null);
        docDocumentsType.setIsSystem(0);
        docDocumentsTypeMapper.insertDocDocumentsType(docDocumentsType);
        // 更新二级单证分类的父级分类
        return docDocumentsTypeMapper.updateDocTypeStage(docDocumentsType.getId(), docDocumentsType.getDocTypeIds(), docDocumentsType.getUpdateBy(), docDocumentsType.getCreateTime());
    }

    /**
     * @param docDocumentsType
     * @return
     */
    @Override
    @Transactional
    public int updateDocType(DocDocumentsType docDocumentsType) {
        docDocumentsType.setUpdateTime(DateUtils.getNowDate());
        //更新一级分类
        docDocumentsTypeMapper.updateDocDocumentsType(docDocumentsType);
        //清空一级分类下所有二级分类的stage
        docDocumentsTypeMapper.clearStageByStage(docDocumentsType.getId());
        // 更新二级单证分类的父级分类
        return docDocumentsTypeMapper.updateDocTypeStage(docDocumentsType.getId(), docDocumentsType.getDocTypeIds(), docDocumentsType.getUpdateBy(), docDocumentsType.getUpdateTime());
    }

    /**
     * 修改单证类型
     *
     * @param docDocumentsType 单证类型
     * @return 结果
     */
    @Override
    public int updateDocDocumentsType(DocDocumentsType docDocumentsType) {
//        redisTemplate.delete("fileType::" + docDocumentsType.getFileTypeKey());
        docDocumentsType.setUpdateTime(DateUtils.getNowDate());
        return docDocumentsTypeMapper.updateDocDocumentsType(docDocumentsType);
    }

    /**
     * 批量删除单证类型
     *
     * @param ids 需要删除的单证类型ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDocDocumentsTypeByIds(Long[] ids) {
        // 解除一级单证分类下的二级单证绑定
        for (int i = 0; i < ids.length; i++) {
            docDocumentsTypeMapper.clearStageByStage(ids[i]);
        }
        // 删除一级单证分类
        return docDocumentsTypeMapper.deleteDocDocumentsTypeByIds(ids);
    }

    /**
     * 删除单证类型信息
     *
     * @param id 单证类型ID
     * @return 结果
     */
    @Override
    public int deleteDocDocumentsTypeById(Long id) {
        return docDocumentsTypeMapper.deleteDocDocumentsTypeById(id);
    }

    /**
     * 父级分类新增时查询未分配父级分类的 单证信息列表
     *
     * @return
     */
    @Override
    public List<DocDocumentsType> selectNoParentDocumentType() {
        return docDocumentsTypeMapper.selectNoParentDocumentType();
    }

    /**
     * 根据父级分类查询所有单证信息
     *
     * @param fileOrderStage 父级id
     * @return
     */
    @Override
    public List<DocDocumentsType> selectDocumentTypeByStage(Long fileOrderStage) {
        return docDocumentsTypeMapper.selectDocumentTypeByStage(fileOrderStage);
    }
}
