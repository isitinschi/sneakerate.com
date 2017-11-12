package com.sneakerate.web.populator.feed;

import com.sneakerate.web.domain.Sneaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DescriptionPopulator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DescriptionPopulator.class);

    public void populate(Map<String, Sneaker> urlToSneaker) {
        LOGGER.info("Started populating description data...");

        urlToSneaker.get("nike-air-max-90").setDescription("Nike's revolutionary Air-Sole unit made its way into Nike footwear in the late '70s. In 1987, the Nike Air Max 1 debuted with visible air in its heel, allowing fans more than just the feel of Air-Sole comfort—suddenly they could see it. Since then, next-generation Nike Air Max shoes have become a hit with athletes and collectors by offering striking color combinations and reliable, lightweight cushioning.");
        urlToSneaker.get("puma-kylie-jenner").setDescription("Bring out your fiercer side. Inspired by dance movements and designed with rigorous training needs in mind, the Fierce tackles design with a play on proportions and style. It's heavy on the material mix but light on weight with technical details that ensure it's up for every training challenge. It's unexpected. It's a gamechanger. And it's ready to take sport to the extreme.");
        urlToSneaker.get("nike-air-jordan-1").setDescription("In 1985, the Air Jordan I forever changed footwear. Since his game-winning shot that brought championship glory to North Carolina, Michael Jordan has been at the forefront of basketball consciousness. He took the court in 1985 wearing the original Air Jordan I, simultaneously breaking league rules and his opponents' will while capturing the imagination of fans worldwide.");
        urlToSneaker.get("nike-air-force-1").setDescription("Transcending foot coverage since 1982, this shoe was named after the aircraft that carries the U.S. President. It was the first basketball shoe to house Nike Air, revolutionising the game while rapidly gaining traction around the world. Today the Air Force 1 stays true to its roots with soft, springy cushioning and a massive midsole, but the Nike Air technology takes a backseat to the shoe's status as an icon.");
        urlToSneaker.get("adidas-cloudfoam-flyer").setDescription("Comfort and lightness makes the feeling of walking on the clouds. All thanks to cloudfoam midsole which provides a superior cushioning. More quiet than flashy design is good for a casual sporty style. Running, walking, traveling and shopping is a real pleasure in the shoes that you do not feel on your feet. Adidas Cloudfoam Flyer is sneakers for active people who will not stay long at one place.");
        urlToSneaker.get("adidas-superstar").setDescription("A contender since the '70s, the adidas Superstar sneaker was the first all-leather low-top basketball shoe. It dominated the court before crossing over to become a hip-hop footwear legend. Today it maintains its status as a cultural icon.");
        urlToSneaker.get("converse-chuck-taylor-all-star").setDescription("Created in 1917 as a non-skid basketball shoe, the All Star was originally promoted for its superior court performance by basketball mastermind Chuck Taylor. But over the decades, something incredible happened: The sneaker, with its timeless silhouette and unmistakable ankle patch, was organically adopted by rebels, artists, musicians, dreamers, thinkers and originals.");
        urlToSneaker.get("vans-authentic").setDescription("Vans The Authentic, Vans original and now iconic style, is a simple low top, lace-up with durable canvas upper, metal eyelets, Vans flag label and Vans original Waffle Outsole.");
        urlToSneaker.get("puma-ignite-evoknit").setDescription("The all-new IGNITE evoKNIT is a running shoe that knows no bounds. Its knitted, form-fitting upper is zoned for stretch and breathability. Its IGNITE Foam midsole provides exceptional cushioning and energy return. And its distinctive branded collar allows it to stand out in a crowd. Tackle whatever your city running route throws at you – and make your mark while you're at it.");
        urlToSneaker.get("new-balance-574").setDescription("Classic. Expressive. Inspired. Created in 1988 by combining 2 different NB sneakers, the 574 has become a symbol of ingenuity and originality.");
        urlToSneaker.get("adidas-stan-smith").setDescription("Originally created for tennis legend Stan Smith back in 1973, the men’s Stan Smith court shoe has since become an icon of style and comfort. Smooth full-grain leather combines with subtle perforated 3-Stripes and a tonal rubber outsole to give this adidas Originals low top the kind of pure, clean style that’s now legendary from courts to streets across the world.");
        urlToSneaker.get("puma-suede").setDescription("Meet the Suede. It's been kicking around for a long time. From '60s basketball warm-up shoe to '90s hip-hop kick, it's been worn by greats across generations and made its mark on a range of different scenes. All smooth suede, streetwise swagger, and sport-inspired style, it remains to this day PUMA's most epic sneaker icon.");

        LOGGER.info("Finished populating description data.");
    }
}
