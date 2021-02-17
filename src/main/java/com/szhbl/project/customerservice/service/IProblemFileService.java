package com.szhbl.project.customerservice.service;

import com.szhbl.project.customerservice.domain.ProblemFile;
import com.szhbl.project.customerservice.vo.FileVo;

import java.util.List;

/**
 * 问题反馈附件Service接口
 * 
 * @author b
 * @date 2020-04-07
 */
public interface IProblemFileService 
{
    /**
     * 查询问题反馈附件
     * 
     * @param fileId 问题反馈附件ID
     * @return 问题反馈附件
     */
    public ProblemFile selectProblemFileById(String fileId);

    /**
     * 查询问题反馈附件列表
     * 
     * @param problemFile 问题反馈附件
     * @return 问题反馈附件集合
     */
    public List<ProblemFile> selectProblemFileList(ProblemFile problemFile);

    /**
     * 新增问题反馈附件
     * 
     * @param problemFile 问题反馈附件
     * @return 结果
     */
    public int insertProblemFile(ProblemFile problemFile);

    /**
     * 修改问题反馈附件
     * 
     * @param problemFile 问题反馈附件
     * @return 结果
     */
    public int updateProblemFile(ProblemFile problemFile);

    /**
     * 批量删除问题反馈附件
     * 
     * @param fileIds 需要删除的问题反馈附件ID
     * @return 结果
     */
    public int deleteProblemFileByIds(String[] fileIds);

    /**
     * 删除问题反馈附件信息
     * 
     * @param fileId 问题反馈附件ID
     * @return 结果
     */
    public int deleteProblemFileById(String fileId);

    List<FileVo> selectFileList(ProblemFile problemFile);

    int deleteProblemFileByType(String problemId);
}
