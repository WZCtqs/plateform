package com.szhbl.project.documentcenter.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.documentcenter.mapper.DocOrderPickconCommandMapper;
import com.szhbl.project.documentcenter.domain.DocOrderPickconCommand;
import com.szhbl.project.documentcenter.service.IDocOrderPickconCommandService;

/**
 * 提箱指令Service业务层处理
 * 
 * @author szhbl
 * @date 2020-08-13
 */
@Service
public class DocOrderPickconCommandServiceImpl implements IDocOrderPickconCommandService 
{
    @Autowired
    private DocOrderPickconCommandMapper docOrderPickconCommandMapper;

    /**
     * 查询提箱指令
     * 
     * @param id 提箱指令ID
     * @return 提箱指令
     */
    @Override
    public DocOrderPickconCommand selectDocOrderPickconCommandById(Long id)
    {
        return docOrderPickconCommandMapper.selectDocOrderPickconCommandById(id);
    }

    /**
     * 查询提箱指令列表
     * 
     * @param docOrderPickconCommand 提箱指令
     * @return 提箱指令
     */
    @Override
    public List<DocOrderPickconCommand> selectDocOrderPickconCommandList(DocOrderPickconCommand docOrderPickconCommand)
    {
        return docOrderPickconCommandMapper.selectDocOrderPickconCommandList(docOrderPickconCommand);
    }

    /**
     * 新增提箱指令
     * 
     * @param docOrderPickconCommand 提箱指令
     * @return 结果
     */
    @Override
    public int insertDocOrderPickconCommand(DocOrderPickconCommand docOrderPickconCommand)
    {
        return docOrderPickconCommandMapper.insertDocOrderPickconCommand(docOrderPickconCommand);
    }

    /**
     * 修改提箱指令
     * 
     * @param docOrderPickconCommand 提箱指令
     * @return 结果
     */
    @Override
    public int updateDocOrderPickconCommand(DocOrderPickconCommand docOrderPickconCommand)
    {
        docOrderPickconCommand.setUpdateTime(DateUtils.getNowDate());
        return docOrderPickconCommandMapper.updateDocOrderPickconCommand(docOrderPickconCommand);
    }

    /**
     * 批量删除提箱指令
     * 
     * @param ids 需要删除的提箱指令ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderPickconCommandByIds(Long[] ids)
    {
        return docOrderPickconCommandMapper.deleteDocOrderPickconCommandByIds(ids);
    }

    /**
     * 删除提箱指令信息
     * 
     * @param id 提箱指令ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderPickconCommandById(Long id)
    {
        return docOrderPickconCommandMapper.deleteDocOrderPickconCommandById(id);
    }
}
