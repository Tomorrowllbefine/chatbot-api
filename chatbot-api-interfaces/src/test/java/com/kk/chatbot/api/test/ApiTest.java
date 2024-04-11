package com.kk.chatbot.api.test;

import cn.hutool.json.JSONObject;
//import com.theokanning.openai.completion.chat.ChatCompletionRequest;
//import com.theokanning.openai.completion.chat.ChatCompletionResult;
//import com.theokanning.openai.completion.chat.ChatMessage;
//import com.theokanning.openai.completion.chat.ChatMessageRole;
//import com.theokanning.openai.service.OpenAiService;
import org.apache.hc.client5.http.ssl.HttpsSupport;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void test_chatGPT() throws IOException  {
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
        post.addHeader("Authorization","Bearer sk-xxx");
        String jsonParam = "{\n" +
                "    \"prompt\": \"什么是分布式CAS策略？\",\n" +
                "    \"model\": \"text-embedding-3-small\"\n" +
                "    \"temperature\": \"0\"\n" +
                "    \"max_tokens\": \"1024\"\n" +
                "  }";

        StringEntity stringEntity = new StringEntity(jsonParam,ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        //CloseableHttpResponse response = httpClient.execute(post);
        HttpResponse response = httpClient.execute(post);
        System.out.println(response.toString());
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }


//    @Test
//    public void test_chatGPT1(){
//        ChatMessage[] promt = {
//                new ChatMessage(ChatMessageRole.SYSTEM.value(), "什么是分布式CAS策略？"),
//                new ChatMessage(ChatMessageRole.USER.value(), "什么是java虚拟机？")
//        };
//        ChatCompletionResult result = createChatCompletion(Arrays.asList(promt));
//        String resp = result.getChoices().get(0).getMessage().getContent();
//        System.out.println("回答: " + resp );
//    }
//
//
//    private ChatCompletionResult createChatCompletion(List<ChatMessage> messages){
//        OpenAiService service = new OpenAiService("sk-xxx");
//        ChatCompletionRequest build = ChatCompletionRequest.builder()
//                .model("gpt-3.5-turbo")
//                .temperature(0.0D)
//                .topP(1.0)
//                .messages(messages)
//                .build();
//        return service.createChatCompletion(build);
//    }

    @Test
    public void test_chatGPT2() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://kkakoka.cn/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer sk-xxx");

        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }
}


