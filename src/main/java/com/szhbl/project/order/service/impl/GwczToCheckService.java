package com.szhbl.project.order.service.impl;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.inquiry.domain.*;
import com.szhbl.project.order.domain.GoodToCheckResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description : @Author : wangzhichao @Date: 2020-11-20 10:09
 */
@Service
public class GwczToCheckService {

    private static final Integer gnWeight = 5000; // 5吨
    private static final Integer gnLength = 350; // 3.5米
    private static final Integer gnAmount = 500; // 500件

    private static final Integer gwWeight = 3000; // 3吨
    private static final Integer gwLength = 350; // 3.5米
    private static final Integer gwAmount = 300; // 300件

    public GoodToCheckResult judgeStationInquiry(
            BookingInquiry oldInquiry,
            BookingInquiryBackup newInquiry,
            BookingInquiryResult oldResult,
            BookingInquiryResultBackup newResult) {
        GoodToCheckResult result = new GoodToCheckResult();
        switch (newInquiry.getGoodsType()) {
            case "0":
                // 整柜
                result = fclGoodTypeCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                break;
            case "1":
                // 拼箱
                boolean hasChange = goodInfoCheck(oldInquiry, newInquiry);
                // 拼箱信息无变化
                if (!hasChange) {
                    newResult = getOldDomesticFee(oldResult, newResult);
                    newResult = getOldForeignFee(oldResult, newResult);
                } else {
                    result = lclGoodTypeCheck(oldInquiry, newInquiry, result);
                    //结果表 0：不需要，1：需要报价
                    newResult.setDomesticFlag(result.isGn() ? "1" : "0");
                    newResult.setForeignFlag(result.isGw() ? "1" : "0");
                }
                result.setInquiryResultBackup(newResult);
                break;
            default:
                break;
        }
        return result;
    }

    // 整柜 散货  提、送货判断
    public GoodToCheckResult fclGoodTypeCheck(
            BookingInquiry oldInquiry,
            BookingInquiryBackup newInquiry,
            GoodToCheckResult result,
            BookingInquiryResult oldResult,
            BookingInquiryResultBackup newResult) {
        switch (oldInquiry.getBookingService()) {
            case "0": // 门到门
                switch (newInquiry.getBookingService()) {
                    case "0": // 服务不变
                        // 散货提货
                        result = pickGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        result = sendGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "1": // 门到站
                        // 散货提货
                        result = pickGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "2": // 站到站
                        // 自送货方式
                        result = pickGoodsSelfCheckA(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "3": // 站到门 减少门到站
                        // 自送货方式
                        result = pickGoodsSelfCheckA(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        result = sendGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    default:
                        break;
                }
                break;
            case "1": // 门到站
                switch (newInquiry.getBookingService()) {
                    case "0": // 门到门 增加站到门
                        // 散货提货
                        result = pickGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        if (newInquiry.getEastOrWest().equals("0")) {
                            result.setGw(true);
                            newResult.setForeignFlag("1");
                        } else {
                            result.setGn(true);
                            newResult.setDomesticFlag("1");
                        }
                        break;
                    case "1": // 服务不变
                        // 散货提货
                        result = pickGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "2": // 站到站 减少门到站
                        // 自送货方式
                        result = pickGoodsSelfCheckA(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "3": // 站到门 减少门到站 增加站到门
                        // 自送货方式
                        result = pickGoodsSelfCheckA(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        result = sendGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    default:
                        break;
                }
                break;
            case "2": // 站到站
                switch (newInquiry.getBookingService()) {
                    case "0": // 门到门 增加门到站 增加站到门
                        // 散货提货
                        result = pickGoodsSelfCheckB(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        if (newInquiry.getEastOrWest().equals("0")) {
                            result.setGw(true);
                            newResult.setForeignFlag("1");
                        } else {
                            result.setGn(true);
                            newResult.setDomesticFlag("1");
                        }
                        break;
                    case "1": // 门到站 增加门到站
                        // 散货提货
                        result = pickGoodsSelfCheckB(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "2":
                        // 自送货方式
                        result = pickGoodsSelfCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "3": // 站到门  增加站到门
                        // 自送货方式
                        result = pickGoodsSelfCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        if (newInquiry.getEastOrWest().equals("0")) {
                            result.setGw(true);
                            newResult.setForeignFlag("1");
                        } else {
                            result.setGn(true);
                            newResult.setDomesticFlag("1");
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "3": // 站到门
                switch (newInquiry.getBookingService()) {
                    case "0": // 门到门 增加门到站
                        // 散货提货
                        result = pickGoodsSelfCheckB(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        result = sendGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "1": // 门到站 减少站到门 增加门到站
                        // 散货提货
                        result = pickGoodsSelfCheckB(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "2": // 站到站 减少站到门
                        // 自送货方式
                        result = pickGoodsSelfCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    case "3":
                        // 自送货方式
                        result = pickGoodsSelfCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        // 散货派送
                        result = sendGoodsCheck(oldInquiry, newInquiry, result, oldResult, newResult);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return result;
    }

    // 拼箱
    public GoodToCheckResult lclGoodTypeCheck(
            BookingInquiry oldInquiry, BookingInquiryBackup newInquiry, GoodToCheckResult result) {
        boolean gn = false;
        boolean gw = false;
        List<BookingInquiryGoodsDetailsBackup> newGoodsList =
                newInquiry.getBookingInquiryGoodsDetailsBackupList();
        // 单件超5吨、单件长度超3.5米，单票超500件，推送给国内场站报理货费、超长超重费；
        // 单件超3吨、单件长度超3.5米，单票超300件，推送给国外场站报理货费、超长超重费；
        if (Float.parseFloat(newInquiry.getTotalAmount()) > gnAmount) {
            gn = true;
            gw = true;
        } else if (Float.parseFloat(newInquiry.getTotalAmount()) > gwAmount) {
            gw = true;
        }
        for (BookingInquiryGoodsDetailsBackup goods : newGoodsList) {
            if (Float.parseFloat(goods.getGoodsLength()) > gnLength
                    || Float.parseFloat(goods.getGoodsWeight()) > gnWeight) {
                gn = true;
                gw = true;
            } else if (Float.parseFloat(goods.getGoodsLength()) > gwLength
                    || Float.parseFloat(goods.getGoodsWeight()) > gwWeight) {
                gw = true;
            }
            // 如果国内报价，则中断后续判断
            if (gn) {
                break;
            }
        }
        // 更改是否超长超重字段，推送给国内场站和国外场站报超长超重费
        if (!oldInquiry.getGoodsGeneral().equals(newInquiry.getGoodsGeneral())) {
            gn = true;
            gw = true;
        }
        result.setGn(gn);
        result.setGw(gw);
        return result;
    }

    // 散货提货判断 委提——委提
    public GoodToCheckResult pickGoodsCheck(
            BookingInquiry oldInquiry,
            BookingInquiryBackup newInquiry,
            GoodToCheckResult result,
            BookingInquiryResult oldResult,
            BookingInquiryResultBackup newResult) {
        // 1散货到堆场
        if ("1".equals(newInquiry.getDeliveryType())) {
            // 判断超长超重
//            result = lclGoodTypeCheck(oldInquiry, newInquiry, result);
            // 1散货到堆场
            if ("1".equals(oldInquiry.getDeliveryType())) {
                // 判断货物信息是否变化
                boolean hasChange = goodInfoCheck(oldInquiry, newInquiry);
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(hasChange);
                    if (!hasChange) {
                        newResult = getOldDomesticFee(oldResult, newResult);
                    } else {
                        newResult.setDomesticFlag("1");
                    }
                } else {
                    result.setGw(hasChange);
                    if (!hasChange) {
                        newResult = getOldForeignFee(oldResult, newResult);
                    } else {
                        newResult.setForeignFlag("1");
                    }
                }
            } else {
                // 0整柜到堆场，
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(true);
                    newResult.setDomesticFlag("1");
                } else {
                    result.setGw(true);
                    newResult.setForeignFlag("1");
                }
            }
        }
        result.setInquiryResultBackup(newResult);
        return result;
    }

    // 自送货方式判断 自送——自送
    public GoodToCheckResult pickGoodsSelfCheck(
            BookingInquiry oldInquiry,
            BookingInquiryBackup newInquiry,
            GoodToCheckResult result,
            BookingInquiryResult oldResult,
            BookingInquiryResultBackup newResult) {

        if ("1".equals(newInquiry.getDeliverySelfType())) {
            // 判断超长超重
//            result = lclGoodTypeCheck(oldInquiry, newInquiry, result);
            if ("1".equals(oldInquiry.getDeliverySelfType())) {
                // 判断货物信息是否变化
                boolean hasChange = goodInfoCheck(oldInquiry, newInquiry);
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(hasChange);
                    if (!hasChange) {
                        newResult = getOldDomesticFee(oldResult, newResult);
                    } else {
                        newResult.setDomesticFlag("1");
                    }
                } else {
                    result.setGw(hasChange);
                    if (!hasChange) {
                        newResult = getOldForeignFee(oldResult, newResult);
                    } else {
                        newResult.setForeignFlag("1");
                    }
                }
            } else {
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(true);
                    newResult.setDomesticFlag("1");
                } else {
                    result.setGw(true);
                    newResult.setForeignFlag("1");
                }
            }
        }
        result.setInquiryResultBackup(newResult);
        return result;
    }

    // 自送货方式判断 自送——委提
    public GoodToCheckResult pickGoodsSelfCheckB(
            BookingInquiry oldInquiry,
            BookingInquiryBackup newInquiry,
            GoodToCheckResult result,
            BookingInquiryResult oldResult,
            BookingInquiryResultBackup newResult) {
        if ("1".equals(newInquiry.getDeliveryType())) {
            // 判断超长超重
//            result = lclGoodTypeCheck(oldInquiry, newInquiry, result);
            if ("1".equals(oldInquiry.getDeliverySelfType())) {
                // 判断货物信息是否变化
                boolean hasChange = goodInfoCheck(oldInquiry, newInquiry);
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(hasChange);
                    if (!hasChange) {
                        newResult = getOldDomesticFee(oldResult, newResult);
                    } else {
                        newResult.setDomesticFlag("1");
                    }
                } else {
                    result.setGw(hasChange);
                    if (!hasChange) {
                        newResult = getOldForeignFee(oldResult, newResult);
                    } else {
                        newResult.setForeignFlag("1");
                    }
                }
            } else {
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(true);
                    newResult.setDomesticFlag("1");
                } else {
                    result.setGw(true);
                    newResult.setForeignFlag("1");
                }
            }
        }
        result.setInquiryResultBackup(newResult);
        return result;
    }

    // 自送货方式判断 委提——自送
    public GoodToCheckResult pickGoodsSelfCheckA(
            BookingInquiry oldInquiry,
            BookingInquiryBackup newInquiry,
            GoodToCheckResult result,
            BookingInquiryResult oldResult,
            BookingInquiryResultBackup newResult) {
        if ("1".equals(newInquiry.getDeliverySelfType())) {
            // 判断超长超重
//            result = lclGoodTypeCheck(oldInquiry, newInquiry, result);
            if ("1".equals(oldInquiry.getDeliveryType())) {
                // 判断货物信息是否变化
                boolean hasChange = goodInfoCheck(oldInquiry, newInquiry);
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(hasChange);
                    if (!hasChange) {
                        newResult = getOldDomesticFee(oldResult, newResult);
                    } else {
                        newResult.setDomesticFlag("1");
                    }
                } else {
                    result.setGw(hasChange);
                    if (!hasChange) {
                        newResult = getOldForeignFee(oldResult, newResult);
                    } else {
                        newResult.setForeignFlag("1");
                    }
                }
            } else {
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGn(true);
                    newResult.setDomesticFlag("1");
                } else {
                    result.setGw(true);
                    newResult.setForeignFlag("1");
                }
            }
        }
        result.setInquiryResultBackup(newResult);
        return result;
    }

    // 散货派送
    public GoodToCheckResult sendGoodsCheck(
            BookingInquiry oldInquiry,
            BookingInquiryBackup newInquiry,
            GoodToCheckResult result,
            BookingInquiryResult oldResult,
            BookingInquiryResultBackup newResult) {
        if ("1".equals(newInquiry.getDistributionType())) {
            // 判断超长超重
//            result = lclGoodTypeCheck(oldInquiry, newInquiry, result);
            if ("1".equals(oldInquiry.getDistributionType())) {
                // 判断货物信息是否变化
                boolean hasChange = goodInfoCheck(oldInquiry, newInquiry);
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGw(hasChange);
                    if (!hasChange) {
                        newResult = getOldForeignFee(oldResult, newResult);
                    } else {
                        newResult.setForeignFlag("1");
                    }
                } else {
                    result.setGn(hasChange);
                    if (!hasChange) {
                        newResult = getOldDomesticFee(oldResult, newResult);
                    } else {
                        newResult.setDomesticFlag("1");
                    }
                }
            } else {
                if ("0".equals(newInquiry.getEastOrWest())) {
                    result.setGw(true);
                    newResult.setForeignFlag("1");
                } else {
                    result.setGn(true);
                    newResult.setDomesticFlag("1");
                }
            }
        }
        result.setInquiryResultBackup(newResult);
        return result;
    }

    public boolean goodInfoCheck(BookingInquiry oldInquiry, BookingInquiryBackup newInquiry) {
        if ("0".equals(newInquiry.getGoodsType())) {
            // 更改货物数量、长宽高、单件重量、箱型、箱量中任何一个或多个
            if (!oldInquiry.getContainerNum().equals(newInquiry.getContainerNum())) {
                return true;
            }
            if (!oldInquiry.getContainerType().equals(newInquiry.getContainerType())) {
                return true;
            }
        }
        // 先判断合计是否改动
        if (!(new BigDecimal(newInquiry.getTotalVolume())
                .compareTo(
                        new BigDecimal(
                                StringUtils.isEmpty(oldInquiry.getTotalVolume())
                                        ? "0"
                                        : oldInquiry.getTotalVolume()))
                == 0)
                || !(new BigDecimal(newInquiry.getTotalWeight())
                .compareTo(
                        new BigDecimal(
                                StringUtils.isEmpty(oldInquiry.getTotalWeight())
                                        ? "0"
                                        : oldInquiry.getTotalWeight()))
                == 0)
                || !(new BigDecimal(newInquiry.getTotalAmount())
                .compareTo(
                        new BigDecimal(
                                StringUtils.isEmpty(oldInquiry.getTotalAmount())
                                        ? "0"
                                        : oldInquiry.getTotalAmount()))
                == 0)) {
            return true;
        } else if (newInquiry.getBookingInquiryGoodsDetailsBackupList().size()
                != oldInquiry.getBookingInquiryGoodsDetailsList().size()) {
            return true;
        } else {
            for (int i = 0; i < newInquiry.getBookingInquiryGoodsDetailsBackupList().size(); i++) {
                BookingInquiryGoodsDetails oldGood = oldInquiry.getBookingInquiryGoodsDetailsList().get(i);
                BookingInquiryGoodsDetailsBackup newGood =
                        (BookingInquiryGoodsDetailsBackup) newInquiry.getBookingInquiryGoodsDetailsBackupList().get(i);
                if (new BigDecimal(oldGood.getGoodsAmount())
                        .compareTo(new BigDecimal(newGood.getGoodsAmount()))
                        != 0) {
                    return true;
                }
                if (new BigDecimal(oldGood.getGoodsHeight())
                        .compareTo(new BigDecimal(newGood.getGoodsHeight()))
                        != 0) {
                    return true;
                }
                if (new BigDecimal(oldGood.getGoodsWidth())
                        .compareTo(new BigDecimal(newGood.getGoodsWidth()))
                        != 0) {
                    return true;
                }
                if (new BigDecimal(oldGood.getGoodsLength())
                        .compareTo(new BigDecimal(newGood.getGoodsLength()))
                        != 0) {
                    return true;
                }
                if (new BigDecimal(oldGood.getGoodsWeight())
                        .compareTo(new BigDecimal(newGood.getGoodsWeight()))
                        != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public BookingInquiryResultBackup getOldDomesticFee(BookingInquiryResult oldResult, BookingInquiryResultBackup newResult) {
        newResult.setDomesticFlag("0");
        // newResult.setDomesticFeedbackTime(oldResult.getDomesticFeedbackTime());
        // newResult.setDomesticNumber(oldResult.getDomesticNumber());
        // newResult.setDomesticOverFee(oldResult.getDomesticOverFee());
        // newResult.setDomesticOverFeeCurrencyType(oldResult.getDomesticOverFeeCurrencyType());
        // newResult.setDomesticPackFee(oldResult.getDomesticPackFee());
        // newResult.setDomesticPackFeeCurrencyType(oldResult.getDomesticPackFeeCurrencyType());
        // newResult.setDomesticRemark(oldResult.getDomesticRemark());
        // newResult.setDomesticTallyFee(oldResult.getDomesticTallyFee());
        // newResult.setDomesticTallyFeeCurrencyType(oldResult.getDomesticTallyFeeCurrencyType());
        // newResult.setDomesticUnPackFee(oldResult.getDomesticUnPackFee());
        // newResult.setDomesticUnPackFeeCurrencyType(oldResult.getDomesticUnPackFeeCurrencyType());
        return newResult;
    }

    public BookingInquiryResultBackup getOldForeignFee(BookingInquiryResult oldResult, BookingInquiryResultBackup newResult) {
        newResult.setForeignFlag("0");
        // newResult.setForeignFeedbackTime(oldResult.getForeignFeedbackTime());
        // newResult.setForeignNumber(oldResult.getForeignNumber());
        // newResult.setForeignOverFee(oldResult.getForeignOverFee());
        // newResult.setForeignOverFeeCurrencyType(oldResult.getForeignOverFeeCurrencyType());
        // newResult.setForeignPackFee(oldResult.getForeignPackFee());
        // newResult.setForeignPackFeeCurrencyType(oldResult.getForeignPackFeeCurrencyType());
        // newResult.setForeignRemark(oldResult.getForeignRemark());
        // newResult.setForeignTallyFee(oldResult.getForeignTallyFee());
        // newResult.setForeignTallyFeeCurrencyType(oldResult.getForeignTallyFeeCurrencyType());
        // newResult.setForeignUnPackFee(oldResult.getForeignUnPackFee());
        // newResult.setForeignUnPackFeeCurrencyType(oldResult.getForeignUnPackFeeCurrencyType());
        return newResult;
    }
}
