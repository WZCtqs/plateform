package com.szhbl.project.backclient.service;

import com.szhbl.project.backclient.domain.Question;
import java.util.List;

/**
 * 客户端常见问题Service接口
 * 
 * @author szhbl
 * @date 2020-01-20
 */
public interface IQuestionService 
{
    /**
     * 查询客户端常见问题
     * 
     * @param id 客户端常见问题ID
     * @return 客户端常见问题
     */
    public Question selectQuestionById(Long id);

    /**
     * 查询客户端常见问题列表
     * 
     * @param question 客户端常见问题
     * @return 客户端常见问题集合
     */
    public List<Question> selectQuestionList(Question question);

    /**
     * 新增客户端常见问题
     * 
     * @param question 客户端常见问题
     * @return 结果
     */
    public int insertQuestion(Question question);

    /**
     * 修改客户端常见问题
     * 
     * @param question 客户端常见问题
     * @return 结果
     */
    public int updateQuestion(Question question);

    /**
     * 批量删除客户端常见问题
     * 
     * @param ids 需要删除的客户端常见问题ID
     * @return 结果
     */
    public int deleteQuestionByIds(Long[] ids);

    /**
     * 删除客户端常见问题信息
     * 
     * @param id 客户端常见问题ID
     * @return 结果
     */
    public int deleteQuestionById(Long id);
}
