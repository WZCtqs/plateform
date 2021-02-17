package com.szhbl.project.test.delayqueue;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessagePojo implements Serializable {
    /**
     * 消息id
     */
    private String messageId;

    /**
     * 延迟时间（毫秒）
     */
    private Long delayNow;

    /**
     * 延迟剩余时间（毫秒）
     */
    private Long delayNext;

    /**
     * 消息
     */
    private Object message;

    @Override
    public String toString() {
        return "MessagePojo{" +
                "messageId='" + messageId + '\'' +
                ", delayNow=" + delayNow +
                ", delayNext=" + delayNext +
                ", message=" + message +
                '}';
    }
}
