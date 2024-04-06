package com.kk.chatbot.api.test;

import cn.hutool.json.JSONObject;
import org.apache.hc.client5.http.ssl.HttpsSupport;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ApiTest {

    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://localhost:8080/qa/manage/list");
        get.addHeader("cookie", "Idea-2869c094=1798a07d-504d-4a0d-988a-0878c98f5b00; JSESSIONID=5EF1D15E88955743D766980F4C7F42C1");
        get.addHeader("Content-Type","application/json");
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/qa/manage/answer");
        post.addHeader("Cookie","Idea-2869c094=1798a07d-504d-4a0d-988a-0878c98f5b00; JSESSIONID=5EF1D15E88955743D766980F4C7F42C1");
        post.addHeader("Content-Type","application/json");
        JSONObject requestBody = new JSONObject();
        requestBody.put("id",4);
        requestBody.put("answer","一次编译，到处运行");
        StringEntity stringEntity = new StringEntity(requestBody.toString(),ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}


