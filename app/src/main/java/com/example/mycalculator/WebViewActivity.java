package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {

    private Button backBtn;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        backBtn = findViewById(R.id.detail_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = findViewById(R.id.web_page);

        String link = (String) getIntent().getSerializableExtra("link");

        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);//true - не нуждаемся в запуске стороннего браузера
        webView.setWebViewClient(new MyWebViewClient());
        // указываем страницу загрузки
        webView.loadUrl(link);


    }


}