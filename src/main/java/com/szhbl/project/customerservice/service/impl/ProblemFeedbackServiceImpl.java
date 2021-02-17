package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.customerservice.vo.ProblemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.ProblemFeedbackMapper;
import com.szhbl.project.customerservice.domain.ProblemFeedback;
import com.szhbl.project.customerservice.service.IProblemFeedbackService;

/**
 * 问题反馈Service业务层处理
 * 
 * @author b
 * @date 2020-04-07
 */
@Service
public class ProblemFeedbackServiceImpl implements IProblemFeedbackService 
{
    @Autowired
    private ProblemFeedbackMapper problemFeedbackMapper;

    /**
     * 查询问题反馈
     * 
     * @param problemId 问题反馈ID
     * @return 问题反馈
     */
    @Override
    public ProblemFeedback selectProblemFeedbackById(String problemId)
    {
        return problemFeedbackMapper.selectProblemFeedbackById(problemId);
    }

    /**
     * 查询问题反馈列表
     * 
     * @param problemFeedback 问题反馈
     * @return 问题反馈
     */
    @Override
    public List<ProblemFeedback> selectProblemFeedbackList(ProblemFeedback problemFeedback)
    {
        return problemFeedbackMapper.selectProblemFeedbackList(problemFeedback);
    }

    /**
     * 新增问题反馈
     * 
     * @param problemFeedback 问题反馈
     * @return 结果
     */
    @Override
    public int insertProblemFeedback(ProblemFeedback problemFeedback)
    {
        problemFeedback.setCreateTime(DateUtils.getNowDate());
        return problemFeedbackMapper.insertProblemFeedback(problemFeedback);
    }

    /**
     * 修改问题反馈
     * 
     * @param problemFeedback 问题反馈
     * @return 结果
     */
    @Override
    public int updateProblemFeedback(ProblemFeedback problemFeedback)
    {
        problemFeedback.setUpdateTime(DateUtils.getNowDate());
        return problemFeedbackMapper.updateProblemFeedback(problemFeedback);
    }

    /**
     * 批量删除问题反馈
     * 
     * @param problemIds 需要删除的问题反馈ID
     * @return 结果
     */
    @Override
    public int deleteProblemFeedbackByIds(String[] problemIds)
    {
        return problemFeedbackMapper.deleteProblemFeedbackByIds(problemIds);
    }

    /**
     * 删除问题反馈信息
     * 
     * @param problemId 问题反馈ID
     * @return 结果
     */
    @Override
    public int deleteProblemFeedbackById(String problemId)
    {
        return problemFeedbackMapper.deleteProblemFeedbackById(problemId);
    }

    @Override
    public List<ProblemVo> selectProblemList(ProblemFeedback problemFeedback) {
        String deptCode = problemFeedback.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (problemFeedback.getDeptCode().contains("YWB")) {
                if (problemFeedback.getDeptCode().length() > 15) {
                    problemFeedback.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    problemFeedback.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    problemFeedback.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    problemFeedback.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    problemFeedback.setReadType("5");
                } else {
                    problemFeedback.setReadType("1");
                }
            }
        }
        return problemFeedbackMapper.selectProblemList(problemFeedback);
    }
}
