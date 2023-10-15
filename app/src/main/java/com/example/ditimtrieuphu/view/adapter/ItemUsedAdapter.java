package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.BonusItem;

import org.w3c.dom.Text;

import java.util.List;

public class ItemUsedAdapter extends RecyclerView.Adapter<ItemUsedAdapter.ItemUsedViewHolder> {
    private List<BonusItem> list;
    private Context context;
    private Executable onItemChecked;

    public ItemUsedAdapter(List<BonusItem> list, Context context, Executable onChecked) {
        this.list = list;
        this.context = context;
        onItemChecked = onChecked;
    }

    @NonNull
    @Override
    public ItemUsedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_use_bonus_item, parent, false);
        return new ItemUsedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemUsedViewHolder holder, int position) {
        BonusItem bonusItem = list.get(position);
        int id = context.getResources().getIdentifier(bonusItem.getIcon(), "drawable", context.getPackageName());
        if (id != 0) {
            Drawable drawable = context.getDrawable(id);
            holder.icon.setImageDrawable(drawable);
        }
        holder.name.setText(bonusItem.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemUsedViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView icon;
        private TextView name;

        public ItemUsedViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_use);
            icon = itemView.findViewById(R.id.iv_image_item);
            name = itemView.findViewById(R.id.tv_item_name);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onItemChecked.execute(getAdapterPosition(), b);
                }
            });
        }
    }
}
