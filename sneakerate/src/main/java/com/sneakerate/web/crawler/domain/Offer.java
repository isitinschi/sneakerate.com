package com.sneakerate.web.crawler.domain;

import java.util.ArrayList;
import java.util.List;

public class Offer {
    private String name;
    private String description;
    private String gender;
    private List<String> offerIds = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getOfferIds() {
        return offerIds;
    }

    public void addOfferId(String offerId) {
        this.offerIds.add(offerId);
    }
}
