package com.greenpoint.patient.utils;

import android.content.Context;

/**
 * 自定义异常处理类
 * @author kzm
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

    // 单例
    private static CrashHandler INSTANCE;

    // 上下文对象
    private Context context;

    public static CrashHandler getInstance(){
        try{
            if(INSTANCE == null){
                INSTANCE = new CrashHandler();
            }
            return INSTANCE;
        }finally {

        }
    }

    public void init(Context context){
        this.context = context;
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.e("程序挂掉了...");
        ex.printStackTrace();
//        File localFile = new File(Constant.ComValue.LOG);
    }
}
