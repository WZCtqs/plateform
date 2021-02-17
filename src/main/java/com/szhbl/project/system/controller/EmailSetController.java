package com.szhbl.project.system.controller;

import com.szhbl.common.utils.email.MailUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.email.IMailService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.system.domain.EmailSet;
import com.szhbl.project.system.service.IEmailSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

/**
 * 邮件配置Controller
 * 
 * @author lzd
 * @date 2020-04-17
 */
@Slf4j
@RestController
@RequestMapping("/system/emailSet")
@Api("邮件配置")
public class EmailSetController extends BaseController
{
    @Autowired
    private IEmailSetService emailSetService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 查询邮件配置列表
     */
    //@PreAuthorize("@ss.hasPermi('basicInfo:emailInstall:list')")
    @GetMapping("/list")
    @ApiOperation("查询消息设置列表")
    public TableDataInfo list(EmailSet emailSet)
    {
        startPage();
        List<EmailSet> list = emailSetService.selectEmailSetList(emailSet);
        return getDataTable(list);
    }

    /**
     * 导出邮件配置列表
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:emailInstall:export')")
    @Log(title = "邮件配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(EmailSet emailSet)
    {
        List<EmailSet> list = emailSetService.selectEmailSetList(emailSet);
        ExcelUtil<EmailSet> util = new ExcelUtil<EmailSet>(EmailSet.class);
        return util.exportExcel(list, "emailSet");
    }

    /**
     * 获取邮件配置详细信息
     */
    //@PreAuthorize("@ss.hasPermi('basicInfo:emailInstall:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取邮件配置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(emailSetService.selectEmailSetById(id));
    }

    /**
     * 新增邮件配置
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:emailInstall:add')")
    @Log(title = "邮件配置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增邮件配置")
    public AjaxResult add(@RequestBody EmailSet emailSet)
    {
        return toAjax(emailSetService.insertEmailSet(emailSet));
    }

    /**
     * 修改邮件配置
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:emailInstall:edit')")
    @Log(title = "邮件配置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改邮件配置")
    public AjaxResult edit(@RequestBody EmailSet emailSet)
    {
        return toAjax(emailSetService.updateEmailSet(emailSet));
    }

    /**
     * 删除邮件配置
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:emailInstall:remove')")
    @Log(title = "邮件配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除邮件配置")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(emailSetService.deleteEmailSetByIds(ids));
    }

    /**
     * 测试发送
     */
    @PreAuthorize("@ss.hasPermi('basicInfo:emailInstall:send')")
    @Log(title = "邮件配置", businessType = BusinessType.OTHER)
    @PostMapping("/sendTest")
    @ApiOperation("测试发送邮件")
    public AjaxResult sendTest(String smtpSever,String emailAuthentication,String sendEmail, String password, String receiveEmail,
                               String subject, String content)throws MessagingException
    {
        log.info("ceshi---------" + smtpSever + "--" + emailAuthentication + "--" + sendEmail + "--" + password + "--" + receiveEmail + "--" + subject + "--" + content);
        MailUtils.mailByAll(smtpSever, emailAuthentication, sendEmail, password, receiveEmail, subject, content);
        return AjaxResult.success("发送成功");
    }

    /*@PostMapping("/send")
    public AjaxResult sendTest()
            throws AddressException, MessagingException
    {
       // mailService.sendSimpleMail( "liuzhd@zih718.com", "邮件测试", "20200417邮件测试");
        //MailUtils.mailByAll("smtp.qiye.163.com","0","liuzhd@zih718.com","Md15238783879","1374633657@qq.com","邮件发送测试测试","测试20200417");
        return toAjax(5);
    }*/

}
