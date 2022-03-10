package com.hbsf.home.bean;

import java.util.ArrayList;
import java.util.List;

public class NewsChannelsListBean {
    private List<NewsChannelsListBean.ChannelBean> list;

    public List<ChannelBean> getList() {
        return list;
    }
    public NewsChannelsListBean() {
        list = new ArrayList<>();
    }
    public NewsChannelsListBean(List<ChannelBean> list) {
        this.list = list;
    }

    public void setList(List<ChannelBean> list) {
        this.list = list;
    }

    public ChannelBean returnChannelBean(String channelId, String name) {
        return new NewsChannelsListBean.ChannelBean(channelId, name);
    }

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
}
