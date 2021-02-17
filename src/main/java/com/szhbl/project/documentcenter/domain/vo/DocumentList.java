package com.szhbl.project.documentcenter.domain.vo;

import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import lombok.Data;

import java.util.List;

@Data
public class DocumentList extends OrderDocuments {

    /**
     * 配舱通知书
     */
    private List<DocOrderDocument> orderNoticeFileList;
    /**
     * 提货时间
     */
    private List<DocOrderDocument> pickGoodsFileList;
    /**
     * 装箱明细（客户自装箱）
     */
    private List<DocOrderDocument> boxingListFilecList;
    /**
     * 报关材料
     */
    private List<DocOrderDocument> customsApplyFilecList;
    /**
     * 随车文件
     */
    private List<DocOrderDocument> followVehicleFilecList;
    /**
     * 放箱单
     */
    private List<DocOrderDocument> pcikContainerFileList;
    /**
     * 拼箱到货信息
     */
    private List<DocOrderDocument> pxArrivalGoodsGileList;
    /**
     * 操作一到货信息
     */
    private List<DocOrderDocument> czArrivalGoodsFileList;
    /**
     * 到货信息表
     */
    private List<DocOrderDocument> arrivalGoodsListFileList;
    /**
     * 装箱方案
     */
    private List<DocOrderDocument> boxingSchemeFileList;
    /**
     * 装箱要求
     */
    private List<DocOrderDocument> boxingRequireFileList;
    /**
     * 装柜清单（件重尺）
     */
    private List<DocOrderDocument> boxingListSizeFileList;
    /**
     * 拆箱代理表
     */
    private List<DocOrderDocument> devanningAgentFileList;
    /**
     * 装柜清单
     */
    private List<DocOrderDocument> boxingListFileList;
    /**
     * 堆场重箱进站表
     */
    private List<DocOrderDocument> yardLoadedInFileList;
    /**
     * 进站集装箱拍照
     */
    private List<DocOrderDocument> arrivalStationPhotoFileList;
    /**
     * 回程到站时间统计
     */
    private List<DocOrderDocument> arrivalStatonTimeTotalFileList;
    /**
     * 报关单
     */
    private List<DocOrderDocument> declareCustomsFileList;
    /**
     * 欧盟报关单
     */
    private List<DocOrderDocument> declareCustomsEuFileList;
    /**
     * 放行单
     */
    private List<DocOrderDocument> clearancePaperFileList;
    /**
     * 正式随车文件
     */
    private List<DocOrderDocument> followVehicleFormalFileList;
    /**
     * SMGS运单
     */
    private List<DocOrderDocument> smgsWaybillFileList;
    /**
     * 口岸转关信息表
     */
    private List<DocOrderDocument> portTransFileList;
    /**
     * 提单信息表
     */
    private List<DocOrderDocument> ladingBillMsgFileList;
    /**
     * 提单草单
     */
    private List<DocOrderDocument> ladingBillDraftFileList;
    /**
     * 电放保函
     */
    private List<DocOrderDocument> letterGuaranteeFilecList;
    /**
     * 提单
     */
    private List<DocOrderDocument> ladingBillFormalFileList;
    /**
     * 海关信
     */
    private List<DocOrderDocument> customsLetterFileList;
    /**
     * CIM运单
     */
    private List<DocOrderDocument> cimWaybillFileList;
    /**
     * ATB
     */
    private List<DocOrderDocument> atbFileList;
    /**
     * 拼箱出入库表
     */
    private List<DocOrderDocument> pxGoodsInoutFileList;
    /**
     * 提箱情况表
     */
    private List<DocOrderDocument> pickConMsgFileList;
    /**
     * 提箱号表
     */
    private List<DocOrderDocument> pickConNoFileList;
    /**
     * 提箱指令
     */
    private List<DocOrderDocument> pickConCommandFileList;
    /**
     * 还箱单
     */
    private List<DocOrderDocument> returnConFileList;
    /**
     * 签收单
     */
    private List<DocOrderDocument> receiptGoodsFileList;

}
