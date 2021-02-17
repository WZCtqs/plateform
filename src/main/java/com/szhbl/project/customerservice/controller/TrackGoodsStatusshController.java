package com.szhbl.project.customerservice.controller;

import java.util.List;
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
import com.szhbl.project.customerservice.domain.TrackGoodsStatussh;
import com.szhbl.project.customerservice.service.ITrackGoodsStatusshService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 运踪_货物状态--以舱位号为单位，标记是否发车Controller
 *
 * @author b
 * @date 2020-03-28
 */
@RestController
@RequestMapping("/customerservice/status")
public class TrackGoodsStatusshController extends BaseController
{
    @Autowired
    private ITrackGoodsStatusshService trackGoodsStatusshService;

    /**
     * 查询运踪_货物状态--以舱位号为单位，标记是否发车列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:status:list')")
    @GetMapping("/list")
    public TableDataInfo list(TrackGoodsStatussh trackGoodsStatussh)
    {
        startPage();
        List<TrackGoodsStatussh> list = trackGoodsStatusshService.selectTrackGoodsStatusshList(trackGoodsStatussh);
        return getDataTable(list);
    }

    /**
     * 导出运踪_货物状态--以舱位号为单位，标记是否发车列表
     */
    @PreAuthorize("@ss.hasPermi('customerservice:status:export')")
    @Log(title = "运踪_货物状态--以舱位号为单位，标记是否发车", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TrackGoodsStatussh trackGoodsStatussh)
    {
        List<TrackGoodsStatussh> list = trackGoodsStatusshService.selectTrackGoodsStatusshList(trackGoodsStatussh);
        ExcelUtil<TrackGoodsStatussh> util = new ExcelUtil<TrackGoodsStatussh>(TrackGoodsStatussh.class);
        return util.exportExcel(list, "status");
    }

    /**
     * 获取运踪_货物状态--以舱位号为单位，标记是否发车详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:status:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(trackGoodsStatusshService.selectTrackGoodsStatusshById(id));
    }

    /**
     * 新增运踪_货物状态--以舱位号为单位，标记是否发车
     */
    @PreAuthorize("@ss.hasPermi('customerservice:status:add')")
    @Log(title = "运踪_货物状态--以舱位号为单位，标记是否发车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TrackGoodsStatussh trackGoodsStatussh)
    {
        return toAjax(trackGoodsStatusshService.insertTrackGoodsStatussh(trackGoodsStatussh));
    }

    /**
     * 修改运踪_货物状态--以舱位号为单位，标记是否发车
     */
    @PreAuthorize("@ss.hasPermi('customerservice:status:edit')")
    @Log(title = "运踪_货物状态--以舱位号为单位，标记是否发车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TrackGoodsStatussh trackGoodsStatussh)
    {
        return toAjax(trackGoodsStatusshService.updateTrackGoodsStatussh(trackGoodsStatussh));
    }

    /**
     * 删除运踪_货物状态--以舱位号为单位，标记是否发车
     */
    @PreAuthorize("@ss.hasPermi('customerservice:status:remove')")
    @Log(title = "运踪_货物状态--以舱位号为单位，标记是否发车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(trackGoodsStatusshService.deleteTrackGoodsStatusshByIds(ids));
    }

    @GetMapping(value = "/boxNums")
    public AjaxResult boxNums(String orderId)
    {
        List<String> list = trackGoodsStatusshService.selectBoxNum(orderId);
        /*if (orderId==null){
            return AjaxResult.error("orderId==null");
        }
        List<String> list = new ArrayList<>();
        list.add("xianghao01");
        list.add("xianghao02");
        list.add("xianghao03");
        list.add("xianghao04");
        list.add("xianghao05");*/
        return AjaxResult.success(list);
    }

    @GetMapping("/findClassDate")
    public List<String> findClassDate(String actualClassId){
        return trackGoodsStatusshService.findClassDate(actualClassId);
    }
}
