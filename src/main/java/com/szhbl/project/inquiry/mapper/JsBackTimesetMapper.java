package com.szhbl.project.inquiry.mapper;

import com.szhbl.project.inquiry.domain.JsBackTimeset;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 集疏部回程提货找车和运输时间Mapper接口
 *
 * @author szhbl
 * @date 2020-07-21
 */
public interface JsBackTimesetMapper {
    /**
     * 查询集疏部回程提货找车和运输时间
     *
     * @param id 集疏部回程提货找车和运输时间ID
     * @return 集疏部回程提货找车和运输时间
     */
    public JsBackTimeset selectJsBackTimesetById(Long id);

    /**
     * 查询集疏部回程提货找车和运输时间列表
     *
     * @param jsBackTimeset 集疏部回程提货找车和运输时间
     * @return 集疏部回程提货找车和运输时间集合
     */
    public List<JsBackTimeset> selectJsBackTimesetList(JsBackTimeset jsBackTimeset);

    /**
     * 新增集疏部回程提货找车和运输时间
     *
     * @param jsBackTimeset 集疏部回程提货找车和运输时间
     * @return 结果
     */
    public int insertJsBackTimeset(JsBackTimeset jsBackTimeset);

    /**
     * 修改集疏部回程提货找车和运输时间
     *
     * @param jsBackTimeset 集疏部回程提货找车和运输时间
     * @return 结果
     */
    public int updateJsBackTimeset(JsBackTimeset jsBackTimeset);

    /**
     * 删除集疏部回程提货找车和运输时间
     *
     * @param id 集疏部回程提货找车和运输时间ID
     * @return 结果
     */
    public int deleteJsBackTimesetById(Long id);

    /**
     * 批量删除集疏部回程提货找车和运输时间
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJsBackTimesetByIds(Long[] ids);

    public JsBackTimeset selectByStationPickCountry(@Param("station") String station, @Param("pickcountry") String pickcountry);

    List<String> getCountry();
}
