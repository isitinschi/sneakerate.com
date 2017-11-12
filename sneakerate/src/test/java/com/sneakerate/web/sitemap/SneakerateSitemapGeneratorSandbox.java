package com.sneakerate.web.sitemap;

import com.sneakerate.web.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class SneakerateSitemapGeneratorSandbox {
    private static final String SITEMAP_PATH = "C:\\Users\\Iana\\workspace\\util\\target\\sitemap.xml";

    @Autowired
    private SneakerateSitemapGenerator sitemapGenerator;

    @Test
    public void generateSitemap() throws IOException {
        sitemapGenerator.generate(SITEMAP_PATH, false);
    }

    @Test
    public void validateSitemap() throws IOException {
        sitemapGenerator.validate(SITEMAP_PATH);
    }
}
