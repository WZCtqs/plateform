package com.szhbl.project.client.service.impl;

import com.szhbl.project.client.VO.ProblemFileVo;
import com.szhbl.project.client.domain.DocClientLatters;
import com.szhbl.project.client.dto.LatterNoticeDto;
import com.szhbl.project.client.mapper.DocClientLattersMapper;
import com.szhbl.project.client.service.IDocClientLattersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 客户维护长期电放保函Service业务层处理
 *
 * @author szhbl
 * @date 2020-10-01
 */
@Service
public class DocClientLattersServiceImpl implements IDocClientLattersService {
    @Autowired
    private DocClientLattersMapper docClientLattersMapper;

    /**
     * 查询客户维护长期电放保函
     *
     * @param id 客户维护长期电放保函ID
     * @return 客户维护长期电放保函
     */
    @Override
    public DocClientLatters selectDocClientLattersById(Long id) {
        return docClientLattersMapper.selectDocClientLattersById(id);
    }

    /**
     * 查询客户维护长期电放保函列表
     *
     * @param docClientLatters 客户维护长期电放保函
     * @return 客户维护长期电放保函
     */
    @Override
    public List<DocClientLatters> selectDocClientLattersList(DocClientLatters docClientLatters) {
        return docClientLattersMapper.selectDocClientLattersList(docClientLatters);
    }

    /**
     * 新增客户维护长期电放保函
     *
     * @param docClientLatters 客户维护长期电放保函
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDocClientLatters(DocClientLatters docClientLatters) {
        int a = 0;
        for (int i = 0; i < docClientLatters.getUrls().size(); i++) {
            ProblemFileVo problem = docClientLatters.getUrls().get(i);
            docClientLatters.setId(null);
            docClientLatters.setUploadTime(new Date());
            docClientLatters.setCreateTime(new Date());
            docClientLatters.setLatterName(problem.getName());
            docClientLatters.setLatterUrl(problem.getUrl());
            a += docClientLattersMapper.insertDocClientLatters(docClientLatters);
        }
        return a;
    }

    /**
     * 修改客户维护长期电放保函
     *
     * @param docClientLatters 客户维护长期电放保函
     * @return 结果
     */
    @Override
    public int updateDocClientLatters(DocClientLatters docClientLatters) {
        return docClientLattersMapper.updateDocClientLatters(docClientLatters);
    }

    /**
     * 批量删除客户维护长期电放保函
     *
     * @param ids 需要删除的客户维护长期电放保函ID
     * @return 结果
     */
    @Override
    public int deleteDocClientLattersByIds(Long[] ids) {
        return docClientLattersMapper.deleteDocClientLattersByIds(ids);
    }

    /**
     * 删除客户维护长期电放保函信息
     *
     * @param id 客户维护长期电放保函ID
     * @return 结果
     */
    @Override
    public int deleteDocClientLattersById(Long id) {
        return docClientLattersMapper.deleteDocClientLattersById(id);
    }

    @Override
    public List<LatterNoticeDto> selectOrderToEmailNoticeLatters() {
        return docClientLattersMapper.selectOrderToEmailNoticeLatters();
    }
}
