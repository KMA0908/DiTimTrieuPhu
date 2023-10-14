package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.Badge;

import java.util.List;

public class EquippedBadgeAdapter extends RecyclerView.Adapter<EquippedBadgeAdapter.EquippedBadgeViewHolder> {
    public static final int MAX_NUMBER_BADGE_EQUIPPED = 5;
    private Context mContext;
    private List<Badge> mEquippedBadges;
    private Executable mItemClickedEvent;

    public EquippedBadgeAdapter(Context context, List<Badge> list, Executable itemEvent) {
        mContext = context;
        mEquippedBadges = list;
        mItemClickedEvent = itemEvent;
    }

    @NonNull
    @Override
    public EquippedBadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_equipped_badge, parent, false);
        return new EquippedBadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquippedBadgeViewHolder holder, int position) {
        try {
            Badge badge = mEquippedBadges.get(position);
            if (badge.isEquipped()) {
                int iconId = mContext.getResources().getIdentifier(badge.getIcon(), "drawable", mContext.getPackageName());
                if (iconId != 0) {
                    Drawable drawable = mContext.getDrawable(iconId);
                    holder.mIconBadge.setImageDrawable(drawable);
                    holder.mRootView.setBackground(null);
                }
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }

    @Override
    public int getItemCount() {
        return MAX_NUMBER_BADGE_EQUIPPED;
    }

    class EquippedBadgeViewHolder extends RecyclerView.ViewHolder {
        View mRootView;
        ImageView mIconBadge;

        public EquippedBadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            mRootView = itemView;
            mIconBadge = itemView.findViewById(R.id.iv_icon_badge);
            itemView.setOnClickListener(view -> {
                mItemClickedEvent.execute(getAdapterPosition());
            });
        }
    }
}
