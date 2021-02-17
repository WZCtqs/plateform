package com.szhbl.project.backclient.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.backclient.domain.ChargingStandard;
import com.szhbl.project.backclient.service.IChargingStandardService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 收费标准Controller
 *
 * @author b
 * @date 2020-05-08
 */
@CrossOrigin
@RestController
@RequestMapping("/backclient/standard")
public class ChargingStandardController extends BaseController
{
    @Autowired
    private IChargingStandardService chargingStandardService;

    /**
     * 查询收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('enquiryPrice:standard:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChargingStandard chargingStandard)
    {
        startPage();
        List<ChargingStandard> list = chargingStandardService.selectChargingStandardList(chargingStandard);
        return getDataTable(list);
    }

    /**
     * 导出收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('enquiryPrice:standard:export')")
    @Log(title = "收费标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ChargingStandard chargingStandard)
    {
        List<ChargingStandard> list = chargingStandardService.selectChargingStandardList(chargingStandard);
        ExcelUtil<ChargingStandard> util = new ExcelUtil<ChargingStandard>(ChargingStandard.class);
        return util.exportExcel(list, "standard");
    }

    /**
     * 获取收费标准详细信息
     */
//    @PreAuthorize("@ss.hasPermi('backclient:standard:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(chargingStandardService.selectChargingStandardById(id));
    }

    /**
     * 新增收费标准
     */
    @PreAuthorize("@ss.hasPermi('enquiryPrice:standard:add')")
    @Log(title = "收费标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChargingStandard chargingStandard)
    {
        //判断该种类有无数据，有的话不能添加
        ChargingStandard c1 = new ChargingStandard();
        c1.setType(chargingStandard.getType());
        c1.setValidStartDate(chargingStandard.getValidStartDate());
        c1.setValidEndDate(chargingStandard.getValidEndDate());
        List<ChargingStandard> list = chargingStandardService.selectSameChargingStandard(c1);
        if (list.size()==0){
            return toAjax(chargingStandardService.insertChargingStandard(chargingStandard));
        }else {
            return AjaxResult.error("请不要重复添加同一类型数据！");
        }
    }

    /**
     * 修改收费标准
     */
    @PreAuthorize("@ss.hasPermi('enquiryPrice:standard:edit')")
    @Log(title = "收费标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChargingStandard chargingStandard)
    {
        ChargingStandard c1 = new ChargingStandard();
        c1.setType(chargingStandard.getType());
        c1.setValidStartDate(chargingStandard.getValidStartDate());
        c1.setValidEndDate(chargingStandard.getValidEndDate());
        List<ChargingStandard> list = chargingStandardService.selectSameChargingStandard(c1);
        //判断修改后的语言和类型有没有重复的
        if (list.size()==0){
            return toAjax(chargingStandardService.updateChargingStandard(chargingStandard));
        }else {
            //判断重复的是不是他自己
            if (list.get(0).getId()==chargingStandard.getId()){
                return toAjax(chargingStandardService.updateChargingStandard(chargingStandard));
            }else {
                return AjaxResult.error("请不要重复添加同一类型数据！");
            }
        }
    }

    /**
     * 删除收费标准
     */
    @PreAuthorize("@ss.hasPermi('enquiryPrice:standard:remove')")
    @Log(title = "收费标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(chargingStandardService.deleteChargingStandardByIds(ids));
    }

    @GetMapping("/cilentGetInfo")
    public AjaxResult cilentGetInfo(ChargingStandard chargingStandard)
    {
        List<ChargingStandard> list = chargingStandardService.selectChargingStandardList(chargingStandard);
        if (list.size()>0){
            return AjaxResult.success(list.get(0));
        }else {
            ChargingStandard rr =  new ChargingStandard();
            rr.setContent("暂无数据");
            return AjaxResult.success(rr);
        }
    }
}
