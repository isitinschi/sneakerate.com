package com.sneakerate.web.controller.stories;

import com.sneakerate.web.controller.AbstractController;
import com.sneakerate.web.domain.Story;
import com.sneakerate.web.service.feed.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class StoriesController extends AbstractController {
    @Autowired
    @Qualifier(value = "inMemoryFeedServiceImpl")
    private FeedService feedService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StoriesController.class);

    @RequestMapping(value = "stories", method = RequestMethod.GET)
    public String getStoris(Model model)
    {
        LOGGER.info("Started processing request...");
        incrementQueryCount();

        List<Story> stories = feedService.getStories();

        model.addAttribute("stories", stories);

        LOGGER.info("Finished processing request");
        return "pages/stories";
    }

    @RequestMapping(value = "story/{url}", method = RequestMethod.GET)
    public String getStory(@PathVariable String url, Model model)
    {
        LOGGER.info("Started processing request...");
        incrementQueryCount();

        Story story = feedService.getStoryByUrl(url);

        if (story == null) {
            throw new RuntimeException("Story is not found for the url: " + url);
        }

        model.addAttribute("story", story);

        LOGGER.info("Finished processing request");
        return "pages/story";
    }
}
