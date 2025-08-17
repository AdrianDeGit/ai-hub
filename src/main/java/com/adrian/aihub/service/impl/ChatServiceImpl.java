package com.adrian.aihub.service.impl;

import com.adrian.aihub.service.IChatService;
import com.adrian.aihub.service.IChatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * 聊天服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements IChatService {

    private final ChatClient chatClient;
    private final ChatClient gameChatClient;
    private final ChatClient serviceChatClient;
    private final IChatSessionService chatSessionService;
    private final ChatClient pdfChatClient;

    @Override
    public Flux<String> chat(String prompt, String chatId) {
        // 保存会话记录
        chatSessionService.addChatSessionId("chat", chatId);
        
        log.info("普通聊天 - prompt: {}, chatId: {}", prompt, chatId);
        
        return chatClient
                .prompt(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    @Override
    public Flux<String> gameChat(String prompt, String chatId) {
        log.info("游戏聊天 - prompt: {}, chatId: {}", prompt, chatId);
        
        return gameChatClient.prompt()
                .user(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    @Override
    public Flux<String> serviceChat(String prompt, String chatId) {
        // 保存会话id
        chatSessionService.addChatSessionId("service", chatId);
        
        log.info("客服聊天 - prompt: {}, chatId: {}", prompt, chatId);
        
        return serviceChatClient.prompt()
                .user(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    @Override
    public Flux<String> pdfChat(String prompt, String chatId) {
        // 保存会话id
        chatSessionService.addChatSessionId("pdf", chatId);

        log.info("PDF聊天 - prompt: {}, chatId: {}", prompt, chatId);

        return pdfChatClient.prompt()
                .user(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }
}
