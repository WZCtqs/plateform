package com.szhbl.project.system.domain.vo.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2020-09-11 17:44
 */
@Data
public class SysQueue implements Serializable {
    private String name;
    private String vhost;
    private String durable;
    private String auto_delete;
//    private String arguments;
}
