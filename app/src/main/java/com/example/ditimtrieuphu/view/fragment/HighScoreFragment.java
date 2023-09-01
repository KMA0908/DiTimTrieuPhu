package com.example.ditimtrieuphu.view.fragment;

import static com.example.ditimtrieuphu.view.fragment.M003PlayFragment.KEY_SHOW_MAIN_FRAGMENT;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.adapter.HighScoreAdapter;
import com.example.ditimtrieuphu.view.fragment.BaseFragment;
import com.example.ditimtrieuphu.viewmodel.HighScoreViewModel;

public class HighScoreFragment extends BaseFragment<HighScoreViewModel> {
    public static final String KEY_SHOW_HIGH_SCORE_FRAGMENT = "KEY_SHOW_HIGH_SCORE_FRAGMENT";

    private RecyclerView recyclerView;

    private ImageView highScoreBackBtn;

    private OnActionCallBack callBack;

    private int images[] = {R.drawable.ic_launcher_background, R.drawable.tutorial_background, R.drawable.ic_call, R.drawable.ic_reset};
    private String names[] = {"Nguyen Van Thanh", "Nguyen Van B", "Nguyen Van C", "Nguyen Van D"};
    private int scores[] = {150000000, 200000, 300000, 400000};
    private String dates[] = {"01/01/2021", "02/02/2021", "03/03/2021", "04/04/2021"};

    @Override
    protected void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        highScoreBackBtn = (ImageView) findViewById(R.id.highScoreBackBtn);
        highScoreBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT);
            }
        });

        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(images, names, scores, dates, mContext);
        recyclerView.setAdapter(highScoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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