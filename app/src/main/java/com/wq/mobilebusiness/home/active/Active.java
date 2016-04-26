package com.wq.mobilebusiness.home.active;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class Active implements Serializable{
    private String imgUrl;
    private String introduce;

    public Active(String imgUrl, String introduce) {
        this.imgUrl = imgUrl;
        this.introduce = introduce;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Active{" +
                "imgUrl='" + imgUrl + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
