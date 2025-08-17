package com.adrian.aihub.service;

import reactor.core.publisher.Flux;

/**
 * 聊天服务接口
 */
public interface IChatService {

    /**
     * 普通聊天
     *
     * @param prompt 用户提问
     * @param chatId 会话ID
     * @return 大模型的回答
     */
    Flux<String> chat(String prompt, String chatId);

    /**
     * 游戏聊天
     *
     * @param prompt 用户提问
     * @param chatId 会话ID
     * @return 大模型的回答
     */
    Flux<String> gameChat(String prompt, String chatId);

    /**
     * 客服聊天
     *
     * @param prompt 用户提问
     * @param chatId 会话ID
     * @return 大模型的回答
     */
    Flux<String> serviceChat(String prompt, String chatId);

    /**
     * PDF聊天
     *
     * @param prompt 用户提问
     * @param chatId 会话ID
     * @return 大模型的回答
     */
    Flux<String> pdfChat(String prompt, String chatId);
}
