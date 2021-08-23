package com.marketdata.tracking.future.ttapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ImageViewHolder> {

    Context mContext;
    private List<Symbol> mdata;
    private OnItemClickListener listener;
    String p, lasts;
    double n, lastd, value;
    Random random=new Random();

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public MainAdapter(Context mContext, List<Symbol> mdata, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mdata = mdata;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row, parent, false);
        return new MainAdapter.ImageViewHolder(view, listener);

    }


    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ImageViewHolder holder, int position) {

        final Symbol symbol = mdata.get(position);


        try {
            if(symbol.getChangePercent()!=null) {
                p = symbol.getChangePercent();
                n = Double.valueOf(p);
                if(n<0){
                    holder.tvChange.setTextColor(Color.RED);
                    holder.tvChange.setText(String.format("%.2f",n)+"%");
                }else if(n==0){
                    holder.tvChange.setTextColor(Color.WHITE);
                    holder.tvChange.setText(String.format("%.2f",n)+"%");
                }else  if(n>0){
                    holder.tvChange.setTextColor(Color.GREEN);
                    holder.tvChange.setText(String.format("+%.2f",n)+"%");
                }
        }else if(symbol.getChangePercent()==null){
                holder.tvChange.setTextColor(Color.WHITE);
                holder.tvChange.setText("0.00%");
            }
        } catch (NumberFormatException e) {
            Toast.makeText(mContext, ".......", Toast.LENGTH_SHORT).show();
        }


        if(symbol.getLast()!=null) {
            holder.tvLast.setText(symbol.getLast());
        }

        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(symbol.getLast()!=null) {
                    lasts = symbol.getLast();
                    lastd = Double.parseDouble(lasts);

                    value = random.nextDouble() * (lastd*1.2 - lastd*0.8) + lastd*0.8;
                    if(value>lastd){
                        holder.tvLast.setTextColor(Color.GREEN);
                        holder.tvLast.animate().setDuration(2000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                // set color back to normal
                                holder.tvLast.setTextColor(Color.WHITE);
                            }
                        }).start();
                    }else if(value<lastd){
                        holder.tvLast.setTextColor(Color.RED);
                        holder.tvLast.animate().setDuration(2000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                // set color back to normal
                                holder.tvLast.setTextColor(Color.WHITE);
                            }
                        }).start();
                    }
                    holder.tvLast.setText(String.format("%.2f",value));


                }else if(symbol.getLast()==null){

                }
            }
        },(random.nextInt(28) + 3)*1000);

        holder.tvName.setText(symbol.getName());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvChange, tvLast;

        public ImageViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvChange = itemView.findViewById(R.id.tvChange);
            tvLast = itemView.findViewById(R.id.tvLast);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
