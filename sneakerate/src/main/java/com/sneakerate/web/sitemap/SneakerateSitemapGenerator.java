package com.sneakerate.web.sitemap;

import com.sneakerate.web.domain.Sneaker;
import com.sneakerate.web.domain.Story;
import com.sneakerate.web.service.feed.FeedService;
import cz.jiripinkas.jsitemapgenerator.Image;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.WebPageBuilder;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class SneakerateSitemapGenerator {
    @Autowired
    private FeedService feedService;

    public void generate(String path, boolean validate) throws IOException {
        SitemapGenerator sitemapGenerator = new SitemapGenerator("https://www.sneakerate.com", new SitemapGenerator.AdditionalNamespace[]{SitemapGenerator.AdditionalNamespace.IMAGE});

        addTrendsPage(sitemapGenerator);
        addFeedPage(sitemapGenerator);
        addSneakerPages(sitemapGenerator);
        addStoriesPage(sitemapGenerator);
        addStoryPages(sitemapGenerator);

        File file = new File(path);
        sitemapGenerator.constructAndSaveSitemap(file);

        if (validate) {
            validate(path);
        }
    }

    public void validate(String path) throws IOException {
        validatePages(path);
        validateImages(path);
    }

    private void validatePages(String path) throws IOException {
        String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
        int pos = 0;
        while ((pos = content.indexOf("https://www.sneakerate.com/", pos)) != -1) {
            int end = content.indexOf('<', pos);
            String urlToCheck = content.substring(pos, end);
            URL u = new URL(urlToCheck);
            HttpURLConnection huc =  (HttpURLConnection)  u.openConnection ();
            huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect () ;
            int code = huc.getResponseCode() ;
            if (code != 200) {
                throw new RuntimeException("Corrupted URL: " + urlToCheck);
            }

            pos = end;
        }
    }

    private void validateImages(String path) throws IOException {
        String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
        int pos = 0;
        while ((pos = content.indexOf("https://www.img.sneakerate.com/", pos)) != -1) {
            int end = content.indexOf('<', pos);
            String urlToCheck = content.substring(pos, end);
            URL u = new URL(urlToCheck);
            HttpURLConnection huc =  (HttpURLConnection)  u.openConnection ();
            huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect () ;
            int code = huc.getResponseCode() ;
            if (code != 200) {
                throw new RuntimeException("Corrupted URL: " + urlToCheck);
            }

            pos = end;
        }
    }

    private void addSneakerPages(SitemapGenerator sitemapGenerator) {
        for (Sneaker sneaker : feedService.getFeed()) {
            WebPage webPage = new WebPageBuilder().name("sneaker/" + sneaker.getUrl()).priorityMax().changeFreqNever().lastModNow().build();

            for (List<String> images : sneaker.getImages()) {
                for (String imageUrl : images) {
                    Image image = new Image();
                    image.setLoc("https://www.img.sneakerate.com/" + imageUrl + ".jpg");
                    image.setTitle(imageUrl.replaceAll("-", " "));
                    image.setCaption(imageUrl.replaceAll("-", " "));
                    webPage.addImage(image);
                }
            }

            sitemapGenerator.addPage(webPage);
        }
    }

    private void addFeedPage(SitemapGenerator sitemapGenerator) {
        WebPage webPage = new WebPageBuilder().name("feed").priorityMax().changeFreqNever().lastModNow().build();

        List<Sneaker> sneakers = feedService.getFeed();
        for (Sneaker sneaker : sneakers) {
            Image image = new Image();
            image.setLoc("https://www.img.sneakerate.com/" + sneaker.getImage());
            image.setTitle(sneaker.getName());
            image.setCaption(sneaker.getName());

            webPage.addImage(image);
        }

        sitemapGenerator.addPage(webPage);
    }

    private void addTrendsPage(SitemapGenerator sitemapGenerator) {
        WebPage webPage = new WebPageBuilder().name("trends").priorityMax().changeFreqNever().lastModNow().build();

        List<Sneaker> sneakers = feedService.getTop();
        for (Sneaker sneaker : sneakers) {
            Image image = new Image();
            image.setLoc("https://www.img.sneakerate.com/" + sneaker.getImage());
            image.setTitle(sneaker.getName());
            image.setCaption(sneaker.getName());

            webPage.addImage(image);
        }

        sitemapGenerator.addPage(webPage);
    }

    private void addStoriesPage(SitemapGenerator sitemapGenerator) {
        WebPage webPage = new WebPageBuilder().name("stories").priorityMax().changeFreqNever().lastModNow().build();
        sitemapGenerator.addPage(webPage);
    }

    private void addStoryPages(SitemapGenerator sitemapGenerator) {
        Map<String, Set<String>> storyToImages = new HashMap<>();
        Set<String> pumaSuedeStoryImages = new HashSet<>();
        pumaSuedeStoryImages.add("puma-suede-story");
        pumaSuedeStoryImages.add("puma-suede-olympic-moment");
        pumaSuedeStoryImages.add("joey-brezinski-nosegrind");

        storyToImages.put("puma-suede", pumaSuedeStoryImages);


        for (Story story : feedService.getStories()) {
            WebPage webPage = new WebPageBuilder().name("story/" + story.getName()).priorityMax().changeFreqNever().lastModNow().build();

            Set<String> images = storyToImages.get(story.getName());
            if (images == null || images.isEmpty()) {
                throw new RuntimeException("The story should have images");
            }

            for (String imageUrl : images) {
                Image image = new Image();
                image.setLoc("https://www.img.sneakerate.com/" + imageUrl + ".jpg");
                image.setTitle(imageUrl.replaceAll("-", " "));
                image.setCaption(imageUrl.replaceAll("-", " "));
                webPage.addImage(image);
            }

            sitemapGenerator.addPage(webPage);
        }
    }
}
