package com.marketdata.tracking.future.ttapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainAdapter2.OnItemClickListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    RecyclerView recyclerView;
    MainAdapter2 adapter;
    TextView tvName;
    ImageView ivA,ivZ,ivAa,ivZz;

    ArrayList<Symbol> symbols=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_drawer);

        recyclerView=findViewById(R.id.rvMain);
        tvName=findViewById(R.id.tvName);
        ivA=findViewById(R.id.ivA);
        ivZ=findViewById(R.id.ivZ);
        ivAa=findViewById(R.id.ivAa);
        ivZz=findViewById(R.id.ivZz);

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

    public void setupBottomNavigationBar(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bnMain);
        bottomNavigationView.setSelectedItemId(R.id.markets);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.markets:
                        return true;
                    case R.id.watchlist:
                        return true;
                    case R.id.portfolio:
                        return true;
                    case R.id.screener:
                        return true;
                    case R.id.news:
                       // startActivity(new Intent(MainActivity2.this,NewsActivity.class));
                       // overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.chng:
                startActivity(new Intent(MainActivity2.this,MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.bidask:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        symbols.clear();
        parseXML();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=parserFactory.newPullParser();
            InputStream is=getApplicationContext().getAssets().open("data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(is,null);

            final ArrayList<Symbol> symbols=processParsing(parser);

            adapter=new MainAdapter2(getApplicationContext(),symbols,this);
            recyclerView.setAdapter(adapter);

            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ivA.getVisibility()==View.GONE && ivZ.getVisibility()==View.GONE){
                        Collections.sort(symbols, new Comparator<Symbol>() {
                            @Override
                            public int compare(Symbol symbol, Symbol t1) {
                                return symbol.getName().compareTo(t1.getName());
                            }
                        });
                        ivA.setVisibility(View.VISIBLE);
                        ivAa.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }else if(ivA.getVisibility()==View.VISIBLE){
                        Collections.sort(symbols, new Comparator<Symbol>() {
                            @Override
                            public int compare(Symbol symbol, Symbol t1) {
                                return t1.getName().compareTo(symbol.getName());
                            }
                        });
                        ivZ.setVisibility(View.VISIBLE);
                        ivA.setVisibility(View.GONE);
                        ivAa.setVisibility(View.VISIBLE);
                        ivZz.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }else if(ivZ.getVisibility()==View.VISIBLE){
                        refresh();
                        ivZ.setVisibility(View.GONE);
                        ivA.setVisibility(View.GONE);
                        ivAa.setVisibility(View.VISIBLE);
                        ivZz.setVisibility(View.VISIBLE);
                    }
                }
            });

        }catch (XmlPullParserException e){

        }catch (IOException e){

        }
    }

    private ArrayList<Symbol> processParsing(XmlPullParser parser)  throws IOException,XmlPullParserException{
        int eventType=parser.getEventType();
        Symbol symbol=null;

        while(eventType!=XmlPullParser.END_DOCUMENT){
            String eltName=parser.getName();

            switch (eventType){
                case XmlPullParser.START_TAG:
                    if("Symbol".equals(eltName)){
                        symbol=new Symbol();
                        symbol.name=parser.getAttributeValue(null,"name");
                        symbol.id=parser.getAttributeValue(null,"id");
                        symbol.tickerSymbol = parser.getAttributeValue(null, "tickerSymbol");
                        symbol.isin = parser.getAttributeValue(null, "isin");
                        symbol.currency = parser.getAttributeValue(null, "currency");
                        symbol.stockExchangeName = parser.getAttributeValue(null, "stockExchangeName");
                        symbol.decorativeName = parser.getAttributeValue(null, "decorativeName");
                    }else if(symbol!=null){
                        if("Quote".equals(eltName)){
                            symbol.change=parser.getAttributeValue(null,"change");
                            symbol.last=parser.getAttributeValue(null,"last");
                            symbol.high = parser.getAttributeValue(null, "high");
                            symbol.low = parser.getAttributeValue(null, "low");
                            symbol.bid = parser.getAttributeValue(null, "bid");
                            symbol.ask = parser.getAttributeValue(null, "ask");
                            symbol.volume = parser.getAttributeValue(null, "volume");
                            symbol.dateTime = parser.getAttributeValue(null, "dateTime");
                            symbol.changePercent = parser.getAttributeValue(null, "changePercent");
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(eltName.equals("Symbol") && symbol!=null){
                        symbols.add(symbol);
                    }
                    break;
            }
            eventType=parser.next();
        }

        return symbols;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(MainActivity2.this,SymbolDetailsActivity.class);
        intent.putExtra("name", symbols.get(position).getName());
        intent.putExtra("tickerSymbol", symbols.get(position).getTickerSymbol());
        intent.putExtra("isin", symbols.get(position).getIsin());
        intent.putExtra("currency", symbols.get(position).getCurrency());
        intent.putExtra("stockExchangeName", symbols.get(position).getStockExchangeName());
        intent.putExtra("decorativeName", symbols.get(position).getDecorativeName());
        intent.putExtra("last", symbols.get(position).getLast());
        intent.putExtra("high", symbols.get(position).getHigh());
        intent.putExtra("low", symbols.get(position).getLow());
        intent.putExtra("bid", symbols.get(position).getBid());
        intent.putExtra("ask", symbols.get(position).getAsk());
        intent.putExtra("volume", symbols.get(position).getVolume());
        intent.putExtra("datetime", symbols.get(position).getDateTime());
        intent.putExtra("change", symbols.get(position).getChange());
        intent.putExtra("changeP", symbols.get(position).getChangePercent());
        //  Toast.makeText(this, ""+symbols.get(position).getId(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}