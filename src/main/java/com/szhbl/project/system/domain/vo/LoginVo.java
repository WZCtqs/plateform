package com.szhbl.project.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    private String username;
    private String password;
    private String code;
    private String uuid;
    private String subSystem;
}
