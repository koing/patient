package com.greenpoint.patient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.greenpoint.patient.activity.BillsActivity;
import com.greenpoint.patient.activity.CollectedInfoActivity;
import com.greenpoint.patient.activity.EnvaluationActivity;
import com.greenpoint.patient.activity.ExaminationCardActivity;
import com.greenpoint.patient.activity.MyOrderActivity;
import com.greenpoint.patient.activity.PatientListActivity;
import com.greenpoint.patient.activity.ReportListActivity;
import com.greenpoint.patient.activity.SettingActivity;
import com.greenpoint.patient.activity.UserInfoActivity;
import com.greenpoint.patient.utils.BaseUtil;


public class FgUserCenter extends BaseFragment implements OnClickListener{

    private ImageView ivDrPhoto;

    private TextView tvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.index_usercenter,null);

        initView(view);

        onRefreshView();
        return view;
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initView(View view){

        view.findViewById(R.id.rl_doctor_info).setOnClickListener(this);

        view.findViewById(R.id.tv_patient_list).setOnClickListener(this);

        view.findViewById(R.id.tv_collect).setOnClickListener(this);

        view.findViewById(R.id.tv_envaluation).setOnClickListener(this);

        view.findViewById(R.id.tv_bill).setOnClickListener(this);

        view.findViewById(R.id.tv_order).setOnClickListener(this);

        view.findViewById(R.id.tv_examination_card).setOnClickListener(this);

        view.findViewById(R.id.tv_setting).setOnClickListener(this);

        view.findViewById(R.id.tv_report).setOnClickListener(this);

        ivDrPhoto = (ImageView)view.findViewById(R.id.iv_dr_photo);

        tvName = (TextView)view.findViewById(R.id.tv_patient_name);

        //TODO test
        tvName.setText("王兴森");

        // 初始化用户头像
//        AppUtil.initUserIcon(ivDrPhoto,LoginInfo.getUserIconUrl());
    }

    /**
     * 刷新视图
     */
    private void onRefreshView(){

    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.tv_patient_list:
                intent.setClass(getActivity(), PatientListActivity.class);
                intent.putExtra("intent","user");
                break;
            case R.id.tv_collect:
                intent.setClass(getActivity(), CollectedInfoActivity.class);

                break;
            case R.id.tv_envaluation:
                intent.setClass(getActivity(), EnvaluationActivity.class);
                break;
            case R.id.tv_setting:

                intent.setClass(getActivity(), SettingActivity.class);
                break;
            case R.id.tv_bill:

                intent.setClass(getActivity(), BillsActivity.class);
                break;

            case R.id.tv_report:

                intent.setClass(getActivity(),ReportListActivity.class);
                break;

            case R.id.tv_order:

                intent.setClass(getActivity(),MyOrderActivity.class);
                break;

            case R.id.tv_examination_card:

                intent.setClass(getActivity(),ExaminationCardActivity.class);
                break;

            case R.id.rl_doctor_info:

                intent.setClass(getActivity(),UserInfoActivity.class);

                BaseUtil.animStartActivityForResult(getActivity(),intent,12);
                return;
        }

        BaseUtil.animStartActivity(getActivity(),intent);
    }
}
