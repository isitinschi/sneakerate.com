package com.sneakerate.web.controller.offer;

import com.sneakerate.web.controller.AbstractController;
import com.sneakerate.web.domain.SpecialOffer;
import com.sneakerate.web.service.feed.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/special-offer")
public class SpecialOfferController extends AbstractController {
    @Autowired
    @Qualifier(value = "inMemoryFeedServiceImpl")
    private FeedService feedService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialOfferController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllOffers(Model model)
    {
        LOGGER.info("Started processing request...");
        incrementQueryCount();

        Collection<SpecialOffer> offers = feedService.getOffers();

        model.addAttribute("offers", offers);

        LOGGER.info("Finished processing request");
        return "pages/all-offers";
    }

    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    public String getOffer(@PathVariable String url, Model model,
                           @RequestParam(value = "newPrice", required = false) String newPrice,
                           @RequestParam(value = "oldPrice", required = false) String oldPrice,
                           @RequestParam(value = "discount", required = false) String discount) {
        LOGGER.info("Started processing request...");
        incrementQueryCount();

        SpecialOffer offer = feedService.getOfferByUrl(url);

        if (offer == null) {
            throw new RuntimeException("Offer is not found for the url: " + url);
        }

        if (newPrice != null && !newPrice.isEmpty()) {
            offer.setNewPrice(newPrice);
        }
        if (oldPrice != null && !oldPrice.isEmpty()) {
            offer.setOldPrice(oldPrice);
        }
        if (discount != null && !discount.isEmpty()) {
            offer.setDiscount(discount);
        }

        model.addAttribute("offer", offer);

        LOGGER.info("Finished processing request");
        return "pages/special-offer";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Collection<SpecialOffer> getOffers() {
        return feedService.getOffers();
    }
}
