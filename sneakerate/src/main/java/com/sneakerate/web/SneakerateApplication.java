package com.sneakerate.web;

import com.sneakerate.web.service.status.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SneakerateApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SneakerateApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting application with following parameters: {}", args);
        if (args != null && args.length != 0) {
            StatusService.buildVersion = args[0];
        }

        SpringApplication.run(SneakerateApplication.class, args);

        LOGGER.info("Application started");
    }
}

