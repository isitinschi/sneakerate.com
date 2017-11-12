package com.sneakerate.web.service.vote;

import com.sneakerate.web.domain.Question;
import com.sneakerate.web.domain.Sneaker;

import java.util.List;

public interface SidebarVoteService {
    void populate();
    List<Question> getQuestions();
    List<Question> getQuestions(Sneaker sneaker);
}
