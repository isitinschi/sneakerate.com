package com.sneakerate.marketing.service;

import com.facebook.ads.sdk.*;
import com.sneakerate.marketing.domain.Gender;
import com.sneakerate.marketing.domain.SpecialOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.sneakerate.marketing.FacebookAppConfig.*;

@Service
public class MarketingService {
    private static final long TIME_TO_WAIT_BETWEEN_REQUESTS_IN_MILLIS = TimeUnit.MINUTES.toMillis(1);
    private static final int NUMBER_OF_ATTEMPTS = 15;
    public static final String CAMPAIGN_NAME = "Sneakerate campaign - ";
    public static final String ADSET_NAME_PATTERN = "%s's sneakers";
    public static final String AD_TITLE_PATTERN = "%s's sneakers. Size %s. Pay %s$ instead of %s$. You save %s.";

    private final static SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    private AdAccount account;
    private APIContext context;
    private BatchRequest batch;

    private long lastRequestTime = System.currentTimeMillis();

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingService.class);

    public MarketingService() {
        context = new APIContext(ACCESS_TOKEN, APP_SECRET);
        account = new AdAccount(AD_ACCOUNT_ID_1, context);

        DATE_PARSER.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public void createAds(List<SpecialOffer> specialOffers) {
        String endDate = specialOffers.stream().map(so -> so.getEndDate()).findFirst().get();

        batch = new BatchRequest(context);
        execute(() -> createCampaign());

        Set<Gender> genders = specialOffers.stream().map(so -> so.getGender()).collect(Collectors.toSet());
        for (Gender gender : genders) {
            execute(() -> createAdSet(gender, endDate));
        }

        for (SpecialOffer specialOffer : specialOffers) {
            LOGGER.info("Creating add for {}", specialOffer.getUrl());
            execute(() -> createAd(specialOffer));
        }

        List<APIResponse> responses = execute(() -> {
            try {
                LOGGER.info("Executing final batch request...");
                return batch.execute();
            } catch (APIException e) {
                throw new RuntimeException("Failed execute batch request", e);
            }
        });

        for (APIResponse response : responses) {
            if (response != null) {
                APIException ex = response.getException();
                if (ex != null) {
                    throw new RuntimeException("Failed create ads.", ex);
                }
            }
        }
        LOGGER.info("Done. " + specialOffers.size() + " ads were created.");
    }

    private boolean createAd(SpecialOffer specialOffer) {
        String imageHash;
        try {
            imageHash = createAdImage(specialOffer.getUrl()).getFieldHash();
        } catch (Exception e) {
            throw new RuntimeException("Failed create adImage", e);
        }

        String description = String.format(AD_TITLE_PATTERN,
                specialOffer.getGender(),
                specialOffer.getSize(),
                specialOffer.getNewPrice(),
                specialOffer.getOldPrice(),
                specialOffer.getDiscount());
        String linkUrl = "https://www.sneakerate.com/special-offer/" + specialOffer.getUrl();

        account.createAdCreative()
                .setTitle(specialOffer.getName())
                .setBody(description)
                .setImageHash(imageHash)
                .setLinkUrl(linkUrl)
                .setObjectStorySpec(new AdCreativeObjectStorySpec()
                        .setFieldLinkData(new AdCreativeLinkData()
                                .setFieldMessage(description)
                                .setFieldName(specialOffer.getName())
                                .setFieldImageHash(imageHash)
                                .setFieldCallToAction(new AdCreativeLinkDataCallToAction()
                                        .setFieldType(AdCreativeLinkDataCallToAction.EnumType.VALUE_SHOP_NOW)
                                        .setFieldValue(
                                                new AdCreativeLinkDataCallToActionValue()
                                                        .setFieldLink(linkUrl)
                                                        .setFieldLinkCaption(linkUrl)
                                        )
                                )
                                .setFieldLink(linkUrl))
                        .setFieldPageId(PAGE_ID)
                )
                .addToBatch(batch, "adCreative" + specialOffer.getUrl());
        account.createAd()
                .setName(specialOffer.getName())
                .setAdsetId("{result=adSet" + specialOffer.getGender() + ":$.id}")
                .setCreative("{creative_id:{result=adCreative" + specialOffer.getUrl() + ":$.id}}")
                .setStatus(Ad.EnumStatus.VALUE_ACTIVE)
                .setRedownload(true)
                .setTrackingSpecs("{\"action.type\":\"offsite_conversion\",\"fb_pixel\":\"" + PIXEL_ID + "\"}")
                .addToBatch(batch);

        return true;
    }

    private AdImage createAdImage(String name) throws APIException, MalformedURLException, URISyntaxException {
        String imageUrl = "https://s3-us-west-2.amazonaws.com/sneakerate/" + name + ".jpg";
        File imageFile = new File(new URI(imageUrl));
        return account.createAdImage().addUploadFile("file", imageFile).execute();
    }

    private boolean createAdSet(Gender gender, String endDate) {
        account.createAdSet()
                .setName(String.format(ADSET_NAME_PATTERN, gender))
                .setCampaignId("{result=campaign:$.id}")
                .setStatus(AdSet.EnumStatus.VALUE_ACTIVE)
                .setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
                .setLifetimeBudget(300L)
                .setIsAutobid(true)
                .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_OFFSITE_CONVERSIONS)
                .setPromotedObject("{\"pixel_id\":\"" + PIXEL_ID + "\",\"custom_event_type\":\"INITIATED_CHECKOUT\"}")
                .setTargeting(createTargeting(gender))
                .setEndTime(endDate)
                .addToBatch(batch, "adSet" + gender);

        return true;
    }

    private boolean createCampaign() {
        String curDate = DATE_PARSER.format(new Date());
        account.createCampaign()
                .setName(CAMPAIGN_NAME + curDate)
                .setObjective(Campaign.EnumObjective.VALUE_CONVERSIONS)
                .setSpendCap(10000L)
                .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                .addToBatch(batch, "campaign");

        return true;
    }

    private Targeting createTargeting(Gender gender) {
        return new Targeting()
                .setFieldGeoLocations(new TargetingGeoLocation().setFieldCountries(Arrays.asList("US")))
                .setFieldAgeMin(18L)
                .setFieldAgeMax(35L)
                .setFieldGenders(Collections.singletonList(getGenderId(gender)))
                .setFieldPublisherPlatforms(Arrays.asList("facebook"))
                .setFieldFacebookPositions(Collections.singletonList("feed"))
                .setFieldLocales(Arrays.asList(6L, 24L))
                .setFieldTargetingOptimization("none");
    }

    private long getGenderId(Gender gender) {
        switch (gender) {
            case Men: return 1L;
            case Women: return 2L;
            default: return 0L;
        }
    }

    private <T> T execute(Supplier<T> exec) {
        sleep();

        T result = null;
        int retryCount = 0;
        while (result == null && retryCount < NUMBER_OF_ATTEMPTS) {
            try {
                result = exec.get();
            } catch (Exception e) {
                ++retryCount;
                LOGGER.warn("Failed attempt #{} out of {}. Sleeping...", retryCount, NUMBER_OF_ATTEMPTS);
                sleep();
            }
        }

        if (result == null) {
            LOGGER.error("Did {} attempts to execute the request. No luck", retryCount);
            throw new RuntimeException("Facebook Marketing API is unaccessible");
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
