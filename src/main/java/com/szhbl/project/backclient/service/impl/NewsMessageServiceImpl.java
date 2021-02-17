package com.szhbl.project.backclient.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.backclient.mapper.NewsMessageMapper;
import com.szhbl.project.backclient.domain.NewsMessage;
import com.szhbl.project.backclient.service.INewsMessageService;

/**
 * 新闻信息Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@Service
public class NewsMessageServiceImpl implements INewsMessageService 
{
    @Autowired
    private NewsMessageMapper newsMessageMapper;

    /**
     * 查询新闻信息
     * 
     * @param id 新闻信息ID
     * @return 新闻信息
     */
    @Override
    public NewsMessage selectNewsMessageById(Long id)
    {
        return newsMessageMapper.selectNewsMessageById(id);
    }

    /**
     * 查询新闻信息列表
     * 
     * @param newsMessage 新闻信息
     * @return 新闻信息
     */
    @Override
    public List<NewsMessage> selectNewsMessageList(NewsMessage newsMessage)
    {
        return newsMessageMapper.selectNewsMessageList(newsMessage);
    }

    /**
     * 新增新闻信息
     * 
     * @param newsMessage 新闻信息
     * @return 结果
     */
    @Override
    public int insertNewsMessage(NewsMessage newsMessage)
    {
        newsMessage.setCreateTime(DateUtils.getNowDate());
        return newsMessageMapper.insertNewsMessage(newsMessage);
    }

    /**
     * 修改新闻信息
     * 
     * @param newsMessage 新闻信息
     * @return 结果
     */
    @Override
    public int updateNewsMessage(NewsMessage newsMessage)
    {
        newsMessage.setUpdateTime(DateUtils.getNowDate());
        return newsMessageMapper.updateNewsMessage(newsMessage);
    }

    /**
     * 批量删除新闻信息
     * 
     * @param ids 需要删除的新闻信息ID
     * @return 结果
     */
    @Override
    public int deleteNewsMessageByIds(Long[] ids)
    {
        return newsMessageMapper.deleteNewsMessageByIds(ids);
    }

    /**
     * 删除新闻信息信息
     * 
     * @param id 新闻信息ID
     * @return 结果
     */
    @Override
    public int deleteNewsMessageById(Long id)
    {
        return newsMessageMapper.deleteNewsMessageById(id);
    }
}
