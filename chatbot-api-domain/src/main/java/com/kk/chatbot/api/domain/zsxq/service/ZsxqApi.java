package com.kk.chatbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.kk.chatbot.api.domain.zsxq.IZsxqApi;
import com.kk.chatbot.api.domain.zsxq.model.aggregates.QuestionAnswerAggregates;
import com.kk.chatbot.api.domain.zsxq.model.req.AnswerReq;
import com.kk.chatbot.api.domain.zsxq.model.req.ReqData;
import com.kk.chatbot.api.domain.zsxq.model.res.AnswerResp;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZsxqApi implements IZsxqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public QuestionAnswerAggregates queryQuestionsAndAnswer(Integer questionId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://localhost:8080/qa/manage/list");
        get.addHeader("cookie", cookie);
        get.addHeader("Content-Type","application/json");
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据 jsonStr: {}",jsonStr);
            return JSON.parseObject(jsonStr, QuestionAnswerAggregates.class);
        }else{
            logger.error("error: {}", response.getStatusLine().getStatusCode() );
            throw new RuntimeException("queryQuestionAnswer Err: " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String cookie, Integer questionId, String answer) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/qa/manage/answer");
        post.addHeader("Cookie",cookie);
        post.addHeader("Content-Type","application/json");
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

        // 组装AnswerReq
        AnswerReq answerReq = new AnswerReq(new ReqData(questionId, answer));
        String paramJson = JSONObject.toJSONString(answerReq);
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            AnswerResp answerResp = JSON.parseObject(res, AnswerResp.class);
            logger.info("回答问题结果 res: {}",res);
            return answerResp.isSucceeded();
        }else{
            logger.error("error: {}", response.getStatusLine().getStatusCode() );
            throw new RuntimeException("answer Err: " + response.getStatusLine().getStatusCode());
        }

    }
}
