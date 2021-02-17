package com.szhbl.project.trains.service.impl;

import java.util.List;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.trains.mapper.BusiClassesNumberMapper;
import com.szhbl.project.trains.domain.BusiClassesNumber;
import com.szhbl.project.trains.service.IBusiClassesNumberService;

/**
 * 班列号Service业务层处理
 * 
 * @author dps
 * @date 2020-03-13
 */
@Service
public class BusiClassesNumberServiceImpl implements IBusiClassesNumberService 
{
    @Autowired
    private BusiClassesNumberMapper busiClassesNumberMapper;

    /**
     * 查询班列号
     * 
     * @param classTId 班列号ID
     * @return 班列号
     */
    @Override
    public BusiClassesNumber selectBusiClassesNumberById(String classTId)
    {
        return busiClassesNumberMapper.selectBusiClassesNumberById(classTId);
    }

    /**
     * 查询班列号列表
     * 
     * @param busiClassesNumber 班列号
     * @return 班列号
     */
    @Override
    public List<BusiClassesNumber> selectBusiClassesNumberList(BusiClassesNumber busiClassesNumber)
    {
        return busiClassesNumberMapper.selectBusiClassesNumberList(busiClassesNumber);
    }

    /**
     * 新增班列号
     * 
     * @param busiClassesNumber 班列号
     * @return 结果
     */
    @Override
    public int insertBusiClassesNumber(BusiClassesNumber busiClassesNumber)
    {
        busiClassesNumber.setClassTId(IdUtils.randomUUID());
        busiClassesNumber.setUpdatetime(DateUtils.getNowDate());
        return busiClassesNumberMapper.insertBusiClassesNumber(busiClassesNumber);
    }

    /**
     * 修改班列号
     * 
     * @param busiClassesNumber 班列号
     * @return 结果
     */
    @Override
    public int updateBusiClassesNumber(BusiClassesNumber busiClassesNumber)
    {
        busiClassesNumber.setUpdatetime(DateUtils.getNowDate());
        return busiClassesNumberMapper.updateBusiClassesNumber(busiClassesNumber);
    }

    /**
     * 批量删除班列号
     * 
     * @param classTIds 需要删除的班列号ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesNumberByIds(String[] classTIds)
    {
        return busiClassesNumberMapper.deleteBusiClassesNumberByIds(classTIds);
    }

    /**
     * 删除班列号信息
     * 
     * @param classTId 班列号ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesNumberById(String classTId)
    {
        return busiClassesNumberMapper.deleteBusiClassesNumberById(classTId);
    }

    /**
     * 校验班列号是否唯一
     *
     * @param classTNumber 班列号
     * @return 结果
     */
    public String checkNumberUnique(String classTNumber){
        int count = busiClassesNumberMapper.checkNumberUnique(classTNumber);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验班列号是否唯一(更新)
     *
     * @param busiClassesNumber 班列号
     * @return 结果
     */
    public String checkNumberUpdUnique(BusiClassesNumber busiClassesNumber){
        String Id = busiClassesNumber.getClassTId();
        BusiClassesNumber info = busiClassesNumberMapper.checkNumberUpdUnique(busiClassesNumber.getClassTNumber());
        if (StringUtils.isNotNull(info) && !StringUtils.equals(info.getClassTId(),Id))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
