package com.szhbl.project.backclient.controller;

import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.backclient.domain.AboutUs;
import com.szhbl.project.backclient.service.IAboutUsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关于我们Controller
 *
 * @author szhbl
 * @date 2020-01-14
 */
@CrossOrigin
@RestController
@RequestMapping("/backclient/us")
public class AboutUsController extends BaseController {
    @Autowired
    private IAboutUsService aboutUsService;

    /**
     * 查询关于我们列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:us:list')")
    @GetMapping("/list")
    public TableDataInfo list(AboutUs aboutUs) {
        startPage();
        List<AboutUs> list = aboutUsService.selectAboutUsList(aboutUs);
        return getDataTable(list);
    }

    @GetMapping("/aboutUsClient")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "语言0中文1英文2俄语3德语", name = "language", paramType = "query", dataType = "String", required = true)
    })
    public AjaxResult aboutUsClient(AboutUs aboutUs) {
        List<AboutUs> list = aboutUsService.selectAboutUsList(aboutUs);
        if (list.size() > 0) {
            return AjaxResult.success(list.get(0));
        }
        return AjaxResult.success("无数据");
    }

    /**
     * 导出关于我们列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:us:export')")
    @Log(title = "关于我们", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AboutUs aboutUs) {
        List<AboutUs> list = aboutUsService.selectAboutUsList(aboutUs);
        ExcelUtil<AboutUs> util = new ExcelUtil<AboutUs>(AboutUs.class);
        return util.exportExcel(list, "us");
    }

    /**
     * 获取关于我们详细信息
     */
    @PreAuthorize("@ss.hasPermi('backclient:us:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(aboutUsService.selectAboutUsById(id));
    }

    /**
     * 新增关于我们
     */
    @PreAuthorize("@ss.hasPermi('backclient:us:add')")
    @Log(title = "关于我们", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AboutUs aboutUs) {
        AboutUs aboutUs1 = new AboutUs();
        aboutUs1.setLanguage(aboutUs.getLanguage());
        List<AboutUs> list = aboutUsService.selectAboutUsList(aboutUs1);
        if (list.size() == 0) {
            return toAjax(aboutUsService.insertAboutUs(aboutUs));
        } else {
            return AjaxResult.error("请不要插入同一语言类型信息！");
        }
    }

    /**
     * 修改关于我们
     */
    @PreAuthorize("@ss.hasPermi('backclient:us:edit')")
    @Log(title = "关于我们", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AboutUs aboutUs) {
        AboutUs aboutUs1 = new AboutUs();
        aboutUs1.setLanguage(aboutUs.getLanguage());
        List<AboutUs> list = aboutUsService.selectAboutUsList(aboutUs1);
        //判断是否有重复的
        if (list.size() == 0) {
            return toAjax(aboutUsService.updateAboutUs(aboutUs));
        } else {
            //有的话是不是当前数据本身
            if (list.get(0).getId() == aboutUs.getId()) {
                return toAjax(aboutUsService.updateAboutUs(aboutUs));
            } else {
                return toAjax(0);
            }
        }

    }

    /**
     * 删除关于我们
     */
    @PreAuthorize("@ss.hasPermi('backclient:us:remove')")
    @Log(title = "关于我们", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(aboutUsService.deleteAboutUsByIds(ids));
    }
}
