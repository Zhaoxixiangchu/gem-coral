package com.gemframework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gemframework.mapper")
public class GemApplication {
    public static void main(String[] args) {
        SpringApplication.run(GemApplication.class, args);
    }

}
