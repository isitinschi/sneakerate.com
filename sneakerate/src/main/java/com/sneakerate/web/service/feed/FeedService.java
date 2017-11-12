package com.sneakerate.web.service.feed;

import com.sneakerate.web.domain.Sneaker;
import com.sneakerate.web.domain.SpecialOffer;
import com.sneakerate.web.domain.Story;

import java.util.Collection;
import java.util.List;

public interface FeedService {
    void populate();
    List<Sneaker> getFeed();
    List<Sneaker> getTop();
    Sneaker getSneakerByUrl(String url);
    List<Story> getStories();
    Story getStoryByUrl(String url);
    SpecialOffer getOfferByUrl(String url);
    void updateSpecialOffers(List<SpecialOffer> specialOffers);
    Collection<SpecialOffer> getOffers();
}
