package com.szhbl.project.documentcenter.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.documentcenter.domain.OrderNoticeFile;
import com.szhbl.project.documentcenter.domain.vo.BusiShippingorder;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author szhbl
 * @date 2020-01-03
 */
public interface IOrderNoticeFileService
{
    /**
     * 入货通知书列表查询
     * @param orderNoticeFile
     * @return
     */
    public List<OrderNoticeFile> orderNoticeFileList(OrderNoticeFile orderNoticeFile);

    /**
     * 发送邮件(不带附件)
     * @param orderIds
     * @return
     */
    public AjaxResult simpleEmail(String[] orderIds);
    /**
     * 发送邮件(带附件)
     * @param orderIds
     * @return
     */
    public AjaxResult fileEmail(String[] orderIds);

    /**
     * 删除入货通知书
     * @param orderId
     * @return
     */
    public boolean deleteOrderNoticeFile(String orderId);

    /**
     * 生成pdf
     * @param orderId
     * @return
     */
    public boolean createOrderNoticePDF(String orderId);

    /**
     * 查询口岸编号
     * @param orderId
     * @return
     */
    public String selectPortByOrderId(String orderId);


    /**
     * 查询班列编号下所有托书信息
     *
     * @param classBh
     * @return
     */
    public List<BusiShippingorder> selectOrderByClassBh(String classBh);

    public List<String> getExamineOrderId();
}
