package com.marketdata.tracking.future.ttapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailsActivity extends AppCompatActivity {

    String news="";
    TextView tvNews1,tvNews2;
    ImageView ivNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        news=getIntent().getExtras().getString("news");
        tvNews1=findViewById(R.id.tvNews1);
        tvNews2=findViewById(R.id.tvNews2);
        ivNews=findViewById(R.id.ivNews);

        tvNews1.setText(news.substring(18));
        tvNews2.setText(news.substring(18));

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            tvNews1.setVisibility(View.GONE);
            tvNews2.setVisibility(View.GONE);

        }
    }
}