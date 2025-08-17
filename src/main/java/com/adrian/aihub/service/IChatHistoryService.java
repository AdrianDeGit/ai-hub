package com.adrian.aihub.service;

import com.adrian.aihub.entity.vo.MessageVO;

import java.util.List;

/**
 * 会话历史服务接口
 * 
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service
 * @Date: 2025/8/16 15:14
 * @Author: Adrian
 * @Version: V1.0
 * @Description: 会话历史管理服务接口
 */
public interface IChatHistoryService {

    /**
     * 查询会话历史列表
     * 
     * @param type 业务类型，如：chat,service,pdf
     * @return chatId列表
     */
    List<String> getChatIds(String type);

    /**
     * 根据业务类型、chatId查询会话历史
     * 
     * @param type   业务类型，如：chat,service,pdf
     * @param chatId 会话id
     * @return 指定会话的历史消息列表
     */
    List<MessageVO> getChatHistory(String type, String chatId);
}
