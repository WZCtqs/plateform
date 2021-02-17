package com.szhbl.project.system.domain.vo.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description :
 * @Author : wangzhichao
 * @Date: 2020-09-11 17:12
 */
@Data
public class SysRabbitmqData implements Serializable {

    private String rabbit_version;
    //    private String[] users;
//    private String[] vhosts;
    //    private String[] permissions;
//    private String[] topic_permissions;
//    private String[] parameters;
//    private String[] global_parameters;
//    private String[] policies;
    private SysQueue[] queues;
    private SysExchange[] exchanges;
    private SysBindingKey[] bindings;
}
