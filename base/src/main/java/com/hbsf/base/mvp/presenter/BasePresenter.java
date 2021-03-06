package com.hbsf.base.mvp.presenter;


import com.hbsf.base.api.IBaseModel;
import com.hbsf.base.api.IBasePresenter;
import com.hbsf.base.mvp.view.IBaseMVPView;

public abstract class BasePresenter<V extends IBaseMVPView, M extends IBaseModel> implements IBasePresenter<V, M> {
    protected V mView;
    protected M mModel;

    public BasePresenter(V view, boolean isAutoBindModel) {
        attachView(view);
        if (isAutoBindModel) {
            mModel = getModel();
        }
    }

    public BasePresenter(V view) {
        this(view, true);
    }
    public void attachModel(M model) {
        this.mModel = model;
    }

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    public void detachView() {
        this.mView = null;
    }

    public void detachModel() {
        this.mModel = null;
    }
    /**
     * View是否绑定
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    public boolean isModelAttached() {
        return mModel != null;
    }


}
