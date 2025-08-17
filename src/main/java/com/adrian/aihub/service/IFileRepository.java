package com.adrian.aihub.service;

import org.springframework.core.io.Resource;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service
 * @Date: 2025/8/17 17:22
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
public interface IFileRepository {
    /**
     * 保存文件,还要记录chatId与文件的映射关系
     * @param chatId 会话id
     * @param resource 文件
     * @return 上传成功，返回true； 否则返回false
     */
    boolean save(String chatId, Resource resource);

    /**
     * 根据chatId获取文件
     * @param chatId 会话id
     * @return 找到的文件
     */
    Resource getFile(String chatId);
}
