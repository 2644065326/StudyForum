package com.hbsf.home.bean;

import com.hbsf.base.api.IBaseBean;

import java.util.List;

public class NewsBean implements IBaseBean {

    private String newsId;

    private String title;

    private String channelName;

    private List<String> imageurls = null;

    private String desc;

    private String channelId;

    private String type;

    public NewsBean(String title, String channelName, List<String> imageurls, String desc, String channelId, String type) {
        this.title = title;
        this.channelName = channelName;
        this.imageurls = imageurls;
        this.desc = desc;
        this.channelId = channelId;
        this.type = type;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<String> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<String> imageurls) {
        this.imageurls = imageurls;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
