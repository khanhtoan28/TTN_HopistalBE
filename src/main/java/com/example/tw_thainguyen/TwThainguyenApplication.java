package com.example.tw_thainguyen;

import com.example.tw_thainguyen.config.DotEnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwThainguyenApplication {

    public static void main(String[] args) {
        // Load .env file trước khi Spring Boot khởi động
        DotEnvConfig.loadDotEnv();
        
        SpringApplication.run(TwThainguyenApplication.class, args);
    }

}
