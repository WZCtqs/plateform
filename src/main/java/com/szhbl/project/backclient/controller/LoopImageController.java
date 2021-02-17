package com.szhbl.project.backclient.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.project.backclient.util.upload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.backclient.domain.LoopImage;
import com.szhbl.project.backclient.service.ILoopImageService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 轮播图Controller
 * 
 * @author szhbl
 * @date 2020-01-07
 */
@RestController
@RequestMapping("/backclient/image")
@Api(tags = " 轮播图",description = "客户端/平台端")
public class LoopImageController extends BaseController
{
    @Autowired
    private ILoopImageService loopImageService;

    /**
     * 查询轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:image:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoopImage loopImage)
    {
        startPage();
        List<LoopImage> list = loopImageService.selectLoopImageList(loopImage);
        return getDataTable(list);
    }

    @GetMapping("/listClient")
    @ApiOperation("客户端查询轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "语言0中文1英文2俄语3德语",name = "language",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult listClient(LoopImage loopImage)
    {
        loopImage.setIsDisplay("0");
        List<LoopImage> list = loopImageService.selectLoopImageList(loopImage);
        return AjaxResult.success(list);
    }
    /**
     * 导出轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:image:export')")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LoopImage loopImage)
    {
        List<LoopImage> list = loopImageService.selectLoopImageList(loopImage);
        ExcelUtil<LoopImage> util = new ExcelUtil<LoopImage>(LoopImage.class);
        return util.exportExcel(list, "image");
    }

    /**
     * 获取轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('backclient:image:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(loopImageService.selectLoopImageById(id));
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('backclient:image:add')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LoopImage loopImage)
    {
        return toAjax(loopImageService.insertLoopImage(loopImage));
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('backclient:image:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LoopImage loopImage)
    {
        return toAjax(loopImageService.updateLoopImage(loopImage));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('backclient:image:remove')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(loopImageService.deleteLoopImageByIds(ids));
    }

    @PostMapping("insertfile")
    public AjaxResult insertfile(HttpServletResponse response, HttpServletRequest request,
                             @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        String url3 = "";
        String url = "";
        if (file != null) {
                url3 = file.getOriginalFilename();
                url = upload.uploadFile(request, file, "bxt");
        }
        AjaxResult ajax = AjaxResult.success();
        InetAddress addr = InetAddress.getLocalHost();
        //ajax.put("fileUrl", SzhblConfig.getServerHost()+":8083/bxt/"+url);
        ajax.put("fileUrl", "https://zxdc.zih718.com/plateform_prod_api/bxt/"+url);
        return ajax;
    }

}
