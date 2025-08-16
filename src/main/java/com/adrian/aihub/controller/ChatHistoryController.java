package com.adrian.aihub.controller;

import com.adrian.aihub.entity.vo.MessageVO;
import com.adrian.aihub.service.IChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.controller
 * @Date: 2025/8/16 15:14
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/history")
public class ChatHistoryController {
    private final IChatSessionService inMemoryChatSessionService;

    private final ChatMemory chatMemory;

    /**
     * 查询会话历史列表
     * @param type 业务类型，如：chat,service,pdf
     * @return chatId列表
     */
    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable("type") String type) {
        return inMemoryChatSessionService.getChatSessionIds(type);
    }

    /**
     * 根据业务类型、chatId查询会话历史
     * @param type 业务类型，如：chat,service,pdf
     * @param chatId 会话id
     * @return 指定会话的历史消息列表
     */
    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        List<Message> messages = chatMemory.get(chatId, 50);
        if(messages == null) {
            return List.of();
        }
        return messages.stream().map(MessageVO::new).toList();
    }

}
