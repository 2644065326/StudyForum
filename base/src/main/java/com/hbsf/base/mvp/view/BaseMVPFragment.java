package com.hbsf.base.mvp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.utils.DialogUtils;
import com.hbsf.base.utils.ToastUtils;
import com.hbsf.base.view.BaseFragment;


import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;

public abstract class BaseMVPFragment<T extends IBasePresenter> extends BaseFragment implements IBaseMVPView {

    protected T mPresenter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName(), "onCreateView");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName(), "onPause");
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void showLoading() {
        DialogUtils.showDialog(getContext());
    }

    @Override
    public void hideLoading() {
        DialogUtils.closeDialog();
    }

    @Override
    public void onError(String errMessage) {
        ToastUtils.showShort(getContext(), errMessage);
    }
}
