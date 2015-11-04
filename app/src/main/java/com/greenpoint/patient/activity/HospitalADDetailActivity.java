package com.greenpoint.patient.activity;

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

import com.greenpoint.patient.R;
import com.greenpoint.patient.dialogs.LoadingDialog;
import com.greenpoint.patient.utils.AppUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class HospitalADDetailActivity extends BaseActivity {

    @ViewInject(R.id.linearlayout)
    private LinearLayout llLayout;

    @ViewInject(R.id.webview)
    private WebView webView;

    private LoadingDialog progressDialog;

    private String url;

    private Thread thread = new Thread(){
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


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
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
            super.handleMessage(msg);
        }
    };

    private WebViewClient webViewClient = new WebViewClient(){

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            return true;
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient(){

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100){
                handler.sendEmptyMessage(0);
            }
            super.onProgressChanged(view, newProgress);
        }
    };

    // 开启进度框
    private void startDialog(){
        if (progressDialog == null){
            progressDialog = LoadingDialog.createDialog(this);
        }

        progressDialog.show();

        thread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_ad);
        ViewUtils.inject(this);

        String activityid = getIntent().getStringExtra("intent");

        if ("annoucement".equals(activityid)){
            tvTitle.setText(R.string.title_announcement);
        }else {

            WebSettings webSettings = webView.getSettings();

            // 设置可执行JavaScript脚本
            webSettings.setJavaScriptEnabled(true);

            // 设置默认编码格式
            webSettings.setDefaultTextEncodingName("GBK");


            webSettings.setBuiltInZoomControls(true);

            // 调整到适合webview大小
            webSettings.setUseWideViewPort(true);

            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            webView.setWebChromeClient(webChromeClient);

            webView.setWebViewClient(webViewClient);

            url = getIntent().getStringExtra("url");

            LogUtils.v("URL:" + url);

            if (!AppUtil.networkisConnected()){
                return;
            }
            webView.loadUrl(url);

            startDialog();
            if ("menual".equals(activityid)){
                tvTitle.setText(R.string.set_menual);
            }else if ("healthpedia".equals(activityid)){
                tvTitle.setText(R.string.title_health_pedia);
            }else if ("statement".equals(activityid)){
                tvTitle.setText(R.string.set_statment);
            }else if ("aboutus".equals(activityid)){
                tvTitle.setText(R.string.set_about_us);
            }

        }
    }
}
