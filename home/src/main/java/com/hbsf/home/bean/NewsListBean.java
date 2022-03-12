package com.hbsf.home.bean;

import com.hbsf.base.api.IBaseBean;

import java.util.List;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NewsListBean {

    private List<NewsBean> list;

    private String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<NewsBean> getList() {
        return list;
    }

    public void setList(List<NewsBean> list) {
        this.list = list;
    }

    public class NewsBean implements IBaseBean {

        private String newsId;

        private String title;

        private String channelName;

        private List<String> imageurls = null;

        private String desc;

        private String channelId;

        private String type;

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

}
