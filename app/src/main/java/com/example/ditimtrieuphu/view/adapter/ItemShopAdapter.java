package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.model.ItemShop;

import java.util.List;

public class ItemShopAdapter extends RecyclerView.Adapter<ItemShopAdapter.ItemHolder> {

    private Context context;
    private List<ItemShop> listItemShop;

    public ItemShopAdapter(Context context, List<ItemShop> listItemShop) {
        this.context = context;
        this.listItemShop = listItemShop;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemShopAdapter.ItemHolder holder, int position) {

        holder.tvNameItem.setText(listItemShop.get(position).getName());
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

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNameItem = itemView.findViewById(R.id.tv_name_item);
        }
    }
}
