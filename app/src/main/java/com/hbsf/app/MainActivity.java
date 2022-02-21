package com.hbsf.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.arouter_annotation.Parameter;
import com.hbsf.arouter_api.manager.RouterManager;


@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {
    @Parameter
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RouterManager.getInstance()
                .build("/login/LoginActivity")
                .withString("name", "zjm")
                .navigation(this);
    }
}