package com.hbsf.base.mvp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.utils.DialogUtils;
import com.hbsf.base.utils.ToastUtils;
import com.hbsf.base.view.BaseActivity;



import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;

public abstract class BaseMVPActivity<T extends IBasePresenter> extends BaseActivity implements IBaseMVPView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        super.onDestroy();
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void showLoading() {
        DialogUtils.showDialog(this);
    }

    @Override
    public void hideLoading() {
        DialogUtils.closeDialog();
    }

    @Override
    public void onError(String errMessage) {
        ToastUtils.showShort(this, errMessage);
    }
}