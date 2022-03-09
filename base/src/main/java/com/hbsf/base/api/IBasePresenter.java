package com.hbsf.base.api;

import com.hbsf.base.mvp.view.IBaseMVPView;

public interface IBasePresenter<V extends IBaseMVPView, M extends IBaseModel> {
    void attachModel(M model);

    M getModel();
    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    void attachView(V view);

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    void detachView();

    void detachModel();
    /**
     * View是否绑定
     *
     * @return
     */
    boolean isViewAttached();

    boolean isModelAttached();
}
