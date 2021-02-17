package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.DocOrderDocumentshMapper;
import com.szhbl.project.customerservice.domain.DocOrderDocumentsh;
import com.szhbl.project.customerservice.service.IDocOrderDocumentshService;

/**
 * 单证文件Service业务层处理
 * 
 * @author szhbl
 * @date 2020-03-27
 */
@Service
public class DocOrderDocumentshServiceImpl implements IDocOrderDocumentshService 
{
    @Autowired
    private DocOrderDocumentshMapper docOrderDocumentshMapper;

    /**
     * 查询单证文件
     * 
     * @param id 单证文件ID
     * @return 单证文件
     */
    @Override
    public DocOrderDocumentsh selectDocOrderDocumentshById(Long id)
    {
        return docOrderDocumentshMapper.selectDocOrderDocumentshById(id);
    }

    /**
     * 查询单证文件列表
     * 
     * @param docOrderDocumentsh 单证文件
     * @return 单证文件
     */
    @Override
    public List<DocOrderDocumentsh> selectDocOrderDocumentshList(DocOrderDocumentsh docOrderDocumentsh)
    {
        return docOrderDocumentshMapper.selectDocOrderDocumentshList(docOrderDocumentsh);
    }

    /**
     * 新增单证文件
     * 
     * @param docOrderDocumentsh 单证文件
     * @return 结果
     */
    @Override
    public int insertDocOrderDocumentsh(DocOrderDocumentsh docOrderDocumentsh)
    {
        return docOrderDocumentshMapper.insertDocOrderDocumentsh(docOrderDocumentsh);
    }

    /**
     * 修改单证文件
     * 
     * @param docOrderDocumentsh 单证文件
     * @return 结果
     */
    @Override
    public int updateDocOrderDocumentsh(DocOrderDocumentsh docOrderDocumentsh)
    {
        return docOrderDocumentshMapper.updateDocOrderDocumentsh(docOrderDocumentsh);
    }

    /**
     * 批量删除单证文件
     * 
     * @param ids 需要删除的单证文件ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderDocumentshByIds(Long[] ids)
    {
        return docOrderDocumentshMapper.deleteDocOrderDocumentshByIds(ids);
    }

    /**
     * 删除单证文件信息
     * 
     * @param id 单证文件ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderDocumentshById(Long id)
    {
        return docOrderDocumentshMapper.deleteDocOrderDocumentshById(id);
    }

    @Override
    public List<String> selectUrl(String fileTypeKey, String orderId) {
        return  docOrderDocumentshMapper.selectUrl(fileTypeKey,orderId);
    }
}
