package com.szhbl.project.inquiry.handler.common;

import com.szhbl.project.enquiry.domain.ZgRailDivision;
import com.szhbl.project.enquiry.domain.ZgTripFee;
import lombok.Builder;

/**
 * 欧线去程整柜及铁路费用
 */
@Builder
public class PickAndRailFees {

    private ZgTripFee zgTripFee;

    private ZgRailDivision zgRailDivision;

    public ZgTripFee getZgTripFee() {
        return zgTripFee;
    }

    public void setZgTripFee(ZgTripFee zgTripFee) {
        this.zgTripFee = zgTripFee;
    }

    public ZgRailDivision getZgRailDivision() {
        return zgRailDivision;
    }

    public void setZgRailDivision(ZgRailDivision zgRailDivision) {
        this.zgRailDivision = zgRailDivision;
    }


}
