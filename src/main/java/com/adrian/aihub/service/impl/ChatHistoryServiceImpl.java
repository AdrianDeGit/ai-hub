package com.adrian.aihub.service.impl;

import com.adrian.aihub.entity.vo.MessageVO;
import com.adrian.aihub.service.IChatHistoryService;
import com.adrian.aihub.service.IChatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会话历史服务实现类
 * 
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service.impl
 * @Date: 2025/8/16 15:14
 * @Author: Adrian
 * @Version: V1.0
 * @Description: 会话历史管理服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatHistoryServiceImpl implements IChatHistoryService {

    private final IChatSessionService inMemoryChatSessionService;
    private final ChatMemory chatMemory;

    @Override
    public List<String> getChatIds(String type) {
        return inMemoryChatSessionService.getChatSessionIds(type);
    }

    @Override
    public List<MessageVO> getChatHistory(String type, String chatId) {
        // 从聊天内存中获取指定会话的历史消息
        List<Message> messages = chatMemory.get(chatId, 50);

        // 如果消息为空，返回空列表
        if (messages == null) {
            return List.of();
        }

        // 将Message对象转换为MessageVO对象并返回
        return messages.stream().map(MessageVO::new).toList();
    }
}
