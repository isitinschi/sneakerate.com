package com.sneakerate.marketing.service;

import com.sneakerate.marketing.domain.Gender;
import com.sneakerate.marketing.domain.SpecialOffer;
import com.sneakerate.util.service.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialOfferService {
    private static final float MIN_DISCOUNT_PERCENTAGE = 40;

    @Autowired
    private S3Service<SpecialOffer> s3Service;

    private List<SpecialOffer> specialOffers = new ArrayList<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialOfferService.class);

    @PostConstruct
    public void populate() {
        LOGGER.info("Started populating data...");

        specialOffers = s3Service.downloadSpecialOffers(SpecialOffer.class);
        specialOffers = specialOffers.stream()
                .filter(so -> !so.getUrl().contains("."))
                .filter(so -> extractDiscountPercentage(so.getDiscount()) >= MIN_DISCOUNT_PERCENTAGE)
                .filter(so -> checkSize(so))
                .filter(so -> so.getSize().endsWith("US"))
                .collect(Collectors.toList());

        LOGGER.info("Finished populating data. {} special offers found", specialOffers.size());
    }

    private boolean checkSize(SpecialOffer so) {
        String size = so.getSize();
        if (so.getGender().equals(Gender.Women)) {
            return size.startsWith("8") || size.startsWith("9");
        } else {
            return size.startsWith("9") || size.startsWith("10");
        }
    }

    private float extractDiscountPercentage(String discount) {
        return Float.valueOf(discount.substring(discount.indexOf("(") + 1, discount.indexOf("%")));
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }
}
