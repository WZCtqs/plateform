package com.szhbl.project.documentcenter.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BeyondDoc implements Serializable {

    private String orderId;

    private String fileTypeKey;

    private String fileTypeText;
}
