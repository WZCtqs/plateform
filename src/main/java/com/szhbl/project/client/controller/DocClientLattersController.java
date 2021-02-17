package com.szhbl.project.client.controller;

import com.szhbl.common.utils.ServletUtils;
import com.szhbl.common.utils.file.FileUploadUtils;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.client.VO.ProblemFileVo;
import com.szhbl.project.client.domain.DocClientLatters;
import com.szhbl.project.client.service.IDocClientLattersService;
import com.szhbl.project.kh.service.ClientTokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户维护长期电放保函Controller
 *
 * @author szhbl
 * @date 2020-10-01
 */
@RestController
@RequestMapping("/docLatters")
public class DocClientLattersController extends BaseController {

    @Autowired
    private ClientTokenService tokenService;

    @Autowired
    private IDocClientLattersService docClientLattersService;

    /**
     * 查询客户维护长期电放保函列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DocClientLatters docClientLatters) {
        startPage();
        String clientId = tokenService.getClientId(ServletUtils.getRequest());
        docClientLatters.setClientId(clientId);
        docClientLatters.setDelFlag(0);
        List<DocClientLatters> list = docClientLattersService.selectDocClientLattersList(docClientLatters);
        return getDataTable(list);
    }

    /**
     * 获取客户维护长期电放保函详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(docClientLattersService.selectDocClientLattersById(id));
    }

    /**
     * 新增客户维护长期电放保函
     */
    @Log(title = "客户维护长期电放保函", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocClientLatters docClientLatters) {
        String clientId = tokenService.getClientId(ServletUtils.getRequest());
        docClientLatters.setClientId(clientId);
        return toAjax(docClientLattersService.insertDocClientLatters(docClientLatters));
    }

    /**
     * 修改客户维护长期电放保函
     */
    @Log(title = "客户维护长期电放保函", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocClientLatters docClientLatters) {
        return toAjax(docClientLattersService.updateDocClientLatters(docClientLatters));
    }

    /**
     * 删除客户维护长期电放保函
     */
    @Log(title = "客户维护长期电放保函", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(docClientLattersService.deleteDocClientLattersByIds(ids));
    }

    @ApiOperation(value = "多文件上传", notes = "多文件上传 ")
    @PostMapping("/uploadDoc")
    public AjaxResult upload(@RequestParam("file") MultipartFile[] file) throws IOException {
        List<ProblemFileVo> fileVos = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            ProblemFileVo vo = new ProblemFileVo();
            vo.setUrl(FileUploadUtils.upload(SzhblConfig.getClientLatterPath(), file[i]));
            vo.setName(file[i].getOriginalFilename());
            fileVos.add(vo);
        }
        return AjaxResult.success(fileVos);
    }

    @PostMapping("/deleteDoc")
    public AjaxResult deleteDoc(@RequestParam("url") String url) {
        return toAjax(FileUploadUtils.deleteFile(url) ? 1 : 0);
    }

}
