package com.kk.chatbot.api.domain.zsxq;

import com.kk.chatbot.api.domain.zsxq.model.aggregates.QuestionAnswerAggregates;

import java.io.IOException;

public interface IZsxqApi {

    QuestionAnswerAggregates queryQuestionsAndAnswer(Integer questionId, String cookie)throws IOException;
    boolean answer(String cookie, Integer questionId, String answer) throws IOException;
}
