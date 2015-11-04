package com.greenpoint.patient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.greenpoint.patient.MyApplication;
import com.greenpoint.patient.R;
import com.greenpoint.patient.utils.AppUtil;
import com.greenpoint.patient.utils.LogUtil;
import com.igexin.sdk.PushManager;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;


/**
 * Created by kzm on 2015/10/12.
 */
public class LeadingActivity extends BaseActivity implements View.OnTouchListener,GestureDetector.OnGestureListener{

    // 当前翻页数
    private int currentItem = 0;
    // 监控屏幕的对象
    private GestureDetector mGestureDetector;

    private ImageView iv_point_0;

    private ImageView iv_point_1;

    private ImageView iv_point_2;

    private ImageView ivCurrentPoint;

    private ViewPager mViewPager;

    private int minVelocity = 0;

    // 垂直方向最小的距离
    private int verticalMinDistance = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化推送界面（开启个推服务）
//        initPush();
        LogUtil.i("LeadingActivity start...");

        // 如果不是第一次登录则直接进入主界面
        if(!getSharedPreferences("login",0).getBoolean("first",true)){
            animStartActivity(new Intent(getApplicationContext(),MainActivityPager.class));

            // 退出本次活动进入主活动界面
            finish();
        }

        // 如果不是第一次登录该APP
//        this.mGestureDetector = new GestureDetector(this);
        mGestureDetector = new GestureDetector(this, this);
        // 第一次登录则跳转到第一次登录布局
        setContentView(R.layout.lead_first);

        this.iv_point_0 = (ImageView)findViewById(R.id.iv_point_0);

        this.iv_point_1 = (ImageView)findViewById(R.id.iv_point_1);

        this.iv_point_2 = (ImageView)findViewById(R.id.iv_point_2);

        this.mViewPager = (ViewPager)findViewById(R.id.vp_lead_image);
        // 指定加载页数为3页(一次性全部重新加载)
        mViewPager.setOffscreenPageLimit(3);

        // 设置翻页格式的适配器
        mViewPager.setAdapter(new LeadPagerAdapter());

        // 设置当前翻页数(初始值为第一页)
        mViewPager.setCurrentItem(currentItem);
        // 默认选择第一幅图片
        iv_point_0.setSelected(true);

        // 当前指向第一幅图片
        ivCurrentPoint = iv_point_0;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 重载翻页方法
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            // 一幅新的图片被选中时回调该方法
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:// 选中第一幅图片时
                        ivCurrentPoint.setSelected(false);
                        iv_point_0.setSelected(true);
                        currentItem = 0;
                        break;
                    case 1: // 选中第二幅图片时
                        ivCurrentPoint.setSelected(false);
                        iv_point_1.setSelected(true);
                        currentItem = 1;
                        break;
                    case 2:
                        ivCurrentPoint.setSelected(false);
                        iv_point_2.setSelected(true);
                        currentItem = 2;
                        break;
                    default:
                        return;
                }
            }

            //
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);

        return super.dispatchTouchEvent(ev);
    }

    // 初始化推送界面方法
    private void initPush(){
        // 初始化个推SDK
        PushManager.getInstance().initialize(getApplicationContext());
        // 获取当前ClientID
        PushManager.getInstance().getClientid(MyApplication.getInstance());
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){ // 按下返回键时
//            return true;
            LeadingActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        LogUtils.i("onFling method invoke...");
        Log.i("每两秒水平方向上滑动的距离:", String.valueOf(velocityX));

        Log.i("当前页数:",String.valueOf(currentItem));

        Log.i("当前滑动距离:",String.valueOf(e1.getX() - e2.getX()));
        // 如果当前页数为2
        // 当前翻动页面x坐标之差大于20则直接跳转到主活动
        if((e1.getX() - e2.getX()>verticalMinDistance)&& (Math.abs(velocityX)>minVelocity)&&(currentItem == 2) ){

            // 跳转到主页面
            animStartActivity(new Intent(getApplicationContext(),MainActivityPager.class));
            // 结束当前活动
            finish();
        }
        return false;
    }

    private class LeadPagerAdapter extends PagerAdapter{

        private LeadPagerAdapter(){

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View)object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // 设置要展示的图片
            ImageView localImageView = new ImageView(LeadingActivity.this);

            ViewPager.LayoutParams localLayoutParams = new ViewPager.LayoutParams();

            // 充满容器
            localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

            // 充满容器
            localLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

            // 设置图片的参数
            localImageView.setLayoutParams(localLayoutParams);

            localImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            String str = ImageDownloader.Scheme.ASSETS.wrap("lead_" + position + ".png");

            ImageLoader.getInstance().displayImage(str, localImageView, AppUtil.getImageOptions(0));

            container.addView(localImageView);

            if(position == 2){
                localImageView.setOnTouchListener(LeadingActivity.this);
                localImageView.setLongClickable(true);
            }
            return localImageView;
        }
    }
}
