package com.example.alwaysenoughtoiletpaper.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Member;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<HistoryItem> historyItems;

    public HistoryAdapter(List<HistoryItem> historyItems){
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem historyItem = historyItems.get(position);
        holder.name.setText(historyItem.getName());
        holder.item.setText(historyItem.getItem());
    }

    public void setHistoryItems(List<HistoryItem> historyItems){
        this.historyItems = historyItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.history_name);
            item = itemView.findViewById(R.id.history_item);
        }
    }
}
