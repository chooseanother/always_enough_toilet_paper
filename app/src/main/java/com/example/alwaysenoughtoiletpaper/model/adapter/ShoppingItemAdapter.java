package com.example.alwaysenoughtoiletpaper.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.model.ShoppingItem;

import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> {
    private List<ShoppingItem> shoppingItemList;
    private OnClickListener listener;

    public ShoppingItemAdapter(List<ShoppingItem> shoppingItemList){
        this.shoppingItemList = shoppingItemList;
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
        holder.itemCheckBox.setChecked(shoppingItem.isBought());
    }

    public void setShoppingItemList(List<ShoppingItem> shoppingItemList) {
        this.shoppingItemList = shoppingItemList;
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
                listener.OnClick(shoppingItemList.get(getBindingAdapterPosition()),false);
            });
            // TODO set onclick listener for delete
            delete.setOnClickListener(view -> {
                listener.OnClick(shoppingItemList.get(getBindingAdapterPosition()),true);
            });
        }
    }

    public interface OnClickListener {
        void OnClick(ShoppingItem item, boolean delete);
    }
}
