package com.nhn.ditimtrieuphu.view.fragment;

import static com.nhn.ditimtrieuphu.view.dialog.PurchaseItemDialog.ITEM_PURCHASE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.nhn.ditimtrieuphu.App;
import com.nhn.ditimtrieuphu.OnActionCallBack;
import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.async.QuestionManager;
import com.nhn.ditimtrieuphu.common.CommonUtils;
import com.nhn.ditimtrieuphu.common.UiUtils;
import com.nhn.ditimtrieuphu.database.AppDatabase;
import com.nhn.ditimtrieuphu.entity.Item;
import com.nhn.ditimtrieuphu.model.ItemUser;
import com.nhn.ditimtrieuphu.view.adapter.ShopItemsAdapter;
import com.nhn.ditimtrieuphu.view.dialog.PurchaseItemDialog;
import com.nhn.ditimtrieuphu.viewmodel.MainFragViewModel;

import java.util.ArrayList;

public class PlayerProfileFragment extends BaseFragment<MainFragViewModel> implements ShopItemsAdapter.OnItemClick, PurchaseItemDialog.OnCallBack {
    public static final String KEY_BACK_PLAYER_PROFILE_FRAGMENT = "KEY_BACK_PLAYER_PROFILE_FRAGMENT";

    private OnActionCallBack mCallBack;
    private UiUtils mUiUtils;
    // view
    private ImageView ivBack;
    private ImageView ivExit;
    private TextView mPlayerNameTextView;
    private TextView mPlayerLevelTextView;
    private TextView mPlayerPropertyTextView;
    private RecyclerView mEquippedBadgeRecyclerView;
    private ArrayList<ItemUser> itemList;
    private ArrayList<Item> itemsFromDatabase;
    private ShopItemsAdapter shopItemsAdapter;

    @Override
    protected void initViews() {
        AppDatabase database =  Room.databaseBuilder(getActivity(),
                        AppDatabase.class, "databases/Question.db")
                .allowMainThreadQueries()
                .build();
        itemsFromDatabase = new ArrayList<>();
        itemList = new ArrayList<>();
        if (CommonUtils.getInstance().isExistPref("score")) {
            QuestionManager.getInstance().getItemUser(CommonUtils.getInstance().getPref("username"), new QuestionManager.OnResultCallBack() {
                @Override
                public void callBack(Object data) {
                    itemsFromDatabase = (ArrayList<Item>) data;
                    dummyItemListData();
                }
            });
        } else {
            dummyDataForNew();
        }


        mUiUtils = UiUtils.getInstance(App.getInstance());
        ivBack = findViewById(R.id.iv_back, this);
        ivExit = findViewById(R.id.iv_exit, this);
        mPlayerNameTextView = findViewById(R.id.tv_player_name);
        mPlayerLevelTextView = findViewById(R.id.tv_player_level);
        mPlayerPropertyTextView = findViewById(R.id.tv_player_money);
        mEquippedBadgeRecyclerView = findViewById(R.id.rv_badges);

        shopItemsAdapter = new ShopItemsAdapter(App.getInstance(), itemList);
        mEquippedBadgeRecyclerView.setAdapter(shopItemsAdapter);
        mEquippedBadgeRecyclerView.setLayoutManager(new GridLayoutManager(App.getInstance(), 3));
        shopItemsAdapter.setOnItemClick(this);
        if (CommonUtils.getInstance().isExistPref("username")) {
            String username = CommonUtils.getInstance().getPref("username");
            mPlayerNameTextView.setText(username);
        }
        if (CommonUtils.getInstance().isExistPref("score")) {
            String score = CommonUtils.getInstance().getScore("score");
            mPlayerPropertyTextView.setText(score);
        }
    }

    private void dummyDataForNew() {
        itemList.add(new ItemUser("Tập sự",R.drawable.b1,"10.000.000",false));
        itemList.add(new ItemUser("Hạ sĩ",R.drawable.b2,"20.000.000",false));
        itemList.add(new ItemUser("Thiếu uý",R.drawable.b3,"40.000.000",false));
        itemList.add(new ItemUser("Tư lệnh",R.drawable.b4,"80.000.000",false));
        itemList.add(new ItemUser("Chỉ huy",R.drawable.b5,"100.000.000",false));
        itemList.add(new ItemUser("Kim cương",R.drawable.b6,"150.000.000",false));
        itemList.add(new ItemUser("Cao thủ",R.drawable.b7,"180.000.000",false));
        itemList.add(new ItemUser("Thách đấu",R.drawable.b8,"220.000.000",false));
    }

    private void dummyItemListData() {
        itemList.add(new ItemUser("Tập sự",R.drawable.b1,"10.000.000",false));
        itemList.add(new ItemUser("Hạ sĩ",R.drawable.b2,"20.000.000",false));
        itemList.add(new ItemUser("Thiếu uý",R.drawable.b3,"40.000.000",false));
        itemList.add(new ItemUser("Tư lệnh",R.drawable.b4,"80.000.000",false));
        itemList.add(new ItemUser("Chỉ huy",R.drawable.b5,"100.000.000",false));
        itemList.add(new ItemUser("Kim cương",R.drawable.b6,"150.000.000",false));
        itemList.add(new ItemUser("Cao thủ",R.drawable.b7,"180.000.000",false));
        itemList.add(new ItemUser("Thách đấu",R.drawable.b8,"220.000.000",false));
        for (Item itemDatabase : itemsFromDatabase) {
            // Lấy chỉ số của itemA
            int indexA = itemDatabase.getIndexItem();

            for (int i = 0; i < itemList.size(); i++) {
                int index = i+1;
                if (index == indexA) {
                    itemList.get(i).setActive(true);
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                mCallBack.onCallBack(KEY_BACK_PLAYER_PROFILE_FRAGMENT);
                break;
            }
            case R.id.iv_exit: {
                CommonUtils.getInstance().clearPref("username");
                CommonUtils.getInstance().clearPref("score");
                CommonUtils.getInstance().clearPref("first_open");
                mCallBack.onCallBack(KEY_BACK_PLAYER_PROFILE_FRAGMENT);
                break;
            }
        }
    }

    @Override
    protected Class<MainFragViewModel> getClassViewModel() {
        return MainFragViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.m007_player_profile_fragment;
    }

    public void setCallBack(OnActionCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void onItemClick(ItemUser item) {
        PurchaseItemDialog purchaseItemDialog = new PurchaseItemDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEM_PURCHASE, item);
        purchaseItemDialog.setArguments(bundle);
        purchaseItemDialog.setOnCallBack(this);
        purchaseItemDialog.show(getParentFragmentManager(), PurchaseItemDialog.TAG_DIALOG_PURCHASE_ITEM);
    }

    @Override
    public void onPurchaseItem(ItemUser item) {
        int index = itemList.indexOf(item);
        if (CommonUtils.getInstance().isExistPref("score")) {
            String score = CommonUtils.getInstance().getScore("score");
            double surplus = CommonUtils.getInstance().convertScoreUserToDouble(score) -
                    CommonUtils.getInstance().convertStringScoreToDouble(item.getPrice());
            if (surplus >= 0) {
                item.setActive(true);
                itemList.set(index, item);
                shopItemsAdapter.notifyItemChanged(index);
                CommonUtils.getInstance().clearPref("score");
                String formattedNumber = String.format("%,.0f", surplus);
                CommonUtils.getInstance().saveScore("score",formattedNumber);
                QuestionManager.getInstance().addNewItemUser(new QuestionManager.OnResultCallBack() {
                    @Override
                    public void callBack(Object data) {
                        mPlayerPropertyTextView.setText(formattedNumber);
                    }
                }, new Item(CommonUtils.getInstance().getPref("username"), index+1));
            } else {
                Toast.makeText(App.getInstance(),"Score của bạn không đủ",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
