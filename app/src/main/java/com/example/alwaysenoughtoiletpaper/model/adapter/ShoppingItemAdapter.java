package com.example.alwaysenoughtoiletpaper.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.model.HistoryItem;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;

import java.util.Collections;
import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<ShoppingItem> shoppingItemList;
    private OnClickListener listener;
    private final ItemSwipedListener swipeListener;

    public ShoppingItemAdapter(List<ShoppingItem> shoppingItemList, ItemSwipedListener swipeListener) {
        this.shoppingItemList = shoppingItemList;
        this.swipeListener = swipeListener;
    }

    @NonNull
    @Override
    public ShoppingItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingItemAdapter.ViewHolder holder, int position) {
        ShoppingItem shoppingItem = shoppingItemList.get(position);
        holder.itemCheckBox.setText(shoppingItem.getName());
        holder.itemCheckBox.setChecked(false);
    }

    public void setShoppingItemList(List<ShoppingItem> shoppingItemList) {
        this.shoppingItemList = shoppingItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return shoppingItemList.size();
    }

    public void setOnClickListener(ShoppingItemAdapter.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox itemCheckBox;
        private final ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCheckBox = itemView.findViewById(R.id.shopping_list_item_checkbox);
            delete = itemView.findViewById(R.id.shopping_list_delete);
            itemCheckBox.setOnClickListener(view -> {
                listener.OnClick(shoppingItemList.get(getBindingAdapterPosition()),false, getBindingAdapterPosition(), itemCheckBox.isChecked());
            });
            delete.setOnClickListener(view -> {
                listener.OnClick(shoppingItemList.get(getBindingAdapterPosition()),true, getBindingAdapterPosition(), itemCheckBox.isChecked());
            });
        }
    }

    public interface OnClickListener {
        void OnClick(ShoppingItem item, boolean delete, int index, boolean isChecked);
    }

    @Override
    public void onItemSwiped(int position, int direction) {
        if (ItemTouchHelper.LEFT == direction) {
            ShoppingItem removedItem = shoppingItemList.get(position);
            shoppingItemList.remove(position);
            notifyItemRemoved(position);
            if (swipeListener != null) {
                swipeListener.itemSwipedLeft(removedItem.getName()); // Notify the listener
            }
        }
        else if (ItemTouchHelper.RIGHT == direction) {
            ShoppingItem removedItem = shoppingItemList.get(position);
            shoppingItemList.remove(position);
            notifyItemRemoved(position);
            if (swipeListener != null) {
                swipeListener.itemSwipedRight(removedItem.getName()); // Notify the listener
            }
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(shoppingItemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(shoppingItemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        swipeListener.itemMoved();
        return true;
    }
}
