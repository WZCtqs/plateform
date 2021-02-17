package com.szhbl.project.track.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.szhbl.common.utils.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.track.domain.TrackAbroad;
import com.szhbl.project.track.service.ITrackAbroadService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;

import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 运踪_中亚境外Controller
 *
 * @author lzd
 * @date 2020-03-26
 */
@Slf4j
@RestController
@RequestMapping("/track/abroad")
@Api(tags = "中亚境外")
public class TrackAbroadController extends BaseController
{
    @Autowired
    private ITrackAbroadService trackAbroadService;

    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 查询运踪_中亚境外列表
     * */
//    @PreAuthorize("@ss.hasPermi('yunFu:abroad:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询中亚境外列表")
    public TableDataInfo list(TrackAbroad trackAbroad)
    {
        startPage();
        List<TrackAbroad> list = trackAbroadService.selectTrackAbroadList(trackAbroad);
        return getDataTable(list);
    }


    /**
     * 导入运踪_中亚境外列表
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abroad:import')")
    @Log(title = "运踪_中亚境外列表导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @ApiOperation(value = "中亚境外列表导入")
    public AjaxResult importData(@RequestParam MultipartFile file)throws Exception {
        if(file.isEmpty()){
            return AjaxResult.error("上传文件为空，请重新上传！");
        }
        return trackAbroadService.importData(file);
    }


    /**
     * 中亚境外运踪模板下载
     * HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls
     * XSSFWorkbook:是操作Excel2007的版本扩展名是.xlsx
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abroad:download')")
    @GetMapping("/download")
    @ApiOperation("下载模板")
    public void downloadModel(HttpServletResponse response)  {
        ServletOutputStream out = null;
        try {
            InputStream fis = FileUtils.getResourcesFileInputStream("templates/中亚境外运踪.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = java.net.URLEncoder.encode("中亚境外运踪", "UTF-8");
            //fileName=new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            log.error("中亚境外下载模板失败：{}",e.toString(),e.getStackTrace());
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
                log.error("中亚境外关闭文件输出流失败：{}",e.toString(),e.getStackTrace());
            }
        }
    }
    /**
     * 导出运踪_中亚境外列表
     */
  /*  @PreAuthorize("@ss.hasPermi('track:abroad:export')")
    @Log(title = "运踪_中亚境外", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TrackAbroad trackAbroad)
    {
        List<TrackAbroad> list = trackAbroadService.selectTrackAbroadList(trackAbroad);
        ExcelUtil<TrackAbroad> util = new ExcelUtil<TrackAbroad>(TrackAbroad.class);
        return util.exportExcel(list, "abroad");
    }*/

    /**
     * 获取运踪_中亚境外详细信息
     */
//    @PreAuthorize("@ss.hasPermi('track:abroad:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取详情")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(trackAbroadService.selectTrackAbroadById(id));
    }

    /**
     * 新增运踪_中亚境外
     */
   /* @PreAuthorize("@ss.hasPermi('track:abroad:add')")
    @Log(title = "运踪_中亚境外", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TrackAbroad trackAbroad)
    {
        return toAjax(trackAbroadService.insertTrackAbroad(trackAbroad));
    }*/

    /**
     * 修改运踪_中亚境外
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abroad:edit')")
    @Log(title = "运踪_中亚境外", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "根据id修改详情数据")
    public AjaxResult edit(@RequestBody TrackAbroad trackAbroad)
    {
        return toAjax(trackAbroadService.updateTrackAbroad(trackAbroad));
    }

    /**
     * 删除运踪_中亚境外
     */
    @PreAuthorize("@ss.hasPermi('yunFu:abroad:remove')")
    @Log(title = "运踪_中亚境外", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value = "批量删除详情数据")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(trackAbroadService.deleteTrackAbroadByIds(ids));
    }
}
