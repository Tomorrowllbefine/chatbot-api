package com.kk.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.kk.chatbot.api.domain.ai.IOpenAI;
import com.kk.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.kk.chatbot.api.domain.ai.model.vo.Choice;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class OpenAI implements IOpenAI {

    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;


    @Override
    public String doChatGPT(String question) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 设置代理地址和端口号
        String proxyHost = "127.0.0.1";
        int proxyPort = 7890;

        // 设置代理
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        HttpPost post = new HttpPost("https://kkakoka.cn/v1/completions");
        post.setConfig(config);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + openAiKey);
        String jsonParam = "{\n" +
                "    \"prompt\": \""+ question +"\",\n" +
                "    \"model\": \"text-embedding-3-small\"\n" +
                "    \"temperature\": \"0\"\n" +
                "    \"max_tokens\": \"1024\"\n" +
                "  }";

        StringEntity stringEntity = new StringEntity(jsonParam, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        System.out.println(response.toString());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            // 回答串
            StringBuilder answers = new StringBuilder();
            List<Choice> choices = aiAnswer.getChoices();
            for(Choice choice : choices){
                answers.append(choice.getText());
            }
            return answers.toString();
        } else {
            log.error("error: {}", response.getStatusLine().getStatusCode() );
        }
        return null;
    }
}
