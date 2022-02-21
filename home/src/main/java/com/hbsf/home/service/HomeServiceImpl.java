package com.hbsf.home.service;

import android.util.Log;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.common.home.HomeService;

@ARouter(path = "/home/HomeServiceImpl")
public class HomeServiceImpl implements HomeService {
    @Override
    public void printf() {
        Log.e("成功调用", "home");
    }
}
