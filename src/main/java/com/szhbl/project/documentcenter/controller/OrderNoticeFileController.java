package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.OrderNoticeFile;
import com.szhbl.project.documentcenter.domain.vo.PxGoodsInOut;
import com.szhbl.project.documentcenter.service.IOrderNoticeFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(tags = "入货通知书页面接口")
@RestController
@RequestMapping("/document/notice")
public class OrderNoticeFileController extends BaseController {

    @Autowired
    private IOrderNoticeFileService orderNoticeFileService;

    @Autowired
    private RabbitTemplate template;

    @ApiOperation(value = "入货通知书列表查询")
//    @PreAuthorize("@ss.hasAnyPermi('document:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderNoticeFile orderNoticeFile) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        orderNoticeFile.setTjr(loginUser.getUser().getReferenceType());
        orderNoticeFile.setTjrId(loginUser.getUser().getTjrId());
        orderNoticeFile.setUserid(loginUser.getUser().getUserId());
        orderNoticeFile.setDeptCode(loginUser.getUser().getDeptCode());
        List<OrderNoticeFile> list = orderNoticeFileService.orderNoticeFileList(orderNoticeFile);
        return getDataTable(list);
    }

    /**
     * 发送邮件(不带附件)（弃用）
     * @param order
     * @return
     */
    @GetMapping("/simpleEmail")
    public AjaxResult simpleEmail(@RequestBody OrderNoticeFile order) {
        if (null != order.getOrderIds()) {
            String[] orderIds = order.getOrderIds();
            return orderNoticeFileService.simpleEmail(orderIds);
        } else {
            return AjaxResult.error(500, "请至少选中一条数据！");
        }
    }

    /**
     * 发送邮件(带附件)（弃用）
     * @param order
     * @return
     */
    @GetMapping("/fileEmail")
    public AjaxResult simpleEmailFile(@RequestBody OrderNoticeFile order) {
        if (null != order.getOrderIds()) {
            String[] orderIds = order.getOrderIds();
            return orderNoticeFileService.fileEmail(orderIds);
        } else {
            return AjaxResult.error(500, "请至少选中一条数据！");
        }
    }

    @GetMapping("/createPDFAgain")
    public AjaxResult createPDFAgain(String orderId) {
        boolean flag = orderNoticeFileService.createOrderNoticePDF(orderId);
        if (flag) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }


    @PostMapping("rabbit")
    public void sss(@RequestBody PxGoodsInOut pxGoodsInOut) throws IOException {
        List<String> orderIds = orderNoticeFileService.getExamineOrderId();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (String orderId : orderIds) {
            if (i > 5) {
                i = 0;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                boolean flag = orderNoticeFileService.createOrderNoticePDF(orderId);
                if (!flag) {
                    sb.append(orderId + ",");
                }
            }
        }
        System.out.println("生成失败托书id : " + sb);
//        orderNoticeFileService.createOrderNoticePDF(pxGoodsInOut.getOrder_id());
//        OrderDocUrl orderDocUrl = new OrderDocUrl();
//        orderDocUrl.setFileName("测试文件名");
//        orderDocUrl.setFileUrl("测试 url");
//        orderDocUrl.setOrderId("dddddddddd");
//        CorrelationData correlationData = new CorrelationData();
//        template.convertAndSend(ShipOrderRabbitmq.ORDER_NOTICE_PDF_TOPIC_EXCHANGE, ShipOrderRabbitmq.ORDER_NOTICE_PDF_ROUTINGKEY, orderDocUrl, correlationData);

    }

}
