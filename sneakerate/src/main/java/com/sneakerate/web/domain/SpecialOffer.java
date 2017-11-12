package com.sneakerate.web.domain;

import com.sneakerate.web.crawler.domain.AmazonItemInfo;
import com.sneakerate.web.crawler.domain.Offer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SpecialOffer {
    private static final String URL_FORMAT = "%s-%s-%s-%s-discount";
    private final static SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    private String name;
    private String url;
    private String description;
    private String size;
    private String gender;
    private String discount;
    private String oldPrice;
    private String newPrice;
    private String amazonLink;
    private String endDate;

    public SpecialOffer() {
    }

    public SpecialOffer(Offer offer, AmazonItemInfo amazonItemInfo) {
        this.name = offer.getName();
        this.description = offer.getDescription();
        this.gender = offer.getGender();
        this.size = amazonItemInfo.getSize();
        this.discount = amazonItemInfo.getDiscount();
        this.oldPrice = amazonItemInfo.getOldPrice();
        this.newPrice = amazonItemInfo.getNewPrice();
        this.amazonLink = amazonItemInfo.getAmazonLink();
        this.url = buildUrl(amazonItemInfo);
        this.endDate = getEndTime();
    }

    private String getEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 24);
        cal.add(Calendar.MINUTE, 2);
        Date endDate = cal.getTime();

        return DATE_PARSER.format(endDate);
    }

    private String buildUrl(AmazonItemInfo info) {
        String namePart = name.trim().replaceAll("[^A-Za-z0-9]", "-");
        String sizePart = info.getSize().trim().replaceAll("[^A-Za-z0-9]", "-");
        String color = info.getColor().trim().replaceAll("[^A-Za-z0-9]", "-");
        String link = String.format(URL_FORMAT, namePart, color, gender, sizePart);
        return link.toLowerCase();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
