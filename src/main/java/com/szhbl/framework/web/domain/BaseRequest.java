package com.szhbl.framework.web.domain;


import lombok.Data;

import java.io.Serializable;

/**
 * 参数接收基础request
 */
@Data
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 6877955227522370690L;

    /**
     * 语言类型
     */
    private String language;

}
