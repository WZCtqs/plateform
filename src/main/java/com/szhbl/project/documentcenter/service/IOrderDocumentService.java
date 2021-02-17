package com.szhbl.project.documentcenter.service;

import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.BeyondDoc;
import com.szhbl.project.documentcenter.domain.vo.DocOrderPickGoodTimeVO;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrl;
import com.szhbl.project.documentcenter.domain.vo.OrderDocuments;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author szhbl
 * @date 2020-01-03
 */
public interface IOrderDocumentService {
    /**
     * 单证页面查询托书列表信息
     *
     * @param orderDocumnets
     * @return
     */
    public List<OrderDocuments> orderDocumentOrderList(OrderDocuments orderDocumnets);

    public List<DocOrderDocument> getByOrderIdFileKey(String orderId, String fileTypeKey);

    /**
     * 查询超节点单证信息
     *
     * @param orderId
     * @return
     */
    public List<BeyondDoc> selectBeyondByOrderId(String orderId);

    public DocOrderPickGoodTimeVO getPickUpGoodsTime(String orderId);

    public DocOrderDocument pickGoodsTime(String orderId);

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DocOrderDocument selectOrderDocumentById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param docOrderDocument 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DocOrderDocument> selectOrderDocumentList(DocOrderDocument docOrderDocument);

    /**
     * 新增【请填写功能名称】
     *
     * @param docOrderDocument 【请填写功能名称】
     * @return 结果
     */
    public int insertOrderDocument(DocOrderDocument docOrderDocument);


    public int insertDocumentMatch(List<DocOrderDocument> documentList);

    /**
     * 修改【请填写功能名称】
     *
     * @param docOrderDocument 【请填写功能名称】
     * @return 结果
     */
    public int updateOrderDocument(DocOrderDocument docOrderDocument);

    public int updateOrderDocumentByOrderId(DocOrderDocument docOrderDocument);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteOrderDocumentByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteOrderDocumentById(Long id);

    /**
     * 查询托书单证是否存在
     * @param document
     * @return
     */
    public int getCountByOrderAndTypeKey(DocOrderDocument document);

    /**
     * 查询托书审核状态是否为 "已审核通过"
     * @param orderId
     * @return
     */
    public String getOrderClientEmail(String orderId);

    /**
     * 获取客户id
     * @param orderId
     * @return
     */
    public String getClientId(String orderId);


    public int deleteByConAndOrderIdAndKey(String containerNo, String orderId, String fileTypeKey);

    public int deleteByFileNameAndOrderIdAndKey(String fileName, String orderId, String fileTypeKey);

    public int deleteByOrderIdAndFileTypeKey(String orderId, String fileTypeKey);

    public int deleteByOrderIdAndUrl(String orderId, String fileTypeKey, String url);

    public OrderDocUrl getOrderIdByNumbers(String orderNumber);

    AjaxResult sendClientEmailNotice(String orderNumer, String orderId);

    public int updateBoxPhotoStatus(DocOrderDocument docOrderDocument);

    List<DocOrderDocument> selectSendGoodsSelf(DocOrderDocument docOrderDocument);

    int deleteByDocument(DocOrderDocument docOrderDocument);
}
