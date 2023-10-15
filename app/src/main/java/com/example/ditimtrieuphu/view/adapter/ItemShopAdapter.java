package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.model.ItemShop;

import java.util.List;

public class ItemShopAdapter extends RecyclerView.Adapter<ItemShopAdapter.ItemHolder> {

    private Context context;
    private List<BonusItem> listItemShop;
    private Executable itemClickExecutable;

    public ItemShopAdapter(Context context, List<BonusItem> listItemShop, Executable onItemClick) {
        this.context = context;
        this.listItemShop = listItemShop;
        itemClickExecutable = onItemClick;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemShopAdapter.ItemHolder holder, int position) {
        BonusItem bonusItem = listItemShop.get(position);
        int id = context.getResources().getIdentifier(bonusItem.getIcon(), "drawable", context.getPackageName());
        if (id != 0) {
            Drawable drawable = context.getDrawable(id);
            holder.ivImageItem.setImageDrawable(drawable);
        }
        holder.tvNameItem.setText(bonusItem.getName());
        String price = String.valueOf(bonusItem.getPriceMoney());
        holder.tvPriceItem.setText(price);
    }

    @Override
    public int getItemCount() {
        if(listItemShop != null){
            return listItemShop.size();
        }
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        private TextView tvNameItem;
        private TextView tvPriceItem;
        private ImageView ivImageItem;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNameItem = itemView.findViewById(R.id.tv_name_item);
            tvPriceItem = itemView.findViewById(R.id.tv_price_item);
            ivImageItem = itemView.findViewById(R.id.iv_image_item);
            itemView.setOnClickListener(view -> {
                itemClickExecutable.execute(getAdapterPosition());
            });
        }
    }
}
