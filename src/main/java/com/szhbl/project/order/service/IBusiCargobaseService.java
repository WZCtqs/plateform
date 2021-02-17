package com.szhbl.project.order.service;

import com.szhbl.project.order.domain.BusiCargobase;
import java.util.List;

/**
 * 货物知识库Service接口
 * 
 * @author szhbl
 * @date 2020-08-18
 */
public interface IBusiCargobaseService 
{
    /**
     * 查询货物知识库
     * 
     * @param id 货物知识库ID
     * @return 货物知识库
     */
    public BusiCargobase selectBusiCargobaseById(Long id);

    /**
     * 查询货物知识库列表
     * 
     * @param busiCargobase 货物知识库
     * @return 货物知识库集合
     */
    public List<BusiCargobase> selectBusiCargobaseList(BusiCargobase busiCargobase);

    /**
     * 新增货物知识库
     * 
     * @param busiCargobase 货物知识库
     * @return 结果
     */
    public int insertBusiCargobase(BusiCargobase busiCargobase);

    /**
     * 修改货物知识库
     * 
     * @param busiCargobase 货物知识库
     * @return 结果
     */
    public int updateBusiCargobase(BusiCargobase busiCargobase);

    /**
     * 批量删除货物知识库
     * 
     * @param ids 需要删除的货物知识库ID
     * @return 结果
     */
    public int deleteBusiCargobaseByIds(Long[] ids);

    /**
     * 删除货物知识库信息
     * 
     * @param id 货物知识库ID
     * @return 结果
     */
    public int deleteBusiCargobaseById(Long id);
}
