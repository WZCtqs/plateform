package com.szhbl.project.trains.service.impl;

import java.util.List;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.trains.mapper.BusiClassesTemplateMapper;
import com.szhbl.project.trains.domain.BusiClassesTemplate;
import com.szhbl.project.trains.service.IBusiClassesTemplateService;

/**
 * 班列模板Service业务层处理
 * 
 * @author szhbl
 * @date 2020-01-10
 */
@Service
public class BusiClassesTemplateServiceImpl implements IBusiClassesTemplateService 
{
    @Autowired
    private BusiClassesTemplateMapper busiClassesTemplateMapper;

    /**
     * 查询班列模板
     * 
     * @param classTId 班列模板ID
     * @return 班列模板
     */
    @Override
    public BusiClassesTemplate selectBusiClassesTemplateById(String classTId)
    {
        return busiClassesTemplateMapper.selectBusiClassesTemplateById(classTId);
    }

    /**
     * 查询班列模板列表
     * 
     * @param busiClassesTemplate 班列模板
     * @return 班列模板
     */
    @Override
    public List<BusiClassesTemplate> selectBusiClassesTemplateList(BusiClassesTemplate busiClassesTemplate)
    {
        return busiClassesTemplateMapper.selectBusiClassesTemplateList(busiClassesTemplate);
    }

    /**
     * 新增班列模板
     * 
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    @Override
    public int insertBusiClassesTemplate(BusiClassesTemplate busiClassesTemplate)
    {
        busiClassesTemplate.setCreatedate(DateUtils.getNowDate());
        busiClassesTemplate.setClassTId(IdUtils.randomUUID());
        return busiClassesTemplateMapper.insertBusiClassesTemplate(busiClassesTemplate);
    }

    /**
     * 修改班列模板
     * 
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    @Override
    public int updateBusiClassesTemplate(BusiClassesTemplate busiClassesTemplate)
    {
        return busiClassesTemplateMapper.updateBusiClassesTemplate(busiClassesTemplate);
    }

    /**
     * 批量删除班列模板
     * 
     * @param classTIds 需要删除的班列模板ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesTemplateByIds(String[] classTIds)
    {
        return busiClassesTemplateMapper.deleteBusiClassesTemplateByIds(classTIds);
    }

    /**
     * 删除班列模板信息
     * 
     * @param classTId 班列模板ID
     * @return 结果
     */
    @Override
    public int deleteBusiClassesTemplateById(String classTId)
    {
        return busiClassesTemplateMapper.deleteBusiClassesTemplateById(classTId);
    }

    /**
     * 校验班列号是否唯一
     *
     * @param classTNumber 班列号
     * @return 结果
     */
    @Override
    public String checkNumberUnique(String classTNumber){
        int count = busiClassesTemplateMapper.checkNumberUnique(classTNumber);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    };

    /**
     * 校验模板中文名是否唯一
     *
     * @param classTBlocktrainCn 班列模板名
     * @return 结果
     */
    @Override
    public String checkTrainCnUnique(String classTBlocktrainCn){
        int count = busiClassesTemplateMapper.checkTrainCnUnique(classTBlocktrainCn);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    };

    /**
     * 校验模板英文名是否唯一
     *
     * @param classTBlocktrainEn 班列模板名
     * @return 结果
     */
    @Override
    public String checkTrainEnUnique(String classTBlocktrainEn){
        int count = busiClassesTemplateMapper.checkTrainEnUnique(classTBlocktrainEn);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    };

    /**
     * 校验班列号是否唯一(更新)
     *
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    @Override
    public String checkNumberUniqueUpd(BusiClassesTemplate busiClassesTemplate)
    {
        String classTId = busiClassesTemplate.getClassTId();
        BusiClassesTemplate info = busiClassesTemplateMapper.checkNumberUniqueUpd(busiClassesTemplate.getClassTNumber());
        if(info != null){
            if (classTId.equals(info.getClassTId())){
                return UserConstants.UNIQUE;
            }else{
                return UserConstants.NOT_UNIQUE;
            }
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验模板中文名是否唯一(更新)
     *
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    @Override
    public String checkTrainCnUniqueUpd(BusiClassesTemplate busiClassesTemplate)
    {
        String classTId = busiClassesTemplate.getClassTId();
        BusiClassesTemplate info = busiClassesTemplateMapper.checkTrainCnUniqueUpd(busiClassesTemplate.getClassTBlocktrainCn());
        if(info != null){
            if (classTId.equals(info.getClassTId())){
                return UserConstants.UNIQUE;
            }else{
                return UserConstants.NOT_UNIQUE;
            }
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验模板英文名是否唯一(更新)
     *
     * @param busiClassesTemplate 班列模板
     * @return 结果
     */
    @Override
    public String checkTrainEnUniqueUpd(BusiClassesTemplate busiClassesTemplate)
    {
        String classTId = busiClassesTemplate.getClassTId();
        BusiClassesTemplate info = busiClassesTemplateMapper.checkTrainEnUniqueUpd(busiClassesTemplate.getClassTBlocktrainEn());
        if(info != null){
            if (classTId.equals(info.getClassTId())){
                return UserConstants.UNIQUE;
            }else{
                return UserConstants.NOT_UNIQUE;
            }
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 查看是否有线路下模板
     *
     * @param lineId 线路id
     * @return 结果
     */
    @Override
    public boolean hasChildByLineId(Long lineId)
    {
        int result = busiClassesTemplateMapper.hasChildByLineId(lineId);
        return result > 0 ? true : false;
    }
}
