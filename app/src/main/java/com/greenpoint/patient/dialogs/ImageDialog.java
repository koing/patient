package com.greenpoint.patient.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.greenpoint.patient.R;

/**
 * Created by Administrator on 2015/10/13.
 */
public class ImageDialog extends Dialog {

    private static ImageDialog mImageDialog = null;

    public static ImageView ivLead;

    public ImageDialog(Context context) {
        super(context);
    }

    // 带主题的构造函数
    public ImageDialog(Context context,int theme){
        super(context,theme);
    }

    public static ImageDialog createDialog(Context context){

        mImageDialog = new ImageDialog(context, R.style.CustomAlterDialog);

        // 动态加载开始界面布局
        View view = LayoutInflater.from(context).inflate(R.layout.lead_page,null);

        mImageDialog.setContentView(view);

        // 设置重力属性居中
        mImageDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        // 设置占据整个父容器
        mImageDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        ivLead = (ImageView)mImageDialog.findViewById(R.id.iv_lead);
        return mImageDialog;
    }
}
