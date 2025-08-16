package com.adrian.aihub.service.impl;

import com.adrian.aihub.service.IChatSessionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service.impl
 * @Date: 2025/8/16 15:11
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@Component
public class InMemoryChatSessionService implements IChatSessionService {

    // 会话数据存储：
    // key 为业务类型（如 chat、service、pdf），value 为该类型下的会话映射；
    // 内层 key 为 chatId，value 为该会话对应的消息记录列表。
    // 使用 ConcurrentHashMap + 同步 List，保证在多线程 Web 场景下的基本线程安全。
    private final Map<String, Map<String, List<String>>> typeToChatIdRecords = new ConcurrentHashMap<>();

    @Override
    public void addChatSessionId(String type, String chatId) {
        // 获取或初始化指定类型的会话映射
        Map<String, List<String>> chatIdToRecords = typeToChatIdRecords.computeIfAbsent(type, k -> new ConcurrentHashMap<>());
        // 确保 chatId 对应的消息列表存在（首次创建为空列表）
        chatIdToRecords.computeIfAbsent(chatId, k -> Collections.synchronizedList(new ArrayList<>()));
    }

    @Override
    public List<String> getChatSessionIds(String type) {
        // 读取该类型下的会话 Map
        Map<String, List<String>> chatIdToRecords = typeToChatIdRecords.get(type);
        // 若为空返回不可变空列表
        if (chatIdToRecords == null || chatIdToRecords.isEmpty()) {
            return List.of();
        }
        // 返回该类型下所有 chatId 的拷贝，避免外部修改内部结构
        return new ArrayList<>(chatIdToRecords.keySet());
    }

    @Override
    public void removeChatSessionId(String type, String chatId) {
        // 读取该类型下的会话 Map
        Map<String, List<String>> chatIdToRecords = typeToChatIdRecords.get(type);
        // 不存在则无需处理
        if (chatIdToRecords == null) {
            return;
        }
        // 移除该 chatId
        chatIdToRecords.remove(chatId);
        // 若该类型已无会话，清理该类型映射
        if (chatIdToRecords.isEmpty()) {
            typeToChatIdRecords.remove(type);
        }
    }
}
