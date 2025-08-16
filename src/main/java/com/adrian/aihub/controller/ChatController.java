package com.adrian.aihub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.controller
 * @Date: 2025/8/16 14:44
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class ChatController {
    private final ChatClient chatClient;

    @RequestMapping(value = "/chat", produces = "text/html;charset=UTF-8")
    public Flux<String> chat(@RequestParam(defaultValue = "讲个笑话") String prompt) {
        return chatClient
                .prompt(prompt)
                .stream()
                .content();
    }
}
