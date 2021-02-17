package com.szhbl.project.backclient.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.backclient.domain.Question;
import com.szhbl.project.backclient.service.IQuestionService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 客户端常见问题Controller
 *
 * @author szhbl
 * @date 2020-01-20
 */
@CrossOrigin
@RestController
@RequestMapping("/backclient/question")
public class QuestionController extends BaseController
{
    @Autowired
    private IQuestionService questionService;

    /**
     * 查询客户端常见问题列表
     */
//    @PreAuthorize("@ss.hasPermi('backclient:question:list')")
    @GetMapping("/list")
    public TableDataInfo list(Question question)
    {
        startPage();
        List<Question> list = questionService.selectQuestionList(question);
        return getDataTable(list);
    }
    //客户端查询问题
    @GetMapping("/listClient")
    public AjaxResult listClient(Question question)
    {
        List<Question> list = questionService.selectQuestionList(question);
        return AjaxResult.success(list);
    }
    /**
     * 导出客户端常见问题列表
     */
    @PreAuthorize("@ss.hasPermi('backclient:question:export')")
    @Log(title = "客户端常见问题", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Question question)
    {
        List<Question> list = questionService.selectQuestionList(question);
        ExcelUtil<Question> util = new ExcelUtil<Question>(Question.class);
        return util.exportExcel(list, "question");
    }

    /**
     * 获取客户端常见问题详细信息
     */
//    @PreAuthorize("@ss.hasPermi('backclient:question:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(questionService.selectQuestionById(id));
    }

    /**
     * 新增客户端常见问题
     */
    @PreAuthorize("@ss.hasPermi('backclient:question:add')")
    @Log(title = "客户端常见问题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Question question)
    {
        return toAjax(questionService.insertQuestion(question));
    }

    /**
     * 修改客户端常见问题
     */
    @PreAuthorize("@ss.hasPermi('backclient:question:edit')")
    @Log(title = "客户端常见问题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Question question)
    {
        return toAjax(questionService.updateQuestion(question));
    }

    /**
     * 删除客户端常见问题
     */
    @PreAuthorize("@ss.hasPermi('backclient:question:remove')")
    @Log(title = "客户端常见问题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questionService.deleteQuestionByIds(ids));
    }
}
