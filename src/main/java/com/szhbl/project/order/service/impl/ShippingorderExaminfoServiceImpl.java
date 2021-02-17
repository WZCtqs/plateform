package com.szhbl.project.order.service.impl;

import java.util.Date;
import java.util.List;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.order.domain.vo.OrderExamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.order.mapper.ShippingorderExaminfoMapper;
import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.service.IShippingorderExaminfoService;

/**
 * 订单转待审核信息(订舱公告)Service业务层处理
 * 
 * @author dps
 * @date 2020-04-01
 */
@Service
public class ShippingorderExaminfoServiceImpl implements IShippingorderExaminfoService 
{
    @Autowired
    private ShippingorderExaminfoMapper shippingorderExaminfoMapper;

    /**
     * 查询订单转待审核信息(订舱公告)
     * 
     * @param examId 订单转待审核信息(订舱公告)ID
     * @return 订单转待审核信息(订舱公告)
     */
    @Override
    public ShippingorderExaminfo selectShippingorderExaminfoById(String examId)
    {
        return shippingorderExaminfoMapper.selectShippingorderExaminfoById(examId);
    }

    /**
     * 查询订单转待审核信息(订舱公告)
     *
     * @param orderId 订单转待审核信息(订舱公告)ID
     * @return 订单转待审核信息(订舱公告)
     */
    @Override
    public ShippingorderExaminfo selectShippingorderExaminfoByOrderId(String orderId)
    {
        return shippingorderExaminfoMapper.selectShippingorderExaminfoByOrderId(orderId);
    }

    /**
     * 查询订单转待审核信息(订舱公告)列表
     * 
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 订单转待审核信息(订舱公告)
     */
    @Override
    public List<ShippingorderExaminfo> selectShippingorderExaminfoList(ShippingorderExaminfo shippingorderExaminfo)
    {
        String searchType = "1";
        //回程N-5
        Date classDateFive = shippingorderExaminfo.getClassDateFive();
        if(StringUtils.isNotNull(classDateFive)){
            searchType = "2";
            shippingorderExaminfo.setClassEastandwest("1");  //0为去程 1为回程
            shippingorderExaminfo.setClassDate(classDateFive);  //班列日期
            shippingorderExaminfo.setClassDateFive(DateUtils.dataChange(classDateFive,5));  //操作开始时间
            shippingorderExaminfo.setClassDateEnd(DateUtils.dataChangeAdd(classDateFive,1));  //操作结束时间
        }
        //去程N-4
        Date classDateFour = shippingorderExaminfo.getClassDateFour();
        if(StringUtils.isNotNull(classDateFour)){
            searchType = "3";
            shippingorderExaminfo.setClassEastandwest("0");  //0为去程 1为回程
            shippingorderExaminfo.setClassDate(classDateFour);  //班列日期
            shippingorderExaminfo.setClassDateFour(DateUtils.dataChange(classDateFour,4));  //操作开始时间
            shippingorderExaminfo.setClassDateEnd(DateUtils.dataChangeAdd(classDateFour,1));  //操作结束时间
        }
        shippingorderExaminfo.setSearchType(searchType);
        return shippingorderExaminfoMapper.selectShippingorderExaminfoList(shippingorderExaminfo);
    }

    /**
     * 每次转待审核修改记录
     */
    @Override
    public String oneEditRecord(String examId){
        return shippingorderExaminfoMapper.oneEditRecord(examId);
    }

    /**
     * 托书转待原因列表
     *
     * @param orderId 订单转待审核信息(订舱公告)
     * @return 订单转待审核信息(订舱公告)集合
     */
    @Override
    public List<ShippingorderExaminfo> selectOrderExaminfoList(String orderId){
        return shippingorderExaminfoMapper.selectOrderExaminfoList(orderId);
    }

    /**
     * 添加订舱组备注
     *
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 结果
     */
    @Override
    public int addRemark(ShippingorderExaminfo shippingorderExaminfo)
    {
        return shippingorderExaminfoMapper.addRemark(shippingorderExaminfo);
    }

    /**
     * 新增订单转待审核信息(订舱公告)
     * 
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 结果
     */
    @Override
    public int insertShippingorderExaminfo(ShippingorderExaminfo shippingorderExaminfo)
    {
        return shippingorderExaminfoMapper.insertShippingorderExaminfo(shippingorderExaminfo);
    }

    /**
     * 修改订单转待审核信息(订舱公告)
     * 
     * @param shippingorderExaminfo 订单转待审核信息(订舱公告)
     * @return 结果
     */
    @Override
    public int updateShippingorderExaminfo(ShippingorderExaminfo shippingorderExaminfo)
    {
        return shippingorderExaminfoMapper.updateShippingorderExaminfo(shippingorderExaminfo);
    }

    /**
     * 批量删除订单转待审核信息(订舱公告)
     * 
     * @param examIds 需要删除的订单转待审核信息(订舱公告)ID
     * @return 结果
     */
    @Override
    public int deleteShippingorderExaminfoByIds(String[] examIds)
    {
        return shippingorderExaminfoMapper.deleteShippingorderExaminfoByIds(examIds);
    }

    /**
     * 删除订单转待审核信息(订舱公告)信息
     * 
     * @param examId 订单转待审核信息(订舱公告)ID
     * @return 结果
     */
    @Override
    public int deleteShippingorderExaminfoById(String examId)
    {
        return shippingorderExaminfoMapper.deleteShippingorderExaminfoById(examId);
    }

    @Override
    public  List<OrderExamVo> getTimes(String lineType,String startDate, String endDate) {

        List<OrderExamVo> orderExamVos = shippingorderExaminfoMapper.getTimes(lineType,startDate,endDate);

        return orderExamVos;

    }
}
