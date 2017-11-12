package com.sneakerate.web.domain;

import java.util.List;

public class SneakerBuilder {
    private Sneaker sneaker;

    public SneakerBuilder() {
        sneaker = new Sneaker();
    }

    public Sneaker build() {
        int smallRating = sneaker.getOverallRating() *
                sneaker.getComfortRating() *
                sneaker.getDesignRating() *
                sneaker.getDurabilityRating() /
                sneaker.getPriceRating();

        int bigRating = (1 + sneaker.getCtr()) * (1 + sneaker.getConversion()) * 50;

        int rating = smallRating + bigRating;

        sneaker.setRating(rating);
        return sneaker;
    }

    public SneakerBuilder setOverallRating(int overallRating) {
        sneaker.setOverallRating(overallRating);
        return this;
    }

    public SneakerBuilder setComfortRating(int comfortRating) {
        sneaker.setComfortRating(comfortRating);
        return this;
    }

    public SneakerBuilder setDurabilityRating(int durabilityRating) {
        sneaker.setDurabilityRating(durabilityRating);
        return this;
    }

    public SneakerBuilder setMediumImage(String mediumImage) {
        sneaker.setMediumImage(mediumImage);
        return this;
    }

    public SneakerBuilder setDescription(String description) {
        sneaker.setDescription(description);
        return this;
    }

    public SneakerBuilder setImages(List<List<String>> images) {
        sneaker.setImages(images);
        return this;
    }

    public SneakerBuilder setVideoId(String videoId) {
        sneaker.setVideoId(videoId);
        return this;
    }

    public SneakerBuilder setName(String name) {
        sneaker.setName(name);
        return this;
    }

    public SneakerBuilder setPriceRating(int priceRating) {
        sneaker.setPriceRating(priceRating);
        return this;
    }

    public SneakerBuilder setDesignRating(int designRating) {
        sneaker.setDesignRating(designRating);
        return this;
    }

    public SneakerBuilder setReleaseYear(int releaseYear) {
        sneaker.setReleaseYear(releaseYear);
        return this;
    }

    public SneakerBuilder setUrl(String url) {
        sneaker.setUrl(url);
        return this;
    }

    public SneakerBuilder setImage(String image) {
        sneaker.setImage(image);
        return this;
    }

    public SneakerBuilder setRating(int rating) {
        sneaker.setRating(rating);
        return this;
    }

    public SneakerBuilder setCtr(int ctr) {
        sneaker.setCtr(ctr);
        return this;
    }

    public SneakerBuilder setConversion(int conversion) {
        sneaker.setConversion(conversion);
        return this;
    }
}
