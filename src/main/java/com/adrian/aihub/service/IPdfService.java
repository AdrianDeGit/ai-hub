package com.adrian.aihub.service;

import com.adrian.aihub.entity.vo.Result;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * PDF服务接口
 * 
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service
 * @Date: 2025/8/17 17:39
 * @Author: Adrian
 * @Version: V1.0
 * @Description: PDF文件处理服务接口
 */
public interface IPdfService {

    /**
     * 上传PDF文件
     * 
     * @param chatId 会话ID
     * @param file 上传的文件
     * @return 上传结果
     */
    Result uploadPdf(String chatId, MultipartFile file);

    /**
     * 下载PDF文件
     * 
     * @param chatId 会话ID
     * @return 文件响应实体
     * @throws IOException IO异常
     */
    ResponseEntity<Resource> downloadPdf(String chatId) throws IOException;

    /**
     * PDF聊天对话
     * 
     * @param prompt 用户输入
     * @param chatId 会话ID
     * @return 流式响应
     */
    Flux<String> chat(String prompt, String chatId);
}
