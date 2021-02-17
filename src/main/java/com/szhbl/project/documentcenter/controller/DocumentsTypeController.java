package com.szhbl.project.documentcenter.controller;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.config.rabbit.order.ShipOrderRabbitmq;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.DocDocumentsType;
import com.szhbl.project.documentcenter.service.IDocDocumentsTypeService;
import com.szhbl.project.order.service.IBusiShippingorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 单证类型管理Controller
 *
 * @author szhbl
 * @date 2020-01-03
 */
@Api(tags = "托书单证类型管理")
@RestController
@RequestMapping("/document/type")
public class DocumentsTypeController extends BaseController {
    @Autowired
    private IDocDocumentsTypeService documentsTypeService;

    @Autowired
    private IBusiShippingorderService busiShippingorderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 查询单证类型管理列表
     */
    @ApiOperation(value = "单证类型管理列表查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activeArea", value = "单证作用域:0:去程，1:回程，2:去回程"),
    })
//    @PreAuthorize("@ss.hasPermi('document:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocDocumentsType docDocumentsType) {
        startPage();
        List<DocDocumentsType> list = documentsTypeService.selectDocDocumentsTypeList(docDocumentsType);
        // 查询出有父级的二级菜单
        docDocumentsType.setIsSystem(1);
        List<DocDocumentsType> listSecond = documentsTypeService.selectParentList(docDocumentsType);
        for (DocDocumentsType stage : list) {
            List<DocDocumentsType> docList = new ArrayList<>();
            for (DocDocumentsType docType : listSecond) {
                // 如果父级id = stage.id
                if (docType.getFileOrderStage() != null && docType.getFileOrderStage().equals(stage.getId())) {
                    docList.add(docType);
                }
            }
            stage.setDocTypesList(docList);
        }
        return getDataTable(list);
    }

    @ApiOperation(value = "单证类型管理二级编辑时查询一级集合", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isSystem", value = "单证作用域:0:一级分类")
    })
//    @PreAuthorize("@ss.hasPermi('document:type:list')")
    @GetMapping("/getOrderDocList")
    public AjaxResult getOrderDocList(DocDocumentsType docDocumentsType) {
        List<DocDocumentsType> list = documentsTypeService.selectDocDocumentsTypeList(docDocumentsType);
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "单证类型管理二级编辑时查询一级集合", notes = "")
    @GetMapping("/getparentlist")
    public AjaxResult getParentlist(DocDocumentsType docDocumentsType) {
        docDocumentsType.setIsSystem(0);
        List<DocDocumentsType> list = documentsTypeService.selectParentList(docDocumentsType);
        return AjaxResult.success(list);
    }

    /**
     * 导出单证类型管理列表
     */
    @ApiImplicitParam(name = "docDocumentsType")
    @PreAuthorize("@ss.hasPermi('document:type:export')")
    @Log(title = "单证类型管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocDocumentsType docDocumentsType) {
        List<DocDocumentsType> list = documentsTypeService.selectDocDocumentsTypeList(docDocumentsType);
        ExcelUtil<DocDocumentsType> util = new ExcelUtil<DocDocumentsType>(DocDocumentsType.class);
        return util.exportExcel(list, "type");
    }

    /**
     * 获取单证类型管理详细信息
     */
    @ApiOperation(value = "根据id获取单证类型管理详细信息")
//    @PreAuthorize("@ss.hasPermi('document:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(documentsTypeService.selectDocDocumentsTypeById(id));
    }

    /**
     * 修改单证类型管理
     */
    @ApiOperation(value = "修改二级单证类型管理")
    @PreAuthorize("@ss.hasPermi('document:type:edit')")
    @Log(title = "单证类型管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocDocumentsType docDocumentsType) {
        int i = documentsTypeService.updateDocDocumentsType(docDocumentsType);
        return toAjax(i);
    }

    /**
     * 删除单证类型管理
     */
    @ApiOperation(value = "删除单证类型管理")
    @PreAuthorize("@ss.hasPermi('document:type:remove')")
    @Log(title = "单证类型管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(documentsTypeService.deleteDocDocumentsTypeByIds(ids));
    }

    /**
     * 获取没有父级类型的单证类型信息
     *
     * @return
     */
    @ApiOperation(value = "新增一级时获取没有父级类型的单证类型信息")
    @Log(title = "单证类型管理")
//    @PreAuthorize("@ss.hasPermi('document:type:add')")
    @GetMapping("/getNoParentDocType")
    public AjaxResult getNoParentDocType() {
        return AjaxResult.success(documentsTypeService.selectNoParentDocumentType());
    }

    /**
     * 更新父级分类时查询子单证及未分配单证
     *
     * @return
     */
    @ApiOperation(value = "更新一级时父级分类时查询子单证及未分配单证")
    @Log(title = "单证类型管理", businessType = BusinessType.DELETE)
//    @PreAuthorize("@ss.hasPermi('document:type:edit')")
    @GetMapping("/getNoParentDocType/{fileOrderStage}")
    public AjaxResult getDocumentTypeForUpdate(@PathVariable("fileOrderStage") Long fileOrderStage) {
        List<DocDocumentsType> noParentDocTypeList = documentsTypeService.selectNoParentDocumentType();
        List<DocDocumentsType> list = documentsTypeService.selectDocumentTypeByStage(fileOrderStage);
        list.addAll(noParentDocTypeList);
        return AjaxResult.success(list);
    }

    /**
     * 新增单证一级类型管理
     */
    @ApiOperation(value = "新增单证一级类型管理")
    @PreAuthorize("@ss.hasPermi('document:type:add')")
    @Log(title = "单证类型管理", businessType = BusinessType.INSERT)
    @PostMapping("/stage")
    public AjaxResult add(@RequestBody DocDocumentsType docDocumentsType) {
        docDocumentsType.setCreateBy(SecurityUtils.getUsername());
//        docDocumentsType.setCreateBy("wangzhichao");
        return toAjax(documentsTypeService.insertDocDocumentsType(docDocumentsType));
    }

    /**
     * 修改单证类型管理
     */
    @ApiOperation(value = "修改单证一级类型管理")
    @PreAuthorize("@ss.hasPermi('document:type:edit')")
    @Log(title = "单证类型管理", businessType = BusinessType.UPDATE)
    @PutMapping("/stage")
    public AjaxResult editStage(@RequestBody DocDocumentsType docDocumentsType) {
        docDocumentsType.setUpdateBy(SecurityUtils.getUsername());
//        docDocumentsType.setUpdateBy("wangzhichao");
        return toAjax(documentsTypeService.updateDocType(docDocumentsType));
    }

    @GetMapping("/email/{aa}")
    public void sss(@PathVariable("aa") String aa){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("orderNumber", "ZIHWB200420PFLie14");
        Message message = new Message("舱位号为\"ZIHWB200420PFLie14\"的报关材料还未上传/填写，请尽快提供！".getBytes(), messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(ShipOrderRabbitmq.BLPT_DOCMESSAGE_TOPIC_EXCHANGE,
                "blpt.docmessage."+aa, message, correlationData);
    }

    @ApiOperation(value = "获取单证一级分类")
    @GetMapping("/getParentTypeList")
    public AjaxResult getParentTypeList(){
        DocDocumentsType param = new DocDocumentsType();
        param.setIsSystem(0);
        return AjaxResult.success(documentsTypeService.selectParentList(param));
    }

    @ApiOperation(value = "获取单证二级分类")
    @GetMapping("/getSonTypeList/{stage}")
    public AjaxResult getSonTypeList(@PathVariable("stage")Long stage){
        return AjaxResult.success(documentsTypeService.selectDocumentTypeByStage(stage));
    }

    @ApiOperation(value = "根据舱位号验证是否存在订单")
    @GetMapping("/getOrderByNumber/{orderNumber}")
    public AjaxResult getSonTypeList(@PathVariable("orderNumber")String orderNumber){
        return AjaxResult.success(busiShippingorderService.selectBusiShippingorderByOrderNumber(orderNumber));
    }

    @ApiOperation(value = "根据单证类型和班列时间获取单证提醒时间")
    @GetMapping("/getRemindTime")
    public AjaxResult getRemindTime(String fileTypeKey, Date classDate){
        DocDocumentsType documentsType = documentsTypeService.getTypeByTypeKey(fileTypeKey);
        if(documentsType.getFileNotice() == null){
            return AjaxResult.success(documentsType.getFileTypeText() + " 暂未设置默认提醒时间");
        } else {
            Date date = DateUtils.getPointTime(documentsType.getFileNotice(), documentsType.getFileNoticeTime(), classDate);
            return AjaxResult.success(DateUtils.parseStr(date));
        }
    }



}
