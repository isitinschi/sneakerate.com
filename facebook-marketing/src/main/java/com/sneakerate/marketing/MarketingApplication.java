package com.sneakerate.marketing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketingApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting application with following parameters: {}", args);

        SpringApplication.run(MarketingApplication.class, args);

        LOGGER.info("Application started");
    }
}

