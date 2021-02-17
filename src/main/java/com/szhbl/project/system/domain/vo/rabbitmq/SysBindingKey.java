package com.szhbl.project.system.domain.vo.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2020-09-11 17:44
 */
@Data
public class SysBindingKey implements Serializable {
    private String source;
    private String vhost;
    private String destination;
    private String destination_type;
    private String routing_key;
//    private String arguments;
}
