package com.szhbl.project.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.szhbl.project.monitor.domain.SysMessage;
import com.szhbl.project.monitor.service.ISysMessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 消息提醒Controller
 *
 * @author szhbl
 * @date 2020-04-07
 */
@RestController
@RequestMapping("/monitor/message")
public class SysMessageController extends BaseController
{
    @Autowired
    private ISysMessageService messageService;

    /**
     * 查询消息提醒列表
     */
    @PreAuthorize("@ss.hasPermi('systemLog:msgSendRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysMessage message)
    {
        startPage();
        List<SysMessage> list = messageService.selectMessageRecords(message);
        return getDataTable(list);
    }

    /**
     * 导出消息提醒列表
     */
    @PreAuthorize("@ss.hasPermi('systemLog:msgSendRecord:download')")
    @Log(title = "消息提醒", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysMessage message)
    {
        List<SysMessage> list = messageService.selectMessageRecords(message);
        String str1 = "yyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(str1);
        for (SysMessage sysMessage:list){
            if ("0".equals(sysMessage.getEmailStatus())){
                sysMessage.setZt("邮件发送成功");
            }else if ("1".equals(sysMessage.getEmailStatus())){
                sysMessage.setZt("邮件发送失败");
            }else {
                sysMessage.setZt("站内信发送成功");
            }
            if (sysMessage.getClassDate()!=null) {
                sysMessage.setCd(sdf1.format(sysMessage.getClassDate()));
            }
        }
        ExcelUtil<SysMessage> util = new ExcelUtil<SysMessage>(SysMessage.class);
        return util.exportExcel(list, "message");
    }

    /**
     * 获取消息提醒详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:query')")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") String messageId)
    {
        return AjaxResult.success(messageService.selectMessageById(messageId));
    }

    /**
     * 新增消息提醒
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:add')")
    @Log(title = "消息提醒", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysMessage message)
    {
        return toAjax(messageService.insertMessage(message));
    }

    /**
     * 删除消息提醒
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:remove')")
    @Log(title = "消息提醒", businessType = BusinessType.DELETE)
	@DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable String[] messageIds)
    {
        return toAjax(messageService.deleteMessageByIds(messageIds));
    }
}
