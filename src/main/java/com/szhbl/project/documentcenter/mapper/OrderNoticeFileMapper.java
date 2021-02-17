package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.OrderNoticeFile;
import com.szhbl.project.documentcenter.domain.vo.BusiShippingorder;

import java.util.List;

/**
 * @author HP
 * @date 2020-01-10
 */
public interface OrderNoticeFileMapper {

    /**
     * 入货通知书列表查询
     * @param orderNoticeFile
     * @return
     */
     public List<OrderNoticeFile> orderNoticeFileList(OrderNoticeFile orderNoticeFile);

    /**
     * 入货通知书列表查询
     * @param orderIds
     * @return
     */
    public List<OrderNoticeFile> selectConsignorEmailByOrderIds(String[] orderIds);
    /**
     * 删除入货通知书
     * @param orderId
     * @return
     */
    public boolean deleteOrderNoticeFile(String orderId);

    /**
     * 根据id查询仓位信息
     * @param orderId
     * @return
     */
    public BusiShippingorder selectBusiShippingorderById(String orderId);

    /**
     * 查询班列编号下所有托书信息
     * @param classBh
     * @return
     */
    public List<BusiShippingorder> selectOrderByClassBh(String classBh);

    /**
     * 查询口岸编号
     *
     * @param orderId
     * @return
     */
    public String selectPortByOrderId(String orderId);

    public List<String> getExamineOrderId();
}
