package com.hbsf.base.mvp.presenter;


import com.hbsf.base.model.BaseModel;
import com.hbsf.base.mvp.view.IBaseMVPView;

public abstract class BasePresenter<V extends IBaseMVPView, M extends BaseModel> {
    protected V mView;
    protected M mModel;

    public BasePresenter(V view) {
        attachView(view);
        mModel = getModel();
    }

    public void attachModel(M model) {
        this.mModel = model;
    }

    public abstract M getModel();
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
