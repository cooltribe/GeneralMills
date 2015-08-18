package com.searun.GIS.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.searun.GIS.application.MyApplication;
import com.searun.GIS.service.LocalService;

public class WelcomeActivity2 extends Activity {
    private MyApplication app;
    private LocationClient mLocationClient;
    private Activity mActivity = null;
    private WebView mWebView = null;


    public String time,lat,lon,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome2);
        app = (MyApplication) getApplication();

        mActivity = this;
        showWebView();
//        startLocalService();

    }

    /**
     * 启动定位服务
     */
    private void startLocalService(){
        Intent intent = new Intent(WelcomeActivity2.this, LocalService.class);
        startService(intent);
    }

    private void showWebView(){		// webView与js交互代码
        try {
            mWebView = new WebView(this);
            setContentView(mWebView);

            mWebView.requestFocus();

            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    mActivity.setTitle("Loading...");
                    mActivity.setProgress(progress);

                    if (progress >= 80) {
                        mActivity.setTitle("JsAndroid Test");
                    }
                }
            });

            mWebView.setOnKeyListener(new View.OnKeyListener() {        // webview can go back
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                    return false;
                }
            });

            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("utf-8");

            mWebView.addJavascriptInterface(getHtmlObject(), "jsObj");
            mWebView.loadUrl("file:///android_asset/index.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHtmlObject(){
        Object insertObj = new Object(){
            @JavascriptInterface
            public String HtmlcallJava(){
                return "Html call Java";
            }
            @JavascriptInterface
            public String HtmlcallJava2(final String param){
                return "Html call Java : " + param;
            }

            @JavascriptInterface
            public void JavacallHtml(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript: showFromHtml()");
                        Toast.makeText(mActivity, "clickBtn", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @JavascriptInterface
            public void JavacallHtml2(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript: showFromHtml2('IT-homer blog')");
                        Toast.makeText(mActivity, "clickBtn2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        return insertObj;
    }
}
