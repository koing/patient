package com.greenpoint.patient.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.greenpoint.patient.R;
import com.greenpoint.patient.dialogs.LoadingDialog;
import com.greenpoint.patient.utils.AppUtil;
import com.greenpoint.patient.utils.BaseUtil;
import com.lidroid.xutils.util.LogUtils;

public class HospitalADActivity extends BaseActivity {

    private LoadingDialog progressDialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            // 当前线程
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:

                        progressDialog.hide();
                        break;
                    case 1:

                        if (thread != null){
                            thread.interrupt();
                            thread = null;
                            progressDialog.hide();
                        }
                        break;
                    default:

                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent();
            intent.putExtra("url", url);
            LogUtils.v("next url is:" + url);
            intent.setClass(HospitalADActivity.this, HospitalADDetailActivity.class);
            intent.putExtra("intent", activityid);
            BaseUtil.animStartActivity(HospitalADActivity.this, intent);
            return true;
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                handler.sendEmptyMessage(0);
            }
            super.onProgressChanged(view, newProgress);
        }
    };
    private String activityid;

    private String url;

    private LinearLayout llLayout;

    private WebView webView;

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hospital_ad);

        activityid = getIntent().getStringExtra("intent");

        if ("annoucement".equals(activityid)) {
            tvTitle.setText(R.string.title_announcement);
        } else {
            url = getIntent().getStringExtra("url");
            llLayout = (LinearLayout) findViewById(R.id.linearlayout);
            webView = (WebView) findViewById(R.id.webview);

            WebSettings webSettings = webView.getSettings();

            // 支持JavaScript脚本
            webSettings.setJavaScriptEnabled(true);

            // 支持文本缩放
            webSettings.setBuiltInZoomControls(true);

            // 设置文本编码格式
            webSettings.setDefaultTextEncodingName("GBK");

            // 将页面调整到适合webview的大小
            webSettings.setUseWideViewPort(true);

            // 设置滚动条样式
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            webView.setWebViewClient(webViewClient);

            webView.setWebChromeClient(webChromeClient);

            tvTitle.setText(R.string.set_menual);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AppUtil.networkisConnected()){

            webView.loadUrl(url);
            LogUtils.v("url:" + url);
            startDialog();
        }else{
            Toast.makeText(this,"当前网络不可用",Toast.LENGTH_LONG).show();
        }
    }

    private void startDialog() {
        if (progressDialog == null) {
            progressDialog = LoadingDialog.createDialog(this);
        }
        progressDialog.show();

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);

                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
