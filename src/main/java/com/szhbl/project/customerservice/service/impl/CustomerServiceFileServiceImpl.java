package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.CustomerServiceFileMapper;
import com.szhbl.project.customerservice.domain.CustomerServiceFile;
import com.szhbl.project.customerservice.service.ICustomerServiceFileService;

/**
 * 售后文件Service业务层处理
 * 
 * @author b
 * @date 2020-04-10
 */
@Service
public class CustomerServiceFileServiceImpl implements ICustomerServiceFileService 
{
    @Autowired
    private CustomerServiceFileMapper customerServiceFileMapper;

    /**
     * 查询售后文件
     * 
     * @param id 售后文件ID
     * @return 售后文件
     */
    @Override
    public CustomerServiceFile selectCustomerServiceFileById(Long id)
    {
        return customerServiceFileMapper.selectCustomerServiceFileById(id);
    }

    /**
     * 查询售后文件列表
     * 
     * @param customerServiceFile 售后文件
     * @return 售后文件
     */
    @Override
    public List<CustomerServiceFile> selectCustomerServiceFileList(CustomerServiceFile customerServiceFile)
    {
        String deptCode = customerServiceFile.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (customerServiceFile.getDeptCode().contains("YWB")) {
                if (customerServiceFile.getDeptCode().length() > 15) {
                    customerServiceFile.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    customerServiceFile.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    customerServiceFile.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    customerServiceFile.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    customerServiceFile.setReadType("5");
                } else {
                    customerServiceFile.setReadType("1");
                }
            }
        }
        return customerServiceFileMapper.selectCustomerServiceFileList(customerServiceFile);
    }

    /**
     * 新增售后文件
     * 
     * @param customerServiceFile 售后文件
     * @return 结果
     */
    @Override
    public int insertCustomerServiceFile(CustomerServiceFile customerServiceFile)
    {
        customerServiceFile.setCreateTime(DateUtils.getNowDate());
        return customerServiceFileMapper.insertCustomerServiceFile(customerServiceFile);
    }

    /**
     * 修改售后文件
     * 
     * @param customerServiceFile 售后文件
     * @return 结果
     */
    @Override
    public int updateCustomerServiceFile(CustomerServiceFile customerServiceFile)
    {
        customerServiceFile.setUpdateTime(DateUtils.getNowDate());
        return customerServiceFileMapper.updateCustomerServiceFile(customerServiceFile);
    }

    /**
     * 批量删除售后文件
     * 
     * @param ids 需要删除的售后文件ID
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceFileByIds(Long[] ids)
    {
        return customerServiceFileMapper.deleteCustomerServiceFileByIds(ids);
    }

    /**
     * 删除售后文件信息
     * 
     * @param id 售后文件ID
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceFileById(Long id)
    {
        return customerServiceFileMapper.deleteCustomerServiceFileById(id);
    }
}
