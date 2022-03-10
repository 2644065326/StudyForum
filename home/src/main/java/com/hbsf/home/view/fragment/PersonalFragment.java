package com.hbsf.home.view.fragment;

import android.view.View;
import android.widget.TextView;

import com.hbsf.base.mvp.view.BaseMVPFragment;
import com.hbsf.home.R;

public class PersonalFragment extends BaseMVPFragment {

    private TextView tvNext;

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }
}
