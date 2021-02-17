package com.szhbl.project.customerservice.controller;

import java.io.*;
import java.net.*;
import java.util.*;

import com.szhbl.common.utils.file.FileUtils;
import com.szhbl.framework.config.SzhblConfig;
import com.szhbl.project.backclient.util.upload;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.customerservice.domain.ProblemFile;
import com.szhbl.project.customerservice.service.IProblemFileService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 问题反馈附件Controller
 *
 * @author b
 * @date 2020-04-07
 */
@Slf4j
@RestController
@RequestMapping("/customerservice/file")
public class ProblemFileController extends BaseController
{
    @Autowired
    private IProblemFileService problemFileService;

    /**
     * 查询问题反馈附件列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProblemFile problemFile)
    {
        List<ProblemFile> list = problemFileService.selectProblemFileList(problemFile);
        return getDataTable(list);
    }

    /**
     * 导出问题反馈附件列表
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:export')")
    @Log(title = "问题反馈附件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProblemFile problemFile)
    {
        List<ProblemFile> list = problemFileService.selectProblemFileList(problemFile);
        ExcelUtil<ProblemFile> util = new ExcelUtil<ProblemFile>(ProblemFile.class);
        return util.exportExcel(list, "file");
    }

    /**
     * 获取问题反馈附件详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:file:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId)
    {
        return AjaxResult.success(problemFileService.selectProblemFileById(fileId));
    }

    /**
     * 新增问题反馈附件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:add')")
    @Log(title = "问题反馈附件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProblemFile problemFile)
    {
        return toAjax(problemFileService.insertProblemFile(problemFile));
    }

    /**
     * 修改问题反馈附件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:edit')")
    @Log(title = "问题反馈附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProblemFile problemFile)
    {
        return toAjax(problemFileService.updateProblemFile(problemFile));
    }

    /**
     * 删除问题反馈附件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:remove')")
    @Log(title = "问题反馈附件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable String[] fileIds)
    {
        return toAjax(problemFileService.deleteProblemFileByIds(fileIds));
    }

    /*上传回复文件*/
    @PostMapping("/insertfile")
    public AjaxResult insertfile(HttpServletResponse response, HttpServletRequest request,
                                 @RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
        String url3 = "";
        String url = "";
        AjaxResult ajax = AjaxResult.success();
        if (file != null) {
            url3 = file.getOriginalFilename();
            url = upload.uploadFile(request, file, "bxt");
        }
        try {
            InetAddress addr = InetAddress.getLocalHost();
           /* ProblemFile problemFile = new ProblemFile();
            problemFile.setFileId(UUID.randomUUID().toString());
            problemFile.setFileName(url3);
            problemFile.setFileUrl("http://"+addr.getHostAddress()+":8080/bxt/"+url);
            problemFile.setProblemId(problemId);
            problemFile.setType("1");
            problemFileService.insertProblemFile(problemFile);*/
            ajax.put("fileUrl", SzhblConfig.getServerHost()+":8083/bxt/"+url);
            ajax.put("fileName", url3);
        } catch (UnknownHostException e) {
            log.error("问题反馈附件Controller上传回复文件失败：{}",e.toString(),e.getStackTrace());
        }
        return ajax;
    }
    @PreAuthorize("@ss.hasPermi('afterSaleManage:afterSaleList:download')")
    @GetMapping("/download")
    public void downloadFile(HttpServletRequest request,
                               HttpServletResponse response,String type) throws IOException {
        String name = "";
        if ("0".equals(type)){
            name = "售后信息导入模板.xlsx";
        }else if ("1".equals(type)){
            name = "赔款跟踪信息导入模板.xlsx";
        }
        ServletOutputStream out = null;
        try {
            InputStream fis = FileUtils.getResourcesFileInputStream("model/"+name);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = java.net.URLEncoder.encode(name, "UTF-8");
            //fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            log.error("问题反馈附件Controller--downloadFile失败：{}",e.toString(),e.getStackTrace());
        } finally {
            //关闭文件输出流
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                log.error("关闭文件输出流失败：{}",e.toString(),e.getStackTrace());
            }
        }
    }

}
