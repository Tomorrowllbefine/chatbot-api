package com.kk.chatbot.api.domain.zsxq.model.req;

/**
 * 请求问题的数据
 */
public class ReqData {

    private Integer id;
    private String answer;

    public ReqData(Integer id, String answer) {
        this.id = id;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
