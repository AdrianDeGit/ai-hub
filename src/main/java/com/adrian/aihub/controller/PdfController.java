package com.adrian.aihub.controller;

import com.adrian.aihub.entity.vo.Result;
import com.adrian.aihub.service.IPdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * PDF控制器
 * 
 * @Project: ai-hub
 * @Package: com.adrian.aihub.controller
 * @Date: 2025/8/17 17:39
 * @Author: Adrian
 * @Version: V1.0
 * @Description: PDF文件处理控制器
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/pdf")
public class PdfController {

    private final IPdfService pdfService;

    /**
     * 文件上传
     */
    @RequestMapping("/upload/{chatId}")
    public Result uploadPdf(@PathVariable String chatId, @RequestParam("file") MultipartFile file) {
        return pdfService.uploadPdf(chatId, file);
    }

    /**
     * 文件下载
     */
    @GetMapping("/file/{chatId}")
    public ResponseEntity<Resource> download(@PathVariable("chatId") String chatId) throws IOException {
        return pdfService.downloadPdf(chatId);
    }

    /**
     * PDF聊天对话
     */
    @RequestMapping(value = "/chat", produces = "text/html;charset=UTF-8")
    public Flux<String> chat(String prompt, String chatId) {
        return pdfService.chat(prompt, chatId);
    }
}
