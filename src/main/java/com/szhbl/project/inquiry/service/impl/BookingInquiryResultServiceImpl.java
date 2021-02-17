package com.szhbl.project.inquiry.service.impl;

import com.szhbl.framework.config.BaiDuMapUtils;
import com.szhbl.project.enquiry.domain.ZgRailDivision;
import com.szhbl.project.enquiry.domain.ZgRussiaGoingFee;
import com.szhbl.project.enquiry.mapper.ZgRailDivisionMapper;
import com.szhbl.project.enquiry.mapper.ZgRussiaGoingFeeMapper;
import com.szhbl.project.inquiry.domain.BookingInquiry;
import com.szhbl.project.inquiry.domain.BookingInquiryResult;
import com.szhbl.project.inquiry.mapper.BookingInquiryResultMapper;
import com.szhbl.project.inquiry.service.IBookingInquiryResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 询价反馈结果数据Service业务层处理
 *
 * @author jhm
 * @date 2020-04-08
 */
@Service
public class BookingInquiryResultServiceImpl implements IBookingInquiryResultService {
    @Autowired
    private BookingInquiryResultMapper bookingInquiryResultMapper;

    @Autowired
    private ZgRailDivisionMapper zgRailDivisionMapper;

    @Autowired
    private ZgRussiaGoingFeeMapper zgRussiaGoingFeeMapper;

    /**
     * 查询询价反馈结果数据
     *
     * @param id 询价反馈结果数据ID
     * @return 询价反馈结果数据
     */
    @Override
    public BookingInquiryResult selectBookingInquiryResultById(Long id) {
        return bookingInquiryResultMapper.selectBookingInquiryResultById(id);
    }

    /**
     * 查询询价反馈结果数据列表
     *
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 询价反馈结果数据
     */
    @Override
    public List<BookingInquiryResult> selectBookingInquiryResultList(BookingInquiryResult bookingInquiryResult) {
        return bookingInquiryResultMapper.selectBookingInquiryResultList(bookingInquiryResult);
    }

    /**
     * 新增询价反馈结果数据
     * 
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 结果
     */
    @Override
    public int insertBookingInquiryResult(BookingInquiryResult bookingInquiryResult)
    {
        return bookingInquiryResultMapper.insertBookingInquiryResult(bookingInquiryResult);
    }

    /**
     * 修改询价反馈结果数据
     * 
     * @param bookingInquiryResult 询价反馈结果数据
     * @return 结果
     */
    @Override
    public int updateBookingInquiryResult(BookingInquiryResult bookingInquiryResult)
    {
        return bookingInquiryResultMapper.updateBookingInquiryResult(bookingInquiryResult);
    }

    /**
     * 批量删除询价反馈结果数据
     * 
     * @param ids 需要删除的询价反馈结果数据ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryResultByIds(Long[] ids)
    {
        return bookingInquiryResultMapper.deleteBookingInquiryResultByIds(ids);
    }

    /**
     * 删除询价反馈结果数据信息
     * 
     * @param id 询价反馈结果数据ID
     * @return 结果
     */
    @Override
    public int deleteBookingInquiryResultById(Long id)
    {
        return bookingInquiryResultMapper.deleteBookingInquiryResultById(id);
    }

    @Override
    public List<BookingInquiryResult> selectBookingInquiryResultWithInquiryId(Long inquiryId) {
        return bookingInquiryResultMapper.selectBookingInquiryResultWithInquiryId(inquiryId);
    }

    @Override
    public int updateBookingInquiryResultWithInquiryId(BookingInquiryResult bookingInquiryResult) {
        return bookingInquiryResultMapper.updateBookingInquiryResultWithInquiryId(bookingInquiryResult);
    }

    @Override
    public String getLastInquiryHxd(String orderId) {
        return bookingInquiryResultMapper.getLastInquiryHxd(orderId);
    }

    @Override
    public Date selectInquiryClassDateByOrderId(String orderId) {
        return bookingInquiryResultMapper.selectInquiryClassDateByOrderId(orderId);
    }

    /**
     * 计算距离
     */
    public Float getDistances(String origin, String destination) {
        String dis = null;
        try {
            dis = BaiDuMapUtils.getDistance(origin, destination);
        } catch (Exception e) {
            return null;
        }
        if (dis.contains("公里")) {
            dis = dis.replace("公里", "");
            return Float.valueOf(dis);
        }
        return Float.valueOf(dis.replace("米", "")) / 1000;
    }

    public BookingInquiryResult updateFeeForRussia(BookingInquiryResult inquiryResult) {
        inquiryResult.setPickUpShowClientFees(inquiryResult.getPickUpFees());
        BookingInquiry inquiry = inquiryResult.getBookingInquiry();
        /* 针对俄线: 已询价、去程、整柜、（门到站、门到门）、（40HC、40GP）询价，处理询价结果费用，区分提货费和铁路运费*/
        if (inquiry.getInquiryState().equals("3")
                && inquiry.getEastOrWest().equals("0")
                && inquiry.getLineType().equals("4")
                && inquiry.getGoodsType().equals("0")
                && (inquiry.getBookingService().equals("0") || inquiry.getBookingService().equals("1")
                && (inquiry.getContainerType().equals("40HC") || inquiry.getContainerType().equals("40GP")))
        ) {
            inquiryResult.setPickUpCurrencyType("$");
            // 获取铁路运费
            Map map = new HashMap();
            map.put("orderUnloadSite", "沃尔西诺");
            map.put("lineType", "4");
            map.put("eastOrWest", "0");
            map.put("containerType", "普箱");
            map.put("sameContainerType", "40GP");
            List<ZgRailDivision> list = zgRailDivisionMapper.selectZgRailDivisionWithMap(map);
            if (list.size() == 2) {
                // 郑州-沃尔西诺
                ZgRailDivision zgZZ = list.get(0);
                // 工厂-沃尔西诺
                ZgRailDivision zgGC = list.get(1);
                inquiryResult.setRailwayFees(zgZZ.getRailCost() + "");
                // 获取提货点到集货点费用信息
                double showClientPickFee = 0;
                double showClientWayFee = 0;
                // 判断提货地 是省内还是省外
                if (inquiry.getSenderProvince().equals("河南省")) {
                    // 省内
                    ZgRussiaGoingFee param = new ZgRussiaGoingFee();
                    param.setPickUpCity(inquiry.getSenderCity().replaceAll("市", ""));
                    param.setContainerType(inquiry.getContainerType());
                    param.setCargoCollectionPoint("郑州");
                    List<ZgRussiaGoingFee> russiaGoingFees = zgRussiaGoingFeeMapper.selectZgRussiaGoingFeeByCity(param);
                    if (russiaGoingFees.size() > 0) {
                        showClientPickFee = russiaGoingFees.get(0).getPickGoodsShowFee();
                    } else {
                        // 费用表没有维护费用时，进行计算
                        // 计算提货城市到集货点的距离
                        double distance = getDistances(inquiry.getSenderCity().replaceAll("市", "") + "站", "郑州国际陆港园区");
//                        }
                        showClientPickFee = distance * 1.5;
                        if (showClientPickFee < 100) {
                            showClientPickFee = 100;
                        } else if (showClientPickFee > 400) {
                            showClientPickFee = 400;
                        }
                    }
                    showClientWayFee = zgZZ.getRailCost();
                } else {
                    // 省外
                    showClientPickFee = 0.0;
                    inquiryResult.setXxyoRemark("提货费已包含在铁路运费中");

                    ZgRussiaGoingFee param = new ZgRussiaGoingFee();
                    param.setPickUpCity(inquiry.getSenderCity().replaceAll("市", ""));
                    param.setContainerType(inquiry.getContainerType());
                    param.setCargoCollectionPoint("郑州");
                    List<ZgRussiaGoingFee> russiaGoingFees = zgRussiaGoingFeeMapper.selectZgRussiaGoingFeeByCity(param);
                    if (russiaGoingFees.size() > 0) {
                        double distance = russiaGoingFees.get(0).getDistance();
                        if (distance <= 1000) {
                            showClientWayFee = zgGC.getRailCost();
                        } else {
                            showClientWayFee = zgGC.getRailCost() + russiaGoingFees.get(0).getPickGoodsFee();
                        }
                    } else {
                        // 计算提货城市到集货点的距离
                        double distance =
                                getDistances(inquiry.getSenderCity().replaceAll("市", "") + "站", "郑州国际陆港园区");
                        //                    }
                        if (distance <= 1000) {
                            showClientWayFee = zgGC.getRailCost();
                        } else {
                            showClientWayFee = zgGC.getRailCost() + (distance - 1000) * 1.5;
                        }
                    }
                }
                inquiryResult.setPickUpShowClientFees(fun2(showClientPickFee * inquiry.getContainerNum()));
                inquiryResult.setRailwayFees(fun2(showClientWayFee * inquiry.getContainerNum()));
            }
        }
        return inquiryResult;
    }

    /**
     * DecimalFormat转换最简便
     */
    public String fun2(double f) {
        if (f == 0) {
            return "0";
        } else {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(f);
        }
    }
}
