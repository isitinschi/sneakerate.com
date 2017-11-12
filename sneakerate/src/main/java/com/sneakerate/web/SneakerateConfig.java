package com.sneakerate.web;

import com.sneakerate.util.service.S3Service;
import com.sneakerate.web.domain.SpecialOffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableScheduling
public class SneakerateConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/trends");
    }

    @Bean
    public S3Service<SpecialOffer> s3Service() {
        return new S3Service<>();
    }
}
