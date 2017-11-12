package com.sneakerate.web.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PoliteWebClient {
    private static long WAIT_TIME_IN_MILLIS = 15*1000;
    private WebClient webClient;

    private long lastLoad = System.currentTimeMillis();

    private static final Logger LOGGER = LoggerFactory.getLogger(PoliteWebClient.class);

    private void init() {
        webClient = new WebClient(BrowserVersion.FIREFOX_45);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(false);
        webClient.setJavaScriptTimeout(3600);
        webClient.setIncorrectnessListener(new SilentListener());
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
//        CookieManager cm = new CookieManager();
//        webClient.setCookieManager(cm);
    }

    public HtmlPage getPage(String link) throws IOException {
        init();
        waitIfNecessary();

        HtmlPage page = webClient.getPage(link);
        lastLoad = System.currentTimeMillis();

        if (page.asText().startsWith("Robot")) {
            WAIT_TIME_IN_MILLIS *= 2;
            LOGGER.warn("Robot check detected. Increased wait time to {}", WAIT_TIME_IN_MILLIS);
            getPage(link);
        } else {
            WAIT_TIME_IN_MILLIS = Math.max(15*1000, WAIT_TIME_IN_MILLIS / 2);
        }

        return page;
    }

    private void waitIfNecessary() {
        long wait = WAIT_TIME_IN_MILLIS - (System.currentTimeMillis() - lastLoad);
        if (wait > 0) {
            try {
                Thread.currentThread().sleep(wait);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
