gpackage com.example.ditimtrieuphu.view.fragment;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.Question;
import com.example.ditimtrieuphu.view.adapter.QuestionAdapter;
import com.example.ditimtrieuphu.viewmodel.QuestionViewModel;

import java.util.ArrayList;

public class M004QuestionFragment extends BaseFragment<QuestionViewModel> {
    private OnActionCallBack callBack;
    public static final String KEY_SHOW_PLAY_FRAGMENT = "KEY_SHOW_PLAY_FRAGMENT";

    private MediaPlayer mediaPlayer;
    private ArrayList<Question> list;
    private QuestionAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void initViews() {

        recyclerView = findViewById(R.id.recycler_view_question);
        init();
        setUp();

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                FrameLayout frameLayout = recyclerView.getLayoutManager().findViewByPosition(10).findViewById(R.id.background_question);
                frameLayout.setVisibility(View.VISIBLE);
                list.get(10).setState(true);
            }
        }, 4200);
        ShowOrHideBackground(10, 5, 5000);
        ShowOrHideBackground(5, 0, 5800);

        PlayMusic();
    }

    private void showPlayFragment() {
        callBack.onCallBack(KEY_SHOW_PLAY_FRAGMENT,null);
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

        list.add(new Question("150.000.000", 15, false));
        list.add(new Question("85.000.000", 14, false));
        list.add(new Question("60.000.000", 13, false));
        list.add(new Question("40.000.000", 12, false));
        list.add(new Question("30.000.000", 11, false));
        list.add(new Question("22.000.000", 10, false));
        list.add(new Question("14.000.000", 9, false));
        list.add(new Question("10.000.000", 8, false));
        list.add(new Question("6.000.000", 7, false));
        list.add(new Question("3.000.000", 6, false));
        list.add(new Question("2.000.000", 5, false));
        list.add(new Question("1.000.000", 4, false));
        list.add(new Question("600.000", 3, false));
        list.add(new Question("400.000", 2, false));
        list.add(new Question("200.000", 1, false));

        adapter = new QuestionAdapter(getContext(), list);
    }
    private void setUp(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


    private void PlayMusic() {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.luatchoi_b);
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp = MediaPlayer.create(getContext(), R.raw.gofind);
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

    private void ShowOrHideBackground(int position, int position2, long delayMillis){
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

