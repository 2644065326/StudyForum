package com.hbsf.home.bean;

import java.util.List;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NewsListBean {

    private List<NewsBean> list;

    public List<NewsBean> getList() {
        return list;
    }

    public void setList(List<NewsBean> list) {
        this.list = list;
    }

    public class NewsBean {
        private String title;

        private String channelName;

        private List<String> imageurls = null;

        private String desc;

        private String channelId;

        public NewsBean(String title, String channelName, List<String> imageurls, String desc, String channelId) {
            this.title = title;
            this.channelName = channelName;
            this.imageurls = imageurls;
            this.desc = desc;
            this.channelId = channelId;
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

}
