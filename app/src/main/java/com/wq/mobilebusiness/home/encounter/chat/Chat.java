package com.wq.mobilebusiness.home.encounter.chat;

/**
 * Created by 王铨 on 2016/4/17.
 */
public class Chat {
    private String fromWho;
    private String headUrl;
    private String date;
    private String content;

    public Chat(String fromWho, String headUrl, String date, String content) {
        this.fromWho = fromWho;
        this.headUrl = headUrl;
        this.date = date;
        this.content = content;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "fromWho='" + fromWho + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }


}
