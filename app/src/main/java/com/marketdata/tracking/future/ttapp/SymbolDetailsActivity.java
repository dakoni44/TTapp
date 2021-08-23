package com.marketdata.tracking.future.ttapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SymbolDetailsActivity extends AppCompatActivity {

    String name,change,last,bid,ask,high,low,tickerSymbol,isin,currency,stockExchangeName,decorativeName,volume,dateTime,changePercent;
    TextView tvName,tvChange,tvLast,tvBid,tvAsk,tvHigh,tvLow,tvTickerSymbol,tvIsin,tvCurrency,tvStockExchangeName,tvDecorativeName,tvVolume,tvDateTime,tvChangePercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol_details);

        tvName=findViewById(R.id.tvName);
        tvChange=findViewById(R.id.tvChange);
        tvLast=findViewById(R.id.tvLast);
        tvBid=findViewById(R.id.tvBid);
        tvAsk=findViewById(R.id.tvAsk);
        tvHigh=findViewById(R.id.tvHigh);
        tvLow=findViewById(R.id.tvLow);
        tvTickerSymbol=findViewById(R.id.tvTickerSymbol);
        tvIsin=findViewById(R.id.tvIsin);
        tvCurrency=findViewById(R.id.tvCurrency);
        tvStockExchangeName=findViewById(R.id.tvStockExchangeName);
        tvDecorativeName=findViewById(R.id.tvDecorativeName);
        tvVolume=findViewById(R.id.tvVolume);
        tvDateTime=findViewById(R.id.tvDateTime);
        tvChangePercent=findViewById(R.id.tvChangePercent);


        name=getIntent().getExtras().getString("name");
        tickerSymbol=getIntent().getExtras().getString("tickerSymbol");
        isin=getIntent().getExtras().getString("isin");
        currency=getIntent().getExtras().getString("currency");
        stockExchangeName=getIntent().getExtras().getString("stockExchangeName");
        decorativeName=getIntent().getExtras().getString("decorativeName");
        last=getIntent().getExtras().getString("last");
        high=getIntent().getExtras().getString("high");
        low=getIntent().getExtras().getString("low");
        bid=getIntent().getExtras().getString("bid");
        ask=getIntent().getExtras().getString("ask");
        volume=getIntent().getExtras().getString("volume");
        dateTime=getIntent().getExtras().getString("datetime");
        change=getIntent().getExtras().getString("change");
        changePercent=getIntent().getExtras().getString("changeP");

        tvName.setText("Name: "+name);
        tvChange.setText("Change:  "+change);
        tvLast.setText("Last: "+ last);
        tvBid.setText("Bid: "+bid);
        tvAsk.setText("Ask: "+ask);
        tvHigh.setText("High: "+high);
        tvLow.setText("Low: "+low);
        tvTickerSymbol.setText("Ticker Symbol: "+tickerSymbol);
        tvIsin.setText("Isin: "+isin);
        tvCurrency.setText("Currency: "+currency);
        tvStockExchangeName.setText("Stock exchange name: "+stockExchangeName);
        tvDecorativeName.setText("Decorative name: "+decorativeName);
        tvVolume.setText("Volume: "+volume);
        tvDateTime.setText("Date and time: "+dateTime);
        tvChangePercent.setText("Change percent: "+changePercent);


       // Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
    }

}