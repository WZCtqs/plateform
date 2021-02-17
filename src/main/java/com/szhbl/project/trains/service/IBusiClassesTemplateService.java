package com.szhbl.project.trains.service;

import com.szhbl.project.trains.domain.BusiClassesTemplate;
import java.util.List;

/**
 * 班列模板Service接口
 * 
 * @author szhbl
 * @date 2020-01-10
 */
public interface IBusiClassesTemplateService 
{
    /**
     * 查询班列模板
     * 
     * @param classTId 班列模板ID
     * @return 班列模板
     */
    public BusiClassesTemplate selectBusiClassesTemplateById(String classTId);

    /**
     * 查询班列模板列表
     * 
     * @param busiClassesTemplate 班列模板
     * @return 班列模板集合
     */
    public List<BusiClassesTemplate> selectBusiClassesTemplateList(BusiClassesTemplate busiClassesTemplate);

    /**
     * 新增班列模板
     * 
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    public int insertBusiClassesTemplate(BusiClassesTemplate busiClassesTemplate);

    /**
     * 修改班列模板
     * 
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    public int updateBusiClassesTemplate(BusiClassesTemplate busiClassesTemplate);

    /**
     * 批量删除班列模板
     * 
     * @param classTIds 需要删除的班列模板ID
     * @return 结果
     */
    public int deleteBusiClassesTemplateByIds(String[] classTIds);

    /**
     * 删除班列模板信息
     * 
     * @param classTId 班列模板ID
     * @return 结果
     */
    public int deleteBusiClassesTemplateById(String classTId);

    /**
     * 校验班列号是否唯一
     *
     * @param classTNumber 班列号
     * @return 结果
     */
    public String checkNumberUnique(String classTNumber);

    /**
     * 校验模板中文名是否唯一
     *
     * @param classTBlocktrainCn 班列模板名
     * @return 结果
     */
    public String checkTrainCnUnique(String classTBlocktrainCn);

    /**
     * 校验模板英文名是否唯一
     *
     * @param classTBlocktrainEn 班列模板名
     * @return 结果
     */
    public String checkTrainEnUnique(String classTBlocktrainEn);

    /**
     * 校验班列号是否唯一(更新)
     *
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    public String checkNumberUniqueUpd(BusiClassesTemplate busiClassesTemplate);

    /**
     * 校验模板中文名是否唯一(更新)
     *
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    public String checkTrainCnUniqueUpd(BusiClassesTemplate busiClassesTemplate);

    /**
     * 校验模板英文名是否唯一(更新)
     *
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    public String checkTrainEnUniqueUpd(BusiClassesTemplate busiClassesTemplate);

    /**
     * 查看是否有线路下模板
     *
     * @param lineId 线路id
     * @return 结果
     */
    public boolean hasChildByLineId(Long lineId);
}
