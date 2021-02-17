package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.DocOrderPickconCommand;
import java.util.List;

/**
 * 提箱指令Mapper接口
 * 
 * @author szhbl
 * @date 2020-08-13
 */
public interface DocOrderPickconCommandMapper 
{
    /**
     * 查询提箱指令
     * 
     * @param id 提箱指令ID
     * @return 提箱指令
     */
    public DocOrderPickconCommand selectDocOrderPickconCommandById(Long id);

    /**
     * 查询提箱指令列表
     * 
     * @param docOrderPickconCommand 提箱指令
     * @return 提箱指令集合
     */
    public List<DocOrderPickconCommand> selectDocOrderPickconCommandList(DocOrderPickconCommand docOrderPickconCommand);

    /**
     * 新增提箱指令
     * 
     * @param docOrderPickconCommand 提箱指令
     * @return 结果
     */
    public int insertDocOrderPickconCommand(DocOrderPickconCommand docOrderPickconCommand);

    /**
     * 修改提箱指令
     * 
     * @param docOrderPickconCommand 提箱指令
     * @return 结果
     */
    public int updateDocOrderPickconCommand(DocOrderPickconCommand docOrderPickconCommand);

    /**
     * 删除提箱指令
     * 
     * @param id 提箱指令ID
     * @return 结果
     */
    public int deleteDocOrderPickconCommandById(Long id);

    /**
     * 批量删除提箱指令
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocOrderPickconCommandByIds(Long[] ids);
}
