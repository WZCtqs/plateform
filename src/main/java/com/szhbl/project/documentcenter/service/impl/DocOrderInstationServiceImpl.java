package com.szhbl.project.documentcenter.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.szhbl.project.documentcenter.mapper.DocOrderInstationMapper;
import com.szhbl.project.documentcenter.domain.DocOrderInstation;
import com.szhbl.project.documentcenter.service.IDocOrderInstationService;

import javax.annotation.Resource;

/**
 * 回程进站统计表Service业务层处理
 *
 * @author szhbl
 * @date 2020-05-25
 */
@Service
public class DocOrderInstationServiceImpl implements IDocOrderInstationService
{
    @Resource
    private DocOrderInstationMapper docOrderInstationMapper;

    /**
     * 查询回程进站统计表
     *
     * @param id 回程进站统计表ID
     * @return 回程进站统计表
     */
    @Override
    public DocOrderInstation selectDocOrderInstationByOrderId(String orderId)
    {
        return docOrderInstationMapper.selectDocOrderInstationByOrderId(orderId);
    }

    /**
     * 查询回程进站统计表列表
     *
     * @param docOrderInstation 回程进站统计表
     * @return 回程进站统计表
     */
    @Override
    public List<DocOrderInstation> selectDocOrderInstationList(DocOrderInstation docOrderInstation)
    {
        return docOrderInstationMapper.selectDocOrderInstationList(docOrderInstation);
    }

    /**
     * 新增回程进站统计表
     *
     * @param docOrderInstation 回程进站统计表
     * @return 结果
     */
    @Override
    public int insertDocOrderInstation(DocOrderInstation docOrderInstation)
    {
        return docOrderInstationMapper.insertDocOrderInstation(docOrderInstation);
    }

    /**
     * 修改回程进站统计表
     *
     * @param docOrderInstation 回程进站统计表
     * @return 结果
     */
    @Override
    public int updateDocOrderInstation(DocOrderInstation docOrderInstation)
    {
        return docOrderInstationMapper.updateDocOrderInstation(docOrderInstation);
    }

    /**
     * 批量删除回程进站统计表
     *
     * @param ids 需要删除的回程进站统计表ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderInstationByIds(Long[] ids)
    {
        return docOrderInstationMapper.deleteDocOrderInstationByIds(ids);
    }

    /**
     * 删除回程进站统计表信息
     *
     * @param orderId 回程进站统计表ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderInstationByOrderId(String orderId)
    {
        return docOrderInstationMapper.deleteDocOrderInstationByOrderId(orderId);
    }
}
