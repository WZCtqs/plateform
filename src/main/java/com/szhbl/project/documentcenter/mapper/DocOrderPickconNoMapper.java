package com.szhbl.project.documentcenter.mapper;

import com.szhbl.project.documentcenter.domain.DocOrderPickconNo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 提箱号表Mapper接口
 *
 * @author szhbl
 * @date 2020-07-04
 */
public interface DocOrderPickconNoMapper {
    /**
     * 查询提箱号表
     *
     * @param id 提箱号表ID
     * @return 提箱号表
     */
    public DocOrderPickconNo selectDocOrderPickconNoById(Long id);

    /**
     * 查询提箱号表列表
     *
     * @param docOrderPickconNo 提箱号表
     * @return 提箱号表集合
     */
    public List<DocOrderPickconNo> selectDocOrderPickconNoList(DocOrderPickconNo docOrderPickconNo);

    /**
     * 新增提箱号表
     *
     * @param docOrderPickconNo 提箱号表
     * @return 结果
     */
    public int insertDocOrderPickconNo(DocOrderPickconNo docOrderPickconNo);

    /**
     * 修改提箱号表
     *
     * @param docOrderPickconNo 提箱号表
     * @return 结果
     */
    public int updateDocOrderPickconNo(DocOrderPickconNo docOrderPickconNo);

    /**
     * 删除提箱号表
     *
     * @param id 提箱号表ID
     * @return 结果
     */
    public int deleteDocOrderPickconNo(@Param("classNum") String classNum, @Param("containerNum") String containerNo);

    /**
     * 批量删除提箱号表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDocOrderPickconNoByIds(Long[] ids);

    DocOrderPickconNo selectPickconNoByClassNumCon(@Param("classNum") String classNum, @Param("containerNum") String containerNum);
}
