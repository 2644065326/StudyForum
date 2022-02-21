package com.hbsf.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.arouter_annotation.Parameter;
import com.hbsf.arouter_api.manager.ParameterManager;
import com.hbsf.arouter_api.manager.RouterManager;
import com.hbsf.common.bean.TestBean;
import com.hbsf.common.home.HomeService;
import com.hbsf.common.login.LoginService;

import java.io.Serializable;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Context context;
    @Parameter(name = "/login/LoginServiceImpl")
    LoginService loginService;
    @Parameter(name = "/home/HomeServiceImpl")
    HomeService homeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        button1 = findViewById(R.id.button1);
        TestBean testBean = new TestBean();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterManager.getInstance()
                        .build("/login/LoginActivity")
                        .withSerializable("testBean", testBean)
                        .withString("name", "app")
                        .navigation(context);
            }
        });

        ParameterManager.getInstance().loadParameter(this);
        loginService.printf();
        homeService.printf();

    }
}