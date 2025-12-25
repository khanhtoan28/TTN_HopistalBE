package com.example.tw_thainguyen.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnvConfig {

    /**
     * Load file .env và đẩy các biến vào System Properties
     * Phải được gọi trước khi SpringApplication.run() để Spring Boot có thể đọc được các biến
     */
    public static void loadDotEnv() {
        // 1. Nạp file .env
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // 2. Đẩy các biến từ .env vào System Properties để Spring Boot đọc được
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}

