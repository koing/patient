package com.greenpoint.patient.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenpoint.patient.R;


public class PictureAlterDialog extends Dialog {

    private static PictureAlterDialog customAlterDialog = null;

    public static TextView ivFilePhoto;

    public static TextView ivTakePhoto;

    public static RelativeLayout llDialog;

    public PictureAlterDialog(Context context) {
        super(context);
    }

    public PictureAlterDialog(Context context,int style){
        super(context,style);
    }

    public static PictureAlterDialog createDialog(Context context){
        customAlterDialog = new PictureAlterDialog(context, R.style.CustomAlterDialog);

        View view = LayoutInflater.from(context).inflate(R.layout.alterdialog_pic,null);

        customAlterDialog.setContentView(view);

        Window window = customAlterDialog.getWindow();

        window.getAttributes().gravity = Gravity.BOTTOM;
        window.getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        window.getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setWindowAnimations(R.style.dialogWindowAnim);
        ivTakePhoto = (TextView) view.findViewById(R.id.tv_take_pic);
        ivFilePhoto = (TextView)view.findViewById(R.id.tv_take_file);

        llDialog = (RelativeLayout)view.findViewById(R.id.ll_pic_dialog);
        return customAlterDialog;
    }

}
