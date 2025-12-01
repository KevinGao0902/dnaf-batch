package com.dnaf.batch.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.dnaf.batch.core.**.mapper")
@EnableTransactionManagement
public class BatchCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchCoreApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
        return event -> {
            String port = event.getApplicationContext().getEnvironment().getProperty("server.port", "8083");
            String appName = event.getApplicationContext().getEnvironment().getProperty("spring.application.name", "batch-core");

            System.out.println("\n");
            System.out.println("=========================================");
            System.out.println("✅ [" + appName + "] 启动成功！");
            System.out.println("📌 端口号: " + port);
            System.out.println("⏰ 启动时间: " + java.time.LocalDateTime.now());
            System.out.println("=========================================");
            System.out.println("\n");
        };
    }
}
