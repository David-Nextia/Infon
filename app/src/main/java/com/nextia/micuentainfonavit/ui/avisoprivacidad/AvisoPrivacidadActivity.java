package com.nextia.micuentainfonavit.ui.avisoprivacidad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nextia.micuentainfonavit.R;

public class AvisoPrivacidadActivity extends AppCompatActivity {

    //Views
    private WebView webView;
    private ProgressBar progressBar;

    //Const
    private String url = "https://portalmx.infonavit.org.mx/wps/portal/infonavit.web/transparencia/aviso-privacidad/!ut/p/z1/jY-7DoJAEEW_xYKWmQXF1W4xiA-CWBBxGwMGVw2wBBB-X0QbEyFON5Nzbu4AhwB4FtY3EVY3mYVJux-5cTJsxNViTNwd245xT00PzamnO0SDQwdoFNE2kbg2XVFkS2ttzRxdswkC_8cfAF4-9gzD1ucd0tfAJx9gIGMDXCQyer_LskinAngRX-IiLtRH0Z6vVZWXcwUVbJpGFVKKJFbPMlXwl3KVZQXBNwl56gd4nyS1w0ZPkMUZLw!!/dz/d5/L2dBISEvZ0FBIS9nQSEh/";
    private AvisoPrivacidadActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso_privacidad);

        initView();
        initWebView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.wvNoticeOfPrivacy);
        progressBar= findViewById(R.id.progressBar);
        progressBar.setMax(100);
    }

    private void initWebView(){
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AvisoPrivacidadActivity.MyWebViewClient());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            webView.setVisibility(View.GONE);
            return true;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            Log.d("url-payment", url);
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            webView.setVisibility(View.VISIBLE);
        }
    }
}