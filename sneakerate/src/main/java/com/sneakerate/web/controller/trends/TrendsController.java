package com.sneakerate.web.controller.trends;

import com.sneakerate.web.controller.AbstractController;
import com.sneakerate.web.domain.Question;
import com.sneakerate.web.domain.Sneaker;
import com.sneakerate.web.service.feed.FeedService;
import com.sneakerate.web.service.status.StatusService;
import com.sneakerate.web.service.vote.SidebarVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class TrendsController extends AbstractController {
    @Autowired
    @Qualifier(value = "inMemoryFeedServiceImpl")
    private FeedService feedService;
    @Autowired
    private SidebarVoteService sidebarVoteService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrendsController.class);

    @RequestMapping(value = "trends", method = RequestMethod.GET)
    public String getTrends(Model model)
    {
        LOGGER.info("Started processing request...");
        incrementQueryCount();

        Collection<Sneaker> sneakers = feedService.getTop();
        Collection<Question> questions = sidebarVoteService.getQuestions();

        model.addAttribute("sneakers", sneakers);
        model.addAttribute("questions", questions);

        LOGGER.info("Finished processing request");
        return "pages/trends";
    }
}
