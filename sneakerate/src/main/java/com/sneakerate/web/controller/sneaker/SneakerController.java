package com.sneakerate.web.controller.sneaker;

import com.sneakerate.web.controller.AbstractController;
import com.sneakerate.web.domain.Question;
import com.sneakerate.web.domain.Sneaker;
import com.sneakerate.web.service.feed.FeedService;
import com.sneakerate.web.service.vote.SidebarVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
@RequestMapping("/sneaker")
public class SneakerController extends AbstractController {
    @Autowired
    @Qualifier(value = "inMemoryFeedServiceImpl")
    private FeedService feedService;
    @Autowired
    private SidebarVoteService sidebarVoteService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SneakerController.class);

    @RequestMapping(value = "/{url}", method = RequestMethod.GET)
    public String getSneaker(@PathVariable String url, Model model)
    {
        LOGGER.info("Started processing request...");
        incrementQueryCount();

        Sneaker sneaker = feedService.getSneakerByUrl(url);

        if (sneaker == null) {
            String newUrl = url.replaceAll("_","-");
            sneaker = feedService.getSneakerByUrl(newUrl);

            if (sneaker == null) {
                throw new RuntimeException("Sneaker is not found for the url: " + url);
            } else {
                return "redirect:/sneaker/" + newUrl;
            }
        }

        Collection<Question> questions = sidebarVoteService.getQuestions(sneaker);

        model.addAttribute("sneaker", sneaker);
        model.addAttribute("questions", questions);
        model.addAttribute("images", sneaker.getImages());

        LOGGER.info("Finished processing request");
        return "pages/sneaker";
    }
}
