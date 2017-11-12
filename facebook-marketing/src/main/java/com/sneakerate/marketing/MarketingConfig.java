package com.sneakerate.marketing;

import com.sneakerate.marketing.domain.SpecialOffer;
import com.sneakerate.util.service.S3Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableScheduling
public class MarketingConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/trends");
    }

    @Bean
    public S3Service<SpecialOffer> s3Service() {
        return new S3Service<>();
    }
}
