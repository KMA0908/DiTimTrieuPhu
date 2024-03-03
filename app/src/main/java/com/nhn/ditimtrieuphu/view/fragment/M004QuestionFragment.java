package com.nhn.ditimtrieuphu.view.fragment;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhn.ditimtrieuphu.App;
import com.nhn.ditimtrieuphu.OnActionCallBack;
import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.model.QuestionIndex;
import com.nhn.ditimtrieuphu.view.act.BaseAct;
import com.nhn.ditimtrieuphu.view.adapter.QuestionAdapter;
import com.nhn.ditimtrieuphu.viewmodel.QuestionViewModel;

import java.util.ArrayList;

public class M004QuestionFragment extends BaseFragment<QuestionViewModel> {
    private OnActionCallBack callBack;
    public static final String KEY_SHOW_QUESTION_DETAIL = "KEY_SHOW_QUESTION_DETAIL";

    private MediaPlayer mediaPlayer,mediaLevelPlayer;
    private ArrayList<QuestionIndex> list;
    private QuestionAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void initViews() {

        recyclerView = findViewById(R.id.recycler_view_question);
        init();
        setUp();
        if (App.getInstance().getStorage().getCurrentLevel() != 0) {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int index = 0;
                    if (App.getInstance().getStorage().getCurrentLevel() == 6) {
                        index = 10;
                    } else {
                        index = 5;
                    }
                    FrameLayout frameLayout = recyclerView.getLayoutManager().
                            findViewByPosition(index).findViewById(R.id.background_question);
                    frameLayout.setVisibility(View.VISIBLE);
                    list.get(index).setState(true);
                }
            }, 2000);
            playLevelMusic();
        } else {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FrameLayout frameLayout = recyclerView.getLayoutManager().
                            findViewByPosition(10).findViewById(R.id.background_question);
                    frameLayout.setVisibility(View.VISIBLE);
                    list.get(10).setState(true);
                }
            }, 4200);
            showOrHideBackground(10, 5, 5000);
            showOrHideBackground(5, 0, 5800);

            playMusic();
        }
    }

    private void showPlayFragment() {
        callBack.onCallBack(KEY_SHOW_QUESTION_DETAIL);
    }

    @Override
    protected Class<QuestionViewModel> getClassViewModel() {
        return QuestionViewModel.class;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.m004_question_fragment;
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }

    private void init(){
        list = new ArrayList<>();

        list.add(new QuestionIndex("150.000.000", 15, false));
        list.add(new QuestionIndex("85.000.000", 14, false));
        list.add(new QuestionIndex("60.000.000", 13, false));
        list.add(new QuestionIndex("40.000.000", 12, false));
        list.add(new QuestionIndex("30.000.000", 11, false));
        list.add(new QuestionIndex("22.000.000", 10, false));
        list.add(new QuestionIndex("14.000.000", 9, false));
        list.add(new QuestionIndex("10.000.000", 8, false));
        list.add(new QuestionIndex("6.000.000", 7, false));
        list.add(new QuestionIndex("3.000.000", 6, false));
        list.add(new QuestionIndex("2.000.000", 5, false));
        list.add(new QuestionIndex("1.000.000", 4, false));
        list.add(new QuestionIndex("600.000", 3, false));
        list.add(new QuestionIndex("400.000", 2, false));
        list.add(new QuestionIndex("200.000", 1, false));

        adapter = new QuestionAdapter(App.getInstance(), list);
    }
    private void setUp(){
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance(), RecyclerView.VERTICAL, false));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private void playMusic() {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(App.getInstance(), R.raw.luatchoi_b);
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp = MediaPlayer.create(App.getInstance(), R.raw.gofind);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        showPlayFragment();
                    }
                });
            }
        });
    }

    private void playLevelMusic() {
        int sourcePlay = 0;
        int nextPlay = 0;
        if (App.getInstance().getStorage().getCurrentLevel() == 6) {
            sourcePlay = R.raw.chuc_mung_vuot_moc_01_0;
            nextPlay = R.raw.ques6;
        } else {
            sourcePlay = R.raw.chuc_mung_vuot_moc_02_0;
            nextPlay = R.raw.ques11;
        }
        if(mediaLevelPlayer == null){
            mediaLevelPlayer = MediaPlayer.create(App.getInstance(), sourcePlay);
        }
        mediaLevelPlayer.start();
        int finalNextPlay = nextPlay;
        mediaLevelPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp = MediaPlayer.create(App.getInstance(), finalNextPlay);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // Tai moc cau hoi nay can confirm nguoi choi co dung choi hay khong de nhan thuong
                        showPlayFragment();
                    }
                });
            }
        });
    }

    private void showOrHideBackground(int position, int position2, long delayMillis){
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                FrameLayout frameLayout2 = recyclerView.getLayoutManager().findViewByPosition(position2).findViewById(R.id.background_question);
                FrameLayout frameLayout = recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.background_question);
                frameLayout.setVisibility(View.GONE);
                frameLayout2.setVisibility(View.VISIBLE);

                list.get(position).setState(false);
                list.get(position2).setState(true);
            }
        }, delayMillis);
    }

}

