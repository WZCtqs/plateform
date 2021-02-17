package com.szhbl.project.backclient.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.backclient.mapper.QtOfficeMapper;
import com.szhbl.project.backclient.domain.QtOffice;
import com.szhbl.project.backclient.service.IQtOfficeService;

/**
 * 办事处信息Service业务层处理
 * 
 * @author szhbl
 * @date 2020-03-26
 */
@Service
public class QtOfficeServiceImpl implements IQtOfficeService 
{
    @Autowired
    private QtOfficeMapper qtOfficeMapper;

    /**
     * 查询办事处信息
     * 
     * @param  officeId 办事处信息ID
     * @return 办事处信息
     */
    @Override
    public QtOffice selectQtOfficeById(Integer  officeId)
    {
        return qtOfficeMapper.selectQtOfficeById( officeId);
    }

    /**
     * 查询办事处信息列表
     * 
     * @param qtOffice 办事处信息
     * @return 办事处信息
     */
    @Override
    public List<QtOffice> selectQtOfficeList(QtOffice qtOffice)
    {
        return qtOfficeMapper.selectQtOfficeList(qtOffice);
    }

    /**
     * 新增办事处信息
     * 
     * @param qtOffice 办事处信息
     * @return 结果
     */
    @Override
    public int insertQtOffice(QtOffice qtOffice)
    {
        qtOffice.setCreateTime(DateUtils.getNowDate());
        return qtOfficeMapper.insertQtOffice(qtOffice);
    }

    /**
     * 修改办事处信息
     * 
     * @param qtOffice 办事处信息
     * @return 结果
     */
    @Override
    public int updateQtOffice(QtOffice qtOffice)
    {
        qtOffice.setUpdateTime(DateUtils.getNowDate());
        return qtOfficeMapper.updateQtOffice(qtOffice);
    }

    /**
     * 批量删除办事处信息
     * 
     * @param  officeIds 需要删除的办事处信息ID
     * @return 结果
     */
    @Override
    public int deleteQtOfficeByIds(Integer[]  officeIds)
    {
        return qtOfficeMapper.deleteQtOfficeByIds( officeIds);
    }

    /**
     * 删除办事处信息信息
     * 
     * @param  officeId 办事处信息ID
     * @return 结果
     */
    @Override
    public int deleteQtOfficeById(Integer  officeId)
    {
        return qtOfficeMapper.deleteQtOfficeById( officeId);
    }
}
