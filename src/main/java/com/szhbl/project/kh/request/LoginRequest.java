package com.szhbl.project.kh.request;

import com.szhbl.framework.web.domain.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("登录model")
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends BaseRequest {

    @ApiModelProperty(value = "登录账号/注册账号",example = "18912399379")
    private String userName;
    @ApiModelProperty(value = "密码",example = "399379")
    private String password;
    @ApiModelProperty(value = "验证码的redis key")
    private String codeToken;
    @ApiModelProperty(value = "验证码（注册时使用）")
    private String code;
}
