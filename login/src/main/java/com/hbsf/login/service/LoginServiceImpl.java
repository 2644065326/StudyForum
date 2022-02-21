package com.hbsf.login.service;

import android.util.Log;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.common.login.LoginService;

@ARouter(path = "/login/LoginServiceImpl")
public class LoginServiceImpl implements LoginService {
    @Override
    public void printf() {
        Log.e("成功调用", "login");
    }
}
