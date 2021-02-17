package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.BeyondDoc;
import com.szhbl.project.documentcenter.domain.vo.OrderDataForDoc;
import com.szhbl.project.documentcenter.domain.vo.OrderDocUrl;
import com.szhbl.project.documentcenter.domain.vo.OrderDocuments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author szhbl
 * @date 2020-01-03
 */
public interface OrderDocumentMapper {

    /**
     * 单证页面查询托书列表信息
     *
     * @param orderDocuments
     * @return
     */
    public List<OrderDocuments> orderDocumentOrderList(OrderDocuments orderDocuments);

    public DocOrderDocument getByOrderIdAndFileTypeKey(@Param("orderId") String orderId, @Param("fileTypeKey") String fileTypeKey);

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

    public int insertDocumentMatch(@Param("documentList") List<DocOrderDocument> documentList);

    /**
     * 修改【请填写功能名称】
     *
     * @param docOrderDocument 【请填写功能名称】
     * @return 结果
     */
    public int updateOrderDocument(DocOrderDocument docOrderDocument);

    public int updateOrderDocumentByOrderId(DocOrderDocument docOrderDocument);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteOrderDocumentById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderDocumentByIds(Long[] ids);

    int deleteByOrderIds(String[] orderIdArray);


    /**
     * 查询订单单证是否已上传
     *
     * @param document
     * @return
     */
    public int getCountByOrderAndTypeKey(DocOrderDocument document);

    /**
     * 查询托书审核状态是否为 "已审核通过"
     *
     * @param orderId
     * @return
     */
    public String getOrderClientEmail(String orderId);

    public String getClientId(String orderId);

    public int deleteByConAndOrderIdAndKey(@Param("containerNo") String containerNo,
                                           @Param("orderId") String orderId,
                                           @Param("fileTypeKey") String fileTypeKey);

    public int deleteByFileNameAndOrderIdAndKey(@Param("fileName") String fileName,
                                                @Param("orderId") String orderId,
                                                @Param("fileTypeKey") String fileTypeKey);

    public int deleteByOrderIdAndFileTypeKey(@Param("orderId") String orderId,
                                             @Param("fileTypeKey") String fileTypeKey);

    public int deleteByOrderIdAndUrl(@Param("orderId") String orderId,
                                     @Param("url") String url);

    /**
     * 根据orderNumber获取orderId
     *
     * @param orderNumber
     * @return
     */
    public OrderDocUrl getOrderIdByNumbers(String orderNumber);

    public List<BeyondDoc> selectBeyondByOrderId(String orderId);

    OrderDataForDoc selectOrderData(String orderId);

    String getClientEmailByOrderId(String orderId);

    List<DocOrderDocument> selectOrderDocumentListForDesc(DocOrderDocument docOrderDocument);

    int updateBoxPhotoStatus(DocOrderDocument docOrderDocument);

    List<DocOrderDocument> selectSendGoodsSelf(DocOrderDocument docOrderDocument);

    int deleteByDocument(DocOrderDocument docOrderDocument);

    List<String> getOrderByContainerNo(String containerNo);
}
