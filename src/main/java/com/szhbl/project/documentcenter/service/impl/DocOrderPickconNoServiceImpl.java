package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.DocOrderPickconNo;
import com.szhbl.project.documentcenter.mapper.DocOrderPickconNoMapper;
import com.szhbl.project.documentcenter.service.IDocOrderPickconNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提箱号表Service业务层处理
 *
 * @author szhbl
 * @date 2020-07-04
 */
@Service
public class DocOrderPickconNoServiceImpl implements IDocOrderPickconNoService {
    @Autowired
    private DocOrderPickconNoMapper docOrderPickconNoMapper;

    /**
     * 查询提箱号表
     *
     * @param id 提箱号表ID
     * @return 提箱号表
     */
    @Override
    public DocOrderPickconNo selectDocOrderPickconNoById(Long id) {
        return docOrderPickconNoMapper.selectDocOrderPickconNoById(id);
    }

    /**
     * 查询提箱号表列表
     *
     * @param docOrderPickconNo 提箱号表
     * @return 提箱号表
     */
    @Override
    public List<DocOrderPickconNo> selectDocOrderPickconNoList(DocOrderPickconNo docOrderPickconNo) {
        return docOrderPickconNoMapper.selectDocOrderPickconNoList(docOrderPickconNo);
    }

    /**
     * 新增提箱号表
     *
     * @param docOrderPickconNo 提箱号表
     * @return 结果
     */
    @Override
    public int insertDocOrderPickconNo(DocOrderPickconNo docOrderPickconNo) {
        return docOrderPickconNoMapper.insertDocOrderPickconNo(docOrderPickconNo);
    }

    /**
     * 修改提箱号表
     *
     * @param docOrderPickconNo 提箱号表
     * @return 结果
     */
    @Override
    public int updateDocOrderPickconNo(DocOrderPickconNo docOrderPickconNo) {
        return docOrderPickconNoMapper.updateDocOrderPickconNo(docOrderPickconNo);
    }

    /**
     * 批量删除提箱号表
     *
     * @param ids 需要删除的提箱号表ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderPickconNoByIds(Long[] ids) {
        return docOrderPickconNoMapper.deleteDocOrderPickconNoByIds(ids);
    }

    /**
     * 删除提箱号表信息
     *
     * @param id 提箱号表ID
     * @return 结果
     */
    @Override
    public int deleteDocOrderPickconNo(String classNum, String containerNum) {
        return docOrderPickconNoMapper.deleteDocOrderPickconNo(classNum, containerNum);
    }
}
