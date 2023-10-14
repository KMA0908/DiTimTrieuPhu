package com.example.ditimtrieuphu.view.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.model.ItemShop;
import com.example.ditimtrieuphu.view.adapter.ItemShopAdapter;
import com.example.ditimtrieuphu.viewmodel.ShopViewModel;

import java.util.ArrayList;


public class ShopFragment extends BaseFragment<ShopViewModel> {
    public static final String KEY_SHOW_MAIN_FRAGMENT = "KEY_SHOW_MAIN_FRAGMENT";
    private OnActionCallBack mCallBack;

    private ItemShopAdapter adapter;
    private RecyclerView recyclerView;

    public void setmCallBack(OnActionCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected void initViews() {
        ArrayList<ItemShop> list = new ArrayList<>();
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        list.add(new ItemShop("150.000.000"));
        recyclerView = findViewById(R.id.rcv_shop);
        adapter = new ItemShopAdapter(getContext(), list);
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
}
