package com.sneakerate.web.crawler;

import com.sneakerate.util.ImageUtil;
import com.sneakerate.util.service.S3Service;
import com.sneakerate.web.crawler.domain.AmazonItemInfo;
import com.sneakerate.web.crawler.domain.Offer;
import com.sneakerate.web.domain.SpecialOffer;
import com.sneakerate.web.service.feed.FeedService;
import com.sneakerate.web.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class AmazonCrawler {
    @Autowired
    private AmazonOfferService amazonOfferService;
    @Autowired
    private AmazonPageAnalyzer amazonPageAnalyzer;
    @Autowired
    private FeedService feedService;
    @Autowired
    private MailService mailService;
    @Autowired
    private S3Service<SpecialOffer> s3Service;

    private Long lastUpdated;

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonCrawler.class);

    @Scheduled(fixedRate=6 * 60 * 60 * 1000)
    public void crawle() {
        LOGGER.info("Started crawling data...");

        try {
            long start = System.currentTimeMillis();
            List<Offer> offers = amazonOfferService.getOffers();

            List<SpecialOffer> specialOffers = new ArrayList<>();
            for (Offer offer : offers) {
                specialOffers.addAll(processOffer(offer));
            }

            feedService.updateSpecialOffers(specialOffers);
            s3Service.upload("special-offers.json", feedService.getOffers());
            lastUpdated = System.currentTimeMillis();

            long durationInSeconds = (System.currentTimeMillis() - start) / 1000;
            String formattedDuration = (durationInSeconds < 60) ? durationInSeconds + " seconds" : durationInSeconds / 60 + " minutes";
            LOGGER.info("Finished crawling data in {}. {} special offers were found/updated", formattedDuration, specialOffers.size());
        } catch (Exception e) {
            LOGGER.error("Exception occurred:", e);
            notifyAboutProblem(e.toString());
        }
    }

    private void createImage(String name, String imageUrl) throws IOException {
        BufferedImage image = ImageUtil.prepareImage(imageUrl);

        BufferedImage image1 = ImageUtil.scaleImage(image, 1000);
        BufferedImage image2 = ImageUtil.scaleImage(image, 500);

        s3Service.upload(name + ".jpg", image);
        s3Service.upload(name + "-1000.jpg", image1);
        s3Service.upload(name + "-500.jpg", image2);
    }

    private List<SpecialOffer> processOffer(Offer offer) throws IOException {
        Collection<AmazonItemInfo> items = amazonPageAnalyzer.analyze(offer);

        List<SpecialOffer> specialOffers = new ArrayList<>();
        items.parallelStream().forEach(info -> {
            SpecialOffer specialOffer = new SpecialOffer(offer, info);
            try {
                SpecialOffer oldOffer = feedService.getOfferByUrl(specialOffer.getUrl());
                if (oldOffer == null) { // do not upload images that already exist
                    createImage(specialOffer.getUrl(), info.getImage());
                }
            } catch (IOException e) {
                throw new RuntimeException("Cannot create image " + info.getImage());
            }

            specialOffers.add(specialOffer);
        });

        return specialOffers;
    }

    private void notifyAboutProblem(String msg) {
        LOGGER.info("Sending notification about problem...");

        StringBuilder builder = new StringBuilder();
        builder.append("Time: ");
        builder.append(new Date());
        builder.append("\n");
        builder.append(msg);

        mailService.send("Crawling failed", builder.toString());

        LOGGER.info("Finished sending notification");
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }
}
