package com.example.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.view.adapter.ItemUsedAdapter;

import java.util.ArrayList;
import java.util.List;

public class UseItemPlayDialog extends DialogFragment {
    public static final String TAG = "TAG_USED_ITEM";

    private RecyclerView mItemRecyclerView;
    private AppCompatButton mPlayX1Button;
    private AppCompatButton mPlayX2Button;
    private ItemUsedAdapter mAdapter;
    private List<BonusItem> mOwnedItems;
    private Executable mPlayExecutable;
    private Executable mItemUsedExecutable;
    private List<BonusItem> usedItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_use_item_before_play, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mItemRecyclerView = view.findViewById(R.id.rv_items);
        mPlayX1Button = view.findViewById(R.id.bt_play_x1);
        mPlayX2Button = view.findViewById(R.id.bt_play_x2);

        if (mOwnedItems == null) {
            mOwnedItems = new ArrayList<>();
        }
        usedItem = new ArrayList<>();
        mAdapter = new ItemUsedAdapter(mOwnedItems, getContext(), this::onItemChecked);
        mItemRecyclerView.setAdapter(mAdapter);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPlayX1Button.setOnClickListener(view1 -> {
            mPlayExecutable.execute(0, usedItem);
        });
        mPlayX1Button.setOnClickListener(view1 -> {
            mPlayExecutable.execute(1, usedItem);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setOwnedItems(List<BonusItem> list) {
        if (mOwnedItems == null) {
            mOwnedItems = new ArrayList<>();
        }
        mOwnedItems.clear();
        mOwnedItems.addAll(list);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setButtonExecutable(Executable executable) {
        mPlayExecutable = executable;
    }

    public void setUseItemExecutable(Executable executable) {
        mItemUsedExecutable = executable;
    }

    private void onItemChecked(Object...objects) {
        if (objects != null && objects.length > 0) {
            int index = (int) objects[0];
            boolean checked = (boolean) objects[1];
            if (checked) {
                usedItem.add(mOwnedItems.get(index));
            } else {
                usedItem.remove(mOwnedItems.get(index));
            }
        }
    }
}
