package com.adrian.aihub.service.impl;

import com.adrian.aihub.service.IChatService;
import com.adrian.aihub.service.IChatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

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
    public Flux<String> Chat(String prompt, String chatId, List<MultipartFile> files) {
        // 保存会话记录
        chatSessionService.addChatSessionId("chat", chatId);

        log.info("普通聊天 - prompt: {}, chatId: {}", prompt, chatId);

        // return chatClient
        //         .prompt(prompt)
        //         .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
        //         .stream()
        //         .content();

        if (files == null || files.isEmpty()) {
            // 没有附件，纯文本聊天
            return textChat(prompt, chatId);
        } else {
            // 有附件，多模态聊天
            return multiModalChat(prompt, chatId, files);
        }


    }

    private Flux<String> multiModalChat(String prompt, String chatId, List<MultipartFile> files) {
        // 1.解析多媒体
        List<Media> medias = files.stream()
                .map(multipartFile -> new Media(
                                MimeType.valueOf(Objects.requireNonNull(multipartFile.getContentType())),
                                multipartFile.getResource()
                        )
                )
                .toList();
        // 2.请求模型
        return chatClient.prompt()
                .user(
                        promptUserSpec -> promptUserSpec
                                .text(prompt)
                                .media(medias.toArray(Media[]::new))
                )
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    private Flux<String> textChat(String prompt, String chatId) {
        return chatClient.prompt()
                .user(prompt)
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
