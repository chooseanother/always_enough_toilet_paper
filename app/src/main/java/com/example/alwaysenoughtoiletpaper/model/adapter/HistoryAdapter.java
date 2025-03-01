package com.example.alwaysenoughtoiletpaper.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.Member;

import java.util.Collections;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<HistoryItem> historyItems;
    private final ItemSwipedListener listener;

    public HistoryAdapter(List<HistoryItem> historyItems, ItemSwipedListener listener){
        this.historyItems = historyItems;
        this.listener = listener; // Initialize the listener
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

    @Override
    public void onItemSwiped(int position, int direction) {
        if (ItemTouchHelper.LEFT == direction) {
            HistoryItem removedItem = historyItems.get(position);
            historyItems.remove(position);
            notifyItemRemoved(position);
            if (listener != null) {
                listener.itemSwipedLeft(removedItem.getItem()); // Notify the listener
            }
        }
        else if (ItemTouchHelper.RIGHT == direction) {
            HistoryItem removedItem = historyItems.get(position);
            historyItems.remove(position);
            notifyItemRemoved(position);
            if (listener != null) {
                listener.itemSwipedRight(removedItem.getItem()); // Notify the listener
            }
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(historyItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(historyItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        listener.itemMoved();
        return true;
    }
}
