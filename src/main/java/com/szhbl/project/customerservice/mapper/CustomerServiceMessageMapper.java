package com.szhbl.project.customerservice.mapper;

import com.szhbl.project.customerservice.domain.CustomerServiceMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 售后Mapper接口
 * 
 * @author b
 * @date 2020-03-30
 */
@Repository
public interface CustomerServiceMessageMapper 
{
    /**
     * 查询售后
     * 
     * @param id 售后ID
     * @return 售后
     */
    public CustomerServiceMessage selectCustomerServiceMessageById(Long id);

    /**
     * 查询售后列表
     * 
     * @param customerServiceMessage 售后
     * @return 售后集合
     */
    public List<CustomerServiceMessage> selectCustomerServiceMessageList(CustomerServiceMessage customerServiceMessage);

    /**
     * 新增售后
     * 
     * @param customerServiceMessage 售后
     * @return 结果
     */
    public int insertCustomerServiceMessage(CustomerServiceMessage customerServiceMessage);

    /**
     * 修改售后
     * 
     * @param customerServiceMessage 售后
     * @return 结果
     */
    public int updateCustomerServiceMessage(CustomerServiceMessage customerServiceMessage);

    /**
     * 删除售后
     * 
     * @param id 售后ID
     * @return 结果
     */
    public int deleteCustomerServiceMessageById(Long id);

    /**
     * 批量删除售后
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomerServiceMessageByIds(Long[] ids);

    List<CustomerServiceMessage> selectOrderNum(String orderNumber);
}
