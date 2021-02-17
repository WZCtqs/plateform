package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocNewRemind;
import com.szhbl.project.documentcenter.domain.vo.DocRemindPage;

import java.util.List;

/**
 * 订单单证提醒设置
Service接口
 *
 * @author szhbl
 * @date 2020-03-30
 */
public interface IDocNewRemindService
{
    /**
     * 查询订单单证提醒设置列表
     * @param docRemindPage
     * @return
     */
    public List<DocRemindPage> selectForRemindPage(DocRemindPage docRemindPage);

    /**
     * 查询订单单证提醒设置列表
     *
     * @param docNewRemind 订单单证提醒设置
     * @return 订单单证提醒设置集合
     */
    public List<DocNewRemind> selectDocNewRemindList(DocNewRemind docNewRemind);

    /**
     * 新增订单单证提醒设置
     *
     * @param docNewRemind 订单单证提醒设置
     * @return 结果
     */
    public int insertDocNewRemind(DocNewRemind docNewRemind) throws Exception;

    /**
     * 根据托书和单证类型查询最新设置
     * @param docNewRemind
     * @return
     */
    public DocNewRemind getDocNewRemind(DocNewRemind docNewRemind);

    /**
     * 逻辑删除新提醒设置
     * @param docNewRemind
     * @return
     */
    public int deleteDocNewRemindByOrder(DocNewRemind docNewRemind);
}
