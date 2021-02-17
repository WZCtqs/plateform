package com.szhbl.project.track.controller;

import java.util.List;

import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.order.domain.BusiZyInfo;
import com.szhbl.project.order.domain.vo.ShippingOrder;
import com.szhbl.project.order.service.IBusiShippingorderService;
import com.szhbl.project.order.service.IBusiZyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.szhbl.project.track.domain.TrackTwoLevel;
import com.szhbl.project.track.service.ITrackTwoLevelService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 运踪二级节点Controller
 *
 * @author lzd
 * @date 2020-03-23
 */
@RestController
@RequestMapping("/track/twoLevel")
@Api(tags = "2级节点")
public class TrackTwoLevelController extends BaseController
{
    @Autowired
    private ITrackTwoLevelService trackTwoLevelService;
    @Autowired
    private IBusiShippingorderService busiShippingorderService;
    @Autowired
    private IBusiZyInfoService busiZyInfoService;

    /**
     * 查询运踪二级节点列表
     */
//    @PreAuthorize("@ss.hasPermi('track:twoLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(TrackTwoLevel trackTwoLevel)
    {
        startPage();
        List<TrackTwoLevel> list = trackTwoLevelService.selectTrackTwoLevelList(trackTwoLevel);
        return getDataTable(list);
    }

    /**
     * 导出运踪二级节点列表
     */
    @PreAuthorize("@ss.hasPermi('yunFu:twoLevel:export')")
    @Log(title = "运踪二级节点", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TrackTwoLevel trackTwoLevel)
    {
        List<TrackTwoLevel> list = trackTwoLevelService.selectTrackTwoLevelList(trackTwoLevel);
        ExcelUtil<TrackTwoLevel> util = new ExcelUtil<TrackTwoLevel>(TrackTwoLevel.class);
        return util.exportExcel(list, "twoLevel");
    }

    /**
     * 获取运踪二级节点详细信息
     */
//    @PreAuthorize("@ss.hasPermi('track:twoLevel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(trackTwoLevelService.selectTrackTwoLevelById(id));
    }

    /**
     * 根据一级节点id获取二级节点
     */
//    @PreAuthorize("@ss.hasPermi('track:twoLevel:query')")
    @GetMapping(value = "detailByOneId")
    @ApiOperation("根据一级节点id获取二级节点，传参数名是oneId，值是一级节点id的值")
    public AjaxResult detailByOneId(Long oneId,String boxNum)
    {
        TrackTwoLevel trackTwoLevel=new TrackTwoLevel();
        trackTwoLevel.setOneId(oneId);
        List<TrackTwoLevel> list=trackTwoLevelService.selectTrackTwoLevelList(trackTwoLevel);
        if(list!=null&&list.size()>0){
            String orderId=list.get(0).getOrderId();
            ShippingOrder shippingOrder=busiShippingorderService.selectBusiShippingorderById(orderId);
            //中亚去程只显示到班列运踪
            if("0".equals(shippingOrder.getClassEastandwest())&&"2".equals(shippingOrder.getLineTypeid())){
                //获取计划班列号
                BusiZyInfo zyInfo=busiZyInfoService.selectZyInfoByOrder(orderId,boxNum);
                for(int i=0;i<list.size();i++){
                    if("已进站".equals(list.get(i).getNameZh())){
                        list.get(i).setRemark(StringUtils.isNotEmpty(list.get(i).getRemark())?list.get(2).getRemark():""+"计划"+zyInfo.getClasszyNo());
                    }
                }
            }
        }
        return AjaxResult.success(list);
    }

    /**
     * 根据orderId获取所有二级节点，传参数orderId
     */
//    @PreAuthorize("@ss.hasPermi('track:twoLevel:query')")
    @GetMapping(value = "detailByOrderId")
    @ApiOperation("根据orderId获取所有二级节点，传参数orderId")
    public AjaxResult detailByOrderId(String orderId,String boxNum)
    {
        return AjaxResult.success(trackTwoLevelService.detailByOrderId(orderId, boxNum));
    }

    /**
     * 新增运踪二级节点
     */
    @PreAuthorize("@ss.hasPermi('yunFu:twoLevel:add')")
    @Log(title = "运踪二级节点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TrackTwoLevel trackTwoLevel)
    {
        return toAjax(trackTwoLevelService.insertTrackTwoLevel(trackTwoLevel));
    }

    /**
     * 修改运踪二级节点
     */
    @PreAuthorize("@ss.hasPermi('yunFu:twoLevel:edit')")
    @Log(title = "运踪二级节点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackTwoLevel trackTwoLevel)
    {
        return toAjax(trackTwoLevelService.updateTrackTwoLevel(trackTwoLevel));
    }

    /**
     * 删除运踪二级节点
     */
    @PreAuthorize("@ss.hasPermi('yunFu:twoLevel:remove')")
    @Log(title = "运踪二级节点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(trackTwoLevelService.deleteTrackTwoLevelByIds(ids));
    }
}
