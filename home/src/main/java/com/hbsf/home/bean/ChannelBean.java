package com.hbsf.home.bean;

public class ChannelBean {

    private String channelId;

    private String name;

    public ChannelBean(String channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
