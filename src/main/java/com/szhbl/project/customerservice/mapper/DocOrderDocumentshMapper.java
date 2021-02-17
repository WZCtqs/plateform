package com.szhbl.project.customerservice.mapper;

import com.szhbl.project.customerservice.domain.DocOrderDocumentsh;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 单证文件Mapper接口
 * 
 * @author szhbl
 * @date 2020-03-27
 */
@Repository
public interface DocOrderDocumentshMapper 
{
    /**
     * 查询单证文件
     * 
     * @param id 单证文件ID
     * @return 单证文件
     */
    public DocOrderDocumentsh selectDocOrderDocumentshById(Long id);

    /**
     * 查询单证文件列表
     * 
     * @param docOrderDocumentsh 单证文件
     * @return 单证文件集合
     */
    public List<DocOrderDocumentsh> selectDocOrderDocumentshList(DocOrderDocumentsh docOrderDocumentsh);

    /**
     * 新增单证文件
     * 
     * @param docOrderDocumentsh 单证文件
     * @return 结果
     */
    public int insertDocOrderDocumentsh(DocOrderDocumentsh docOrderDocumentsh);

    /**
     * 修改单证文件
     * 
     * @param docOrderDocumentsh 单证文件
     * @return 结果
     */
    public int updateDocOrderDocumentsh(DocOrderDocumentsh docOrderDocumentsh);

    /**
     * 删除单证文件
     * 
     * @param id 单证文件ID
     * @return 结果
     */
    public int deleteDocOrderDocumentshById(Long id);

    /**
     * 批量删除单证文件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocOrderDocumentshByIds(Long[] ids);

    List<String> selectUrl(@Param("fileTypeKey")String fileTypeKey,@Param("orderId") String orderId);
}
