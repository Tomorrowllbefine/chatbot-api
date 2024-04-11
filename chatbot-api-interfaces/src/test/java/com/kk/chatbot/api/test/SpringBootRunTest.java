package com.kk.chatbot.api.test;

import com.alibaba.fastjson.JSON;
import com.kk.chatbot.api.domain.ai.IOpenAI;
import com.kk.chatbot.api.domain.zsxq.IZsxqApi;
import com.kk.chatbot.api.domain.zsxq.model.aggregates.QuestionAnswerAggregates;
import com.kk.chatbot.api.domain.zsxq.model.res.RespData;
import com.kk.chatbot.api.domain.zsxq.service.ZsxqApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Test
    public void test_zsxqApi() throws IOException {
        QuestionAnswerAggregates questionAnswerAggregates = zsxqApi.queryQuestionsAndAnswer(1, cookie);
        logger.info("测试结果: {}", JSON.toJSON(questionAnswerAggregates));
        List<RespData> data = questionAnswerAggregates.getData();
        for(RespData item : data){
            Integer qId = item.getId();
            String content = item.getContent();
            logger.info("问题id: {}, 问题内容: {}", qId, content);

            // Todo 调用GPT的接口获取答案

            boolean answer = zsxqApi.answer(cookie, qId, content);
            logger.info("回答情况: {}",answer);

        }
    }

    @Test
    public void test_openAi() throws IOException {
        String response = openAI.doChatGPT("帮我写一个冒泡排序");
        logger.info("测试结果: {}", response);
    }

}
