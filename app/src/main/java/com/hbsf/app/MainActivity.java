package com.hbsf.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.arouter_annotation.Parameter;
import com.hbsf.arouter_api.manager.ParameterManager;
import com.hbsf.arouter_api.manager.RouterManager;
import com.hbsf.common.home.HomeService;
import com.hbsf.common.login.LoginService;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Parameter(name = "/login/LoginServiceImpl")
    LoginService loginService;
    @Parameter(name = "/home/HomeServiceImpl")
    HomeService homeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RouterManager.getInstance()
                .build("/login/LoginActivity")
                .navigation(this);

        ParameterManager.getInstance().loadParameter(this);
        loginService.printf();
        homeService.printf();

    }
}