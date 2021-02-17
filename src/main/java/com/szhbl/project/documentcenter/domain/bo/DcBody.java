package com.szhbl.project.documentcenter.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2020-12-07 16:26
 */
@Data
public class DcBody implements Serializable {

    private List<DcSettlementBO> rows;

    private Integer total;
}
