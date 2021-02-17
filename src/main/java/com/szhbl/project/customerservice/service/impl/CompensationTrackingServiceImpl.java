package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.CompensationTrackingMapper;
import com.szhbl.project.customerservice.domain.CompensationTracking;
import com.szhbl.project.customerservice.service.ICompensationTrackingService;

/**
 * 赔款跟踪Service业务层处理
 * 
 * @author bxt
 * @date 2020-03-30
 */
@Service
public class CompensationTrackingServiceImpl implements ICompensationTrackingService 
{
    @Autowired
    private CompensationTrackingMapper compensationTrackingMapper;

    /**
     * 查询赔款跟踪
     * 
     * @param id 赔款跟踪ID
     * @return 赔款跟踪
     */
    @Override
    public CompensationTracking selectCompensationTrackingById(Long id)
    {
        return compensationTrackingMapper.selectCompensationTrackingById(id);
    }

    /**
     * 查询赔款跟踪列表
     * 
     * @param compensationTracking 赔款跟踪
     * @return 赔款跟踪
     */
    @Override
    public List<CompensationTracking> selectCompensationTrackingList(CompensationTracking compensationTracking)
    {
        String deptCode = compensationTracking.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (compensationTracking.getDeptCode().contains("YWB")) {
                if (compensationTracking.getDeptCode().length() > 15) {
                    compensationTracking.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    compensationTracking.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    compensationTracking.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    compensationTracking.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    compensationTracking.setReadType("5");
                } else {
                    compensationTracking.setReadType("1");
                }
            }
        }
        return compensationTrackingMapper.selectCompensationTrackingList(compensationTracking);
    }

    /**
     * 新增赔款跟踪
     * 
     * @param compensationTracking 赔款跟踪
     * @return 结果
     */
    @Override
    public int insertCompensationTracking(CompensationTracking compensationTracking)
    {
        compensationTracking.setCreateTime(DateUtils.getNowDate());
        return compensationTrackingMapper.insertCompensationTracking(compensationTracking);
    }

    /**
     * 修改赔款跟踪
     * 
     * @param compensationTracking 赔款跟踪
     * @return 结果
     */
    @Override
    public int updateCompensationTracking(CompensationTracking compensationTracking)
    {
        compensationTracking.setUpdateTime(DateUtils.getNowDate());
        return compensationTrackingMapper.updateCompensationTracking(compensationTracking);
    }

    /**
     * 批量删除赔款跟踪
     * 
     * @param ids 需要删除的赔款跟踪ID
     * @return 结果
     */
    @Override
    public int deleteCompensationTrackingByIds(Long[] ids)
    {
        return compensationTrackingMapper.deleteCompensationTrackingByIds(ids);
    }

    /**
     * 删除赔款跟踪信息
     * 
     * @param id 赔款跟踪ID
     * @return 结果
     */
    @Override
    public int deleteCompensationTrackingById(Long id)
    {
        return compensationTrackingMapper.deleteCompensationTrackingById(id);
    }
}
