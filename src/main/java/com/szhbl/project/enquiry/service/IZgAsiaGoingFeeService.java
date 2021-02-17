package com.szhbl.project.enquiry.service;

import com.szhbl.project.enquiry.domain.ZgAsiaGoingFee;

import java.util.List;

/**
 * 郑亚、郑越去程整柜提货费Service接口
 *
 * @author szhbl
 * @date 2020-09-03
 */
public interface IZgAsiaGoingFeeService {
    /**
     * 查询郑亚、郑越去程整柜提货费
     *
     * @param id 郑亚、郑越去程整柜提货费ID
     * @return 郑亚、郑越去程整柜提货费
     */
    public ZgAsiaGoingFee selectZgAsiaGoingFeeById(Long id);

    /**
     * 查询郑亚、郑越去程整柜提货费列表
     *
     * @param zgAsiaGoingFee 郑亚、郑越去程整柜提货费
     * @return 郑亚、郑越去程整柜提货费集合
     */
    public List<ZgAsiaGoingFee> selectZgAsiaGoingFeeList(ZgAsiaGoingFee zgAsiaGoingFee);

    /**
     * 新增郑亚、郑越去程整柜提货费
     *
     * @param zgAsiaGoingFee 郑亚、郑越去程整柜提货费
     * @return 结果
     */
    public int insertZgAsiaGoingFee(ZgAsiaGoingFee zgAsiaGoingFee);

    /**
     * 修改郑亚、郑越去程整柜提货费
     *
     * @param zgAsiaGoingFee 郑亚、郑越去程整柜提货费
     * @return 结果
     */
    public int updateZgAsiaGoingFee(ZgAsiaGoingFee zgAsiaGoingFee);

    /**
     * 批量删除郑亚、郑越去程整柜提货费
     *
     * @param ids 需要删除的郑亚、郑越去程整柜提货费ID
     * @return 结果
     */
    public int deleteZgAsiaGoingFeeByIds(Long[] ids);

    /**
     * 删除郑亚、郑越去程整柜提货费信息
     *
     * @param id 郑亚、郑越去程整柜提货费ID
     * @return 结果
     */
    public int deleteZgAsiaGoingFeeById(Long id);


    List<ZgAsiaGoingFee> selectZgAsiaGoingFeeByCity(ZgAsiaGoingFee zgAsiaGoingFee);
}
