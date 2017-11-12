package com.sneakerate.web.crawler;

import com.sneakerate.web.crawler.domain.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AmazonOfferService {
    private List<Offer> offers;

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonOfferService.class);

    @PostConstruct
    public void populate() {
        LOGGER.info("Started populating offers...");

        offers = new ArrayList<>();
        offers.add(buildOffer("Adidas Stan Smith", "Men",
                "Originally created for tennis legend Stan Smith back in 1973, the men’s Stan Smith court shoe has since become an icon of style and comfort. Smooth full-grain leather combines with subtle perforated 3-Stripes and a tonal rubber outsole to give this adidas Originals low top the kind of pure, clean style that’s now legendary from courts to streets across the world.",
                "B00J5ILRJG"));
        offers.add(buildOffer("Puma Suede", "Men",
                "Meet the Suede. It's been kicking around for a long time. From '60s basketball warm-up shoe to '90s hip-hop kick, it's been worn by greats across generations and made its mark on a range of different scenes. All smooth suede, streetwise swagger, and sport-inspired style, it remains to this day PUMA's most epic sneaker icon.",
                "B0058XG2LA"));
        offers.add(buildOffer("Puma Ignite Evoknit", "Women",
                "The all-new IGNITE evoKNIT is a running shoe that knows no bounds. Its knitted, form-fitting upper is zoned for stretch and breathability. Its IGNITE Foam midsole provides exceptional cushioning and energy return. And its distinctive branded collar allows it to stand out in a crowd. Tackle whatever your city running route throws at you – and make your mark while you're at it.",
                "B01J5MS3RK"));
        offers.add(buildOffer("Adidas Superstar", "Men",
                "A contender since the '70s, the adidas Superstar sneaker was the first all-leather low-top basketball shoe. It dominated the court before crossing over to become a hip-hop footwear legend. Today it maintains its status as a cultural icon.",
                "B01IPJ3R6G", "B01HNBFYX6", "B00RLWOCJU", "B01I3ALVY2"));
        offers.add(buildOffer("Adidas Superstar", "Women",
                "A contender since the '70s, the adidas Superstar sneaker was the first all-leather low-top basketball shoe. It dominated the court before crossing over to become a hip-hop footwear legend. Today it maintains its status as a cultural icon.",
                "B01J4SIQLI"));
        offers.add(buildOffer("Adidas Stan Smith", "Women",
                "Originally created for tennis legend Stan Smith back in 1973, the men’s Stan Smith court shoe has since become an icon of style and comfort. Smooth full-grain leather combines with subtle perforated 3-Stripes and a tonal rubber outsole to give this adidas Originals low top the kind of pure, clean style that’s now legendary from courts to streets across the world.",
                "B01K8P56BY"));
        offers.add(buildOffer("Puma Ignite Evoknit", "Men",
                "The all-new IGNITE evoKNIT is a running shoe that knows no bounds. Its knitted, form-fitting upper is zoned for stretch and breathability. Its IGNITE Foam midsole provides exceptional cushioning and energy return. And its distinctive branded collar allows it to stand out in a crowd. Tackle whatever your city running route throws at you – and make your mark while you're at it.",
                "B01J5F21SE", "B01M18Q5K0"));
        offers.add(buildOffer("Nike Air Force 1", "Men",
                "Transcending foot coverage since 1982, this shoe was named after the aircraft that carries the U.S. President. It was the first basketball shoe to house Nike Air, revolutionising the game while rapidly gaining traction around the world. Today the Air Force 1 stays true to its roots with soft, springy cushioning and a massive midsole, but the Nike Air technology takes a backseat to the shoe's status as an icon.",
                "B001IDLGN8", "B015RULIGC"));
        offers.add(buildOffer("Nike Air Force 1 Ultra Flyknit", "Men",
                "The Nike Air Force 1 Ultra Flyknit Shoe weighs 50 percent less than the '82 hoops original thanks to its all-new, ultra-breathable Nike Flyknit upper. Strategically crafted Flyknit panels add dimension while remaining true to the AF1 design aesthetic. Woven Flyknit yarns integrate areas of high breathability, stretch and support where you need it most, conforming to the shape of your foot for a comfortable, lightweight feel. The lightweight, injected midsole includes an encapsulated Air-Sole unit in the heel for long-lasting cushioning and impact protection.",
                "B01N34NMGG", "B01GZQFFPW"));
        offers.add(buildOffer("Nike Hyperdunk 2016", "Men",
                "Control the game everytime you step on the hardwood in the Men's Nike Hyperdunk 2016 Basketball Shoes. This new Hyperdunk features a mid-top collar designed to offer comfortable support without restricting movement, while Zoom Air cushioning delivers a lightweight, responsive ride. The midtop design gives you comfortable support around the ankle, so you can defend even the toughest crossover. Flywire cables wrap the forefoot for a dynamic fit and lockdown. You'll love the low-profile responsiveness you get from the heel and forefoot Zoom Air units.",
                "B008BLDLC4"));
        offers.add(buildOffer("Nike Hyperdunk 2015", "Men",
                "The Nike Hyperdunk 2015 has definitely mixed it up when it comes to the style of the brands most notorious hoop shoe. The Nike Hyperdunk 2015 Basketball Shoe continues the legacy with an even lighter, more responsive ride and a dynamic upper that feel like a high-top but performs like a low-top. An extended sock-like ankle support that has been seen on other Nike Basketball styles. Nike Zoom cushioning for low-profile responsiveness on the court. Flywire cables wrap the forefoot for a dynamic fit and lockdown. Solid rubber outsole with herringbone pattern for durability and traction.",
                "B00RKDXSIG"));
        offers.add(buildOffer("Nike Hyperdunk 2014", "Men",
                "The Nike Hyperdunk 2014 Basketball Shoes boast a complex mix of the latest in hoops technology, inlcuding Hyperfuse construction and Flywire technology. Updated to include ultra-light lunarlon cushioning, the responsive Hyperdunk offers a plush feel without the extra bulk that can hold you back on the court. Durable and speedy, the Hyperfuse construction means even less weight. And of course, an advanced traction pattern on the sole means no slippage on the court. Stylish and fast, the Nike Hyperdunk 2014 Basketball Shoe is a must for versatile players who demand performance.",
                "B00JDN3NOK"));
        offers.add(buildOffer("Nike Lebron XIII", "Men",
                "LeBron James needs a shoe that can stand up to his powerful playing style. That means ultra-responsive cushioning, lock- down support, and protection from the rigors of the game. A one-piece sleeve made of breathable mesh offers sock-like comfort, flexibility, and zoned support for superior lockdown. Hyperposite material on the toe, midfoot and collar provides zoned protection where LeBron needs it most. Four low-profile Nike Zoom Air units deliver incredibly responsive cushioning to power LeBron’s every move.",
                "B015OT7R20"));
        offers.add(buildOffer("Adidas Gazelle", "Men",
                "Ultimate simplicity for three decades and counting. This version of the Gazelle honors the favorite version of 1991, with the same materials, colors, textures and proportions as the original. The leather upper features contrast 3-Stripes and heel tab that echo the early-'90s style.",
                "B01HLJHQE0"));
        offers.add(buildOffer("Adidas D Rose 5 Boost ", "Men",
                "Boost and basketball. Touted as Derrick Rose's most personal signature sneaker yet, this uber-cushy and energy-returning ball shoe features his signature Rose logo prominently on silhouette that decidedly is missing the Three Stripes. This is also the first signature model to incorporate the renowned Boost technology that trumps traditional EVA with more energy return, responsiveness, and comfort. Boost up your ball game with the D Rose 5 Boost.",
                "B00S1VXKU2"));

        verify(offers);

        LOGGER.info("Finished populating offers.");
    }

    private Offer buildOffer(String name, String gender, String description, String ... offerIds) {
        Offer offer = new Offer();
        offer.setName(name);
        offer.setGender(gender);
        offer.setDescription(description);

        for (String offerId : offerIds) {
            offer.addOfferId(offerId);
        }

        return offer;
    }

    private void verify(List<Offer> offers) {
        // check name and duplicates
        for (int i = 0; i < offers.size(); ++i) {
            Offer o1 = offers.get(i);
            for (int j = i + 1; j < offers.size(); ++j) {
                Offer o2 = offers.get(j);

                if (o1.getName().equals(o2.getName())) {
                    if (!o1.getDescription().equals(o2.getDescription())) {
                        throw new RuntimeException("Failed name verification");
                    } else if (o1.getGender().equals(o2.getGender())) {
                        throw new RuntimeException("Failed duplicates verification");
                    }
                }
            }
        }

        // check description
        for (int i = 0; i < offers.size(); ++i) {
            Offer o1 = offers.get(i);
            for (int j = i + 1; j < offers.size(); ++j) {
                Offer o2 = offers.get(j);

                if (o1.getDescription().equals(o2.getDescription()) && o1.getGender().equals(o2.getGender())) {
                    if (!o1.getName().equals(o2.getName())) {
                        LOGGER.error("o1 #{}[{}] conflicts with o2 #{}[{}]", i, o1.getName(), j, o2.getName());
                        throw new RuntimeException("Failed description verification");
                    }
                }
            }
        }

        // check offerId and gender
        Set<String> offerIds = new HashSet<>();
        for (Offer offer : offers) {
            if (!offer.getGender().equals("Men") && !offer.getGender().equals("Women")) {
                throw new RuntimeException("Failed gender verification");
            }
            for (String offerId : offer.getOfferIds()) {
                if (!offerIds.add(offerId)) {
                    throw new RuntimeException("Failed offerId verification");
                }
            }
        }
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
