package com.szhbl.project.documentcenter.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HP
 */
@Data
public class LadingBill implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lbId;

    private String lb1;

    private String lb2;

    private String lb3;

    private String lb4;

    private String lb5;

    private String lb6;

    private String lb7;

    private String lb8;

    private String lb9;

    private String lb10;

    private String lb11;

    private String lb12;

    private String lb131;

    private String lb132;

    private String lb14;

    private String lb151;

    private String lb152;

    private String lb153;

    private String lb16;

    private String lb19;

    private String lb20;

    private String lbRemark;

    private String lbState;

    private String lbGroup1;

    private String lbGroup2;

    private String lbContainer;

    private String lbNumber;

    private String lbMark;

    private String lbUrl1;

    private String lbReason1;

    private String lbReason2;

    private String lbLetterstate;

    private String ciId;

    private String lbEastandwest;

    private String orderId;

    private String lbClientRemark;

    private Date ciStartdate;

    @Override
    public String toString() {
        return "LadingBill{" +
                "lbId=" + lbId +
                ", lb1=" + lb1 +
                ", lb2=" + lb2 +
                ", lb3=" + lb3 +
                ", lb4=" + lb4 +
                ", lb5=" + lb5 +
                ", lb6=" + lb6 +
                ", lb7=" + lb7 +
                ", lb10=" + lb10 +
                ", lb11=" + lb11 +
                ", lb12=" + lb12 +
                ", lb131=" + lb131 +
                ", lb132=" + lb132 +
                ", lb14=" + lb14 +
                ", lb151=" + lb151 +
                ", lb152=" + lb152 +
                ", lb153=" + lb153 +
                ", lb16=" + lb16 +
                ", lb19=" + lb19 +
                ", lb20=" + lb20 +
                ", lbRemark=" + lbRemark +
                ", lbState=" + lbState +
                ", lbGroup1=" + lbGroup1 +
                ", lbGroup2=" + lbGroup2 +
                ", lbContainer=" + lbContainer +
                ", lbNumber=" + lbNumber +
                ", lbMark=" + lbMark +
                ", lbUrl1=" + lbUrl1 +
                ", lbReason1=" + lbReason1 +
                ", lbReason2=" + lbReason2 +
                ", lbLetterstate=" + lbLetterstate +
                ", ciId=" + ciId +
                ", lbEastandwest=" + lbEastandwest +
                "}";
    }

}
