package com.szhbl.project.enquiry.util;

import com.szhbl.project.enquiry.domain.ZgRailDivision;

import java.util.*;

public class CaculateMinFeeUtil {
    public static ZgRailDivision getMinRailFee(List<ZgRailDivision> zgRailDivisionList) {

        //循环遍历list集合取出最小值的对象
        if (zgRailDivisionList.size() == 0) {
            return null;
        }
        ZgRailDivision zgRailDivision = zgRailDivisionList.stream().min(Comparator.comparing(ZgRailDivision::getRailCost)).get();
        System.out.println("最小值对象:" + zgRailDivision);
        return zgRailDivision;
    }

}
