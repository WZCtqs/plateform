package com.szhbl.project.inquiry.mapper;

import com.szhbl.project.inquiry.domain.Area;
import com.szhbl.project.inquiry.domain.City;
import com.szhbl.project.inquiry.domain.JsBackTimeset;
import com.szhbl.project.inquiry.domain.Province;
import com.szhbl.project.inquiry.dto.PcaDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 省市区查询接口
 *
 * @author shahy
 * @date 2020-10-01
 */
@Repository
public interface PcaMapper {

    /**
     * 根据provinceCode查询省数据
     *
     * @param provinceCode
     * @return
     */
    Province selectProvince(String provinceCode);
    /**
     * 根据cityCode查询城市数据
     *
     * @param cityCode
     * @return
     */
    City selectCity(String cityCode);
    /**
     * 根据areaCode查询区域数据
     *
     * @param areaCode
     * @return
     */
    Area selectArea(String areaCode);

    /**
     * 根据cityCode查询省市数据
     *
     * @param cityCode
     * @return
     */
    PcaDTO selectPc(String cityCode);

    /**
     * 根据areaCode查询省市区数据
     *
     * @param areaCode
     * @return
     */
    PcaDTO selectPca(String areaCode);


}
