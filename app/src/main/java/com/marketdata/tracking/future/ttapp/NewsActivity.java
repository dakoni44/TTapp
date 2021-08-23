package com.marketdata.tracking.future.ttapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NewsAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    NewsAdapter adapter;
    ArrayList<News> news1=new ArrayList<>();

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    TextView tvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_drawer);

        tvNews=findViewById(R.id.tvNews);

        recyclerView=findViewById(R.id.rvNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        parseXML();

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setupBottomNavigationBar();

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    public void setupBottomNavigationBar(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bnMain);
        bottomNavigationView.setSelectedItemId(R.id.news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.markets:
                        startActivity(new Intent(NewsActivity.this,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.watchlist:
                        return true;
                    case R.id.portfolio:
                        return true;
                    case R.id.screener:
                        return true;
                    case R.id.news:
                        return true;
                }
                return false;
            }
        });
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=parserFactory.newPullParser();
            InputStream is=getApplicationContext().getAssets().open("news.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(is,null);

            final ArrayList<News> news=processParsing(parser);

            tvNews.setText(news1.get(0).getNews().substring(18));

            news1.remove(0);

            adapter=new NewsAdapter(getApplicationContext(),news,this);
            recyclerView.setAdapter(adapter);


        }catch (XmlPullParserException e){

        }catch (IOException e){

        }
    }

    private ArrayList<News> processParsing(XmlPullParser parser)  throws IOException,XmlPullParserException{
        int eventType=parser.getEventType();
        News news=null;

        while(eventType!=XmlPullParser.END_DOCUMENT){
            String eltName=parser.getName();

            switch (eventType){
                case XmlPullParser.START_TAG:
                    if("NewsArticle".equals(eltName)){
                        news=new News();
                    }else if(news!=null){
                        if("Headline".equals(eltName)){
                            news.news=parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(eltName.equals("NewsArticle") && news!=null){
                        news1.add(news);
                    }
                    break;
            }
            eventType=parser.next();
        }

        return news1;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(NewsActivity.this,NewsDetailsActivity.class);
        intent.putExtra("news", news1.get(position).getNews());
        //  Toast.makeText(this, ""+symbols.get(position).getId(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}