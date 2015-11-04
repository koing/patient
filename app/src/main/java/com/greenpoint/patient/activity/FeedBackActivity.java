package com.greenpoint.patient.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.greenpoint.patient.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2015/10/22.
 */
public class FeedBackActivity extends BaseActivity {

    // 意见反馈内容
    @ViewInject(R.id.et_inputcontent)
    private EditText etInputContent;

    // 邮箱地址
    @ViewInject(R.id.et_link)
    private EditText etLink;

    // 确认按钮
    @ViewInject(R.id.btn_submit)
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userfeedback);

        ViewUtils.inject(this);
        tvTitle.setText(R.string.set_feedback);

        initView();
    }

    // 初始化
    private void initView(){

        btnSubmit.setEnabled(false);

        etLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @TargetApi(16)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((etLink.isFocused()) && (etLink.getText().length() > 1)) {
                    btnSubmit.setBackgroundResource(R.drawable.btn_select_submit);
                    btnSubmit.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
