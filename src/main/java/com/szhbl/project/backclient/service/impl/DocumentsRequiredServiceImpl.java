package com.szhbl.project.backclient.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.backclient.mapper.DocumentsRequiredMapper;
import com.szhbl.project.backclient.domain.DocumentsRequired;
import com.szhbl.project.backclient.service.IDocumentsRequiredService;

/**
 * 单证要求Service业务层处理
 * 
 * @author bxt
 * @date 2020-01-07
 */
@Service
public class DocumentsRequiredServiceImpl implements IDocumentsRequiredService 
{
    @Autowired
    private DocumentsRequiredMapper documentsRequiredMapper;

    /**
     * 查询单证要求
     * 
     * @param id 单证要求ID
     * @return 单证要求
     */
    @Override
    public DocumentsRequired selectDocumentsRequiredById(Long id)
    {
        return documentsRequiredMapper.selectDocumentsRequiredById(id);
    }

    /**
     * 查询单证要求列表
     * 
     * @param documentsRequired 单证要求
     * @return 单证要求
     */
    @Override
    public List<DocumentsRequired> selectDocumentsRequiredList(DocumentsRequired documentsRequired)
    {
        return documentsRequiredMapper.selectDocumentsRequiredList(documentsRequired);
    }

    /**
     * 新增单证要求
     * 
     * @param documentsRequired 单证要求
     * @return 结果
     */
    @Override
    public int insertDocumentsRequired(DocumentsRequired documentsRequired)
    {
        documentsRequired.setCreateTime(DateUtils.getNowDate());
        return documentsRequiredMapper.insertDocumentsRequired(documentsRequired);
    }

    /**
     * 修改单证要求
     * 
     * @param documentsRequired 单证要求
     * @return 结果
     */
    @Override
    public int updateDocumentsRequired(DocumentsRequired documentsRequired)
    {
        documentsRequired.setUpdateTime(DateUtils.getNowDate());
        return documentsRequiredMapper.updateDocumentsRequired(documentsRequired);
    }

    /**
     * 批量删除单证要求
     * 
     * @param ids 需要删除的单证要求ID
     * @return 结果
     */
    @Override
    public int deleteDocumentsRequiredByIds(Long[] ids)
    {
        return documentsRequiredMapper.deleteDocumentsRequiredByIds(ids);
    }

    /**
     * 删除单证要求信息
     * 
     * @param id 单证要求ID
     * @return 结果
     */
    @Override
    public int deleteDocumentsRequiredById(Long id)
    {
        return documentsRequiredMapper.deleteDocumentsRequiredById(id);
    }
}
