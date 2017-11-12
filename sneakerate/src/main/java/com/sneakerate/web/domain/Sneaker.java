package com.sneakerate.web.domain;

import java.util.ArrayList;
import java.util.List;

public class Sneaker implements Comparable {
    private String name;
    private int overallRating;
    private int designRating;
    private int comfortRating;
    private int durabilityRating;
    private int priceRating;
    private int releaseYear;
    private String url;
    private String image;
    private String mediumImage;
    private String videoId;
    private int ctr;
    private int conversion;
    private int rating;
    private String description;
    private List<List<String>> images = new ArrayList<>();
    private List<String> amazonLinks = new ArrayList<>();

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    public int getComfortRating() {
        return comfortRating;
    }

    public void setComfortRating(int comfortRating) {
        this.comfortRating = comfortRating;
    }

    public int getDurabilityRating() {
        return durabilityRating;
    }

    public void setDurabilityRating(int durabilityRating) {
        this.durabilityRating = durabilityRating;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<List<String>> getImages() {
        return images;
    }

    public void setImages(List<List<String>> images) {
        this.images = images;
    }

    public List<String> getAmazonLinks() {
        return amazonLinks;
    }

    public void setAmazonLinks(List<String> amazonLinks) {
        this.amazonLinks = amazonLinks;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(int priceRating) {
        this.priceRating = priceRating;
    }

    public int getDesignRating() {
        return designRating;
    }

    public void setDesignRating(int designRating) {
        this.designRating = designRating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCtr() {
        return ctr;
    }

    public void setCtr(int ctr) {
        this.ctr = ctr;
    }

    public int getConversion() {
        return conversion;
    }

    public void setConversion(int conversion) {
        this.conversion = conversion;
    }

    @Override
    public int compareTo(Object o) {
        if (o != null && o instanceof Sneaker) {
            Sneaker other = (Sneaker) o;
            return Integer.valueOf(rating).compareTo(other.getRating());
        }

        return 0;
    }

    public boolean validate() {
        if (images == null || amazonLinks == null || amazonLinks.size() != images.size()) {
            return false;
        }

        return true;
    }
}
