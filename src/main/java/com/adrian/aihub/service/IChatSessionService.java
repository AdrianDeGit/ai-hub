package com.adrian.aihub.service;

import java.util.List;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service
 * @Date: 2025/8/16 15:10
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
public interface IChatSessionService {

    /**
     * 保存会话记录
     *
     * @param type   业务类型，如：chat、service、pdf
     * @param chatId 会话ID
     */
    void addChatSessionId(String type, String chatId);

    /**
     * 获取会话ID列表
     *
     * @param type 业务类型，如：chat、service、pdf
     * @return 会话ID列表
     */
    List<String> getChatSessionIds(String type);

    /**
     * 删除会话记录
     *
     * @param type   业务类型，如：chat、service、pdf
     * @param chatId 会话ID
     */
    void removeChatSessionId(String type, String chatId);
}
