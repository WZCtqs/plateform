package com.szhbl.project.kh.controller;

import java.util.List;

import com.szhbl.common.utils.ServletUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.kh.service.ClientTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.kh.domain.KhMenuPermission;
import com.szhbl.project.kh.service.IKhMenuPermissionService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 客户端---菜单权限Controller
 * 
 * @author jhm
 * @date 2020-03-21
 */
@RestController
@RequestMapping("/kh/menuPermission")
@Api(tags = "客户端---菜单权限")
public class KhMenuPermissionController extends BaseController
{
    @Autowired
    private IKhMenuPermissionService khMenuPermissionService;
    @Autowired
    private ClientTokenService tokenService;

    /**
     * 查询客户端---菜单权限列表
     */
    @GetMapping("/list")
    @ApiOperation("查询客户端---菜单权限列表")
    public TableDataInfo list(KhMenuPermission khMenuPermission)
    {
        khMenuPermission.setClientId(tokenService.getClientId(ServletUtils.getRequest()));
        startPage();
        List<KhMenuPermission> list = khMenuPermissionService.selectKhMenuPermissionList(khMenuPermission);
        return getDataTable(list);
    }

    /**
     * 导出客户端---菜单权限列表
     */
    @Log(title = "客户端---菜单权限", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出客户端---菜单权限列表")
    public AjaxResult export(KhMenuPermission khMenuPermission)
    {
        khMenuPermission.setClientId(tokenService.getClientId(ServletUtils.getRequest()));
        List<KhMenuPermission> list = khMenuPermissionService.selectKhMenuPermissionList(khMenuPermission);
        ExcelUtil<KhMenuPermission> util = new ExcelUtil<KhMenuPermission>(KhMenuPermission.class);
        return util.exportExcel(list, "menuPermission");
    }

    /**
     * 获取客户端---菜单权限详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取客户端---菜单权限详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        KhMenuPermission khMenuPermission = khMenuPermissionService.selectKhMenuPermissionById(id);
        if(StringUtils.isNotEmpty( khMenuPermission.getMenuId())){
            khMenuPermission.setMenuIds( khMenuPermission.getMenuId().split(","));
        }

        return AjaxResult.success(khMenuPermission);
    }

    /**
     * 新增客户端---菜单权限
     */
    //@PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "客户端---菜单权限", businessType = BusinessType.INSERT)
    @ApiOperation("获取客户端---菜单权限详细信息")
    @PostMapping
    public AjaxResult add(@RequestBody KhMenuPermission khMenuPermission)
    {
        if(StringUtils.isNotEmpty(khMenuPermission.getMenuIds())){

            String str4 = StringUtils.join(khMenuPermission.getMenuIds(), ","); // 数组转字符串(逗号分隔)(推荐)
            System.out.println(str4);
            khMenuPermission.setMenuId(str4);
       }
        khMenuPermission.setClientId(tokenService.getClientId(ServletUtils.getRequest()));

        return toAjax(khMenuPermissionService.insertKhMenuPermission(khMenuPermission));
    }

    /**
     * 修改客户端---菜单权限
     */
    //@PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "客户端---菜单权限", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KhMenuPermission khMenuPermission)
    {
        return toAjax(khMenuPermissionService.updateKhMenuPermission(khMenuPermission));
    }

    /**
     * 删除客户端---菜单权限
     */
    //@PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "客户端---菜单权限", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation("删除客户端---菜单权限")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(khMenuPermissionService.deleteKhMenuPermissionByIds(ids));
    }
}
