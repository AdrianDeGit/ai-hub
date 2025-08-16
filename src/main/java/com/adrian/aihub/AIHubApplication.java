package com.adrian.aihub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.adrian.aihub.mapper")
public class AIHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AIHubApplication.class, args);
    }

}
