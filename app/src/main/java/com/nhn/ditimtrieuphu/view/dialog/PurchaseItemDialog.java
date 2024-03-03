package com.nhn.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.common.UiUtils;
import com.nhn.ditimtrieuphu.model.ItemUser;
public class PurchaseItemDialog extends DialogFragment {
    public static final String TAG_DIALOG_PURCHASE_ITEM = "TAG_DIALOG_PURCHASE_ITEM";
    public static final String ITEM_PURCHASE = "ITEM_PURCHASE";

    private TextView tvTitle, tvPrice;

    private ImageView ivItem;
    private AppCompatButton btPurchase, btClose;
    private ItemUser data;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = UiUtils.getInstance(getContext()).calculateWidthWithMargin(24f);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return inflater.inflate(R.layout.dialog_purchase_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitle = view.findViewById(R.id.tv_title_purchase_dialog);
        tvPrice = view.findViewById(R.id.tv_price_item);
        ivItem = view.findViewById(R.id.iv_item);
        btClose = view.findViewById(R.id.bt_close_purchase);
        btPurchase = view.findViewById(R.id.bt_confirm_purchase);
        Bundle args = getArguments();
        if (args != null) {
            data = (ItemUser) args.getSerializable(PurchaseItemDialog.ITEM_PURCHASE);
            tvTitle.setText("Bạn muốn mở khoá huy hiệu "+data.getName());
            tvPrice.setText("Giá huy hiệu là "+data.getPrice());
            ivItem.setImageResource(data.getImage());
        }

        btPurchase.setOnClickListener(view1 -> {
            callBack.onPurchaseItem(data);
            dismiss();
        });
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    private OnCallBack callBack;

    public void setOnCallBack(OnCallBack event) {
        callBack = event;
    }

    public interface OnCallBack {
        void onPurchaseItem(ItemUser itemUser);
    }
}
