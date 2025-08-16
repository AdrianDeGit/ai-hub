package com.adrian.aihub.config;

import com.adrian.aihub.constants.SystemConstants;
import com.adrian.aihub.tools.CustomerServiceTools;
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
    public ChatClient chatClient(OpenAiChatModel chatModel, ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                .defaultSystem(SystemConstants.CHAT_SYSTEM_PROMPT)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    @Bean
    public ChatClient gameChatClient(OpenAiChatModel model, ChatMemory chatMemory) {
        return ChatClient
                .builder(model)
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    @Bean
    public ChatClient serviceChatClient(OpenAiChatModel model, ChatMemory chatMemory, CustomerServiceTools customerServiceTools) {
        return ChatClient
                .builder(model)
                .defaultSystem(SystemConstants.CUSTOMER_SERVICE_SYSTEM_PROMPT)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .defaultTools(customerServiceTools)
                .build();
    }


    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}


