package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.DocNewRemind;
import com.szhbl.project.documentcenter.domain.DocOrder;
import com.szhbl.project.documentcenter.domain.DocOrderDocument;
import com.szhbl.project.documentcenter.domain.vo.DocRemindPage;
import com.szhbl.project.documentcenter.mapper.DocOrderMapper;
import com.szhbl.project.documentcenter.mapper.OrderDocumentMapper;
import com.szhbl.project.documentcenter.service.IDocNewRemindService;
import com.szhbl.project.monitor.domain.SysMessage;
import com.szhbl.project.monitor.service.ISysMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单单证提醒设置Controller
 *
 * @author szhbl
 * @date 2020-03-30
 */
@Slf4j
@Api(tags = "提醒设置、节点维护")
@RestController
@RequestMapping("/document/remind")
public class DocNewRemindController extends BaseController
{

    @Autowired
    private IDocNewRemindService docNewRemindService;

    @Resource
    private OrderDocumentMapper orderDocumentMapper;
@Autowired
    ISysMessageService messageService;
    @Resource
    DocOrderMapper docOrderMapper;

    /**
     * 新增订单单证提醒设置
     */
    @ApiOperation(value = "新增订单单证提醒设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "orderId:订单id，在输入仓位号，并且验证通过的情况下，传参"),
            @ApiImplicitParam(name = "fileTypeKey", value = "fileTypeKey:单证类型，在选中第二个下拉框的时候，并且验证通过的情况下，传参"),
            @ApiImplicitParam(name = "newRemind", value = "newRemind:更改默认时间，（例：  -2，  2， 3）")
    })
    @PreAuthorize("@ss.hasPermi('document:remind:add')")
    @Log(title = "订单单证提醒设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocNewRemind docNewRemind) throws Exception {
        docNewRemind.setCreateBy(SecurityUtils.getUsername());
        log.info("订单单证提醒设置: " + docNewRemind.toString());
//        return toAjax(1);
        return toAjax(docNewRemindService.insertDocNewRemind(docNewRemind));
    }

    /**
     * 查询订单单证提醒设置列表
     */
    @ApiOperation(value = "提醒设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNumber", value = "订单编号"),
            @ApiImplicitParam(name = "emailStatus", value = "邮件发送结果：0：成功，1：失败"),
            @ApiImplicitParam(name = "uploaded", value = "单证状态：0：未上传，1:上传"),
            @ApiImplicitParam(name = "classDate", value = "班列日期")
    })
//    @PreAuthorize("@ss.hasPermi('document:remind:list')")
    @GetMapping("/list")
    public TableDataInfo selectForRemindPage(DocRemindPage docRemindPage) {
        startPage();
        LoginUser user = SecurityUtils.getLoginUser();
        docRemindPage.setTjr(user.getUser().getReferenceType());
        docRemindPage.setTjrId(user.getUser().getTjrId());
        docRemindPage.setUserid(user.getUser().getUserId());
        docRemindPage.setDeptCode(user.getUser().getDeptCode());
        List<DocRemindPage> list = docNewRemindService.selectForRemindPage(docRemindPage);
        return getDataTable(list);
    }
    @GetMapping("dataUpdate")
    public void sss(){
        List<DocOrderDocument> orderDocuments = orderDocumentMapper.selectOrderDocumentList(new DocOrderDocument());
        log.info(String.valueOf(orderDocuments.size()));
        int a = 0;
        for (DocOrderDocument document : orderDocuments){
            DocOrder docOrder = new DocOrder();
            docOrder.setOrderId(document.getOrderId());
            docOrder.setActualSupplyTime(document.getUploadTime());
            docOrder.setActualSupply(1);
            docOrder.setFileTypeKey(document.getFileTypeKey());
            SysMessage sysMessage = new SysMessage();
            sysMessage.setOrderId(document.getOrderId());
            sysMessage.setFileTypeKey(document.getFileTypeKey());
            List<SysMessage> messages = messageService.selectMessageList(sysMessage);
            if(messages.size()>0){
            for (int i = 0; i < 1; i++) {
                SysMessage message = messages.get(0);
                docOrder.setEmailStatus(message.getEmailStatus());
            }
            }
            int i = docOrderMapper.updateActualSupply(docOrder);
            a+=i;
        }
        log.info(a + "");
    }

}
