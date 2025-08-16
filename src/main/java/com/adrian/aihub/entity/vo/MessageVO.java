package com.adrian.aihub.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.entity.vo
 * @Date: 2025/8/16 15:12
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@NoArgsConstructor
@Data
public class MessageVO {
    private String role;
    private String content;

    public MessageVO(Message message) {
        this.role = switch (message.getMessageType()) {
            case USER -> "user";
            case ASSISTANT -> "assistant";
            case SYSTEM -> "system";
            default -> "";
        };
        this.content = message.getText();
    }
}