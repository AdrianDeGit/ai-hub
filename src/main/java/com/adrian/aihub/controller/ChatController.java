package com.adrian.aihub.controller;

import com.adrian.aihub.service.IChatSessionService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.controller
 * @Date: 2025/8/16 14:44
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class ChatController {

    private final ChatClient chatClient;

    @Resource(name = "inMemoryChatSessionService")
    private IChatSessionService inMemoryChatSessionService;

    @RequestMapping(value = "/chat", produces = "text/html;charset=UTF-8")
    public Flux<String> chat(@RequestParam(defaultValue = "你好") String prompt, String chatId) {

        // 保存会话记录
        inMemoryChatSessionService.addChatSessionId("chat", chatId);

        return chatClient
                .prompt(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }
}
