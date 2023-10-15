package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.entity.Badge;

import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {
    private Context mContext;
    private List<Badge> mOwnedBadge;
    private Executable mButtonEvent;
    private Executable mOnEquipFail;

    public BadgeAdapter(Context context, List<Badge> list, Executable buttonEvent, Executable onEquipFail) {
        mContext = context;
        mOwnedBadge = list;
        mButtonEvent = buttonEvent;
        mOnEquipFail = onEquipFail;
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_badge, parent, false);
        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        Badge badge = mOwnedBadge.get(position);
        int iconId = mContext.getResources().getIdentifier(badge.getIcon(), "drawable", mContext.getPackageName());
        if (iconId != 0) {
            Drawable drawable = mContext.getDrawable(iconId);
            holder.mIconBadgeImageView.setImageDrawable(drawable);
            holder.mBadgeNameTextView.setText(badge.getName());
            setStateButton(holder.mEquipButton, badge.isEquipped());
        }
    }

    @Override
    public int getItemCount() {
        return mOwnedBadge.size();
    }

    private void setStateButton(Button button, boolean equipped) {
        if (equipped) {
            button.setText(R.string.unequip);
            button.setBackgroundColor(mContext.getResources().getColor(R.color.color_question));
        } else {
            button.setText(R.string.equip);
            button.setBackgroundColor(mContext.getResources().getColor(R.color.green));
        }
    }

    private int getEquippedBadgesCount() {
        int count = 0;
        for (Badge badge: mOwnedBadge) {
            if (badge.isEquipped()) {
                count++;
            }
        }
        return count;
    }

    class BadgeViewHolder extends RecyclerView.ViewHolder {
        ImageView mIconBadgeImageView;
        TextView mBadgeNameTextView;
        Button mEquipButton;

        public BadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            mIconBadgeImageView = itemView.findViewById(R.id.iv_icon_badge);
            mBadgeNameTextView = itemView.findViewById(R.id.tv_badge_name);
            mEquipButton = itemView.findViewById(R.id.bt_equip);

            mEquipButton.setOnClickListener(view -> {
                // Check full 5 equip thi khong cho equip nua
                if (getEquippedBadgesCount() >= GameConstant.MAX_EQUIPPED_BADGE
                        && !mOwnedBadge.get(getAdapterPosition()).isEquipped()) {
                    mOnEquipFail.execute();
                    return;
                }
                if (mOwnedBadge.get(getAdapterPosition()).isEquipped()) {
                    mOwnedBadge.get(getAdapterPosition()).setEquipped(false);
                    setStateButton(mEquipButton, false);
                } else {
                    mOwnedBadge.get(getAdapterPosition()).setEquipped(true);
                    setStateButton(mEquipButton, true);
                }
                mButtonEvent.execute(getAdapterPosition());
            });
        }
    }
}
