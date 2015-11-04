package com.greenpoint.patient.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.greenpoint.patient.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * 系统常用工具类
 */
public class AppUtil {


    public static DisplayImageOptions getImageOptions(int paramInt){
//        return new DisplayImageOptions.Builder().showImageForEmptyUri(paramInt).showImageOnFail(paramInt).showImageOnLoading(paramInt).resetViewBeforeLoading(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).build();
        return new DisplayImageOptions.Builder().showImageForEmptyUri(paramInt).showImageOnFail(paramInt).resetViewBeforeLoading(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
//        return null;
    }

    // 验证字符串是否有效
    public static boolean isInvalidate(String str){

        return (str != null) && (str.length() >= 1) && (!"null".equals(str));
    }

    // 验证List是否有效
    public static <T> boolean isInvalidate(List<T> params){
        return (params != null) && (params.size() >1);
    }

    /**
     * 验证给定的map值是否有效
     * @param paramMap
     * @param <T>
     * @return
     */
    public static <T> boolean isInvalide(Map<String, T> paramMap)
    {
        return (paramMap != null) && (paramMap.size() >= 1);
    }

    /**
     * 判断JSON数组的有效性
     * @param data
     * @return
     */
    public static boolean isInvalidate(JSONArray data){
        return (data != null)&&(data.length() >= 1);
    }

    /**
     * 判断网络是否连接
     * @return
     */
    public static boolean networkisConnected(){

        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager == null){
            return false;
        }
        NetworkInfo info;

        info = manager.getActiveNetworkInfo();
        if((!info.isAvailable())|| (info == null)){
            return false;
        }
        return true;
    }

    public static void toastMessage(String msg){
        try
        {
            Toast localToast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_LONG);
            (localToast.getView()).setPadding(px2dip(30.0F), px2dip(50.0F), px2dip(28.0F), px2dip(50.0F));
            localToast.setGravity(17, 0, 0);
            localToast.show();
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    // 根据传入的电话号码呼叫
    public static void makePhoneCall(Context context,String telNo){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + telNo));
        context.startActivity(intent);
    }

    public static int px2dip(float paramFloat)
    {
        return (int)(0.5F + paramFloat * MyApplication.getInstance().getResources().getDisplayMetrics().density);
    }

    public static String decompress(byte[] bytes){

        return "";
    }
}
