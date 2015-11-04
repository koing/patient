package com.greenpoint.patient.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.greenpoint.patient.adapter.BaseFragmentPagerAdapter;
import com.greenpoint.patient.fragment.FgIntroduction;
import com.greenpoint.patient.fragment.FgNavigation;
import com.greenpoint.patient.fragment.FgTraffic;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 医院介绍Activity
 */
public class HospitalActivity extends BaseFragmentActivity implements View.OnClickListener{

    // 医院介绍
    private static final int INTRODUCTION = 0;
    // 交通信息
    private static final int TRAFFIC = 1;
    // 院内导航
    private static final int NAVAGATION = 2;

    private String cityname="南京";

    private String mLatitude;

    private String mLatLag;

    private FgIntroduction fgIntroduction;

    private FgTraffic fgTraffic;

    private FgNavigation fgNavigation;

    private BaseFragmentPagerAdapter pagerAdapter;

    // 医院图标
    @ViewInject(R.id.iv_hospital)
    private ImageView ivPhoto;

    // 医院地址
    private String mAddress;

    private String hspid;

    // 医院电话
    private String mHspTel;

    // 医院名称
    private String mHspName;

    // 医院介绍
    private String mIntroduction;

    // 交通信息
    private String mTraffic;

    @ViewInject(R.id.relativelayout)
    private RelativeLayout rlayout;

    @ViewInject(R.id.tv_address_content)
    private TextView tvAddress;

    @ViewInject(R.id.tv_grade_content)
    private TextView tvGrade;

    @ViewInject(R.id.tv_intro)
    private TextView tvIntro;

    @ViewInject(R.id.tv_hospital_name)
    private TextView tvName;

    @ViewInject(R.id.tv_navigation)
    private TextView tvNavigation;

    @ViewInject(R.id.tv_phone_content)
    private TextView tvPhone;

    @ViewInject(R.id.tv_traffic)
    private TextView tvTraffic;

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hospital);

        // 实例化控件
        ViewUtils.inject(this);

        tvTitle.setText(R.string.title_hospital_intr);

        ibRight.setImageResource(R.drawable.star);
        ibLeft.setOnClickListener(this);

        ibRight.setOnClickListener(this);
        ibRight.setTag(Integer.valueOf(0));

        initView();

        initData();

        setTabSelection(0);
    }

    /**
     * 初始化视图控件
     */
    private void initView(){

        pagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.v("当前选中:" + position);
                setTabSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvIntro.setOnClickListener(this);

        tvTraffic.setOnClickListener(this);

        tvNavigation.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){

        testData();

        initViewPager();
    }

    // 填充测试数据
    private void testData(){
        mIntroduction = "南京鼓楼医院";
        mAddress = "江苏省南京市鼓楼区";
        mHspName = "南京鼓楼医院";
        mHspTel = "021-88888888";
        mLatitude = "1279,1212,33";
        mLatLag = "33028,80472,1824";
    }

    private void setTabSelection(int index){
        clearSelection();

        switch (index){
            case 0:
                tvIntro.setSelected(true);
                break;
            case 1:
                tvTraffic.setSelected(true);
                break;
            case 2:
                tvNavigation.setSelected(true);
                break;
            default:

                break;

        }
    }
    /**
     * 初始化选择标签
     */
    private void clearSelection(){
        tvIntro.setSelected(false);
        tvTraffic.setSelected(false);
        tvNavigation.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_intro:// 点击医院介绍
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_traffic:// 点击交通信息
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_navigation:// 点击院内导航
                viewPager.setCurrentItem(2);
                break;

            case R.id.ib_title_right:// 点击关注该医院按钮

                ibRight.setImageResource(R.drawable.star_on);
                break;

            case R.id.ib_title_left:

                onBackPressed();
                break;

            default:

                break;
        }
    }

    private void initViewPager(){

        ArrayList<Fragment> arrayList = new ArrayList<Fragment>();

        fgIntroduction = new FgIntroduction();

        Bundle bundle = new Bundle();
        bundle.putString("hsp_info",this.mIntroduction);
        bundle.putString("hsp_tel", this.mHspTel);
        fgIntroduction.setArguments(bundle);

        fgTraffic = new FgTraffic();

        Bundle budle2 = new Bundle();
        budle2.putString("name",this.mHspName);
        budle2.putString("address",this.mAddress);
        budle2.putString("lat",this.mLatitude);
        budle2.putString("lng",this.mLatLag);
        budle2.putString("cityname", this.cityname);
        fgTraffic.setArguments(budle2);

        fgNavigation = new FgNavigation();

        Bundle bundle3 = new Bundle();
        bundle3.putString("hsp_id",this.hspid);

        fgNavigation.setArguments(bundle3);

        arrayList.add(fgIntroduction);
        arrayList.add(fgTraffic);
        arrayList.add(fgNavigation);

        pagerAdapter.bindData(arrayList);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(-1);
    }
}
