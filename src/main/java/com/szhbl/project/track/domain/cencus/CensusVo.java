package com.szhbl.project.track.domain.cencus;

import com.szhbl.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

@Data
public class CensusVo {//运行统计导出vo

    @Excel(name="出/入境口岸")
    private String port;
    @Excel(name="班列日期")
    private String classDate;
    @Excel(name="运行时间/天")
    private String totalDay;
    @Excel(name="发车时间")
    private String actualRunTime;
    @Excel(name="到站时间")
    private String destinationTime;

}
