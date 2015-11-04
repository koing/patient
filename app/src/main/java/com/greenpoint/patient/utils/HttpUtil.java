package com.greenpoint.patient.utils;

import android.content.Context;

/**
 * Created by Administrator on 2015/10/14.
 */
public class HttpUtil {

    // 判断是否有网络状态
    public static boolean isNetworkAvailable(Context context){
        return NetworkHelper.isNetworkAvailable(context);
    }
}
