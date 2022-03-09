package com.hbsf.base.rx;

import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.bean.BaseObjectBean;
import com.hbsf.base.model.BaseModel;
import com.hbsf.base.mvp.view.IBaseMVPView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class BaseObserver<T extends BaseObjectBean, V extends IBaseMVPView, M extends IBaseModel> implements Observer<T> {
    private V mView;
    private M mModel;

    public BaseObserver(V view, M model) {
        this.mView = view;
        this.mModel = model;
    }

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mView.showLoading();
    }

    @Override
    public void onNext(@NonNull T t) {
        if (mModel == null) return;
        mModel.handleResult(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        mView.onError(e.getMessage());
        mView.hideLoading();
    }

    @Override
    public void onComplete() {
        mView.hideLoading();
    }
}
