package com.szhbl.project.kh.controller;

import java.util.List;

import com.szhbl.common.utils.ServletUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.service.IBusiClientsService;
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
import com.szhbl.project.kh.domain.KhUser;
import com.szhbl.project.kh.service.IKhUserService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 客户端用户Controller
 * 
 * @author jhm
 * @date 2020-03-21
 */
@RestController
@RequestMapping("/kh/user")
@Api(tags = "客户端用户")
public class KhUserController extends BaseController
{
    @Autowired
    private IKhUserService khUserService;
    @Autowired
    private ClientTokenService tokenService;
    @Autowired
    private IBusiClientsService busiClientsService;
    /**
     * 查询客户端用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(KhUser khUser)
    {
        khUser.setClientId(tokenService.getClientId(ServletUtils.getRequest()));
        startPage();
        List<KhUser> list = khUserService.selectKhUserList(khUser);
        return getDataTable(list);
    }

    /**
     * 导出客户端用户列表
     */
    @Log(title = "客户端用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(KhUser khUser)
    {   khUser.setClientId(tokenService.getClientId(ServletUtils.getRequest()));
        List<KhUser> list = khUserService.selectKhUserList(khUser);
        ExcelUtil<KhUser> util = new ExcelUtil<KhUser>(KhUser.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 获取客户端用户详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取客户端用户详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(khUserService.selectKhUserById(id));
    }

    /**
     * 新增客户端用户
     */
    @Log(title = "客户端用户", businessType = BusinessType.INSERT)
    @ApiOperation("新增客户端用户")
    @PostMapping
    public AjaxResult add(@RequestBody KhUser khUser)
    {
        //新增之前查询手机号是否已存在
        KhUser user = khUserService.selectKhUserByPhone(khUser.getPhone());
        BusiClients busiClients=busiClientsService.selectBusiClientsByName(khUser.getPhone());
        if(StringUtils.isNull(user)&&StringUtils.isNull(busiClients)){
            khUser.setClientId(tokenService.getClientId(ServletUtils.getRequest()));
            return toAjax(khUserService.insertKhUser(khUser));
        }
        return AjaxResult.error("手机号已存在，请确认");
    }

    /**
     * 修改客户端用户
     */
    @Log(title = "客户端用户", businessType = BusinessType.UPDATE)
    @ApiOperation("修改客户端用户")
    @PutMapping
    public AjaxResult edit(@RequestBody KhUser khUser)
    {
        KhUser user = khUserService.selectKhUserByPhone(khUser.getPhone());
        BusiClients busiClients=busiClientsService.selectBusiClientsByName(khUser.getPhone());
        if(StringUtils.isNull(busiClients)&&(StringUtils.isNull(user)||(user!=null&&user.getId()==khUser.getId()))){
            return toAjax(khUserService.updateKhUser(khUser));
        }
        return AjaxResult.error("手机号已存在，请重新填写");
    }

    /**
     * 删除客户端用户
     */
    @Log(title = "客户端用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(khUserService.deleteKhUserByIds(ids));
    }
}
