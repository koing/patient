package com.greenpoint.patient.utils;

import com.greenpoint.patient.Constant.Constant;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/10/20.
 */
public final class HttpHelper {
    /** 编码格式*/
    private static final String CHARSET = "utf-8";

    /** 连接超时时间*/
    private static final int CONNECT_TIMEOUT = 5000;

    /** 错误信息*/
    private static final String ERROR_MESSAGE = "网络出错，请重新尝试";
    /** 请求超时时间*/
    private static final int REQUEST_TIMEOUT = 8000;
    /** 超时时间*/
    private static final int TIMEOUT = 3000;
    /** 超时错误信息*/
    private static final String TIMEOUT_MESSAGE = "连接超时，请重新尝试";
    /** 是否为头部刷新*/
    private static boolean isHeadUpdate = false;
    /** 返回码*/
    private static String returnCode = "";
    /** 返回信息*/
    private static String returnMessage = "请求出错，请重新尝试";

    public static String getReturnCode(){

        return returnCode;
    }


    public static JSONObject onSuccess(JSONObject jsonObject){
        JSONObject header = setReuestHeader();

        JSONObject object1;
        try {
            String str = DES3CBCUtil.des3EncodeCBC(header.toString(), Constant.ComValue.DES3KEY,Constant.ComValue.DES3IV);

            object1 = jsonByPost(Constant.ComValue.url,str);

            LogUtils.v("Async..." + object1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    /** 不带编码格式的post请求*/
    public static JSONObject jsonByPost(String url,String msg){
        return jsonByPost(url,msg,"utf-8");

    }

    /** 使用带utf-8编码格式的post请求*/
    public static JSONObject jsonByPost(String url,String msg,String charset){
        String str = stringByPost(url,msg,charset);

        if (str != null){
            if (!str.isEmpty()){
                try {
                    JSONObject object = new JSONObject(AppUtil.decompress(str.getBytes()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static String stringByPost(String url,String msg,String charset){
        InputStream in = doPost(url,msg,charset);

        // 将输入流转化为字符串
        String str = StreamHelper.streamToString(in, charset);
        return str;
    }


    public static InputStream doPost(String url,String msg,String charset){

        InputStream inputStream = null;

        if (AppUtil.networkisConnected()){
            BasicHttpParams params = new BasicHttpParams();
            if (!isHeadUpdate){
                ConnManagerParams.setTimeout(params,3000);
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params,8000);
            }else {
                try {
                    DefaultHttpClient client = new DefaultHttpClient(params);
                    HttpPost post = new HttpPost(url);
                    post.setEntity(new StringEntity(msg,charset));
                    inputStream = client.execute(post).getEntity().getContent();
                    isHeadUpdate = false;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            returnCode = "900";
            returnMessage = "网络出错，请重新重试";
        }

        return inputStream;
    }
    private static JSONObject setReuestHeader(){

        return null;
    }
}
