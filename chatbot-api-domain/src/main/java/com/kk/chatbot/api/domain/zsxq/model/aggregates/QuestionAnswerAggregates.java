package com.kk.chatbot.api.domain.zsxq.model.aggregates;

import com.kk.chatbot.api.domain.zsxq.model.res.RespData;

import java.util.List;

/**
 * 问答页面的聚合信息
 */
public class QuestionAnswerAggregates {

    private boolean success;
    private String errorMsg;
    private List<RespData> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<RespData> getData() {
        return data;
    }

    public void setData(List<RespData> data) {
        this.data = data;
    }
}
