package com.sneakerate.web.service.vote.impl;

import com.sneakerate.web.domain.Question;
import com.sneakerate.web.domain.Sneaker;
import com.sneakerate.web.service.feed.FeedService;
import com.sneakerate.web.service.feed.impl.InMemoryFeedServiceImpl;
import com.sneakerate.web.service.vote.SidebarVoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SidebarVoteServiceImpl implements SidebarVoteService {
    @Autowired
    @Qualifier(value = "inMemoryFeedServiceImpl")
    private FeedService feedService;
    private List<String> questions;
    private Random random = new Random();

    private int nextQuestion = -1;

    private static final Logger LOGGER = LoggerFactory.getLogger(SidebarVoteServiceImpl.class);

    @Override
    @PostConstruct
    public void populate() {
        LOGGER.info("Started populating sidebar vote data...");

        questions = new ArrayList<>();

        questions.add("Do you have these sneakers?");
        questions.add("Do you like the design of these sneakers?");
        questions.add("Have you ever seen these sneakers before?");
        questions.add("Would you like to have these sneakers?");

        LOGGER.info("Finished populating sidebar vote data");
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questionsToAsk = new ArrayList<>();
        questionsToAsk.add(getNextQuestion());
        questionsToAsk.add(getNextQuestion());

        return questionsToAsk;
    }

    @Override
    public List<Question> getQuestions(Sneaker sneaker) {
        List<Question> questionsToAsk = new ArrayList<>();
        questionsToAsk.add(getNextQuestion(sneaker));
        questionsToAsk.add(getNextQuestion(sneaker));

        return questionsToAsk;
    }

    private Question getNextQuestion() {
        List<Sneaker> sneakers = feedService.getFeed();
        Sneaker sneaker = sneakers.get(random.nextInt(sneakers.size()));

        return getNextQuestion(sneaker);
    }

    private Question getNextQuestion(Sneaker sneaker) {
        if (nextQuestion == questions.size() - 1) {
            nextQuestion = 0;
        } else {
            ++nextQuestion;
        }

        Question question = new Question();
        question.setQuestion(questions.get(nextQuestion));
        question.setTitle(sneaker.getName());
        question.setImage(sneaker.getImage());
        question.setUrl(sneaker.getUrl());

        return question;
    }
}
