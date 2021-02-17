package com.szhbl.project.enquiry.controller;

import com.szhbl.common.utils.CommonUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.enquiry.domain.ZgTripFee;
import com.szhbl.project.enquiry.service.IZgTripFeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 郑欧线整柜去程费用Controller
 *
 * @author jhm
 * @date 2020-04-01
 */
@RestController
@RequestMapping("/enquiry/tripFee")
public class ZgTripFeeController extends BaseController
{
    @Autowired
    private IZgTripFeeService zgTripFeeService;
    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 查询郑欧线整柜去程费用列表
     */
//    @PreAuthorize("@ss.hasPermi('enquiry:tripFee:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZgTripFee zgTripFee)
    {
        startPage();
        List<ZgTripFee> list = zgTripFeeService.selectZgTripFeeList(zgTripFee);
        return getDataTable(list);
    }

    /**
     * 导出郑欧线整柜去程费用列表
     */
    @PreAuthorize("@ss.hasPermi('enquiry:tripFee:export')")
    @Log(title = "郑欧线整柜去程费用", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ZgTripFee zgTripFee)
    {
        List<ZgTripFee> list = zgTripFeeService.selectZgTripFeeList(zgTripFee);
        ExcelUtil<ZgTripFee> util = new ExcelUtil<ZgTripFee>(ZgTripFee.class);
        return util.exportExcel(list, "整柜去程费用");
    }

    /**
     * 获取郑欧线整柜去程费用详细信息
     */
//    @PreAuthorize("@ss.hasPermi('enquiry:tripFee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(zgTripFeeService.selectZgTripFeeById(id));
    }

    /**
     * 新增郑欧线整柜去程费用
     */
    @PreAuthorize("@ss.hasPermi('enquiry:tripFee:add')")
    @Log(title = "郑欧线整柜去程费用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZgTripFee zgTripFee)
    {
        return toAjax(zgTripFeeService.insertZgTripFee(zgTripFee));
    }

    /**
     * 修改郑欧线整柜去程费用
     */
    @PreAuthorize("@ss.hasPermi('enquiry:tripFee:edit')")
    @Log(title = "郑欧线整柜去程费用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZgTripFee zgTripFee)
    {
        return toAjax(zgTripFeeService.updateZgTripFee(zgTripFee));
    }

    /**
     * 删除郑欧线整柜去程费用
     */
    @PreAuthorize("@ss.hasPermi('enquiry:tripFee:remove')")
    @Log(title = "郑欧线整柜去程费用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(zgTripFeeService.deleteZgTripFeeByIds(ids));
    }


    /**
     * 下载模板
     */
    @PreAuthorize("@ss.hasPermi('enquiry:tripFee:download')")
    @GetMapping("/downLoadExcel")
    @ApiOperation(value = "下载郑欧线整柜去程提货费用导入模板")
    public void downModel(HttpServletRequest request, HttpServletResponse response) {
        String filename = "郑欧线整柜去程提货费用导入模板.xlsx";
        String path = "templates/郑欧线整柜去程提货费用导入模板.xlsx";
        CommonUtils.downloadThymeleaf(resourceLoader,filename,path,request,response);
    }
    /**
     * 导入
     */
  //  @PreAuthorize("@ss.hasPermi('enquiry:tripFee:import')")
    @PostMapping("/importExcel")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("导入郑欧线整柜去程提货费用导入模板")
    public AjaxResult importExcel(@RequestParam MultipartFile excelFile) throws Exception {

        ExcelUtil<ZgTripFee> util = new ExcelUtil<>(ZgTripFee.class);
        List<ZgTripFee> list = util.importExcel(excelFile.getInputStream());
        int total = list.size();
        int successNum = 0;
        int errorNum = 0;
        logger.debug("导入郑欧线整柜去程提货费用,\n{}",list);
        Date date = new Date();
        for (ZgTripFee zg : list) {
            String[] conTypes = zg.getContainerType().split("、");
            for (int i = 0; i < conTypes.length; i++) {
                if (StringUtils.isEmpty(conTypes[i])) {
                    continue;
                }
                zg.setContainerType(conTypes[i].trim().replace("\n", ""));
                zg.setPickUpCity(zg.getPickUpCity().trim().replace("\n", ""));
                zg.setCargoCollectionPoint(zg.getCargoCollectionPoint().trim().replace("\n", ""));
                zg.setCreateTime(date);
                zgTripFeeService.insertOrUpdateZgTripFee(zg);
                if (i == conTypes.length - 1) {
                    successNum++;
                }
            }
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
