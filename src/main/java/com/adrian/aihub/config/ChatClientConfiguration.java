package com.adrian.aihub.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.controller
 * @Date: 2025/8/16 14:40
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@Configuration
public class ChatClientConfiguration {
    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("你是一个智能聊天机器人，有渊博的学识和智慧。你的名字叫“知渊”。")
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}


