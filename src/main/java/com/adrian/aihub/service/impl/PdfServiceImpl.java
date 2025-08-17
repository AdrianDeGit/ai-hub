package com.adrian.aihub.service.impl;

import com.adrian.aihub.entity.vo.Result;
import com.adrian.aihub.service.IChatService;
import com.adrian.aihub.service.IFileRepository;
import com.adrian.aihub.service.IPdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * PDF服务实现类
 * 
 * @Project: ai-hub
 * @Package: com.adrian.aihub.service.impl
 * @Date: 2025/8/17 17:39
 * @Author: Adrian
 * @Version: V1.0
 * @Description: PDF文件处理服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements IPdfService {

    private final IFileRepository fileRepository;
    private final VectorStore vectorStore;
    private final IChatService chatService;

    @Override
    public Result uploadPdf(String chatId, MultipartFile file) {
        try {
            // 1. 校验文件是否为PDF格式
            if (!Objects.equals(file.getContentType(), "application/pdf")) {
                return Result.fail("只能上传PDF文件！");
            }
            
            // 2. 保存文件
            boolean success = fileRepository.save(chatId, file.getResource());
            if (!success) {
                return Result.fail("保存文件失败！");
            }
            
            // 3. 写入向量库
            writeToVectorStore(file.getResource());
            
            return Result.ok();
        } catch (Exception e) {
            log.error("上传PDF失败，失败原因：{}", e.getMessage());
            return Result.fail("上传文件失败！");
        }
    }

    @Override
    public ResponseEntity<Resource> downloadPdf(String chatId) throws IOException {
        // 1. 读取文件
        Resource resource = fileRepository.getFile(chatId);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        // 2. 文件名编码，写入响应头
        String filename = URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8);
        
        // 3. 返回文件
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @Override
    public Flux<String> chat(String prompt, String chatId) {
        return chatService.pdfChat(prompt, chatId);
    }

    /**
     * 将PDF文件写入向量存储
     * 
     * @param resource PDF文件资源
     */
    private void writeToVectorStore(Resource resource) {
        // 1. 创建PDF的读取器
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                resource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.defaults())
                        .withPagesPerDocument(1)
                        .build()
        );
        
        // 2. 读取PDF文档，拆分为Document
        List<Document> documents = reader.read();
        
        // 3. 写入向量库
        vectorStore.add(documents);
    }
}
