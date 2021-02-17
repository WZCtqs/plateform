package com.szhbl.project.order.service.impl;

import com.szhbl.project.order.domain.BusiShippingorders;
import com.szhbl.project.order.mapper.BusiShippingorderMapper;
import org.springframework.stereotype.Service;

/**
 * @Description : 转待审核服务
 * @Author : wangzhichao
 * @Date: 2020-07-02 14:31
 */
@Service
public class ZhuanDshService {

    private BusiShippingorderMapper busiShippingorderMapper;

    /**
     * @param busiShippingorder 托书信息
     * @param RailwayBj         是否需要铁路报价（0：不需要，1：需要）
     */
    public void orderCheckReplyNotXj(BusiShippingorders busiShippingorder, int RailwayBj) {
        //需要铁路报价
        if (RailwayBj == 1) {
            // todo 调用铁路询价方法
            String line = busiShippingorder.getLineTypeid();
            // 整拼箱  0整柜 1拼箱
            String isconsolidation = busiShippingorder.getIsconsolidation();
            /** 0中欧 2中亚 3中越 4中俄 */
            /** 1郑欧 整柜*/
            if ("0".equals(line) && "0".equals(isconsolidation)) {

            }
            /** 2郑欧 拼箱*/
            if ("0".equals(line) && "1".equals(isconsolidation)) {

            }
            /** 3郑俄 整柜*/
            if ("4".equals(line) && "0".equals(isconsolidation)) {

            }
            /** 4郑俄 拼箱*/
            if ("4".equals(line) && "1".equals(isconsolidation)) {

            }
            /** 5郑中亚 整柜*/
            if ("2".equals(line) && "0".equals(isconsolidation)) {

            }
            /** 6郑中亚 拼箱*/
            if ("2".equals(line) && "1".equals(isconsolidation)) {

            }
            /** 7郑越 整柜*/
            if ("3".equals(line) && "0".equals(isconsolidation)) {

            }
            /** 8郑越 拼箱*/
            if ("3".equals(line) && "1".equals(isconsolidation)) {

            }
        }
        //根据托书编号更新托书状态
        busiShippingorderMapper.updateIsExamlineByOrderId(busiShippingorder.getOrderId(), "10");
    }

}
