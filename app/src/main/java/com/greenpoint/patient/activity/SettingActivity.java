package com.greenpoint.patient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    // 使用手册
    @ViewInject(R.id.tv_menual)
    private TextView tvMenual;

    @ViewInject(R.id.tv_about_us)
    private TextView tvAboutUs;

    // 免责声明
    @ViewInject(R.id.tv_free)
    private TextView tvFree;

    // 意见反馈
    @ViewInject(R.id.tv_feedback)
    private TextView tvFeedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userinfosetting);

        ViewUtils.inject(this);
        tvTitle.setText(R.string.title_my_setting);

        initView();

        checkNewVersion();

        getDisplayMetrics();
    }

    // 设置监听事件
    private void initView(){
        tvMenual.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvFree.setOnClickListener(this);
        tvFeedBack.setOnClickListener(this);
    }

    private void checkNewVersion(){

    }

    private void getDisplayMetrics(){

    }

    @Override
    public void onClick(View v) {

        Intent intenet = new Intent();
        switch (v.getId()){
            case R.id.tv_menual:

                intenet.setClass(this,HospitalADActivity.class);
                intenet.putExtra("url", "http://121.40.151.38:9023/user_help.html");
                intenet.putExtra("intent","menual");
                animStartActivity(intenet);
                break;

            case R.id.tv_about_us:// 点击关于我们触发的事件

                intenet.setClass(this, HospitalADDetailActivity.class);
                intenet.putExtra("url", "http://www.zyt91.com/aboutusapp/zyt91.html");
                intenet.putExtra("intent", "aboutus");
                animStartActivity(intenet);
                break;

            case R.id.tv_free:

                intenet.setClass(this,HospitalADDetailActivity.class);
                intenet.putExtra("url", "http://www.zyt91.com/zyt_statement.html");
                intenet.putExtra("intent", "statement");
                animStartActivity(intenet);
                break;

            case R.id.tv_feedback:
                intenet.setClass(this,FeedBackActivity.class);

                animStartActivity(intenet);

                break;
            default:

                break;
        }
    }
}
