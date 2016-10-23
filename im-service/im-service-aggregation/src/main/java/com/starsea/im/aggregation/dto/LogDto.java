package com.starsea.im.aggregation.dto;

/**
 * Created by Administrator on 2016/9/16.
 */
public class LogDto {
    private String openId;
    private String childOpenId;
    private String title;
    private String labels;
    private String content;
    private String time;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getChildOpenId() {
        return childOpenId;
    }

    public void setChildOpenId(String childOpenId) {
        this.childOpenId = childOpenId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
