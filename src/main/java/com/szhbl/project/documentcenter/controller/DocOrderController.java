package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.service.IDocOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单—单证（订单所需单证）Controller
 *
 * @author hp
 * @date 2020-04-13
 */
@RestController
@RequestMapping("/document/docorder")
public class DocOrderController extends BaseController
{
    @Autowired
    private IDocOrderService docOrderService;

    /**
     * 查询订单—单证（订单所需单证）列表
     */
//    @PreAuthorize("@ss.hasPermi('document:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocOrder docOrder) {
        startPage();
        LoginUser user = SecurityUtils.getLoginUser();
        docOrder.setTjr(user.getUser().getReferenceType());
        docOrder.setTjrId(user.getUser().getTjrId());
        docOrder.setUserid(user.getUser().getUserId());
        docOrder.setDeptCode(user.getUser().getDeptCode());
        List<DocOrder> list = docOrderService.selectDocOrderList(docOrder);
        return getDataTable(list);
    }

    /**
     * 获取订单—单证（订单所需单证）详细信息
     */
//    @PreAuthorize("@ss.hasPermi('document:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId)
    {
        return AjaxResult.success(docOrderService.selectDocOrderByOrderId(orderId));
    }

    /**
     * 新增订单—单证（订单所需单证）
     */
//    @PreAuthorize("@ss.hasPermi('document:order:add')")
    @Log(title = "订单—单证（订单所需单证）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(String orderId) throws Exception {
        return toAjax(docOrderService.insertDocOrderMatch(orderId));
    }

    /**
     * 删除订单—单证（订单所需单证）
     */
//    @PreAuthorize("@ss.hasPermi('document:order:remove')")
    @Log(title = "订单—单证（订单所需单证）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderId}")
    public AjaxResult remove(@PathVariable String orderId)
    {
        return toAjax(docOrderService.deleteDocOrderByOrderId(orderId));
    }
}
