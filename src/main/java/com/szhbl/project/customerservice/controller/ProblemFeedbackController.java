package com.szhbl.project.customerservice.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.project.customerservice.domain.ProblemFile;
import com.szhbl.project.customerservice.service.IProblemFileService;
import com.szhbl.project.customerservice.vo.FileVo;
import com.szhbl.project.customerservice.vo.ProblemVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.szhbl.project.customerservice.domain.ProblemFeedback;
import com.szhbl.project.customerservice.service.IProblemFeedbackService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 问题反馈Controller
 *
 * @author b
 * @date 2020-04-07
 */
@RestController
@RequestMapping("/customerservice/feedback")
public class ProblemFeedbackController extends BaseController
{
    @Autowired
    private IProblemFeedbackService problemFeedbackService;
    @Autowired
    private IProblemFileService problemFileService;

    /**
     * 查询问题反馈列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProblemFeedback problemFeedback)
    {
        startPage();
        problemFeedback.setDeptCode(SecurityUtils.getLoginUser().getUser().getDeptCode());
        problemFeedback.setUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        List<ProblemVo> list = problemFeedbackService.selectProblemList(problemFeedback);
        return getDataTable(list);
    }

    /**
     * 导出问题反馈列表
     */
    @PreAuthorize("@ss.hasPermi('afterSaleManage:feedback:export')")
    @Log(title = "问题反馈", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProblemFeedback problemFeedback)
    {
        List<ProblemVo> list = problemFeedbackService.selectProblemList(problemFeedback);
        String str = "yyy-MM-dd HH:mm:ss";
        String str1 = "yyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        SimpleDateFormat sdf1 = new SimpleDateFormat(str1);
        for (ProblemVo problemVo:list){
            if (problemVo.getSignTime()!=null) {
                problemVo.setSt(sdf1.format(problemVo.getSignTime()));
            }
            if (problemVo.getDoTime()!=null) {
                problemVo.setDt(sdf.format(problemVo.getDoTime()));
            }
            problemVo.setPt(sdf.format(problemVo.getPostTime()));
           if ("0".equals(problemVo.getIsPay())){
               problemVo.setIsPay("是");
           }else {
               problemVo.setIsPay("否");
           }
        }
        ExcelUtil<ProblemVo> util = new ExcelUtil<ProblemVo>(ProblemVo.class);
        return util.exportExcel(list, "feedback");
    }

    /**
     * 获取问题反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('customerservice:feedback:query')")
    @GetMapping(value = "/{problemId}")
    public AjaxResult getInfo(@PathVariable("problemId") String problemId)
    {
        ProblemFeedback problemFeedback = problemFeedbackService.selectProblemFeedbackById(problemId);
        ProblemFile problemFile = new ProblemFile();
        problemFile.setProblemId(problemId);
        problemFile.setType("1");
        List<FileVo> list =  problemFileService.selectFileList(problemFile);
        problemFeedback.setList(list);
        return AjaxResult.success(problemFeedback);
    }

    /**
     * 新增问题反馈
     */
    @PreAuthorize("@ss.hasPermi('customerservice:feedback:add')")
    @Log(title = "问题反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProblemFeedback problemFeedback)
    {
        return toAjax(problemFeedbackService.insertProblemFeedback(problemFeedback));
    }

    /**
     * 修改问题反馈
     */
    @PreAuthorize("@ss.hasPermi('afterSaleManage:feedback:huifu')")
    @Log(title = "问题反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody ProblemFeedback problemFeedback)
    {
        System.out.println(problemFeedback);
        if (problemFeedback.getList().size()>0){
            problemFileService.deleteProblemFileByType(problemFeedback.getProblemId());
            for (FileVo fileVo:problemFeedback.getList()){
                ProblemFile problemFile = new ProblemFile();
                problemFile.setFileId(UUID.randomUUID().toString());
                problemFile.setFileName(fileVo.getName());
                problemFile.setFileUrl(fileVo.getUrl());
                problemFile.setProblemId(problemFeedback.getProblemId());
                problemFile.setType("1");
                problemFileService.insertProblemFile(problemFile);
            }
        }
        return toAjax(problemFeedbackService.updateProblemFeedback(problemFeedback));
    }

    /**
     * 删除问题反馈
     */
    @PreAuthorize("@ss.hasPermi('afterSaleManage:feedback:delete')")
    @Log(title = "问题反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{problemIds}")
    public AjaxResult remove(@PathVariable String[] problemIds)
    {
        return toAjax(problemFeedbackService.deleteProblemFeedbackByIds(problemIds));
    }
}
