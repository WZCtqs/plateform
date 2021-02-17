package com.szhbl.project.backclient.mapper;

import com.szhbl.project.backclient.domain.NewsMessage;
import java.util.List;

/**
 * 新闻信息Mapper接口
 * 
 * @author szhbl
 * @date 2020-01-07
 */
public interface NewsMessageMapper 
{
    /**
     * 查询新闻信息
     * 
     * @param id 新闻信息ID
     * @return 新闻信息
     */
    public NewsMessage selectNewsMessageById(Long id);

    /**
     * 查询新闻信息列表
     * 
     * @param newsMessage 新闻信息
     * @return 新闻信息集合
     */
    public List<NewsMessage> selectNewsMessageList(NewsMessage newsMessage);

    /**
     * 新增新闻信息
     * 
     * @param newsMessage 新闻信息
     * @return 结果
     */
    public int insertNewsMessage(NewsMessage newsMessage);

    /**
     * 修改新闻信息
     * 
     * @param newsMessage 新闻信息
     * @return 结果
     */
    public int updateNewsMessage(NewsMessage newsMessage);

    /**
     * 删除新闻信息
     * 
     * @param id 新闻信息ID
     * @return 结果
     */
    public int deleteNewsMessageById(Long id);

    /**
     * 批量删除新闻信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNewsMessageByIds(Long[] ids);
}
