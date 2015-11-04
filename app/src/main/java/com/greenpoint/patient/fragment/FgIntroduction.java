package com.greenpoint.patient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenpoint.patient.R;
import com.greenpoint.patient.utils.AppUtil;
import com.lidroid.xutils.util.LogUtils;

/**
 * 医院介绍fragment
 */
public class FgIntroduction extends BaseFragment implements View.OnClickListener{

    // 医院电话
    private String hspTel;

    // 电话图标
    private ImageView mIvTel;


    private TextView mTvTel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hospital_introduction,null);

        initView(view);

        setListener();

        return view;
    }


    private void initView(View view){
        mIvTel = (ImageView)view.findViewById(R.id.iv_tel);

        TextView hspIntro = (TextView)view.findViewById(R.id.tv_hsp_intr_info);

        mTvTel = (TextView)view.findViewById(R.id.tv_phone_content);

        String intro = getArguments().getString("hsp_info");
        String hspTel = getArguments().getString("hsp_tel");

        if (AppUtil.isInvalidate(intro)){
            hspIntro.setText(intro);
            mTvTel.setText(hspTel);
        }else{
            view.findViewById(R.id.scrollview).setVisibility(View.VISIBLE);
            view.findViewById(R.id.noinfo).setVisibility(View.VISIBLE);
        }

    }

    private void setListener(){
        mIvTel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_tel:
                LogUtils.v(mTvTel.getText().toString().trim());
                if (AppUtil.isInvalidate(hspTel)){
                    String telNo = mTvTel.getText().toString().trim().replace("-","");
                    AppUtil.makePhoneCall(getActivity(),telNo);
                }

                break;
            default:

                break;
        }
    }
}
