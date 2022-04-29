package com.hbsf.base.bean;

import com.hbsf.base.api.IBaseBean;

import java.util.List;

public class BaseArrayBean<T> extends BaseBean{

    /**
     * status : 1
     * msg : 获取成功
     * result : [] 数组
     */


    private List<T> result;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}