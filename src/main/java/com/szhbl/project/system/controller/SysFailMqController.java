package com.szhbl.project.system.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.system.domain.SysFailMq;
import com.szhbl.project.system.service.ISysFailMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息队列发送失败消息Controller
 *
 * @author szhbl
 * @date 2020-12-08
 */
@RestController
@RequestMapping("/system/mq")
public class SysFailMqController extends BaseController {
    @Autowired
    private ISysFailMqService sysFailMqService;

    /**
     * 查询消息队列发送失败消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:mq:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFailMq sysFailMq) {
        startPage();
        List<SysFailMq> list = sysFailMqService.selectSysFailMqList(sysFailMq);
        return getDataTable(list);
    }

    /**
     * 导出消息队列发送失败消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:mq:export')")
    @Log(title = "消息队列发送失败消息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysFailMq sysFailMq) {
        List<SysFailMq> list = sysFailMqService.selectSysFailMqList(sysFailMq);
        ExcelUtil<SysFailMq> util = new ExcelUtil<SysFailMq>(SysFailMq.class);
        return util.exportExcel(list, "mq");
    }

    /**
     * 获取消息队列发送失败消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:mq:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysFailMqService.selectSysFailMqById(id));
    }

    /**
     * 新增消息队列发送失败消息
     */
    @PreAuthorize("@ss.hasPermi('system:mq:add')")
    @Log(title = "消息队列发送失败消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFailMq sysFailMq) {
        return toAjax(sysFailMqService.insertSysFailMq(sysFailMq));
    }

    /**
     * 修改消息队列发送失败消息
     */
    @PreAuthorize("@ss.hasPermi('system:mq:edit')")
    @Log(title = "消息队列发送失败消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFailMq sysFailMq) {
        return toAjax(sysFailMqService.updateSysFailMq(sysFailMq));
    }

    /**
     * 删除消息队列发送失败消息
     */
    @PreAuthorize("@ss.hasPermi('system:mq:remove')")
    @Log(title = "消息队列发送失败消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysFailMqService.deleteSysFailMqByIds(ids));
    }
}
