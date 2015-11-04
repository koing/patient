package com.greenpoint.patient;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.baidu.mapapi.SDKInitializer;
import com.greenpoint.patient.utils.CrashHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * @Title MyApplication.java
 * @author kzm
 * @description 应用程序的主程序
 * @version 1.0
 */
public class MyApplication extends Application {

    // 单例变量
    private static MyApplication instance;

    // 当前应用程序版本号
    private String version;

    // activity启动栈  记录栈中的activity实例
    private static ArrayList<Activity> activityStack;

    public static MyApplication getInstance() {
        return instance;
    }

    // 初始化ImageLoader(此处必须有)
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration localImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(3) // 线程池内加载的数量
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的URI名称用MD5 加密
                .discCacheSize(2*1024*1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO) // 先进后出模式
                .memoryCache(new WeakMemoryCache())
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(localImageLoaderConfiguration);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        // 初始化百度地图显示
        SDKInitializer.initialize(this);
        activityStack = new ArrayList<Activity>();
        try {
            this.version = Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        instance = this;
        // 异常处理方法
        initMyCrashHandler();
    }

    // 初始化程序异常捕获
    private void initMyCrashHandler() {
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());
        // 异常处理设置到主线程中
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    /**
     * 将应用程序的任务栈中的一activity实例添加到activitystack中
     *
     * @param activity
     *            一个activity实例
     */
    public static void addActivity2Stack(Activity activity) {
        instance.activityStack.add(activity);
    }

    /**
     * 经activity从activitystack中移除该activity
     * @param activity
     *      一个activity实例
     */
    public static void removeFromStack(Activity activity){
        instance.activityStack.remove(activity);
    }

    // 销毁所有的Activity 直接退出应用程序
    public static void finishAll() {

        if (activityStack != null) {
            for (Activity activity : activityStack) {
                activity.finish();
            }
            activityStack.clear();
        }
    }

    // 应用程序整体销毁的时候调用此方法
    @Override
    public void onTerminate() {

        // 程序安全退出
        MyApplication.finishAll();
        super.onTerminate();
    }
}
