package com.szhbl.project.system.domain;

import com.szhbl.framework.web.domain.BaseEntity;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringStyle;

/**
 * 消息队列发送失败消息对象 sys_fail_mq
 *
 * @author szhbl
 * @date 2020-12-08
 */
public class SysFailMq extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String body;

    private Integer code;

    private String text;

    private String exchange;

    private String routingkey;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }

    public void setRoutingkey(String routingkey) {
        this.routingkey = routingkey;
    }

    public String getRoutingkey() {
        return routingkey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("body", getBody())
                .append("code", getCode())
                .append("text", getText())
                .append("exchange", getExchange())
                .append("routingkey", getRoutingkey())
                .toString();
    }
}
