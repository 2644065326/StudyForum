package com.hbsf.login;

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

@ARouter(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity {
    private Button button1;
    private Context context;
    @Parameter
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterManager.getInstance()
                        .build("/home/HomeActivity")
                        .withString("name", "login")
                        .navigation(context);
            }
        });
        ParameterManager.getInstance().loadParameter(this);
        Log.e("ARouter data", name);

    }
}