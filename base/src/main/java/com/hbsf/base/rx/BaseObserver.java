package com.hbsf.base.rx;

import com.hbsf.base.api.IBaseView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseObserver<T, V extends IBaseView> implements Observer<T> {
    private V mView;

    public BaseObserver(V view) {
        this.mView = view;
    }

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mView.showLoading();
    }

    @Override
    public void onNext(@NonNull T t) {
        success(t);
    }

    public abstract void success(@NonNull T t);

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
