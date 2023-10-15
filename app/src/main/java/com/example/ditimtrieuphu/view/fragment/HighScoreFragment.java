package com.example.ditimtrieuphu.view.fragment;

import static com.example.ditimtrieuphu.view.fragment.M003PlayFragment.KEY_SHOW_MAIN_FRAGMENT;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.dto.PlayerInfo;
import com.example.ditimtrieuphu.entity.HighScore;
import com.example.ditimtrieuphu.view.adapter.HighScoreAdapter;
import com.example.ditimtrieuphu.viewmodel.HighScoreViewModel;
import java.util.ArrayList;
import java.util.List;

public class HighScoreFragment extends BaseFragment<HighScoreViewModel> {
    public static final String KEY_SHOW_HIGH_SCORE_FRAGMENT = "KEY_SHOW_HIGH_SCORE_FRAGMENT";

    private RecyclerView recyclerView;

    private ImageView highScoreBackBtn;

    private OnActionCallBack callBack;
    private List<HighScore> highScoreList;

    @Override
    protected void initViews() {
        mModel.syncDataHighScore();
        highScoreList = new ArrayList<>();
        mModel.getmPlayerInfoMutableLiveData().observe(this, new Observer<List<PlayerInfo>>() {
            @Override
            public void onChanged(List<PlayerInfo> playerInfos) {
                filterDataHighScore(playerInfos);
                updateUI();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);

        highScoreBackBtn = findViewById(R.id.highScoreBackBtn);
        highScoreBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT);
            }
        });
    }

    private void updateUI() {
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(highScoreList, mContext);
        recyclerView.setAdapter(highScoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void filterDataHighScore(List<PlayerInfo> playerInfos) {
        for (PlayerInfo playerInfo : playerInfos) {
            highScoreList.add(new HighScore(playerInfo.getDisplayName(),String.valueOf(playerInfo.getProperty()),String.valueOf(playerInfo.getLevel())));
        }
    }

    @Override
    protected Class getClassViewModel() {
        return HighScoreViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_high_score;
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }
}