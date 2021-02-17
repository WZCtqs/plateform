package com.szhbl.project.inquiry.handler.common;

import java.util.List;
import java.util.Objects;

/**
 * 费用比较
 *
 * @author shahy
 */
public class CompareFeesUtil {

    /**
     * 比较费用，获取最低的
     *
     * @param list
     * @return
     */
    public static PickAndRailFees getMinPickFee(List<PickAndRailFees> list){
        PickAndRailFees pickAndRailFees =
                    list.stream()
                        .filter(Objects::nonNull)
                        .filter(pickAndRailFees1 -> pickAndRailFees1.getZgTripFee() != null)
                        .filter(pickAndRailFees2 -> pickAndRailFees2.getZgRailDivision() != null)
                        .min((o1, o2) -> {
                            Long ol1 = o1.getZgTripFee().getBzhPickUpCharge() + o1.getZgRailDivision().getRailCost();
                            Long ol2 = o2.getZgTripFee().getBzhPickUpCharge() + o2.getZgRailDivision().getRailCost();
                            return ol1.compareTo(ol2);
                        }).get();
        return pickAndRailFees;
    }

}
