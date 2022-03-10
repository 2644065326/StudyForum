package com.hbsf.login.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbsf.arouter_annotation.ARouter;
import com.hbsf.arouter_api.manager.RouterManager;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.mvp.view.BaseMVPActivity;
import com.hbsf.base.utils.KeyBoardUtils;
import com.hbsf.base.utils.StringUtils;
import com.hbsf.base.utils.ToastUtils;
import com.hbsf.common.utils.UserUtils;
import com.hbsf.login.R;
import com.hbsf.login.api.LoginContract;
import com.hbsf.login.bean.LoginBean;
import com.hbsf.login.presenter.LoginPresenter;

@ARouter(path = "/login/LoginActivity")
public class LoginActivity extends BaseMVPActivity<LoginContract.Persenter> implements LoginContract.View{
    private Button button;
    private EditText nameEdit;
    private EditText passwordEdit;
    private LoginContract.Persenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        button = findViewById(R.id.login_button);
        nameEdit = findViewById(R.id.name_edit);
        passwordEdit = findViewById(R.id.password_edit);
        presenter = new LoginPresenter(this);
        RouterManager.getInstance()
                .build("/home/HomeActivity")
                .withString("name", "home")
                .navigation(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (StringUtils.isEmpty(userName) ||  StringUtils.isEmpty(password)) {
                    ToastUtils.showShort(getApplicationContext(), "账号和密码不能为空");
                } else {
                    presenter.login(userName, password);
                }
            }
        });
    }

    @Override
    public void loginSuccess() {
        RouterManager.getInstance()
                .build("/home/HomeActivity")
                .withString("name", "home")
                .navigation(this);
        finish();
    }

    @Override
    public void loginFail(String msg) {
        ToastUtils.showShort(getApplicationContext(), msg);
        passwordEdit.setText("");
    }


}