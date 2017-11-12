package com.sneakerate.web.crawler.service;

import com.ECS.client.jax.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.ws.handler.HandlerResolver;
import java.util.List;
import java.util.function.Supplier;

@Service
public class AmazonFeedService {
    private static final long TIME_TO_WAIT_BETWEEN_REQUESTS_IN_MILLIS = 1500;
    private static final String AWS_ACCESS_KEY_ID = "AKIAJYSGP3UFHVVC27DQ";
    private static final String AWS_SECRET_ACCESS_KEY = "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";
    public static final String ASSOCIATE_TAG = "sneakerate-20";

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonFeedService.class);

    private AWSECommerceServicePortType port;

    private long lastRequestTime = System.currentTimeMillis();

    public AmazonFeedService() {
        HandlerResolver handlerResolver = new AwsHandlerResolver(AWS_SECRET_ACCESS_KEY);
        AWSECommerceService ecs = new AWSECommerceService();
        ecs.setHandlerResolver(handlerResolver);
        port = ecs.getAWSECommerceServicePort();
    }

    public ItemLookupResponse itemLookup(String itemId) {
        ItemLookupRequest request = new ItemLookupRequest();
        request.setIdType("ASIN");
        request.getItemId().add(itemId);
        request.setCondition("New");
        request.getResponseGroup().add("Medium");

        ItemLookup item = new ItemLookup();
        item.setAssociateTag(ASSOCIATE_TAG);
        item.setAWSAccessKeyId(AWS_ACCESS_KEY_ID);
        item.getRequest().add(request);

        return execute(() -> port.itemLookup(item));
    }

    public ItemLookupResponse findParentIds(List<String> offerIds) {
        ItemLookupRequest request = new ItemLookupRequest();
        request.setIdType("ASIN");

        for (String offerId : offerIds) {
            request.getItemId().add(offerId);
        }

        ItemLookup item = new ItemLookup();
        item.setAssociateTag(ASSOCIATE_TAG);
        item.setAWSAccessKeyId(AWS_ACCESS_KEY_ID);

        item.getRequest().add(request);

        return execute(() -> port.itemLookup(item));
    }

    public ItemLookupResponse findVariants(List<String> parentIds) {
        ItemLookupRequest request = new ItemLookupRequest();
        request.setIdType("ASIN");
        request.setCondition("New");
        request.getResponseGroup().add("Images");
        request.getResponseGroup().add("Variations");
        request.getResponseGroup().add("VariationMatrix");
        request.getResponseGroup().add("VariationImages");
        request.getResponseGroup().add("OfferFull");

        for (String itemId : parentIds) {
            request.getItemId().add(itemId);
        }

        ItemLookup item = new ItemLookup();
        item.setAssociateTag(ASSOCIATE_TAG);
        item.setAWSAccessKeyId(AWS_ACCESS_KEY_ID);

        item.getRequest().add(request);

        return execute(() -> port.itemLookup(item));
    }

    private ItemLookupResponse execute(Supplier<ItemLookupResponse> exec) {
        sleep();

        ItemLookupResponse result = null;
        int retryCount = 0;
        while (result == null && retryCount < 3) {
            try {
                result = exec.get();
            } catch (Exception e) {
                ++retryCount;
                sleep();
            }
        }

        if (result == null) {
            LOGGER.error("Did {} attempts to execute the request. No luck", retryCount);
            throw new RuntimeException("Amazon Product API is unaccessible");
        }

        lastRequestTime = System.currentTimeMillis();

        return result;
    }

    private void sleep() {
        long timeToSleep = TIME_TO_WAIT_BETWEEN_REQUESTS_IN_MILLIS - (System.currentTimeMillis() - lastRequestTime);

        if (timeToSleep > 0) {
            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
