package com.szhbl.project.backclient.mapper;

import com.szhbl.project.backclient.domain.QtOffice;
import java.util.List;

/**
 * 办事处信息Mapper接口
 * 
 * @author szhbl
 * @date 2020-03-26
 */
public interface QtOfficeMapper 
{
    /**
     * 查询办事处信息
     * 
     * @param  officeId 办事处信息ID
     * @return 办事处信息
     */
    public QtOffice selectQtOfficeById(Integer officeId);

    /**
     * 查询办事处信息列表
     * 
     * @param qtOffice 办事处信息
     * @return 办事处信息集合
     */
    public List<QtOffice> selectQtOfficeList(QtOffice qtOffice);

    /**
     * 新增办事处信息
     * 
     * @param qtOffice 办事处信息
     * @return 结果
     */
    public int insertQtOffice(QtOffice qtOffice);

    /**
     * 修改办事处信息
     * 
     * @param qtOffice 办事处信息
     * @return 结果
     */
    public int updateQtOffice(QtOffice qtOffice);

    /**
     * 删除办事处信息
     * 
     * @param  officeId 办事处信息ID
     * @return 结果
     */
    public int deleteQtOfficeById(Integer officeId);

    /**
     * 批量删除办事处信息
     * 
     * @param  officeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteQtOfficeByIds(Integer[] officeIds);
}
