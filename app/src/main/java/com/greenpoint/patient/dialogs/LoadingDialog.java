package com.greenpoint.patient.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.greenpoint.patient.R;

/**
 * Created by Administrator on 2015/10/12.
 */
public class LoadingDialog extends Dialog {

    private static LoadingDialog customProgressDialog = null;

    static Animation imgAnim;
    // 构造方法
    public LoadingDialog(Context context) {
        super(context);
    }


    public LoadingDialog(Context context,int paramInt){
        super(context,paramInt);
    }

    // 创建对话框
    public static LoadingDialog createDialog(Context context){
        // 创建对话框的样式
        customProgressDialog = new LoadingDialog(context,R.style.CustomProgressDialog);
        // 设置填充布局
        customProgressDialog.setContentView(R.layout.loading_dialog);
        // 设置属性居中显示
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        // 加载动画
        imgAnim = AnimationUtils.loadAnimation(context,R.anim.loading_animation);

        return customProgressDialog;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (customProgressDialog == null){
            return;
        }
        (customProgressDialog.findViewById(R.id.loadingImageView)).startAnimation(imgAnim);
    }
}
