package com.szhbl.project.customerservice.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.customerservice.vo.FileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.customerservice.mapper.ProblemFileMapper;
import com.szhbl.project.customerservice.domain.ProblemFile;
import com.szhbl.project.customerservice.service.IProblemFileService;

/**
 * 问题反馈附件Service业务层处理
 * 
 * @author b
 * @date 2020-04-07
 */
@Service
public class ProblemFileServiceImpl implements IProblemFileService 
{
    @Autowired
    private ProblemFileMapper problemFileMapper;

    /**
     * 查询问题反馈附件
     * 
     * @param fileId 问题反馈附件ID
     * @return 问题反馈附件
     */
    @Override
    public ProblemFile selectProblemFileById(String fileId)
    {
        return problemFileMapper.selectProblemFileById(fileId);
    }

    /**
     * 查询问题反馈附件列表
     * 
     * @param problemFile 问题反馈附件
     * @return 问题反馈附件
     */
    @Override
    public List<ProblemFile> selectProblemFileList(ProblemFile problemFile)
    {
        return problemFileMapper.selectProblemFileList(problemFile);
    }

    /**
     * 新增问题反馈附件
     * 
     * @param problemFile 问题反馈附件
     * @return 结果
     */
    @Override
    public int insertProblemFile(ProblemFile problemFile)
    {
        problemFile.setCreateTime(DateUtils.getNowDate());
        return problemFileMapper.insertProblemFile(problemFile);
    }

    /**
     * 修改问题反馈附件
     * 
     * @param problemFile 问题反馈附件
     * @return 结果
     */
    @Override
    public int updateProblemFile(ProblemFile problemFile)
    {
        problemFile.setUpdateTime(DateUtils.getNowDate());
        return problemFileMapper.updateProblemFile(problemFile);
    }

    /**
     * 批量删除问题反馈附件
     * 
     * @param fileIds 需要删除的问题反馈附件ID
     * @return 结果
     */
    @Override
    public int deleteProblemFileByIds(String[] fileIds)
    {
        return problemFileMapper.deleteProblemFileByIds(fileIds);
    }

    /**
     * 删除问题反馈附件信息
     * 
     * @param fileId 问题反馈附件ID
     * @return 结果
     */
    @Override
    public int deleteProblemFileById(String fileId)
    {
        return problemFileMapper.deleteProblemFileById(fileId);
    }

    @Override
    public List<FileVo> selectFileList(ProblemFile problemFile) {
        return problemFileMapper.selectFileList(problemFile);
    }

    @Override
    public int deleteProblemFileByType(String problemId) {
        return problemFileMapper.deleteProblemFileByType(problemId);
    }
}
