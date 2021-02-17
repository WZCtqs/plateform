package com.szhbl.project.order.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.BusiCargobaseMapper;
import com.szhbl.project.order.domain.BusiCargobase;
import com.szhbl.project.order.service.IBusiCargobaseService;

/**
 * 货物知识库Service业务层处理
 * 
 * @author szhbl
 * @date 2020-08-18
 */
@Service
public class BusiCargobaseServiceImpl implements IBusiCargobaseService 
{
    @Autowired
    private BusiCargobaseMapper busiCargobaseMapper;

    /**
     * 查询货物知识库
     * 
     * @param id 货物知识库ID
     * @return 货物知识库
     */
    @Override
    public BusiCargobase selectBusiCargobaseById(Long id)
    {
        return busiCargobaseMapper.selectBusiCargobaseById(id);
    }

    /**
     * 查询货物知识库列表
     * 
     * @param busiCargobase 货物知识库
     * @return 货物知识库
     */
    @Override
    public List<BusiCargobase> selectBusiCargobaseList(BusiCargobase busiCargobase)
    {
        return busiCargobaseMapper.selectBusiCargobaseList(busiCargobase);
    }

    /**
     * 新增货物知识库
     * 
     * @param busiCargobase 货物知识库
     * @return 结果
     */
    @Override
    public int insertBusiCargobase(BusiCargobase busiCargobase)
    {
        busiCargobase.setCreateTime(DateUtils.getNowDate());
        return busiCargobaseMapper.insertBusiCargobase(busiCargobase);
    }

    /**
     * 修改货物知识库
     * 
     * @param busiCargobase 货物知识库
     * @return 结果
     */
    @Override
    public int updateBusiCargobase(BusiCargobase busiCargobase)
    {
        return busiCargobaseMapper.updateBusiCargobase(busiCargobase);
    }

    /**
     * 批量删除货物知识库
     * 
     * @param ids 需要删除的货物知识库ID
     * @return 结果
     */
    @Override
    public int deleteBusiCargobaseByIds(Long[] ids)
    {
        return busiCargobaseMapper.deleteBusiCargobaseByIds(ids);
    }

    /**
     * 删除货物知识库信息
     * 
     * @param id 货物知识库ID
     * @return 结果
     */
    @Override
    public int deleteBusiCargobaseById(Long id)
    {
        return busiCargobaseMapper.deleteBusiCargobaseById(id);
    }
}
