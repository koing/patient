package com.greenpoint.patient.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.lidroid.xutils.ViewUtils;


public class BaseFragmentActivity extends FragmentActivity {


    protected ImageButton ibLeft;

    protected ImageView ibRight;

    protected ImageView ivChoice;

    protected TextView tvTitle;

    protected TextView tvLeft;

    protected TextView tvRight;

    protected LinearLayout llHospital;

    protected LinearLayout llLayout;

    protected RelativeLayout llTabs;

    protected TextView tvTabLeft;

    protected TextView tvTabRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 注入view和事件
        ViewUtils.inject(BaseFragmentActivity.this);
        setActionBarLayout();
    }


    public void setActionBarLayout() {
        // 实例化一个ActionBar
        ActionBar localActionBar = getActionBar();
        if (localActionBar != null) {
            localActionBar.setDisplayHomeAsUpEnabled(false);
            localActionBar.setDisplayShowHomeEnabled(false);
            localActionBar.setDisplayShowCustomEnabled(true);
            localActionBar.setDisplayShowTitleEnabled(false);

            View localView = LayoutInflater.from(this).inflate(R.layout.diy_actionbar, null);

            ibLeft = (ImageButton)localView.findViewById(R.id.ib_title_left);

            ibLeft.setImageResource(R.drawable.back);

            ibLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseFragmentActivity.this.finish();
                    BaseFragmentActivity.this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
                }
            });
            localActionBar.setCustomView(localView);


            // 初始化控件
            ibRight = (ImageButton)findViewById(R.id.ib_title_right);

            llHospital = (LinearLayout)findViewById(R.id.ll_hospital_choice);

            ivChoice = (ImageView)findViewById(R.id.iv_choice);

            tvTitle = (TextView)findViewById(R.id.tv_title);

            tvLeft = (TextView)findViewById(R.id.tv_title_left);

            tvRight = (TextView)findViewById(R.id.tv_title_right);

            llLayout = (LinearLayout)findViewById(R.id.ll_layout);

            llTabs = (RelativeLayout)findViewById(R.id.action_tab);

            tvTabLeft = (TextView)findViewById(R.id.tv_tab_left);

            tvTabRight = (TextView)findViewById(R.id.tv_tab_right);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
    }
}
