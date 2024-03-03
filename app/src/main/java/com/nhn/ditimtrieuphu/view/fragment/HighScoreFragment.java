package com.nhn.ditimtrieuphu.view.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nhn.ditimtrieuphu.App;
import com.nhn.ditimtrieuphu.OnActionCallBack;
import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.async.QuestionManager;
import com.nhn.ditimtrieuphu.database.AppDatabase;
import com.nhn.ditimtrieuphu.entity.HighScore;
import com.nhn.ditimtrieuphu.view.adapter.HighScoreAdapter;
import com.nhn.ditimtrieuphu.viewmodel.HighScoreViewModel;
import java.util.ArrayList;
import java.util.List;

public class HighScoreFragment extends BaseFragment<HighScoreViewModel> {
    public static final String KEY_SHOW_HIGH_SCORE_FRAGMENT = "KEY_SHOW_HIGH_SCORE_FRAGMENT";

    public static final String KEY_BACK_HOME_FRAGMENT = "KEY_BACK_HOME_FRAGMENT";

    private RecyclerView recyclerView;

    private ImageView highScoreBackBtn;

    private OnActionCallBack callBack;
    private List<HighScore> highScoreList;

    private AdView mAdView;

    @Override
    protected void initViews() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AppDatabase database =  Room.databaseBuilder(getActivity(),
                        AppDatabase.class, "databases/Question.db")
                .allowMainThreadQueries()
                .build();
        highScoreList = new ArrayList<>();
        // sync data cua high score

        recyclerView = findViewById(R.id.recyclerView);

        highScoreBackBtn = findViewById(R.id.highScoreBackBtn);
        highScoreBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onCallBack(KEY_BACK_HOME_FRAGMENT);
            }
        });
        QuestionManager.getInstance().getAllHighScore(new QuestionManager.OnResultCallBack() {
            @Override
            public void callBack(Object data) {
                List<HighScore> highScores = (List<HighScore>) data;
                highScoreList.addAll(highScores);
                if (!highScoreList.isEmpty()) {
                     updateUI();
                }
            }
        });
    }

    private void updateUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HighScoreAdapter highScoreAdapter = new HighScoreAdapter(highScoreList, App.getInstance());
                recyclerView.setAdapter(highScoreAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));
            }
        });
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