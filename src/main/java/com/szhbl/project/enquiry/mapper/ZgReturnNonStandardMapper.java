package com.szhbl.project.enquiry.mapper;

import com.szhbl.project.enquiry.domain.ZgReturnNonStandard;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 非标准送货地与还箱地对应表
 * 
 * @author shy
 * @date 2020-06-18
 */
@Repository
public interface ZgReturnNonStandardMapper
{
    ZgReturnNonStandard selectZgReturnNonStandardById(Long id);

    List<ZgReturnNonStandard> selectZgReturnNonStandardList(ZgReturnNonStandard zgReturnNonStandard);

    int insertZgReturnNonStandard(ZgReturnNonStandard zgReturnNonStandard);

    int updateZgReturnNonStandard(ZgReturnNonStandard zgReturnNonStandard);

    int deleteZgReturnNonStandardById(Long id);

    int deleteZgReturnNonStandardByIds(Long[] ids);

    List<ZgReturnNonStandard> selectZgReturnNonStandardWithMap(Map<String, String> map);

    List<ZgReturnNonStandard> selectListByCity(String receiveCity);
}
