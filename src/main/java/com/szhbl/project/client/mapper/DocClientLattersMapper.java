package com.szhbl.project.client.mapper;

import com.szhbl.project.client.domain.DocClientLatters;
import com.szhbl.project.client.dto.LatterNoticeDto;

import java.util.List;

/**
 * 客户维护长期电放保函Mapper接口
 *
 * @author szhbl
 * @date 2020-10-01
 */
public interface DocClientLattersMapper {
    /**
     * 查询客户维护长期电放保函
     *
     * @param id 客户维护长期电放保函ID
     * @return 客户维护长期电放保函
     */
    public DocClientLatters selectDocClientLattersById(Long id);

    /**
     * 查询客户维护长期电放保函列表
     *
     * @param docClientLatters 客户维护长期电放保函
     * @return 客户维护长期电放保函集合
     */
    public List<DocClientLatters> selectDocClientLattersList(DocClientLatters docClientLatters);

    /**
     * 新增客户维护长期电放保函
     *
     * @param docClientLatters 客户维护长期电放保函
     * @return 结果
     */
    public int insertDocClientLatters(DocClientLatters docClientLatters);

    /**
     * 修改客户维护长期电放保函
     *
     * @param docClientLatters 客户维护长期电放保函
     * @return 结果
     */
    public int updateDocClientLatters(DocClientLatters docClientLatters);

    /**
     * 删除客户维护长期电放保函
     *
     * @param id 客户维护长期电放保函ID
     * @return 结果
     */
    public int deleteDocClientLattersById(Long id);

    /**
     * 批量删除客户维护长期电放保函
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocClientLattersByIds(Long[] ids);

    List<LatterNoticeDto> selectOrderToEmailNoticeLatters();
}
