package com.szhbl.project.customerservice.mapper;

import com.szhbl.project.customerservice.domain.CustomerServiceFile;
import java.util.List;

/**
 * 售后文件Mapper接口
 * 
 * @author b
 * @date 2020-04-10
 */
public interface CustomerServiceFileMapper 
{
    /**
     * 查询售后文件
     * 
     * @param id 售后文件ID
     * @return 售后文件
     */
    public CustomerServiceFile selectCustomerServiceFileById(Long id);

    /**
     * 查询售后文件列表
     * 
     * @param customerServiceFile 售后文件
     * @return 售后文件集合
     */
    public List<CustomerServiceFile> selectCustomerServiceFileList(CustomerServiceFile customerServiceFile);

    /**
     * 新增售后文件
     * 
     * @param customerServiceFile 售后文件
     * @return 结果
     */
    public int insertCustomerServiceFile(CustomerServiceFile customerServiceFile);

    /**
     * 修改售后文件
     * 
     * @param customerServiceFile 售后文件
     * @return 结果
     */
    public int updateCustomerServiceFile(CustomerServiceFile customerServiceFile);

    /**
     * 删除售后文件
     * 
     * @param id 售后文件ID
     * @return 结果
     */
    public int deleteCustomerServiceFileById(Long id);

    /**
     * 批量删除售后文件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomerServiceFileByIds(Long[] ids);
}
