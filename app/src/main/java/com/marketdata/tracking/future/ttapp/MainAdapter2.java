package com.marketdata.tracking.future.ttapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter2 extends RecyclerView.Adapter<MainAdapter2.ImageViewHolder> {

    Context mContext;
    private List<Symbol> mdata;
    private MainAdapter2.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public MainAdapter2(Context mContext, List<Symbol> mdata, MainAdapter2.OnItemClickListener listener) {
        this.mContext = mContext;
        this.mdata = mdata;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainAdapter2.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_second, parent, false);
        return new MainAdapter2.ImageViewHolder(view, listener);

    }


    @Override
    public void onBindViewHolder(@NonNull final MainAdapter2.ImageViewHolder holder, int position) {

        final Symbol symbol = mdata.get(position);


        holder.tvBid.setText(symbol.getBid());
        holder.tvAsk.setText(symbol.getAsk());
        holder.tvHigh.setText(symbol.getHigh());
        holder.tvLow.setText(symbol.getLow());
        holder.tvName.setText(symbol.getName());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvBid, tvAsk,tvHigh,tvLow;

        public ImageViewHolder(@NonNull View itemView, final MainAdapter2.OnItemClickListener listener) {
            super(itemView);
            tvBid = itemView.findViewById(R.id.tvBid);
            tvAsk = itemView.findViewById(R.id.tvAsk);
            tvHigh = itemView.findViewById(R.id.tvHigh);
            tvLow = itemView.findViewById(R.id.tvLow);
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
