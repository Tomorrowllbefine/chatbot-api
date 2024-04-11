package com.kk.chatbot.api.application.job;

import com.alibaba.fastjson.JSON;
import com.kk.chatbot.api.domain.ai.IOpenAI;
import com.kk.chatbot.api.domain.zsxq.IZsxqApi;
import com.kk.chatbot.api.domain.zsxq.model.aggregates.QuestionAnswerAggregates;
import com.kk.chatbot.api.domain.zsxq.model.req.ReqData;
import com.kk.chatbot.api.domain.zsxq.model.res.RespData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@Slf4j
@EnableScheduling
@Configuration
public class ChatbotSchedule {

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {

        try {
            if(new Random().nextBoolean()){
                log.info("随机打烊中...");
                return;
            }
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour < 7 || hour >22){
                log.info("{} 打烊时间不工作， AI休息了！");
                return;
            }

            // 检索问题
            QuestionAnswerAggregates questionAnswerAggregates = zsxqApi.queryQuestionsAndAnswer(1, cookie);
            log.info("检索结果: {}", JSON.toJSON(questionAnswerAggregates));

            List<RespData> dataList = questionAnswerAggregates.getData();
            if(dataList == null || CollectionUtils.isEmpty(dataList)){
                log.error("本次检索未查询到问题");
                return;
            }

            // AI 回答问题
            RespData question = dataList.get(0);
            String answer = openAI.doChatGPT(question.getContent().trim());

            // 问题回复
            boolean status = zsxqApi.answer(cookie, question.getId(), answer);
            log.info("问题编号:{}  问题:{}  回答:{}  状态:{}",question.getId(), question.getContent(),question.getAnswer(),answer, status);
            if(status){
                // Todo 答案存入数据库
            }
        } catch (IOException e) {
            log.error("自动回答问题异常 ",e);
        }


    }
}
