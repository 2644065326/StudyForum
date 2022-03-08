package com.hbsf.base.utils;

import android.content.Context;
import android.graphics.Color;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

public class DialogUtils {
    private ZLoadingDialog dialog;

    private static volatile DialogUtils instance;

    private static DialogUtils getInstance() {
        if (instance == null) {
            synchronized (DialogUtils.class) {
                if (instance == null) {
                    instance = new DialogUtils();
                }
            }
        }
        return instance;
    }
    public static void showDialog(Context context) {
        DialogUtils instance = getInstance();
        if (instance == null) {
            return;
        }
        instance.dialog = new ZLoadingDialog(context);
        instance.dialog.setLoadingBuilder(Z_TYPE.CIRCLE)//设置类型
                .setLoadingColor(Color.WHITE)//颜色
                .setHintText("Loading...")
                .setCancelable(false)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .show();

    }

    public static void closeDialog() {
        DialogUtils instance = getInstance();
        if (getInstance() == null || getInstance().dialog == null) {
            return;
        }
        instance.dialog.cancel();

    }

    public ZLoadingDialog getDialog() {
        return dialog;
    }
}
