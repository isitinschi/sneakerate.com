package com.sneakerate.web.crawler;

import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemLookupResponse;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OfferListing;
import com.sneakerate.web.crawler.domain.AmazonItemInfo;
import com.sneakerate.web.crawler.domain.Offer;
import com.sneakerate.web.crawler.service.AmazonFeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AmazonPageAnalyzer {
    private static final int MIN_DISCOUNT_PERCENTAGE = 40;
    @Autowired
    private AmazonFeedService amazonFeedService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonPageAnalyzer.class);

    public Collection<AmazonItemInfo> analyze(Offer offer) {
        LOGGER.info("Started analyzing the offer [{}] ...", offer.getName());

        ItemLookupResponse response = amazonFeedService.findParentIds(offer.getOfferIds());
        List<String> parentIds = extractParentIds(response);

        response = amazonFeedService.findVariants(parentIds);

        return extractVariants(response);
    }

    private List<String> extractParentIds(ItemLookupResponse response) {
        List<Items> responseItems = response.getItems();
        if (responseItems == null || responseItems.isEmpty() || responseItems.size() != 1) {
            throw new RuntimeException("Unexpected state. Please, check me");
        }
        List<Item> items = responseItems.get(0).getItem();
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Unexpected state. Please, check me");
        }

        return items.stream().map(Item::getParentASIN).collect(Collectors.toList());
    }

    private Collection<AmazonItemInfo> extractVariants(ItemLookupResponse response) {
        Collection<AmazonItemInfo> infoCollection = new ArrayList<>();

        for (Items items : response.getItems()) {
            for (Item item : items.getItem()) {
                for (Item variation : item.getVariations().getItem()) {
                    List<com.ECS.client.jax.Offer> offers = variation.getOffers().getOffer();
                    if (offers == null || offers.isEmpty() || offers.size() != 1) {
                        throw new RuntimeException("Unexpected state. Please, check me");
                    }
                    List<OfferListing> offerListings = offers.get(0).getOfferListing();
                    if (offerListings == null || offerListings.isEmpty() || offerListings.size() != 1) {
                        throw new RuntimeException("Unexpected state. Please, check me");
                    }
                    OfferListing offerListing = offerListings.get(0);
                    BigInteger percentageSaved = offerListing.getPercentageSaved();
                    if (percentageSaved == null || percentageSaved.intValue() < MIN_DISCOUNT_PERCENTAGE) {
                        continue;
                    }
                    if (variation.getLargeImage() == null) {
                        continue; // broken item
                    }

                    if (offerListing.getAmountSaved() == null || offerListing.getPrice() == null || variation.getItemAttributes().getListPrice() == null) {
                        throw new RuntimeException("Unexpected state. Please, check me");
                    }

                    AmazonItemInfo info = new AmazonItemInfo();
                    info.setColor(variation.getItemAttributes().getColor());
                    info.setSize(formatSize(variation.getItemAttributes().getSize()));
                    info.setNewPrice(offerListing.getPrice().getFormattedPrice());
                    int oldPrice = offerListing.getPrice().getAmount().add(offerListing.getAmountSaved().getAmount()).intValue();
                    info.setOldPrice(formatPrice(oldPrice));
                    info.setDiscount(offerListing.getAmountSaved().getFormattedPrice() + " (" + offerListing.getPercentageSaved().intValue() + "%)");

                    String largeImage = variation.getLargeImage().getURL();
                    info.setImage(largeImage.replace(".jpg", "._UL1500_.jpg"));

                    ItemLookupResponse result = amazonFeedService.itemLookup(variation.getASIN());
                    String amazonLink = result.getItems().get(0).getItem().get(0).getDetailPageURL();
                    if (amazonLink == null) {
                        continue; // broken url
                    }
                    info.setAmazonLink(amazonLink);

                    infoCollection.add(info);
                }
            }
        }

        return infoCollection;
    }

    private String formatSize(String size) {
        String [] sizeParts = size.trim().split(" ");
        if (sizeParts.length < 2 || sizeParts.length > 3) {
            throw new RuntimeException("Unexpected state. Please, check me");
        }

        return sizeParts[0] + " " + sizeParts[sizeParts.length - 1];
    }

    private String formatPrice(int price) {
        StringBuilder builder = new StringBuilder();
        builder.append("$");
        builder.append(price / 100);
        builder.append(".");
        if (price % 100 >= 10) {
            builder.append(price % 100);
        } else {
            builder.append("0");
            builder.append(price % 100);
        }

        return builder.toString();
    }
}
