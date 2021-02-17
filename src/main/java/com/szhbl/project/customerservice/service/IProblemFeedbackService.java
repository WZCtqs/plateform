package com.szhbl.project.customerservice.service;

import com.szhbl.project.customerservice.domain.ProblemFeedback;
import com.szhbl.project.customerservice.vo.ProblemVo;

import java.util.List;

/**
 * 问题反馈Service接口
 * 
 * @author b
 * @date 2020-04-07
 */
public interface IProblemFeedbackService 
{
    /**
     * 查询问题反馈
     * 
     * @param problemId 问题反馈ID
     * @return 问题反馈
     */
    public ProblemFeedback selectProblemFeedbackById(String problemId);

    /**
     * 查询问题反馈列表
     * 
     * @param problemFeedback 问题反馈
     * @return 问题反馈集合
     */
    public List<ProblemFeedback> selectProblemFeedbackList(ProblemFeedback problemFeedback);

    /**
     * 新增问题反馈
     * 
     * @param problemFeedback 问题反馈
     * @return 结果
     */
    public int insertProblemFeedback(ProblemFeedback problemFeedback);

    /**
     * 修改问题反馈
     * 
     * @param problemFeedback 问题反馈
     * @return 结果
     */
    public int updateProblemFeedback(ProblemFeedback problemFeedback);

    /**
     * 批量删除问题反馈
     * 
     * @param problemIds 需要删除的问题反馈ID
     * @return 结果
     */
    public int deleteProblemFeedbackByIds(String[] problemIds);

    /**
     * 删除问题反馈信息
     * 
     * @param problemId 问题反馈ID
     * @return 结果
     */
    public int deleteProblemFeedbackById(String problemId);

    List<ProblemVo> selectProblemList(ProblemFeedback problemFeedback);
}
