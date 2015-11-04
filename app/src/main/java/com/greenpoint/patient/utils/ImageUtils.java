package com.greenpoint.patient.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.lidroid.xutils.util.LogUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/10/23.
 */
public class ImageUtils {

    /**
     * 获取图片的绝对路径
     * @param activity
     * @param uri
     * @return
     */
    public static String getAbsoluteImagePath(Activity activity,Uri uri){
        Cursor cursor = activity.managedQuery(uri,new String[]{"_data"},null,null,null);

        String str = "";
        if (cursor!=null){
            int i = cursor.getColumnIndexOrThrow("_data");

            if ((cursor.getCount() >0)&&(cursor.moveToFirst())){
                str = cursor.getString(i);
            }
        }

        return str;

    }

    /**
     * 从当前uri中获取绝对路径地址
     * @param uri
     * @return
     */
    public static String getAbsolutePathFromNoStandUri(Uri uri) {

        String str1 = Uri.decode(uri.toString());

        String str2 = "file:///sdcard" + File.separator;

        String str3 = "file:///mnt/sdcard" + File.separator;

        String str4 = "";

        if (str1.startsWith(str2)) {
            str4 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + str1.substring(str2.length());

        }else if (str1.startsWith(str3)){
            str4 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + str1.substring(str3.length());
        }

        LogUtils.v(">>>>当前地址为:" + str4);
        return str4;
    }
}
