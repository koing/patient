package com.greenpoint.patient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2015/10/14.
 */
public class NetworkHelper {

    // 判断当前是否联网
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager == null){
            return false;
        }else {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info!=null){
                for (int i = 0;i<info.length;i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
