package com.szhbl.project.cabinarrangement.controller;

import java.util.ArrayList;
import java.util.List;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.cabinarrangement.mapper.BusiShippingorderGoodsPcMapper;
import com.szhbl.project.cabinarrangement.mapper.BusiShippingorderPcMapper;
import com.szhbl.project.cabinarrangement.vo.Box;
import com.szhbl.project.cabinarrangement.vo.PaiCangDetail;
import com.szhbl.project.cabinarrangement.vo.SmallBox;
import com.szhbl.project.cabinarrangement.vo.px;
import com.szhbl.project.track.mapper.TrackRunTimeCensusMapper;
import com.szhbl.project.trains.mapper.BusiSiteMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.szhbl.project.cabinarrangement.domain.BusiClassesPc;
import com.szhbl.project.cabinarrangement.service.IBusiClassesPcService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 排舱管理Controller
 * 
 * @author dps
 * @date 2020-01-14
 */
@RestController
@RequestMapping("/cabinarrangement/classes")
public class BusiClassesPcController extends BaseController
{
    @Autowired
    private IBusiClassesPcService busiClassesService;
    @Autowired
    private BusiShippingorderGoodsPcMapper busiShippingorderGoodsMapper;
    @Autowired
    private BusiShippingorderPcMapper busiShippingorderPcMapper;
    @Autowired
    private BusiSiteMapper busiSiteMapper;
    @Autowired
    private TrackRunTimeCensusMapper trackRunTimeCensusMapper;


    /**
     * 查询排舱管理列表
     */
   // @PreAuthorize("@ss.hasPermi('cabinarrangement:classes:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiClassesPc busiClassesPc)
    {
        String lcl=busiClassesPc.getReceiveSiteLcl();
        String ful=busiClassesPc.getReceiveSiteFull();
        if(StringUtils.isNotEmpty(lcl)){
            busiClassesPc.setReceiveSiteLcl(busiSiteMapper.getCodeByName(busiClassesPc.getReceiveSiteLcl()));
        }
        if(StringUtils.isNotEmpty(ful)){
            busiClassesPc.setReceiveSiteFull(busiSiteMapper.getCodeByName(busiClassesPc.getReceiveSiteFull()));
        }
        if((StringUtils.isNotEmpty(lcl)&&StringUtils.isEmpty(busiClassesPc.getReceiveSiteLcl()))||
                (StringUtils.isNotEmpty(ful)&&StringUtils.isEmpty(busiClassesPc.getReceiveSiteFull()))){
            return getDataTable(new ArrayList<>());
        }
        startPage();
        List<BusiClassesPc> list = busiClassesService.selectBusiClassesList(busiClassesPc);
        System.out.println(list.size());
        for (BusiClassesPc busiClassesPc1:list){
            if("en".equals(busiClassesPc.getLanguage())){
                busiClassesPc1.setClassStationofdeparture(trackRunTimeCensusMapper.selectBusiSiteVo(busiClassesPc1.getClassStationofdeparture()).getNameEn());
                busiClassesPc1.setClassStationofdestination(trackRunTimeCensusMapper.selectBusiSiteVo(busiClassesPc1.getClassStationofdestination()).getNameEn());
            }else{
                busiClassesPc1.setClassStationofdeparture(busiSiteMapper.selectName(busiClassesPc1.getClassStationofdeparture()));
                busiClassesPc1.setClassStationofdestination(busiSiteMapper.selectName(busiClassesPc1.getClassStationofdestination()));
            }
            //判断整柜是否可定 1满 0未满
            //仓位总数判断
             if (busiClassesPc1.getClassSpacenumber()==0){
                 busiClassesPc1.setZxState("1");
             }else {
                 Integer zg = busiShippingorderPcMapper.zgCount(busiClassesPc1.getClassId());
                 //如果此班列还没订单信息查出来可能为null
                 if (zg==null){
                     zg=0;
                 }
                //舱位总数和整柜数比较
                 if(busiClassesPc1.getClassSpacenumber()<=zg){
                     busiClassesPc1.setZxState("1");
                 }else {
                     //整柜舱位数和整柜数比较
                     if (busiClassesPc1.getZxcnt()<=zg){
                         busiClassesPc1.setZxState("1");
                     }else {
                         busiClassesPc1.setZxState("0");
                     }
                 }
             }
             //判断拼箱是否可定
             if ("0".equals(busiClassesPc1.getPxstate())){
                px px = busiShippingorderGoodsMapper.pxCount(busiClassesPc1.getClassId());
                if (px!=null){
                   if (px.getKg()>=busiClassesPc1.getPxkgs()||px.getVal()>=busiClassesPc1.getPxcnt()){
                       busiClassesPc1.setPxstate("1");
                   }
                }
             }
        }
        return getDataTable(list);
    }

    /**
     * 导出排舱管理列表
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:classes:export')")
    @Log(title = "排舱管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BusiClassesPc busiClassesPc)
    {
        List<BusiClassesPc> list = busiClassesService.selectBusiClassesList(busiClassesPc);
        ExcelUtil<BusiClassesPc> util = new ExcelUtil<BusiClassesPc>(BusiClassesPc.class);
        return util.exportExcel(list, "classes");
    }

    /**
     * 获取排舱管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:classes:see')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") String classId)
    {
        List<PaiCangDetail> list = new ArrayList<>();
        String westAndEast = busiShippingorderPcMapper.getClassesFlag(classId);
        if(StringUtils.isNotEmpty(westAndEast)){
            List<String> stations = busiShippingorderPcMapper.selectStation(classId,westAndEast);
            if (stations.size()>0){
                for (String name:stations){
                    List<Box> list1 = busiShippingorderPcMapper.selectZg(classId,name,westAndEast);
                    px px = busiShippingorderGoodsMapper.selectPx(classId,name,westAndEast);
                    PaiCangDetail paiCangDetail = new PaiCangDetail();
                    if (px==null){
                        paiCangDetail.setKg("0");
                        paiCangDetail.setVal("0");
                    }else {
                        paiCangDetail.setKg(String.valueOf(px.getKg()));
                        paiCangDetail.setVal(String.valueOf(px.getVal()));
                    }
                    paiCangDetail.setList(list1);
                    paiCangDetail.setStation(name);
                    list.add(paiCangDetail);
                }
                return AjaxResult.success(list);
            }else {
                return AjaxResult.success(list);
            }
        }else{
            return AjaxResult.success(list);
        }
    }

    /**
     * 新增排舱管理
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:classes:add')")
    @Log(title = "排舱管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiClassesPc busiClassesPc)
    {
        return toAjax(busiClassesService.insertBusiClasses(busiClassesPc));
    }

    /**
     * 修改排舱管理
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:classes:edit')")
    @Log(title = "排舱管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiClassesPc busiClassesPc)
    {
        return toAjax(busiClassesService.updateBusiClasses(busiClassesPc));
    }

    /**
     * 删除排舱管理
     */
    @PreAuthorize("@ss.hasPermi('cabinarrangement:classes:remove')")
    @Log(title = "排舱管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable Long[] classIds)
    {
        return toAjax(busiClassesService.deleteBusiClassesByIds(classIds));
    }

    @GetMapping("/smallBox")
    public AjaxResult smallBox(String classId)
    {
        List<SmallBox> list = busiShippingorderPcMapper.smallBox(classId);
        return AjaxResult.success(list);
    }
}
