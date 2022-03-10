package com.hbsf.base.mvp.view;

import androidx.lifecycle.Lifecycle;

import com.hbsf.base.utils.DialogUtils;
import com.hbsf.base.view.BaseFragment;
import com.hbsf.base.mvp.presenter.BasePresenter;

import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;

public abstract class BaseMVPFragment<T extends BasePresenter> extends BaseFragment implements IBaseMVPView {

    protected T mPresenter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
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

    }
}
