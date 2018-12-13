package com.infox.messagebook.modules.wechat;/**
 * Created by Chenrongrong on 2018/12/13.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Article {
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;

    public Article(String title, String description, String picUrl, String url) {
        Title = title;
        Description = description;
        PicUrl = picUrl;
        Url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
