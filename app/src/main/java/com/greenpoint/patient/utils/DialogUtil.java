package com.greenpoint.patient.utils;

import android.app.Activity;

import com.greenpoint.patient.dialogs.LoadingDialog;

/**
 * Created by Administrator on 2015/10/19.
 */
public class DialogUtil {

    public static LoadingDialog progressDialog;

    /**
     * 开始显示进度条对话框
     * @param activity
     */
    public static void startProgressDialog(Activity activity){
        if (progressDialog == null){
            progressDialog = LoadingDialog.createDialog(activity);
            progressDialog.show();
        }
    }

    /**
     * 取消进度条显示
     */
    public static void stopProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
