package com.szhbl.project.customerservice.mapper;

import com.szhbl.project.customerservice.domain.ProblemFeedback;
import com.szhbl.project.customerservice.vo.ProblemVo;

import java.util.List;

/**
 * 问题反馈Mapper接口
 * 
 * @author b
 * @date 2020-04-07
 */
public interface ProblemFeedbackMapper 
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
     * 删除问题反馈
     * 
     * @param problemId 问题反馈ID
     * @return 结果
     */
    public int deleteProblemFeedbackById(String problemId);

    /**
     * 批量删除问题反馈
     * 
     * @param problemIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProblemFeedbackByIds(String[] problemIds);

    List<ProblemVo> selectProblemList(ProblemFeedback problemFeedback);
}
