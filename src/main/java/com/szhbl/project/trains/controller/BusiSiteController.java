package com.szhbl.project.trains.controller;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.trains.domain.BusiSite;
import com.szhbl.project.trains.service.IBusiSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 站点Controller
 *
 * @author szhbl
 * @date 2020-01-10
 */
@RestController
@RequestMapping("/trains/site")
@Api(tags = "站点管理")
public class BusiSiteController extends BaseController {
    @Autowired
    private IBusiSiteService busiSiteService;


    @GetMapping("/getBusiSiteByName")
    public String getBusiSiteByName(String language, String name) {
        return busiSiteService.getBusiSiteByName(name, language);
    }

    /**
     * 查询站点列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:site:list')")
    @GetMapping("/siteList")
    @ApiOperation("站点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "第几页", name = "pageNum", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(value = "每页显示记录数", name = "pageSize", paramType = "query", dataType = "int", required = true)
    })
    public TableDataInfo list(BusiSite busiSite)
    {
        startPage();
        List<BusiSite> list = busiSiteService.selectBusiSiteList(busiSite);
        return getDataTable(list);
    }

    /**
     * 根据选中口岸查询站点列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:site:list')")
    @GetMapping("/listByPort")
    @ApiOperation("根据选中口岸查询站点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "线路类别",name = "lineTypeid",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(value = "选中口岸",name = "portCode",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo selectlistByPort(BusiSite busiSite){
        String portCode = (busiSite.getPortCode()).trim();
        List<BusiSite> list = null;
        if(StringUtils.isEmpty(portCode)){
            String lineTypeid = busiSite.getLineTypeid();
            if(StringUtils.isNotEmpty(lineTypeid)){
                list = busiSiteService.selectBusiSiteListByLineType(lineTypeid);
            }
        }else{
            String[] otherPortArr =new String[5] ;
            if(portCode.equals("86_833418")){  //阿拉山口
                otherPortArr= new String[]{"86_012600", "86_021400", "86_835221", "249", "007_256"};
            }
            if(portCode.equals("86_012600")){  //二连浩特
                otherPortArr= new String[]{"86_833418","86_021400", "86_835221", "249", "007_256"};
            }
            if(portCode.equals("86_021400")){  //满洲里
                otherPortArr= new String[]{"86_833418", "86_012600","86_835221", "249", "007_256"};
            }
            if(portCode.equals("86_835221")){  //霍尔果斯
                otherPortArr= new String[]{"86_833418", "86_012600", "86_021400","249", "007_256"};
            }
            if(portCode.equals("249")){  //绥芬河
                otherPortArr= new String[]{"86_833418", "86_012600", "86_021400", "86_835221","007_256"};
            }
            if(portCode.equals("007_256")){  //凭祥
                otherPortArr= new String[]{"86_833418", "86_012600", "86_021400", "86_835221", "249",};
            }
            busiSite.setOtherPortCode(otherPortArr);
            list = busiSiteService.selectlistByPort(busiSite);
        }
        return getDataTable(list);
    }

    /**
     * 查询线路类别下站点列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:site:list')")
    @GetMapping("/listByLineType")
    @ApiOperation("线路类别下站点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "线路类别 0中欧 2中亚 3中越 4中俄",name = "lineTypeid",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo listByLineType(String lineTypeid)
    {
        List<BusiSite> list = busiSiteService.selectBusiSiteListByLineType(lineTypeid);
        return getDataTable(list);
    }

    /**
     * 查询线路下站点列表
     */
//    @PreAuthorize("@ss.hasPermi('trains:site:list')")
    @GetMapping("/listByLine")
    @ApiOperation("线路下站点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "线路id",name = "id",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo listByLine(String id)
    {
        List<BusiSite> list = busiSiteService.selectBusiSiteListByLine(id);
        return getDataTable(list);
    }

    /**
     * 查询指定站点
     */
//    @PreAuthorize("@ss.hasPermi('trains:site:list')")
    @GetMapping("/listBySiteCodes")
    @ApiOperation("查询指定站点")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "站点code",name = "siteCode",paramType = "query",dataType = "String",required = true)
    })
    public TableDataInfo listBySiteCodes(String siteCodes){
        List<BusiSite> list = busiSiteService.listBySiteCodes(siteCodes);
        return getDataTable(list);
    }

    /**
     * 查询上下货站点
     */
    @GetMapping("/siteListCollect")
    @ApiOperation("查询上下货站点")
    public TableDataInfo siteListCollect(){
        List<BusiSite> list = busiSiteService.siteListCollect();
        return getDataTable(list);
    }


    /**
     * 获取站点详细信息
     */
//    @PreAuthorize("@ss.hasPermi('trains:site :query')")
    @GetMapping("/getInfo")
    @ApiOperation("站点详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "站点id",name = "id",paramType = "query",dataType = "Long",required = true)
    })
    public AjaxResult getInfo(Long id)
    {
        return AjaxResult.success(busiSiteService.selectBusiSiteById(id));
    }

    /**
     * 新增站点
     */
    @Log(title = "站点", businessType = BusinessType.INSERT)
    @PostMapping("/siteAdd")
    @ApiOperation("站点新增")
    public AjaxResult add(@RequestBody BusiSite busiSite)
    {
        return toAjax(busiSiteService.insertBusiSite(busiSite));
    }

    /**
     * 修改站点
     */
    @Log(title = "站点", businessType = BusinessType.UPDATE)
    @PutMapping("/siteEdit")
    @ApiOperation("站点编辑")
    public AjaxResult edit(@RequestBody BusiSite busiSite)
    {
        return toAjax(busiSiteService.updateBusiSite(busiSite));
    }

    /**
     * 修改站点(平台端调用)
     */
    @PreAuthorize("@ss.hasPermi('trains:site:edit')")
    @Log(title = "站点", businessType = BusinessType.UPDATE)
    @PutMapping("/sitePEdit")
    @ApiOperation("站点编辑")
    public AjaxResult pedit(@RequestBody BusiSite busiSite)
    {
        return toAjax(busiSiteService.updateBusiSite(busiSite));
    }

    /**
     * 删除站点
     */
    @Log(title = "站点", businessType = BusinessType.DELETE)
    @GetMapping("/siteDel")
    @ApiOperation("站点删除")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "站点编号",name = "code",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult remove(String code)
    {
        return toAjax(busiSiteService.deleteBusiSiteById(code));
    }

    /**
     * 站点状态修改 0禁用 1启用
     */
    @PreAuthorize("@ss.hasPermi('trains:site:edit')")
    @Log(title = "站点状态编辑", businessType = BusinessType.UPDATE)
    @ApiOperation("站点状态编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "站点id"),
            @ApiImplicitParam(name = "state",value = "状态：0禁用 1启用")
    })
    @GetMapping("/changeStatus")
    public AjaxResult changeStatus(Long id,String state){
        return toAjax(busiSiteService.updateSiteStatus(id,state));
    }

    /**
     * 导出站点列表
     */
    @PreAuthorize("@ss.hasPermi('trains:site:export')")
    @Log(title = "站点", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiSite busiSite)
    {
        List<BusiSite> list = busiSiteService.selectBusiSiteList(busiSite);
        ExcelUtil<BusiSite> util = new ExcelUtil<BusiSite>(BusiSite.class);
        return util.exportExcel(list, "site");
    }
}
