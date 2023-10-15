package com.example.ditimtrieuphu.view.fragment;

import android.text.TextUtils;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.UiUtils;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.model.ItemShop;
import com.example.ditimtrieuphu.view.adapter.ItemShopAdapter;
import com.example.ditimtrieuphu.view.dialog.BonusItemDetailsDialog;
import com.example.ditimtrieuphu.viewmodel.ShopViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ShopFragment extends BaseFragment<ShopViewModel> {
    public static final String KEY_SHOW_MAIN_FRAGMENT = "KEY_SHOW_MAIN_FRAGMENT";
    public static final String KEY_SHOP_FRAGMENT = "KEY_SHOP_FRAGMENT";
    private OnActionCallBack mCallBack;

    private ItemShopAdapter adapter;
    private RecyclerView recyclerView;
    private List<BonusItem> mBonusItems;
    private UiUtils mUiUtils;

    public void setmCallBack(OnActionCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected void initViews() {
        mUiUtils = UiUtils.getInstance();
        mBonusItems = mModel.getAllItem();
        recyclerView = findViewById(R.id.rcv_shop);
        adapter = new ItemShopAdapter(getContext(), mBonusItems, this::onItemShopClicked);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Class<ShopViewModel> getClassViewModel() {
        return ShopViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_fragment;
    }

    private void gotoMainFragment() {
        mCallBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT);
    }

    private void onItemShopClicked(Object...objects) {
        if (objects != null && objects.length > 0) {
            int index = (int) objects[0];
            BonusItemDetailsDialog dialog = new BonusItemDetailsDialog();
            dialog.setBonusItem(mBonusItems.get(index));
            dialog.setItemBuyClickExecutable(objects1 -> {
                if (objects1 != null && objects1.length > 0) {
                    BonusItem bonusItem = (BonusItem) objects1[0];
                    mUiUtils.showBlur(getParentFragmentManager());
                    mModel.buyItem(bonusItem, objects2 -> {
                        mUiUtils.dismissBlur();
                        // mua thanh cong xoa item khoi shop
                        mBonusItems.remove(index);
                        adapter.notifyDataSetChanged();
                    }, objects2 -> {
                        mUiUtils.dismissBlur();
                        mUiUtils.showMessage(getParentFragmentManager(), (String) objects2[0]);
                    });
                }
            });
            dialog.show(getParentFragmentManager(), BonusItemDetailsDialog.TAG);
        }
    }
}
