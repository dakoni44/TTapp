package com.marketdata.tracking.future.ttapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ImageViewHolder> {

        Context mContext;
private List<News> mdata;
private OnItemClickListener listener;


public interface OnItemClickListener {
    void onItemClick(int position);
}


    public NewsAdapter(Context mContext, List<News> mdata, NewsAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        mdata.remove(0);
        this.mdata = mdata;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_news, parent, false);
        return new NewsAdapter.ImageViewHolder(view, listener);

    }


    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.ImageViewHolder holder, int position) {

        final News news = mdata.get(position);


        holder.tvNews.setText(news.getNews().substring(18));
        holder.ivNews.setImageResource(R.drawable.newspic);

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


public class ImageViewHolder extends RecyclerView.ViewHolder {

    TextView tvNews;
    ImageView ivNews;

    public ImageViewHolder(@NonNull View itemView, final NewsAdapter.OnItemClickListener listener) {
        super(itemView);
        tvNews = itemView.findViewById(R.id.tvNews);
        ivNews = itemView.findViewById(R.id.ivNews);
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
