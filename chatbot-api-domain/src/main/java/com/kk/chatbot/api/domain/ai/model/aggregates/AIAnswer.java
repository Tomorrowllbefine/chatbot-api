package com.kk.chatbot.api.domain.ai.model.aggregates;

import com.kk.chatbot.api.domain.ai.model.vo.Choice;

import java.util.List;

/**
 * AI回答的聚合对象
 */
public class AIAnswer {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choice> choices;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
