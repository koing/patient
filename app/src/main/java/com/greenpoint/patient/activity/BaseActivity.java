package com.greenpoint.patient.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.greenpoint.patient.dialogs.LoadingDialog;


public class BaseActivity extends Activity {


    private LoadingDialog progressDialog;

    protected ImageButton ibLeft;

    protected ImageButton ibRight;

    protected ImageView ivChoice;

    protected LinearLayout llHospital;

    protected RelativeLayout llNavi;

    protected LinearLayout llSearch;

    protected TextView tvLeft;

    protected TextView tvRight;

    protected TextView tvTitle;

    protected TextView tvEndPos;

    protected SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarLayout();
    }


    public void animStartActivity(Intent intent){
        startActivity(intent);
        // 进出时的动画效果
//        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_left);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
    }


    // 退出活动时的动画效果
    public void animFinish(){
        finish();
        overridePendingTransition(R.anim.in_from_right,R.anim.out_from_right);
    }



    public void animStartActivityForResult(Intent paramIntent,int paramInt){
        startActivityForResult(paramIntent, paramInt);
        overridePendingTransition(R.anim.in_from_left,R.anim.out_from_left);
    }

    // 按下返回键时触发的动作
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_right,R.anim.out_from_right);
    }

    //
    public void setActionBarLayout(){

        ActionBar actionBar = getActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            View view = LayoutInflater.from(this).inflate(R.layout.diy_actionbar,null);

            actionBar.setCustomView(view,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            ibLeft =(ImageButton) view.findViewById(R.id.ib_title_left);

            ibLeft.setImageResource(R.drawable.back);

            ibLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });

            llHospital = (LinearLayout)findViewById(R.id.ll_hospital_choice);

            tvLeft = (TextView)findViewById(R.id.tv_title_left);

            tvRight = (TextView)findViewById(R.id.tv_title_right);

            ivChoice = (ImageView)findViewById(R.id.iv_choice);

            ibRight = (ImageButton)findViewById(R.id.ib_title_right);

            llSearch = (LinearLayout)findViewById(R.id.ll_search);

            llNavi = (RelativeLayout)findViewById(R.id.ll_navigation);

            tvEndPos = (TextView)findViewById(R.id.tv_end);

            tvTitle = (TextView)findViewById(R.id.tv_title);

            searchView = (SearchView)findViewById(R.id.search_view);
        }
    }


    // 显示进度条方法
    public void startProgressDialog(){
        if(this.progressDialog == null){
            this.progressDialog = LoadingDialog.createDialog(this);
        }
        progressDialog.show();
    }

    // 关闭进度框方法
    public void stopProgressDialog(){
        if(this.progressDialog != null){
            // 使进度框消失
            progressDialog.dismiss();
            this.progressDialog = null;
        }
    }


    // activity销毁时回调该方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
