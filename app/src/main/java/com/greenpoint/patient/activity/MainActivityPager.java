package com.greenpoint.patient.activity;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.greenpoint.patient.R;
import com.greenpoint.patient.dialogs.ImageDialog;
import com.greenpoint.patient.fragment.FgHospitals;
import com.greenpoint.patient.fragment.FgRecords;
import com.greenpoint.patient.fragment.FgUserCenter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class MainActivityPager extends BaseFragmentActivity implements
        View.OnClickListener, AMapLocationListener {

    // 欢迎界面显示
    private ImageDialog imageDialog;

    private FragmentManager fragmentManager;

    private FgHospitals fgHospital;

    private FgRecords fgRecord;

    private FgUserCenter fgUserCenter;

    @ViewInject(R.id.ll_hospital)
    private LinearLayout llHospitals;

    @ViewInject(R.id.ll_records)
    private LinearLayout llRecords;

    @ViewInject(R.id.ll_usercenter)
    private LinearLayout llUserCenter;

    @ViewInject(R.id.iv_hospital)
    private ImageView ivHospital;

    @ViewInject(R.id.iv_records)
    private ImageView ivRecord;

    @ViewInject(R.id.iv_usercenter)
    private ImageView ivUserCenter;

    @ViewInject(R.id.tv_hospital)
    private TextView tvHospital;

    @ViewInject(R.id.tv_records)
    private TextView tvRecord;

    @ViewInject(R.id.tv_usercenter)
    private TextView tvUserCenter;


    // 定位服务类
    // 这个定位类允许应用定时更新获取设备的地理位置，
    // 或者当这个设备进入指定的地理位置时，启动一个应用指定的Intent
    private LocationManagerProxy locationManagerProxy;

    Thread threadDialog = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //TODO 使欢迎界面消失
            imageDialog.dismiss();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.index_main);

        ViewUtils.inject(this);
        // 实例化fragmentManager
        fragmentManager = getSupportFragmentManager();

        ibRight.setImageResource(R.drawable.notification);

        ibRight.setOnClickListener(this);
        // 判断是否为第一次登录
        isFirstLogin();
        // 初始化地理位置
//        initLocation();

        initView();
        // 第一次启动时选择第一个Tab
        setFragmentSelection(1, false);

    }

    /**
     * 设置监听事件
     */
    private void initView() {
        llHospitals.setOnClickListener(this);
        llRecords.setOnClickListener(this);
        llUserCenter.setOnClickListener(this);
    }

    private void setFragmentSelection(int index, boolean flag) {
        // 开始对Fragment进行一系列的操作
        FragmentTransaction ft = fragmentManager.beginTransaction();

        hideFragments(ft);

        setTabSelection(index);
        setActionbar(index);
        switch (index) {
            case 1: // 当前选中医院标签页的时候
                if (fgHospital == null) { // 当前标签页内容为空时
                    fgHospital = new FgHospitals();
                    ft.add(R.id.fragment, fgHospital);
                }
                ft.show(fgHospital);
//                else {
//                    fgHospital.onRefreshView();
//                }


                break;
            case 2:
                if (fgRecord == null){
                    fgRecord = new FgRecords();
                    ft.add(R.id.fragment,fgRecord);
                }
                ft.show(fgRecord);
                break;
            case 3:

                if (fgUserCenter == null){
                    fgUserCenter = new FgUserCenter();
                    ft.add(R.id.fragment,fgUserCenter);
                }
                ft.show(fgUserCenter);
                break;
            default:
                if (flag) {
                    ft.commitAllowingStateLoss();
                }
                break;
        }
        ft.commit();
    }

    // 选择的标签页
    private void setTabSelection(int index) {
        clearSelected();

        switch (index) {
            case 1:
                llHospitals.setSelected(true);
                ivHospital.setSelected(true);
                tvHospital.setSelected(true);
                break;
            case 2:
                llRecords.setSelected(true);
                ivRecord.setSelected(true);
                tvRecord.setSelected(true);
                break;
            case 3:
                llUserCenter.setSelected(true);
                ivUserCenter.setSelected(true);
                tvUserCenter.setSelected(true);
                break;
        }
    }


    /**
     * 每次选中之前先清除掉上次的选中状态
     */
    private void clearSelected() {
        llHospitals.setSelected(false);
        ivHospital.setSelected(false);
        tvHospital.setSelected(false);

        llRecords.setSelected(false);
        ivRecord.setSelected(false);
        tvRecord.setSelected(false);

        llUserCenter.setSelected(false);
        ivUserCenter.setSelected(false);
        tvUserCenter.setSelected(false);
    }

    //
    private void setActionbar(int index) {

        switch (index) {
            case 1:// 当前选择医院Fragment时

                llHospital.setClickable(true);
                // 定位按钮可见
                ivChoice.setVisibility(View.VISIBLE);

                ibLeft.setVisibility(View.VISIBLE);

                ibLeft.setEnabled(true);

                ibLeft.setImageResource(R.drawable.hospital_location);
                setTitles();
                break;
            case 2:// 当前选择记录

                llHospital.setClickable(false);

                ivChoice.setVisibility(View.GONE);

                ibLeft.setVisibility(View.INVISIBLE);

                ibLeft.setEnabled(false);

                tvTitle.setText(R.string.title_query_records);

                break;

            case 3:
                llHospital.setClickable(false);

                ivChoice.setVisibility(View.GONE);

                ibLeft.setVisibility(View.INVISIBLE);

                ibLeft.setEnabled(false);

                tvTitle.setText(R.string.users);

                break;
            default:
                break;
        }

    }

    // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
    private void hideFragments(FragmentTransaction ft) {
        if (fgHospital != null) {
            ft.hide(fgHospital);
        }
        if (fgRecord != null) {
            ft.hide(fgRecord);
        }
        if (fgUserCenter != null) {
            ft.hide(fgUserCenter);
        }
    }

    // 设置标题
    private void setTitles() {

        // 暂时设置默认的标题 后续从后台获取
        tvTitle.setText("选择医院");
    }

    // 判断是否第一次登录
    private void isFirstLogin() {
        SharedPreferences preferences = getSharedPreferences("login", 0);
        if (preferences.getBoolean("first", true)) {
            preferences.edit().putBoolean("first", false).apply();
            return;
        }
        // 如果不是第一次登录则显示欢迎界面
        startLeadDialog();
    }

    // 初始化位置信息
    private void initLocation() {
        locationManagerProxy = LocationManagerProxy.getInstance(this);
        // 使用高德地图进行定位
        locationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 500L, 50.0F, this);
    }

    // 开始欢迎界面
    private void startLeadDialog() {
        if (imageDialog == null) {
            imageDialog = ImageDialog.createDialog(this);
        }
        ImageDialog.ivLead.setBackgroundResource(R.drawable.leadpage);
        imageDialog.show();
        threadDialog.start();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_hospital:

                setFragmentSelection(1, false);
                break;
            case R.id.ll_records:
                LogUtils.i("点击记录...");
                setFragmentSelection(2, false);

                break;
            case R.id.ll_usercenter:

                setFragmentSelection(3, false);
                break;
        }
    }

    // 停止定位
    private void stopLocation() {
        if (locationManagerProxy != null) {
            // 移除给定的listener 位置更新
            locationManagerProxy.removeUpdates(this);
            // 销毁对象
            locationManagerProxy.destroy();
        }
    }

    // 暂停状态停止定位
    @Override
    protected void onPause() {
        super.onPause();
        stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ibRight.setImageResource(R.drawable.notification);
    }

}
