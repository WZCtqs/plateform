package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.CustomerServiceMessageMapper;
import com.szhbl.project.customerservice.domain.CustomerServiceMessage;
import com.szhbl.project.customerservice.service.ICustomerServiceMessageService;

/**
 * 售后Service业务层处理
 * 
 * @author b
 * @date 2020-03-30
 */
@Service
public class CustomerServiceMessageServiceImpl implements ICustomerServiceMessageService 
{
    @Autowired
    private CustomerServiceMessageMapper customerServiceMessageMapper;

    /**
     * 查询售后
     * 
     * @param id 售后ID
     * @return 售后
     */
    @Override
    public CustomerServiceMessage selectCustomerServiceMessageById(Long id)
    {
        return customerServiceMessageMapper.selectCustomerServiceMessageById(id);
    }

    /**
     * 查询售后列表
     * 
     * @param customerServiceMessage 售后
     * @return 售后
     */
    @Override
    public List<CustomerServiceMessage> selectCustomerServiceMessageList(CustomerServiceMessage customerServiceMessage)
    {
        String deptCode = customerServiceMessage.getDeptCode();
        if (StringUtils.isNotEmpty(deptCode)) {
            if (customerServiceMessage.getDeptCode().contains("YWB")) {
                if (customerServiceMessage.getDeptCode().length() > 15) {
                    customerServiceMessage.setReadType("0");
                }else if (deptCode.equals("YWB1000100")) {  //业务部职级人员，根据规则查询
                    customerServiceMessage.setReadType("2");
                } else if (deptCode.equals("YWB1000101")) {  //业务部职级人员，根据规则查询
                    customerServiceMessage.setReadType("3");
                } else if (deptCode.equals("YWB1000200")) {  //业务部职级人员，根据规则查询
                    customerServiceMessage.setReadType("4");
                } else if (deptCode.equals("YWB1000201")) {  //业务部职级人员，根据规则查询
                    customerServiceMessage.setReadType("5");
                } else {
                    customerServiceMessage.setReadType("1");
                }
            }
        }
        return customerServiceMessageMapper.selectCustomerServiceMessageList(customerServiceMessage);
    }

    /**
     * 新增售后
     * 
     * @param customerServiceMessage 售后
     * @return 结果
     */
    @Override
    public int insertCustomerServiceMessage(CustomerServiceMessage customerServiceMessage)
    {
        customerServiceMessage.setCreateTime(DateUtils.getNowDate());
        return customerServiceMessageMapper.insertCustomerServiceMessage(customerServiceMessage);
    }

    /**
     * 修改售后
     * 
     * @param customerServiceMessage 售后
     * @return 结果
     */
    @Override
    public int updateCustomerServiceMessage(CustomerServiceMessage customerServiceMessage)
    {
        customerServiceMessage.setUpdateTime(DateUtils.getNowDate());
        return customerServiceMessageMapper.updateCustomerServiceMessage(customerServiceMessage);
    }

    /**
     * 批量删除售后
     * 
     * @param ids 需要删除的售后ID
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceMessageByIds(Long[] ids)
    {
        return customerServiceMessageMapper.deleteCustomerServiceMessageByIds(ids);
    }

    /**
     * 删除售后信息
     * 
     * @param id 售后ID
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceMessageById(Long id)
    {
        return customerServiceMessageMapper.deleteCustomerServiceMessageById(id);
    }
}
