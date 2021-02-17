package com.szhbl.project.backclient.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.backclient.mapper.QuestionMapper;
import com.szhbl.project.backclient.domain.Question;
import com.szhbl.project.backclient.service.IQuestionService;

/**
 * 客户端常见问题Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-20
 */
@Service
public class QuestionServiceImpl implements IQuestionService 
{
    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 查询客户端常见问题
     * 
     * @param id 客户端常见问题ID
     * @return 客户端常见问题
     */
    @Override
    public Question selectQuestionById(Long id)
    {
        return questionMapper.selectQuestionById(id);
    }

    /**
     * 查询客户端常见问题列表
     * 
     * @param question 客户端常见问题
     * @return 客户端常见问题
     */
    @Override
    public List<Question> selectQuestionList(Question question)
    {
        return questionMapper.selectQuestionList(question);
    }

    /**
     * 新增客户端常见问题
     * 
     * @param question 客户端常见问题
     * @return 结果
     */
    @Override
    public int insertQuestion(Question question)
    {
        question.setCreateTime(DateUtils.getNowDate());
        return questionMapper.insertQuestion(question);
    }

    /**
     * 修改客户端常见问题
     * 
     * @param question 客户端常见问题
     * @return 结果
     */
    @Override
    public int updateQuestion(Question question)
    {
        question.setUpdateTime(DateUtils.getNowDate());
        return questionMapper.updateQuestion(question);
    }

    /**
     * 批量删除客户端常见问题
     * 
     * @param ids 需要删除的客户端常见问题ID
     * @return 结果
     */
    @Override
    public int deleteQuestionByIds(Long[] ids)
    {
        return questionMapper.deleteQuestionByIds(ids);
    }

    /**
     * 删除客户端常见问题信息
     * 
     * @param id 客户端常见问题ID
     * @return 结果
     */
    @Override
    public int deleteQuestionById(Long id)
    {
        return questionMapper.deleteQuestionById(id);
    }
}
