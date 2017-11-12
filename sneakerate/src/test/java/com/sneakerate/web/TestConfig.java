package com.sneakerate.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"com.sneakerate.util", "com.sneakerate.web"})
@PropertySource("classpath:/application.properties")
@EnableAutoConfiguration
public class TestConfig {
}
