package com.sneakerate.web.service.feed.impl;

import com.sneakerate.util.service.S3Service;
import com.sneakerate.web.domain.*;
import com.sneakerate.web.populator.feed.DescriptionPopulator;
import com.sneakerate.web.populator.feed.ImagePopulator;
import com.sneakerate.web.service.feed.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InMemoryFeedServiceImpl implements FeedService {
    @Autowired
    private ImagePopulator imagePopulator;
    @Autowired
    private DescriptionPopulator descriptionPopulator;
    @Autowired
    private S3Service<SpecialOffer> s3Service;

    private List<Sneaker> sneakers;
    private List<Sneaker> top;
    private Map<String, Sneaker> urlToSneaker;
    private List<Story> stories;
    private Map<String, Story> urlToStory;
    private Map<String, SpecialOffer> urlToOffer = new HashMap<>();
    private SpecialOfferComparator specialOfferComparator = new SpecialOfferComparator();

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryFeedServiceImpl.class);

    @Override
    public List<Sneaker> getFeed() {
        return sneakers;
    }

    @Override
    public List<Sneaker> getTop() {
        return top;
    }

    public Sneaker getSneakerByUrl(String url) {
        return urlToSneaker.get(url);
    }

    @Override
    public List<Story> getStories() {
        return stories;
    }

    @Override
    public Story getStoryByUrl(String url) {
        return urlToStory.get(url);
    }

    @Override
    public void updateSpecialOffers(List<SpecialOffer> specialOffers) {
        for (SpecialOffer specialOffer : specialOffers) {
            urlToOffer.put(specialOffer.getUrl(), specialOffer);
        }
    }

    @Override
    public Collection<SpecialOffer> getOffers() {
        List<SpecialOffer> offers = urlToOffer.values().stream().collect(Collectors.toList());
        Collections.sort(offers, specialOfferComparator.reversed());

        return offers;
    }

    @Override
    public SpecialOffer getOfferByUrl(String url) {
        return urlToOffer.get(url);
    }

    @Override
    @PostConstruct
    public void populate() {
        LOGGER.info("Started populating data...");

        sneakers = new ArrayList<>();

        sneakers.add(new SneakerBuilder()
                .setName("Adidas Cloudfoam Flyer")
                .setOverallRating(4)
                .setDesignRating(4)
                .setComfortRating(5)
                .setDurabilityRating(3)
                .setPriceRating(3)
                .setReleaseYear(2016)
                .setUrl("adidas-cloudfoam-flyer")
                .setImage("adidas-cloudfoam-flyer.jpg")
                .setMediumImage("adidas-cloudfoam-flyer.jpg?w=512")
                .setVideoId("_TDr60O9EBs")
                .setCtr(9)
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Nike Air Force 1")
                .setOverallRating(3)
                .setDesignRating(3)
                .setComfortRating(3)
                .setDurabilityRating(4)
                .setPriceRating(4)
                .setReleaseYear(1982)
                .setUrl("nike-air-force-1")
                .setImage("nike-air-force-1.jpg")
                .setMediumImage("nike-air-force-1.jpg?w=512")
                .setVideoId("IVw1snCZ_Js")
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Puma Ignite Evoknit")
                .setOverallRating(4)
                .setDesignRating(4)
                .setComfortRating(4)
                .setDurabilityRating(3)
                .setPriceRating(5)
                .setReleaseYear(2016)
                .setUrl("puma-ignite-evoknit")
                .setImage("puma-ignite-evoknit.jpg")
                .setMediumImage("puma-ignite-evoknit.jpg?w=512")
                .setVideoId("S-AmA3fsnWE")
                .setCtr(7)
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Puma Kylie Jenner")
                .setOverallRating(3)
                .setDesignRating(4)
                .setComfortRating(3)
                .setDurabilityRating(3)
                .setPriceRating(4)
                .setReleaseYear(2016)
                .setUrl("puma-kylie-jenner")
                .setImage("puma-kylie-jenner.jpg")
                .setMediumImage("puma-kylie-jenner.jpg?w=512")
                .setVideoId("Dlq5s9hq4aA")
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Adidas Superstar")
                .setOverallRating(4)
                .setDesignRating(4)
                .setComfortRating(3)
                .setDurabilityRating(4)
                .setPriceRating(4)
                .setReleaseYear(1970)
                .setUrl("adidas-superstar")
                .setImage("adidas-superstar.jpg")
                .setMediumImage("adidas-superstar.jpg?w=512")
                .setVideoId("TI-C0ZYq6pA")
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Nike Air Max 90")
                .setOverallRating(4)
                .setDesignRating(4)
                .setComfortRating(3)
                .setDurabilityRating(4)
                .setPriceRating(4)
                .setReleaseYear(1990)
                .setUrl("nike-air-max-90")
                .setImage("nike-air-max-90.jpg")
                .setMediumImage("nike-air-max-90.jpg?w=512")
                .setVideoId("QOeGOVb1-jM")
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Converse All Star")
                .setOverallRating(4)
                .setDesignRating(3)
                .setComfortRating(3)
                .setDurabilityRating(5)
                .setPriceRating(3)
                .setReleaseYear(1917)
                .setUrl("converse-chuck-taylor-all-star")
                .setImage("converse-chuck-taylor-all-star.jpg")
                .setMediumImage("converse-chuck-taylor-all-star.jpg?w=512")
                .setVideoId("PWVyPeGaKJ0")
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Nike Air Jordan 1")
                .setOverallRating(4)
                .setDesignRating(4)
                .setComfortRating(3)
                .setDurabilityRating(4)
                .setPriceRating(5)
                .setReleaseYear(1985)
                .setUrl("nike-air-jordan-1")
                .setImage("nike-air-jordan-1.jpg")
                .setMediumImage("nike-air-jordan-1.jpg?w=512")
                .setVideoId("UvEAF2RHPOg")
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Vans Authentic")
                .setOverallRating(3)
                .setDesignRating(3)
                .setComfortRating(2)
                .setDurabilityRating(4)
                .setPriceRating(2)
                .setReleaseYear(1966)
                .setUrl("vans-authentic")
                .setImage("vans-authentic.jpg")
                .setMediumImage("vans-authentic.jpg?w=512")
                .setVideoId("p5n2x0ox47w")
                .setCtr(3)
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("New Balance 574")
                .setOverallRating(4)
                .setDesignRating(3)
                .setComfortRating(3)
                .setDurabilityRating(4)
                .setPriceRating(3)
                .setReleaseYear(1988)
                .setUrl("new-balance-574")
                .setImage("new-balance-574.jpg")
                .setMediumImage("new-balance-574.jpg?w=512")
                .setVideoId("Mj-MWQpur6U")
                .setCtr(4)
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Adidas Stan Smith")
                .setOverallRating(4)
                .setDesignRating(4)
                .setComfortRating(3)
                .setDurabilityRating(4)
                .setPriceRating(3)
                .setReleaseYear(1973)
                .setUrl("adidas-stan-smith")
                .setImage("adidas-stan-smith.jpg")
                .setMediumImage("adidas-stan-smith.jpg?w=512")
                .setVideoId("m4zfm2IFn8I")
                .setCtr(4)
                .build()
        );

        sneakers.add(new SneakerBuilder()
                .setName("Puma Suede")
                .setOverallRating(4)
                .setDesignRating(4)
                .setComfortRating(3)
                .setDurabilityRating(4)
                .setPriceRating(3)
                .setReleaseYear(1968)
                .setUrl("puma-suede")
                .setImage("puma-suede.jpg")
                .setMediumImage("puma-suede.jpg?w=512")
                .setVideoId("sCjCum1YDhA")
                .setCtr(19)
                .setConversion(1)
                .build()
        );

        Collections.sort(sneakers, Collections.reverseOrder());

        urlToSneaker = new HashMap<>();
        for (Sneaker sneaker : sneakers) {
            urlToSneaker.put(sneaker.getUrl(), sneaker);
        }

        imagePopulator.populate(urlToSneaker);
        descriptionPopulator.populate(urlToSneaker);

        top = sneakers.stream().limit(10).collect(Collectors.toList());

        stories = new ArrayList<>();

        String keywords = "puma suede story, puma suede origin story, puma suede history, puma clyde, puma sneakers, puma trainers";
        String description = "The Puma Suede Story — Almost 50 years of iconic style. Considered as a simple and comfortable shoe, it became a perfect lifestyle sneaker. Its thick, rubber sole and rounded silhouette crafted in tough suede — it is impossible to walk around any city and do not see it at least once. A witness to many defining moments in history, it has been on top of trends almost 50 years since it first hit our shelves. Though it’s now deeply rooted in street culture, the Suede’s history actually begins in the world of professional sports. Let’s retrace the steps of this absolutely necessary item in any fashionable collection to find out how it was involved in the birth and growth of an entire musical genre, became one of the first ever sporting endorsements and was part of an iconic Olympic moment.";
        stories.add(new Story("puma-suede", "The Puma Suede Story", keywords, description));

        urlToStory = new HashMap<>();
        for (Story story : stories) {
            urlToStory.put(story.getName(), story);
        }

        List<SpecialOffer> specialOffers = s3Service.downloadSpecialOffers(SpecialOffer.class);
        updateSpecialOffers(specialOffers);

        validate();

        LOGGER.info("Finished populating data");
    }

    private void validate() {
        if (sneakers == null || sneakers.size() < 10) {
            throw new RuntimeException("Wrong feed data!");
        }

        if (top == null || top.size() < 5) {
            throw new RuntimeException("Wrong feed data!");
        }

        if (!sneakers.containsAll(top)) {
            throw new RuntimeException("Wrong feed data!");
        }

        for (Sneaker sneaker : sneakers) {
            if (!sneaker.validate()) {
                throw new RuntimeException("Wrong feed data!");
            }
        }

        if (stories == null || stories.isEmpty()) {
            throw new RuntimeException("Wrong feed data!");
        }

        for (Story story : stories) {
            if (!story.equals(urlToStory.get(story.getName()))) {
                throw new RuntimeException("Wrong feed data!");
            }
            story.validate();
        }
    }
}
