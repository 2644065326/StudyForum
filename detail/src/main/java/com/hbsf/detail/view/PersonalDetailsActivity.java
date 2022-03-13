package com.hbsf.detail.view;

import android.content.Context;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.arouter_annotation.Parameter;
import com.hbsf.arouter_api.manager.ParameterManager;
import com.hbsf.arouter_api.manager.RouterManager;
import com.hbsf.base.view.BaseActivity;
import com.hbsf.detail.R;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

@ARouter(path = "/detail/PersonalDetailsActivity")
public class PersonalDetailsActivity extends BaseActivity {

    @Parameter(name = "type")
    String type;
    @Parameter(name = "userId")
    String userId;

    private ImageView backBtn;
    private RecyclerView recyclerView;
    private ImageView issueBtn;
    private Context context;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_detail;
    }

    @Override
    public void initView() {
        ParameterManager.getInstance().loadParameter(this);
        backBtn = findViewById(R.id.back_btn);
        issueBtn =findViewById(R.id.issue_btn);
        context = this;
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.getInstance()
                        .build("/detail/IssueActivity")
                        .navigation(context);
            }
        });
        recyclerView = findViewById(R.id.list_view);
        recyclerView.setItemAnimator(new SlideInLeftAnimator(new OvershootInterpolator(0.5f)));

    }
}
