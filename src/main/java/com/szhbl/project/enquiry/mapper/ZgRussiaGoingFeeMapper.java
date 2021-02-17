package com.szhbl.project.enquiry.mapper;

import com.szhbl.project.enquiry.domain.ZgRussiaGoingFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 俄线提货费Mapper接口
 * 
 * @author szhbl
 * @date 2020-07-10
 */
@Repository
public interface ZgRussiaGoingFeeMapper 
{
    /**
     * 查询俄线提货费
     * 
     * @param id 俄线提货费ID
     * @return 俄线提货费
     */
    public ZgRussiaGoingFee selectZgRussiaGoingFeeById(Long id);

    /**
     * 查询俄线提货费列表
     * 
     * @param zgRussiaGoingFee 俄线提货费
     * @return 俄线提货费集合
     */
    public List<ZgRussiaGoingFee> selectZgRussiaGoingFeeList(ZgRussiaGoingFee zgRussiaGoingFee);

    /**
     * 新增俄线提货费
     * 
     * @param zgRussiaGoingFee 俄线提货费
     * @return 结果
     */
    public int insertZgRussiaGoingFee(ZgRussiaGoingFee zgRussiaGoingFee);

    /**
     * 修改俄线提货费
     * 
     * @param zgRussiaGoingFee 俄线提货费
     * @return 结果
     */
    public int updateZgRussiaGoingFee(ZgRussiaGoingFee zgRussiaGoingFee);

    /**
     * 删除俄线提货费
     * 
     * @param id 俄线提货费ID
     * @return 结果
     */
    public int deleteZgRussiaGoingFeeById(Long id);

    /**
     * 批量删除俄线提货费
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZgRussiaGoingFeeByIds(Long[] ids);

    List<ZgRussiaGoingFee> selectZgRussiaGoingFeeByCity(ZgRussiaGoingFee zgRussiaGoingFee);

    List<ZgRussiaGoingFee> selectZePickFee(ZgRussiaGoingFee zgRussiaGoingFee);
}
