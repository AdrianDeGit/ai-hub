package com.adrian.aihub.service.impl;

import com.adrian.aihub.service.IFileRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service.impl
 * @Date: 2025/8/17 17:23
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalPdfFileRepository implements IFileRepository {

    private final VectorStore vectorStore;

    // 会话id 与 文件名的对应关系，方便查询会话历史时重新加载文件
    private final Properties chatFiles = new Properties();

    /**
     * 保存文件,还要记录chatId与文件的映射关系
     *
     * @param chatId   会话id
     * @param resource 文件
     * @return 上传成功，返回true； 否则返回false
     */
    @Override
    public boolean save(String chatId, Resource resource) {
        // 2.保存到本地磁盘
        String filename = resource.getFilename();
        File target = new File(Objects.requireNonNull(filename));
        if (!target.exists()) {
            try {
                Files.copy(resource.getInputStream(), target.toPath());
            } catch (IOException e) {
                log.error("保存PDF资源失败，异常信息：{}", e.getMessage());
                return false;
            }
        }
        // 3.保存映射关系
        chatFiles.put(chatId, filename);
        return true;
    }

    /**
     * 根据chatId获取文件
     *
     * @param chatId 会话id
     * @return 找到的文件
     */
    @Override
    public Resource getFile(String chatId) {
        return new FileSystemResource(chatFiles.getProperty(chatId));
    }

    /**
     * 初始化方法，加载配置文件和向量存储文件
     * 该方法在Bean初始化后自动调用，用于加载之前保存的会话id与文件名映射关系和向量存储数据
     * 如果配置文件或向量存储文件不存在，则不会进行加载
     */
    @PostConstruct
    private void init() {
        // 创建PDF配置文件资源对象，指向chat-pdf.properties文件
        FileSystemResource pdfResource = new FileSystemResource("chat-pdf.properties");
        // 检查PDF配置文件是否存在
        if (pdfResource.exists()) {
            try {
                // 使用UTF-8编码读取配置文件内容到Properties对象中
                chatFiles.load(new BufferedReader(
                        new InputStreamReader(pdfResource.getInputStream(), StandardCharsets.UTF_8)));
            } catch (IOException e) {
                // 如果读取失败，抛出运行时异常
                throw new RuntimeException(e);
            }
        }

        // 创建向量存储文件资源对象，指向chat-pdf.json文件
        FileSystemResource vectorResource = new FileSystemResource("chat-pdf.json");
        // 检查向量存储文件是否存在
        if (vectorResource.exists()) {
            // 将vectorStore强制转换为SimpleVectorStore类型
            SimpleVectorStore simpleVectorStore = (SimpleVectorStore) vectorStore;
            // 从JSON文件加载已保存的向量数据到向量存储中
            simpleVectorStore.load(vectorResource);
        }
    }


    /**
     * 销毁方法，在Bean销毁前自动调用，用于保存会话id与文件名映射关系和向量存储数据
     * 如果保存失败，抛出运行时异常
     */
    @PreDestroy
    private void persistent() {
        try {
            // 将Properties对象内容保存到chat-pdf.properties文件，使用当前时间作为注释
            chatFiles.store(new FileWriter("chat-pdf.properties"), LocalDateTime.now().toString());
            // 将vectorStore强制转换为SimpleVectorStore类型
            SimpleVectorStore simpleVectorStore = (SimpleVectorStore) vectorStore;
            // 将向量存储中的数据保存到chat-pdf.json文件中
            simpleVectorStore.save(new File("chat-pdf.json"));
        } catch (IOException e) {
            // 如果保存失败，抛出运行时异常
            throw new RuntimeException(e);
        }
    }
}
