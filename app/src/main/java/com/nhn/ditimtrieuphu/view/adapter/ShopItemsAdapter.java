package com.nhn.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.model.ItemUser;
import java.util.List;


public class ShopItemsAdapter extends RecyclerView.Adapter<ShopItemsAdapter.ItemHolder> {
    private OnItemClick callBack;

    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    private List<ItemUser> items;
    private Context context;

    public ShopItemsAdapter(Context context,List<ItemUser> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_shop, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.item = items.get(position);
        holder.name.setText(items.get(position).getName());
        holder.image.setImageResource(items.get(position).getImage());
        holder.price.setText(items.get(position).getPrice());
        if (items.get(position).isActive()) {
            holder.ivActive.setVisibility(View.VISIBLE);
        } else {
            holder.ivActive.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        ItemUser item;
        ImageView image;
        ImageView ivActive;
        TextView name;
        TextView price;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image_item);
            name = itemView.findViewById(R.id.tv_name_item);
            ivActive = itemView.findViewById(R.id.iv_active);
            price = itemView.findViewById(R.id.tv_price_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!item.isActive()) {
                        callBack.onItemClick(item);
                    }
                }
            });
        }
    }

    public interface OnItemClick {
        void onItemClick(ItemUser item);
    }
}
