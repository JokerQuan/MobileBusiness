package com.wq.mobilebusiness.home.smartlife;

/**
 * Created by 王铨 on 2016/4/10.
 */
public class Business {
    private String imgUrl;
    private String name;
    private String introduce;

    public Business(String imgUrl, String name, String introduce) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.introduce = introduce;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Business{" +
                "imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
