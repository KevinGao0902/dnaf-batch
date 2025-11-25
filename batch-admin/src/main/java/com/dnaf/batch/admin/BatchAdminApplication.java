package com.dnaf.batch.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class BatchAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchAdminApplication.class, args);
    }

}
