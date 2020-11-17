package com.nextia.micuentainfonavit.ui.avisoprivacidad;
/**
 * class of terms and conditions policy
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nextia.micuentainfonavit.R;

public class TermConditionsActivity extends AppCompatActivity {

    //Views
    private WebView webView;
    private ProgressBar progressBar;

    //Const
    private String url = "https://portalmx.infonavit.org.mx/wps/portal/infonavit.web/contactanos/terminos-condiciones/!ut/p/z1/jZDBCoJAEIafxqszriFbNw1bkzCDRNtLaNgqqCu65esn1SUoc24zfN_8zACHBHiT3kuRqlI2aTX2J26dLYborRdGwHwkeNg7xzAijskCAvETIBSROTgC1KNob9ytu9yZhEUG8Dk-_igb5_kTAJ9eHwOfikDTegNTJ_4L8YGLSmavf9pNZlIBvMuveZd3-q0bx4VSbb_SUMNhGHQhpahy_SJrDb8phewVJJ8ktHUUJViGdUz7BwjD5EQ!/dz/d5/L2dBISEvZ0FBIS9nQSEh/";
    private AvisoPrivacidadActivity mContext;

    //creating the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_conditions);
        initView();
        initWebView();
    }

    //initiate the main view, with progressbar
    private void initView() {
        webView = (WebView) findViewById(R.id.wvNoticeOfPrivacy);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
    }

    //Loading webView  to View
    private void initWebView() {
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new TermConditionsActivity.MyWebViewClient());
    }

    //create Web client
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            webView.setVisibility(View.GONE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            webView.setVisibility(View.VISIBLE);
        }
    }
}