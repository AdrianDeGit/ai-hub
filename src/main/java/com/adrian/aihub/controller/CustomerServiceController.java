package com.adrian.aihub.controller;

import com.adrian.aihub.service.IChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.controller
 * @Date: 2025/8/16 18:47
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class CustomerServiceController {
    private final ChatClient serviceChatClient;
    private final IChatSessionService inMemoryChatSessionService;

    @RequestMapping(value = "/service", produces = "text/html;charset=utf-8")
    public String service(String prompt, String chatId) {
        // 1.保存会话id
        inMemoryChatSessionService.addChatSessionId("service", chatId);
        // 2.请求模型
        return serviceChatClient.prompt()
                .user(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .call()
                .content();
    }
}
