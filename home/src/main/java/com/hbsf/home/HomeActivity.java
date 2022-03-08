package com.hbsf.home;

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

@ARouter(path = "/home/HomeActivity")
public class HomeActivity extends AppCompatActivity {
    private Button button1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button1 = findViewById(R.id.button1);
        context = this;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}