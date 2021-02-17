package com.szhbl.project.enquiry.controller;

import java.util.Date;
import java.util.List;

import com.szhbl.common.utils.CommonUtils;
import com.szhbl.project.enquiry.domain.ZgRailDivision;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.enquiry.domain.ZgReturnTripFee;
import com.szhbl.project.enquiry.service.IZgReturnTripFeeService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 郑欧整柜回程送货费用Controller
 *
 * @author jhm
 * @date 2020-04-02
 */
@RestController
@RequestMapping("/enquiry/zgReturnfee")
public class ZgReturnTripFeeController extends BaseController
{
    @Autowired
    private IZgReturnTripFeeService zgReturnTripFeeService;
    @Resource
    private ResourceLoader resourceLoader;
    /**
     * 查询郑欧整柜回程送货费用列表
     */
//    @PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZgReturnTripFee zgReturnTripFee)
    {
        startPage();
        List<ZgReturnTripFee> list = zgReturnTripFeeService.selectZgReturnTripFeeList(zgReturnTripFee);
        return getDataTable(list);
    }

    /**
     * 导出郑欧整柜回程送货费用列表
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:export')")
    @Log(title = "郑欧整柜回程送货费用", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZgReturnTripFee zgReturnTripFee)
    {
        List<ZgReturnTripFee> list = zgReturnTripFeeService.selectZgReturnTripFeeList(zgReturnTripFee);
        ExcelUtil<ZgReturnTripFee> util = new ExcelUtil<ZgReturnTripFee>(ZgReturnTripFee.class);
        return util.exportExcel(list, "郑欧整柜回程送货费用");
    }

    /**
     * 获取郑欧整柜回程送货费用详细信息
     */
//    @PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(zgReturnTripFeeService.selectZgReturnTripFeeById(id));
    }

    /**
     * 新增郑欧整柜回程送货费用
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:add')")
    @Log(title = "郑欧整柜回程送货费用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZgReturnTripFee zgReturnTripFee)
    {
        return toAjax(zgReturnTripFeeService.insertZgReturnTripFee(zgReturnTripFee));
    }

    /**
     * 修改郑欧整柜回程送货费用
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:edit')")
    @Log(title = "郑欧整柜回程送货费用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZgReturnTripFee zgReturnTripFee)
    {
        return toAjax(zgReturnTripFeeService.updateZgReturnTripFee(zgReturnTripFee));
    }

    /**
     * 删除郑欧整柜回程送货费用
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:remove')")
    @Log(title = "郑欧整柜回程送货费用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(zgReturnTripFeeService.deleteZgReturnTripFeeByIds(ids));
    }

    /**
     * 下载模板
     */
    @PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:download')")
    @GetMapping("/downLoadExcel")
    @ApiOperation(value = "下载回程各线路整柜送货费用导入模板")
    public void downModel(HttpServletRequest request, HttpServletResponse response) {
        String filename = "回程各线路整柜送货费用导入模板.xlsx";
        String path = "templates/回程各线路整柜送货费用导入模板.xlsx";
        CommonUtils.downloadThymeleaf(resourceLoader,filename,path,request,response);
    }
    /**
     * 导入
     */
    //@PreAuthorize("@ss.hasPermi('enquiry:zgReturnfee:import')")
    @PostMapping("/importExcel")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("导入郑欧线整柜回程送货费用导入模板")
    public AjaxResult importExcel(@RequestParam MultipartFile excelFile) throws Exception {

        ExcelUtil<ZgReturnTripFee> util = new ExcelUtil<>(ZgReturnTripFee.class);
        List<ZgReturnTripFee> list = util.importExcel(excelFile.getInputStream());
        int total = list.size();
        int successNum = 0;
        int errorNum = 0;
        logger.debug("导入郑欧线整柜回程送货费用,\n{}"+list);
        for (ZgReturnTripFee zg : list) {
            successNum++;
            zg.setProvince(zg.getProvince().trim().replace("\n",""));
            zg.setCityTrainStation(zg.getCityTrainStation().trim().replace("\n",""));
            zg.setReceiptPlace(zg.getReceiptPlace().trim().replace("\n",""));
            zg.setCreateTime(new Date());
//            zgReturnTripFeeService.insertZgReturnTripFee(zg);
            zgReturnTripFeeService.insertOrUpdateZgReturnTripFee(zg);
        }
        if(successNum==0){
            return AjaxResult.error("全部失败，请重新填写");
        }else if(successNum<total && successNum>0){
            return AjaxResult.error("部分成功,有"+errorNum+"条数据必填项为空，请重新填写");
        }else{
            return AjaxResult.success("全部导入成功");
        }

    }
}
