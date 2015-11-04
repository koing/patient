package com.greenpoint.patient.utils;

import android.app.Activity;
import android.content.Intent;

import com.greenpoint.patient.R;

/**
 * Created by Administrator on 2015/10/19.
 */
public class BaseUtil {

    /**
     * 带动画效果的启动活动
     * @param activity
     * @param intent
     */
    public  static void animStartActivity(Activity activity,Intent intent){
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.in_from_right,R.anim.out_from_right);
    }

    /**
     * 带有返回值的启动活动
     * @param activity
     * @param intent
     * @param requestCode
     */
    public static void animStartActivityForResult(Activity activity,Intent intent,int requestCode){

        activity.startActivityForResult(intent,requestCode);

        activity.overridePendingTransition(R.anim.in_from_right,R.anim.out_from_right);
    }
}
