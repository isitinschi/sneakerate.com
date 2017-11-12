package com.sneakerate.marketing.controller;

import com.sneakerate.marketing.domain.SpecialOffer;
import com.sneakerate.marketing.service.MarketingService;
import com.sneakerate.marketing.service.SpecialOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class MarketingController {
    @Autowired
    private MarketingService marketingService;
    @Autowired
    private SpecialOfferService specialOfferService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingController.class);

    @Scheduled(fixedRate=12 * 60 * 60 * 1000)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String createAd() {
        List<SpecialOffer> specialOffers = specialOfferService.getSpecialOffers();
        if (specialOffers == null || specialOffers.isEmpty()) {
            return "no offers";
        }

        marketingService.createAds(specialOffers);

        return "Done";
    }
}
