package com.szhbl.project.documentcenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.DocGwczSettlement;
import com.szhbl.project.documentcenter.domain.bo.DcBody;
import com.szhbl.project.documentcenter.domain.bo.DcSettlementBO;
import com.szhbl.project.documentcenter.service.IDocGwczSettlementService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * gwczSettlementController
 *
 * @author szhbl
 * @date 2020-12-07
 */
@RestController
@RequestMapping("/document/gwczSettlement")
public class DocGwczSettlementController extends BaseController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IDocGwczSettlementService docGwczSettlementService;

    /**
     * 查询gwczSettlement列表
     */
    @PreAuthorize("@ss.hasPermi('gwczSettlement:gwczSettlement:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocGwczSettlement docGwczSettlement) {
        startPage();
        List<DocGwczSettlement> list = docGwczSettlementService.selectDocGwczSettlementList(docGwczSettlement);
        return getDataTable(list);
    }

    /**
     * 导出gwczSettlement列表
     */
    @PreAuthorize("@ss.hasPermi('gwczSettlement:gwczSettlement:export')")
    @Log(title = "gwczSettlement", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocGwczSettlement docGwczSettlement) {
        List<DocGwczSettlement> list = docGwczSettlementService.selectDocGwczSettlementList(docGwczSettlement);
        ExcelUtil<DocGwczSettlement> util = new ExcelUtil<DocGwczSettlement>(DocGwczSettlement.class);
        return util.exportExcel(list, "gwczSettlement");
    }

    /**
     * 获取gwczSettlement详细信息
     */
    @PreAuthorize("@ss.hasPermi('gwczSettlement:gwczSettlement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(docGwczSettlementService.selectDocGwczSettlementById(id));
    }

    /**
     * 新增gwczSettlement
     */
    @PreAuthorize("@ss.hasPermi('gwczSettlement:gwczSettlement:add')")
    @Log(title = "gwczSettlement", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocGwczSettlement docGwczSettlement) {
        return toAjax(docGwczSettlementService.insertDocGwczSettlement(docGwczSettlement));
    }

    /**
     * 修改gwczSettlement
     */
    @PreAuthorize("@ss.hasPermi('gwczSettlement:gwczSettlement:edit')")
    @Log(title = "gwczSettlement", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocGwczSettlement docGwczSettlement) {
        return toAjax(docGwczSettlementService.updateDocGwczSettlement(docGwczSettlement));
    }

    /**
     * 删除gwczSettlement
     */
    @PreAuthorize("@ss.hasPermi('gwczSettlement:gwczSettlement:remove')")
    @Log(title = "gwczSettlement", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(docGwczSettlementService.deleteDocGwczSettlementByIds(ids));
    }

    @RequestMapping("/dcSettlementInfo")
    public AjaxResult getDcSettlementInfo(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        DcBody bo = JSONObject.parseObject(sb.toString(), DcBody.class);
        DcSettlementBO settlement = bo.getRows().get(0);
        DocGwczSettlement s = docGwczSettlementService.selectSettlementByOrderIdAndConNo(settlement.getORDERID(), settlement.getCONTAINERNO());
        DocGwczSettlement gwczSettlement = new DocGwczSettlement();
        gwczSettlement.setPxSettlementVolume(settlement.getPXSETTLEMENTVOLUME());
        gwczSettlement.setPxVolume(settlement.getPXVOLUME());
        gwczSettlement.setPxWeight(settlement.getPXLONGANDWIDE());
        gwczSettlement.setContainerNo(settlement.getCONTAINERNO());
        if (s != null) {
            gwczSettlement.setId(s.getId());
            gwczSettlement.setUpdateTime(new Date());
            return toAjax(docGwczSettlementService.updateDocGwczSettlement(gwczSettlement));
        } else {
            gwczSettlement.setOrderId(settlement.getORDERID());
            gwczSettlement.setOrderNumber(settlement.getORDERNUMBER());
            gwczSettlement.setCreateTime(new Date());
            gwczSettlement.setUpdateTime(gwczSettlement.getCreateTime());
            return toAjax(docGwczSettlementService.insertDocGwczSettlement(gwczSettlement));
        }
    }
}
