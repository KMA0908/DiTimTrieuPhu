package com.example.ditimtrieuphu.view.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.common.UiUtils;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.view.adapter.ItemShopAdapter;
import com.example.ditimtrieuphu.view.dialog.BonusItemDetailsDialog;
import com.example.ditimtrieuphu.viewmodel.BagViewModel;

import java.util.List;

public class BagFragment extends BaseFragment<BagViewModel>{
    public static final String KEY_BAG_FRAGMENT = "KEY_BAG_FRAGMENT";
    private OnActionCallBack mCallBack;
    private ItemShopAdapter adapter;
    private RecyclerView recyclerView;
    private List<BonusItem> mOwnedItems;

    @Override
    protected void initViews() {
        mOwnedItems = mModel.getAllItem();
        recyclerView = findViewById(R.id.rcv_shop);
        adapter = new ItemShopAdapter(getContext(), mOwnedItems, this::onItemClicked);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Class<BagViewModel> getClassViewModel() {
        return BagViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bag_fragment;
    }

    public void setCallBack(OnActionCallBack callBack) {
        mCallBack = callBack;
    }

    private void onItemClicked(Object...objects) {
        if (objects != null && objects.length > 0) {
            int index = (int) objects[0];
            BonusItemDetailsDialog dialog = new BonusItemDetailsDialog();
            dialog.setBonusItem(mOwnedItems.get(index));
            dialog.setIsItemInShop(false);
            dialog.show(getParentFragmentManager(), BonusItemDetailsDialog.TAG);
        }
    }
}
