package com.szhbl.project.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.order.domain.vo.OrderExamExcelVo;
import com.szhbl.project.order.domain.vo.OrderExamVo;
import com.szhbl.project.order.domain.vo.RecordObj;
import com.szhbl.project.order.domain.vo.RecordObjCon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.order.domain.ShippingorderExaminfo;
import com.szhbl.project.order.service.IShippingorderExaminfoService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 订单转待审核信息(订舱公告)Controller
 *
 * @author dps
 * @date 2020-04-01
 */
@RestController
@RequestMapping("/order/examinfo")
@Api(tags = "订舱公告")
public class ShippingorderExaminfoController extends BaseController
{
    @Autowired
    private IShippingorderExaminfoService shippingorderExaminfoService;

    /**
     * 查询订单转待审核信息(订舱公告)列表
     */
//    @PreAuthorize("@ss.hasPermi('order:examinfo:list')")
    @GetMapping("/examList")
    @ApiOperation("订舱公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true)
    })
    public TableDataInfo list(ShippingorderExaminfo shippingorderExaminfo)
    {
        startPage();
        if(StringUtils.isNotEmpty(shippingorderExaminfo.getOrderNumber())){
            shippingorderExaminfo.setOrderNumber((shippingorderExaminfo.getOrderNumber()).trim());
        }
        List<ShippingorderExaminfo> list = shippingorderExaminfoService.selectShippingorderExaminfoList(shippingorderExaminfo);
        //修改状态 0发车前更改 1发车后更改
        for(ShippingorderExaminfo examItem:list){
            if(StringUtils.isNotNull(examItem.getClassDate()) && StringUtils.isNotNull(examItem.getExamDate())){
                Long classDate = (examItem.getClassDate()).getTime();
                Long examDate = (examItem.getExamDate()).getTime();
                if(classDate>examDate){
                    examItem.setChangeType("0");
                }else{
                    examItem.setChangeType("1");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 每次转待审核修改记录
     */
    @GetMapping("/oneEditRecord")
    @ApiOperation("每次转待审核修改记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "公告id",name = "examId",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult oneEditRecord(String examId){
        String editRecord = shippingorderExaminfoService.oneEditRecord(examId);
        RecordObj recordObj = new RecordObj();
        if(StringUtils.isNotEmpty(editRecord)){
            //截去末尾符号
            editRecord = StringUtils.substring(editRecord,0,-9);
            String title = editRecord.substring(editRecord.indexOf("<th>")+4, editRecord.lastIndexOf("</th>")); //标题
            String listStr = editRecord.substring(title.length()+9, editRecord.length());; //修改记录
            String[] recordS = listStr.split("<td>");
            recordObj.setTitle(title);
            recordObj.setList(recordS);
        }
        return AjaxResult.success(recordObj);
    }

    /**
     * 托书转待原因列表
     */
    @PreAuthorize("@ss.hasPermi('classOrder:orders:zdReason')")
    @GetMapping("/examInfoList")
    @ApiOperation("托书转待原因列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "托书id",name = "orderId",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo examInfoList(String orderId)
    {
        startPage();
        List<ShippingorderExaminfo> list = shippingorderExaminfoService.selectOrderExaminfoList(orderId);
        return getDataTable(list);
    }

    /**
     * 添加订舱组备注
     */
    @PreAuthorize("@ss.hasPermi('classOrder:publicOrderMsg:edit')")
    @Log(title = "添加订舱组备注", businessType = BusinessType.UPDATE)
    @PutMapping("/addRemark")
    @ApiOperation("添加订舱组备注")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "公告id",name = "examId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "备注内容",name = "remark",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "是否已读",name = "isread",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult addRemark(@RequestBody ShippingorderExaminfo shippingorderExaminfo)
    {
        return toAjax(shippingorderExaminfoService.addRemark(shippingorderExaminfo));
    }

    /**
     * 驳回原因已看
     */
    @Log(title = "驳回原因已看", businessType = BusinessType.UPDATE)
    @PutMapping("/addIsRead")
    @ApiOperation("驳回原因已看")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "托书id",name = "orderId",paramType = "query",dataType = "String",required = true),
    })
    public AjaxResult addIsRead(@RequestBody ShippingorderExaminfo shippingorderExaminfo)
    {
        shippingorderExaminfo.setIsread("1");
        int result = shippingorderExaminfoService.updateShippingorderExaminfo(shippingorderExaminfo);
        return toAjax(result);
    }

    /**
     * 导出订单转待审核信息(订舱公告)列表
     */
    @PreAuthorize("@ss.hasPermi('order:examinfo:export')")
    @Log(title = "订单转待审核信息(订舱公告)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ShippingorderExaminfo shippingorderExaminfo)
    {
        List<ShippingorderExaminfo> list = shippingorderExaminfoService.selectShippingorderExaminfoList(shippingorderExaminfo);
        ExcelUtil<ShippingorderExaminfo> util = new ExcelUtil<ShippingorderExaminfo>(ShippingorderExaminfo.class);
        return util.exportExcel(list, "examinfo");
    }

    /**
     * 获取订单转待审核信息(订舱公告)详细信息
     */
//    @PreAuthorize("@ss.hasPermi('order:examinfo:query')")
    @GetMapping(value = "/{examId}")
    public AjaxResult getInfo(@PathVariable("examId") String examId)
    {
        return AjaxResult.success(shippingorderExaminfoService.selectShippingorderExaminfoById(examId));
    }

    /**
     * 获取订单转待审核信息(订舱公告)详细信息
     */
//    @PreAuthorize("@ss.hasPermi('order:examinfo:query')")
    @GetMapping("/getInfoByOrderId")
    public AjaxResult getInfoByOrderId(String orderId)
    {
        return AjaxResult.success(shippingorderExaminfoService.selectShippingorderExaminfoByOrderId(orderId));
    }

    /**
     * 新增订单转待审核信息(订舱公告)
     */
//    @PreAuthorize("@ss.hasPermi('order:examinfo:add')")
    @Log(title = "订单转待审核信息(订舱公告)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShippingorderExaminfo shippingorderExaminfo)
    {
        return toAjax(shippingorderExaminfoService.insertShippingorderExaminfo(shippingorderExaminfo));
    }

    /**
     * 修改订单转待审核信息(订舱公告)
     */
    @PreAuthorize("@ss.hasPermi('order:examinfo:edit')")
    @Log(title = "订单转待审核信息(订舱公告)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShippingorderExaminfo shippingorderExaminfo)
    {
        return toAjax(shippingorderExaminfoService.updateShippingorderExaminfo(shippingorderExaminfo));
    }

    /**
     * 删除订单转待审核信息(订舱公告)
     */
    @PreAuthorize("@ss.hasPermi('order:examinfo:remove')")
    @Log(title = "订单转待审核信息(订舱公告)", businessType = BusinessType.DELETE)
	@DeleteMapping("/{examIds}")
    public AjaxResult remove(@PathVariable String[] examIds)
    {
        return toAjax(shippingorderExaminfoService.deleteShippingorderExaminfoByIds(examIds));
    }

    /**
     * 查询时间段内托书转待审核次数
     *
     * @param startDate 开始日期 YYYY-MM-DD
     * @param endDate 截止日期 YYYY-MM-DD
     * @return 结果
     */
    @GetMapping("/getTimes")
    public AjaxResult getTimes(String lineType,String startDate, String endDate)
    {
        // 获取时间段内全部转待审核记录
        List<OrderExamVo> orderExamVos = shippingorderExaminfoService.getTimes(lineType,startDate, endDate);

        // 添加集合中去程N-4,回程N-5的时间点
        orderExamVos.forEach(orderExamVo -> {
            orderExamVo.setEffectiveDate(
                    DateUtils.convertEffectiveDate(orderExamVo.getClassDate(),orderExamVo.getEastOrWest())
            );
        });

        //过滤
        //排除掉不符合条件的数据(保留转待审核时间在N-几之后的)
        List<OrderExamVo> orderExamVoList =
                orderExamVos.stream().filter(orderExamVo ->
                        orderExamVo.getExamDate().after(orderExamVo.getEffectiveDate()))
                        .collect(Collectors.toList());

        // 分组
        // 根据舱位号
        Map<String, List<OrderExamVo>> groupByOrderNumber =
                orderExamVoList.stream().collect(Collectors.groupingBy(OrderExamVo::getOrderNumber));

        List<OrderExamExcelVo> orderExamExcelVos = new ArrayList<>();

        // 遍历分组后的数据
        for (Map.Entry<String, List<OrderExamVo>> entry : groupByOrderNumber.entrySet()) {
            String key = entry.getKey();
            int size = entry.getValue().size();
            logger.debug("托书编号：{},有效转待审核次数：{}",key, size);
            OrderExamExcelVo orderExamExcelVo = new OrderExamExcelVo();
            orderExamExcelVo.setOrderNumber(key);
            orderExamExcelVo.setSize(size);
            orderExamExcelVos.add(orderExamExcelVo);
        }
        ExcelUtil<OrderExamExcelVo> examExcelVoExcelUtil = new ExcelUtil<>(OrderExamExcelVo.class);

        return examExcelVoExcelUtil.exportExcel(orderExamExcelVos,"orderExam");
    }

}
