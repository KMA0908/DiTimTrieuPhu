package com.example.ditimtrieuphu.view.fragment;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.App;
import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.model.QuestionIndex;
import com.example.ditimtrieuphu.view.adapter.QuestionAdapter;
import com.example.ditimtrieuphu.viewmodel.QuestionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if (App.getInstance().getCurrentLevel() != 0) {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FrameLayout frameLayout = recyclerView.getLayoutManager().
                            findViewByPosition(10).findViewById(R.id.background_question);
                    frameLayout.setVisibility(View.VISIBLE);
                    list.get(10).setState(true);
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
        Log.d("MinhNTn", "showPlayFragment: ");
        callBack.onCallBack(KEY_SHOW_QUESTION_DETAIL,null);
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

        adapter = new QuestionAdapter(getContext(), list);
    }
    private void setUp(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    private void playMusic() {
        Log.d("MinhNTn", "playMusic: ");
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.luatchoi_b);
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("MinhNTn", "onCompletion: ");
                mp = MediaPlayer.create(getContext(), R.raw.gofind);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d("MinhNTn", "onCompletion: 1");
                        showPlayFragment();
                    }
                });
            }
        });
    }

    private void playLevelMusic() {
        if(mediaLevelPlayer == null){
            mediaLevelPlayer = MediaPlayer.create(getContext(), R.raw.chuc_mung_vuot_moc_01_0);
        }
        mediaLevelPlayer.start();
        mediaLevelPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp = MediaPlayer.create(getContext(), R.raw.ques6);
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

