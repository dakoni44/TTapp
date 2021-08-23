package com.marketdata.tracking.future.ttapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SymbolDetailsActivity extends AppCompatActivity {

    double  lastd, value, changePd,changed;
    Random random=new Random();

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

        if(changePercent!=null) {
            changePd = Double.parseDouble(changePercent);
            if (changePd > 0) {
                tvChangePercent.setTextColor(Color.GREEN);
                tvChangePercent.setText(String.format("Cahnge percent: +%.2f", changePd) + "%");
            } else if (changePd < 0) {
                tvChangePercent.setTextColor(Color.RED);
                tvChangePercent.setText(String.format("Cahnge percent: %.2f", changePd) + "%");
            } else if (changePd == 0) {
                tvChangePercent.setTextColor(Color.WHITE);
                tvChangePercent.setText(String.format("Cahnge percent: %.2f", 0.00) + "%");
            }
        }else if(changePercent==null){
            tvChangePercent.setTextColor(Color.WHITE);
            tvChangePercent.setText("Change percent: 0.00%");
        }

        if(change!=null) {
            changed = Double.parseDouble(change);
            if (changed > 0) {
                tvChange.setTextColor(Color.GREEN);
                tvChange.setText("Cahnge percent: "+ changed );
            } else if (changed < 0) {
                tvChange.setTextColor(Color.RED);
                tvChange.setText("Cahnge percent: "+ changed );
            } else if (changed == 0) {
                tvChange.setTextColor(Color.WHITE);
                tvChange.setText("Cahnge percent: "+ changed );
            }
        }else if(change==null){
            tvChange.setTextColor(Color.WHITE);
            tvChange.setText("Change percent: 0.00%");
        }


        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep((random.nextInt(28) + 3)*1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                lastd = Double.parseDouble(last);

                                value = random.nextDouble() * (lastd*1.2 - lastd*0.8) + lastd*0.8;
                                if(value>lastd){
                                    tvLast.setTextColor(Color.GREEN);
                                    tvLast.animate().setDuration(2000).withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            // set color back to normal
                                            tvLast.setTextColor(Color.WHITE);
                                        }
                                    }).start();
                                }else if(value<lastd){
                                    tvLast.setTextColor(Color.RED);
                                    tvLast.animate().setDuration(2000).withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            // set color back to normal
                                            tvLast.setTextColor(Color.WHITE);
                                        }
                                    }).start();
                                }
                                tvLast.setText(String.format("Last: %.2f",value));

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

}