package com.szhbl.project.customerservice.controller;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.backclient.util.upload;
import com.szhbl.project.customerservice.domain.CustomerServiceFile;
import com.szhbl.project.customerservice.domain.CustomerServiceMessage;
import com.szhbl.project.customerservice.mapper.CustomerServiceMessageMapper;
import com.szhbl.project.customerservice.service.ICustomerServiceFileService;
import com.szhbl.project.customerservice.vo.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * 售后文件Controller
 *
 * @author b
 * @date 2020-04-10
 */
@Slf4j
@RestController
@RequestMapping("/customerservice/serviceFile")
public class CustomerServiceFileController extends BaseController
{
    @Autowired
    private ICustomerServiceFileService customerServiceFileService;
    @Autowired
    private CustomerServiceMessageMapper customerServiceMessageMapper;

    /**
     * 查询售后文件列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerServiceFile customerServiceFile)
    {
        startPage();
     /*   LoginUser loginUser = SecurityUtils.getLoginUser();
        if ("1".equals(loginUser.getUser().getReferenceType())){
            customerServiceFile.setClientTjrId(loginUser.getUser().getTjrId());
        }*/
        customerServiceFile.setDeptCode(SecurityUtils.getLoginUser().getUser().getDeptCode());
        customerServiceFile.setUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        List<CustomerServiceFile> list = customerServiceFileService.selectCustomerServiceFileList(customerServiceFile);
        return getDataTable(list);
    }

    /**
     * 导出售后文件列表
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:export')")
    @Log(title = "售后文件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CustomerServiceFile customerServiceFile)
    {
        List<CustomerServiceFile> list = customerServiceFileService.selectCustomerServiceFileList(customerServiceFile);
        ExcelUtil<CustomerServiceFile> util = new ExcelUtil<CustomerServiceFile>(CustomerServiceFile.class);
        return util.exportExcel(list, "file");
    }

    /**
     * 获取售后文件详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:file:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(customerServiceFileService.selectCustomerServiceFileById(id));
    }

    /**
     * 新增售后文件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:add')")
    @Log(title = "售后文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerServiceFile customerServiceFile)
    {
        List<CustomerServiceMessage> list = customerServiceMessageMapper.selectOrderNum(customerServiceFile.getOrdernumber());
        if (list.size()>0) {
        for (FileVo fileVo : customerServiceFile.getList()) {
            CustomerServiceFile customerServiceFile1 = new CustomerServiceFile();
            customerServiceFile1.setOrdernumber(customerServiceFile.getOrdernumber());
            customerServiceFile1.setType(customerServiceFile.getType());
            customerServiceFile1.setName(fileVo.getName());
            customerServiceFile1.setUrl(fileVo.getUrl());
            customerServiceFile1.setCreateBy(customerServiceFile.getCreateBy());
            customerServiceFile1.setCreateTime(new Date());
            customerServiceFile.setClientTjrId(list.get(0).getClientTjrId());
            customerServiceFileService.insertCustomerServiceFile(customerServiceFile1);
        }
            return toAjax(1);
    }else {
            return AjaxResult.error("舱位号输入有误，不存在！");
        }
    }

    /**
     * 修改售后文件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:edit')")
    @Log(title = "售后文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerServiceFile customerServiceFile)
    {
        return toAjax(customerServiceFileService.updateCustomerServiceFile(customerServiceFile));
    }

    /**
     * 删除售后文件
     */
    @PreAuthorize("@ss.hasPermi('customerservice:file:remove')")
    @Log(title = "售后文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerServiceFileService.deleteCustomerServiceFileByIds(ids));
    }
    /*上传回复文件*/
    @PostMapping("/insertfile")
    public AjaxResult insertfile(HttpServletResponse response, HttpServletRequest request,
                                 @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        String url3 = "";
        String url = "";
        AjaxResult ajax = AjaxResult.success();
        if (file != null) {
            url3 = file.getOriginalFilename();
            url = upload.uploadFile(request, file, "bxt");
        }
        ajax.put("url", "E:/szhbl/upload/bxt/" + url);
        ajax.put("name", url3);
            return ajax;
    }
    @PreAuthorize("@ss.hasPermi('afterSaleManage:serviceFileDownload:download')")
    @GetMapping("/download")
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response,Long id) throws UnsupportedEncodingException {
        /*String filename = "2.jpg";
        String filePath = "F:/test";*/
        CustomerServiceFile customerServiceFile = customerServiceFileService.selectCustomerServiceFileById(id);
        File file = new File(customerServiceFile.getUrl());
        if (file.exists()) { //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(customerServiceFile.getName().getBytes("utf-8"),"ISO8859-1"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("售后文件Controller--download失败：{}",e.toString(),e.getStackTrace());
            }
            // System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error("售后文件Controller关闭流失败：{}",e.toString(),e.getStackTrace());
            }
        }
        return null;
    }
}
