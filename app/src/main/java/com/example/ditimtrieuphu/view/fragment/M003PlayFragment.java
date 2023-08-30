package com.example.ditimtrieuphu.view.fragment;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.example.ditimtrieuphu.App;
import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.async.QuestionManager;
import com.example.ditimtrieuphu.database.AppDatabase;
import com.example.ditimtrieuphu.entity.Question;
import com.example.ditimtrieuphu.view.dialog.BarChartQuestion;
import com.example.ditimtrieuphu.view.dialog.HelpCallDialog;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class M003PlayFragment extends BaseFragment<MainFragViewModel> {
    public static final String KEY_SHOW_QUESTION_FRAGMENT = "KEY_SHOW_QUESTION_FRAGMENT";
    public static final String KEY_SHOW_MAIN_FRAGMENT = "KEY_SHOW_MAIN_FRAGMENT";
    private OnActionCallBack callBack;
    private ProgressBar progressBar;

    private TextView timerTextView;

    private TextView tvQuestion,tvCaseA,tvCaseB,tvCaseC,tvCaseD, tvIndexQuestion;
    private ImageView ivCaseA,ivCaseB,ivCaseC,ivCaseD,ivHelp50,ivChangeQuestion,ivAudienceHelp,ivCallHelp;
    private FrameLayout frameCaseA,frameCaseB,frameCaseC,frameCaseD;
    private MediaPlayer mediaPlayer,mPlayer;
    private String trueCase;
    private int index;
    private List<FrameLayout> frameCaseList ;
    @Override
    protected void initViews() {
        frameCaseList =  new ArrayList<>();
        index = 1;
        tvIndexQuestion = findViewById(R.id.tv_index_question);
        tvQuestion = findViewById(R.id.question);
        progressBar = findViewById(R.id.timeQuestion);
        tvCaseA = findViewById(R.id.tv_caseA);
        tvCaseB = findViewById(R.id.tv_caseB);
        tvCaseC = findViewById(R.id.tv_caseC);
        tvCaseD = findViewById(R.id.tv_caseD);
        ivCaseA = findViewById(R.id.iv_caseA, this);
        ivCaseB = findViewById(R.id.iv_caseB, this);
        ivCaseC = findViewById(R.id.iv_caseC, this);
        ivCaseD = findViewById(R.id.iv_caseD, this);
        frameCaseA = findViewById(R.id.frame_caseA);
        frameCaseB = findViewById(R.id.frame_caseB);
        frameCaseC = findViewById(R.id.frame_caseC);
        frameCaseD = findViewById(R.id.frame_caseD);
        timerTextView = findViewById(R.id.timerTextView);
        ivHelp50 = findViewById(R.id.iv_help_50_50, this);
        ivChangeQuestion = findViewById(R.id.iv_change_question_help, this);
        ivAudienceHelp = findViewById(R.id.iv_audience_help, this);
        ivCallHelp = findViewById(R.id.iv_call_help, this);
        findViewById(R.id.icon_person, this);
        findViewById(R.id.iv_stop, this);
        countDownQuestion();
        AppDatabase database =  Room.databaseBuilder(getActivity(),
                        AppDatabase.class, "databases/Question.db")
                .allowMainThreadQueries()
                .build();

        if (App.getInstance().getStorage().getCurrentLevel() != 0 ) {
           index = App.getInstance().getStorage().getCurrentLevel();
        }
        setTextForQuestion(index);
        setStateHelp();
        QuestionManager.getInstance().getQuestionByLevel(index, new QuestionManager.OnResultCallBack() {
            @Override
            public void callBack(Object data) {

                getActivity().runOnUiThread(() -> {
                    initDataQuestion(data);
                });
            }
        });

        playMusic();
    }

    private void countDownQuestion() {
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) (30000 - millisUntilFinished));
                long secondsLeft = millisUntilFinished / 1000;
                timerTextView.setText(String.valueOf(secondsLeft));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0");
            }
        };

        countDownTimer.start();
    }

    private void setStateHelp() {
        if (App.getInstance().getStorage().isState50()) {
            ivHelp50.setImageResource(R.drawable.ic_50_50_done);
        }
        if (App.getInstance().getStorage().isStateAudi()) {
            ivAudienceHelp.setImageResource(R.drawable.ic_audience_done);
        }
        if (App.getInstance().getStorage().isStateCall()){
            ivCallHelp.setImageResource(R.drawable.ic_phone_done);
        }
        if (App.getInstance().getStorage().isStateChange()){
            ivChangeQuestion.setImageResource(R.drawable.ic_reset_done);
        }
    }

    private void playMusic() {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.background_music);
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
    }
    private void initDataQuestion(Object data) {
        List<Question> listQuestion = (List<Question>) data;
        tvQuestion.setText(listQuestion.get(0).getQuestion());
        tvCaseA.setText(listQuestion.get(0).getCaseA());
        tvCaseB.setText(listQuestion.get(0).getCaseB());
        tvCaseC.setText(listQuestion.get(0).getCaseC());
        tvCaseD.setText(listQuestion.get(0).getCaseD());
        trueCase = listQuestion.get(0).getTrueCase();
        Log.i("KMFG", "initDataQuestion: "+trueCase);
    }

    private void addCaseFailHelp50(String trueCase) {
        switch (trueCase) {
            case "1":
                frameCaseList.add(frameCaseB);
                frameCaseList.add(frameCaseC);
                frameCaseList.add(frameCaseD);
                break;
            case "2":
                frameCaseList.add(frameCaseA);
                frameCaseList.add(frameCaseC);
                frameCaseList.add(frameCaseD);
                break;
            case "3":
                frameCaseList.add(frameCaseB);
                frameCaseList.add(frameCaseA);
                frameCaseList.add(frameCaseD);
                break;
            default:
                frameCaseList.add(frameCaseB);
                frameCaseList.add(frameCaseC);
                frameCaseList.add(frameCaseA);
                break;
        }
    }
    private void resetMedia() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_person :
                callBack.onCallBack(KEY_SHOW_QUESTION_FRAGMENT,null);
                break;
            case R.id.iv_caseA :
                handleClickQuestion(R.id.iv_caseA);
                break;
            case R.id.iv_caseB :
                handleClickQuestion(R.id.iv_caseB);
                break;
            case R.id.iv_caseC :
                handleClickQuestion(R.id.iv_caseC);
                break;
            case R.id.iv_caseD :
                handleClickQuestion(R.id.iv_caseD);
                break;
            case R.id.iv_help_50_50 :
                if (!App.getInstance().getStorage().isState50()) {
                    resetMedia();
                    playHelp50Music();
                    ivHelp50.setClickable(false);
                    App.getInstance().getStorage().setState50(true);
                    ivHelp50.setImageResource(R.drawable.ic_50_50_done);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Random random = new Random();
                            List<Integer> hiddenIndices = new ArrayList<>();
                            while (hiddenIndices.size() < 2) {
                                int randomIndex = random.nextInt(frameCaseList.size());
                                if (!hiddenIndices.contains(randomIndex)) {
                                    hiddenIndices.add(randomIndex);
                                    frameCaseList.get(randomIndex).setVisibility(View.GONE);
                                }
                            }
                        }
                    },5000);
                } else {
                    Toast.makeText(App.getInstance(),"Bạn đã dùng sự trợ giúp 50/50 rồi",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_change_question_help:
                if (!App.getInstance().getStorage().isStateChange()) {
                    ivChangeQuestion.setClickable(false);
                    App.getInstance().getStorage().setStateChange(true);
                    ivChangeQuestion.setImageResource(R.drawable.ic_reset_done);
                    QuestionManager.getInstance().getQuestionByLevel(index, new QuestionManager.OnResultCallBack() {
                        @Override
                        public void callBack(Object data) {

                            getActivity().runOnUiThread(() -> {
                                initDataQuestion(data);
                            });
                        }
                    });
                } else {
                    Toast.makeText(App.getInstance(),"Bạn đã dùng sự trợ giúp đổi câu hỏi khác rồi",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_audience_help:
                if (!App.getInstance().getStorage().isStateAudi()) {
                    resetMedia();
                    ivAudienceHelp.setClickable(false);
                    ivAudienceHelp.setImageResource(R.drawable.ic_audience_done);
                    App.getInstance().getStorage().setStateAudi(true);
                    showAudienceHelp();
                } else {
                    Toast.makeText(App.getInstance(),"Bạn đã dùng sự trợ giúp hỏi ý kiến khán giả trong trường quay rồi",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_call_help:
                if (!App.getInstance().getStorage().isStateCall()) {
                    resetMedia();
                    ivCallHelp.setClickable(false);
                    ivCallHelp.setImageResource(R.drawable.ic_phone_done);
                    App.getInstance().getStorage().setStateCall(true);
                    showCallDialog();
                } else {
                    Toast.makeText(App.getInstance(),"Bạn đã dùng sự trợ giúp gọi điện thoại người thân rồi",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_stop:
                callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT,null);
                App.getInstance().getStorage().resetPlaySession();
                break;
            default:
        }
    }

    private void showCallDialog() {
        if(mPlayer == null){
            mPlayer = MediaPlayer.create(getContext(), R.raw.help_call);
        }
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.release();
                mPlayer = null;
                HelpCallDialog helpCallDialog = new HelpCallDialog();
                helpCallDialog.show(getActivity().getSupportFragmentManager(), HelpCallDialog.TAG);
                mp = MediaPlayer.create(getContext(), R.raw.help_callb);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        helpCallDialog.dismiss();
                    }
                });
            }
        });
    }

    private void showAudienceHelp() {
        if(mPlayer == null){
            mPlayer = MediaPlayer.create(getContext(), R.raw.khan_gia);
        }
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.release();
                mPlayer = null;
                mp = MediaPlayer.create(getContext(), R.raw.hoi_y_kien_chuyen_gia_01b);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        BarChartQuestion barChartQuestion = new BarChartQuestion();
                        barChartQuestion.show(getActivity().getSupportFragmentManager(),BarChartQuestion.TAG);
                    }
                });
            }
        });
    }

    private void playHelp50Music() {
        if(mPlayer == null){
            mPlayer = MediaPlayer.create(getContext(), R.raw.sound5050);
        }
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.release();
                mPlayer = null;
                addCaseFailHelp50(trueCase);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void handleClickQuestion(int id) {
        switch (id) {
            case R.id.iv_caseA: {
                ivCaseA.setImageResource(R.drawable.ic_play_answer_selected);
                ivCaseB.setClickable(false);
                ivCaseC.setClickable(false);
                ivCaseD.setClickable(false);
                handleUserAnswer(R.id.iv_caseA);
                break;
            }
            case R.id.iv_caseB: {
                ivCaseB.setImageResource(R.drawable.ic_play_answer_selected);
                ivCaseA.setClickable(false);
                ivCaseC.setClickable(false);
                ivCaseD.setClickable(false);
                handleUserAnswer(R.id.iv_caseB);
                break;
            }

            case R.id.iv_caseC: {
                ivCaseC.setImageResource(R.drawable.ic_play_answer_selected);
                ivCaseB.setClickable(false);
                ivCaseA.setClickable(false);
                ivCaseD.setClickable(false);
                handleUserAnswer(R.id.iv_caseC);
                break;
            }
            case R.id.iv_caseD: {
                ivCaseD.setImageResource(R.drawable.ic_play_answer_selected);
                ivCaseB.setClickable(false);
                ivCaseC.setClickable(false);
                ivCaseA.setClickable(false);
                handleUserAnswer(R.id.iv_caseD);
                break;
            }
            default:
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resetMedia();
    }

    @SuppressLint("NonConstantResourceId")
    private void handleUserAnswer(int id) {
        int idSource = 0;
        int trueAns = 0;
        boolean isUserSelect = false;
        switch (id) {
            case R.id.iv_caseA: {
                idSource = R.raw.ans_a;
                switch (trueCase) {
                    case "1":
                        trueAns = R.raw.true_a;
                        isUserSelect = true;
                        break;
                    case "2":
                        trueAns = R.raw.lose_b2;
                        break;
                    case "3":
                        trueAns = R.raw.lose_c2;
                        break;
                    default:
                        trueAns = R.raw.lose_d2;
                        break;
                }
                break;
            }
            case R.id.iv_caseB: {
                idSource = R.raw.ans_b;
                switch (trueCase) {
                    case "1":
                        trueAns = R.raw.lose_a2;
                        break;
                    case "2":
                        trueAns = R.raw.true_b;
                        isUserSelect = true;
                        break;
                    case "3":
                        trueAns = R.raw.lose_c2;
                        break;
                    default:
                        trueAns = R.raw.lose_d2;
                        break;
                }
                break;
            }
            case R.id.iv_caseC: {
                idSource = R.raw.ans_c;
                switch (trueCase) {
                    case "1":
                        trueAns = R.raw.lose_a2;
                        break;
                    case "2":
                        trueAns = R.raw.lose_b2;
                        break;
                    case "3":
                        trueAns = R.raw.true_c;
                        isUserSelect = true;
                        break;
                    default:
                        trueAns = R.raw.lose_d2;
                        break;
                }
                break;
            }
            case R.id.iv_caseD: {
                idSource = R.raw.ans_d;
                switch (trueCase) {
                    case "1":
                        trueAns = R.raw.lose_a2;
                        break;
                    case "2":
                        trueAns = R.raw.lose_b2;
                        break;
                    case "3":
                        trueAns = R.raw.lose_c2;
                        break;
                    default:
                        trueAns = R.raw.true_d2;
                        isUserSelect = true;
                        break;
                }
                break;
            }
        }
        Log.i("KMFG", "handleUserAnswer: "+trueAns);
        if(mPlayer == null){
            mPlayer = MediaPlayer.create(getContext(), idSource);
        } else {
            // Tạm dừng và giải phóng MediaPlayer hiện tại
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                mPlayer.release();
                mPlayer = null;
            }

            // Khởi tạo và chơi tệp âm thanh mới
            mPlayer = MediaPlayer.create(getContext(), idSource);
        }
        mPlayer.start();
        int finalTrueAns = trueAns;
        boolean finalIsUserSelect = isUserSelect;
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp = MediaPlayer.create(getContext(), finalTrueAns);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (finalIsUserSelect) {
                            showTrueQuestion(findViewById(id));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ivCaseA.setClickable(true);
                                    ivCaseB.setClickable(true);
                                    ivCaseC.setClickable(true);
                                    ivCaseD.setClickable(true);
                                    ivCaseA.setImageResource(R.drawable.ic_play_answer);
                                    ivCaseB.setImageResource(R.drawable.ic_play_answer);
                                    ivCaseC.setImageResource(R.drawable.ic_play_answer);
                                    ivCaseD.setImageResource(R.drawable.ic_play_answer);
                                    frameCaseA.setVisibility(View.VISIBLE);
                                    frameCaseB.setVisibility(View.VISIBLE);
                                    frameCaseC.setVisibility(View.VISIBLE);
                                    frameCaseD.setVisibility(View.VISIBLE);
                                    goToNextQuestion(++index);
                                }
                            },2500);
                        } else {
                            showFalseQuestion(findViewById(id));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT,null);
                                    mediaPlayer.stop();
                                    App.getInstance().getStorage().resetPlaySession();
                                }
                            },2500);

                        }
                    }
                });
            }
        });
    }

    private void showTrueQuestion(ImageView imageView) {
        imageView.setImageResource(R.drawable.ic_true_answer);
    }

    private void showFalseQuestion(ImageView imageView) {
        if (trueCase.equals("1")) {
            ivCaseA.setImageResource(R.drawable.ic_true_answer);
        }else if (trueCase.equals("2")) {
            ivCaseB.setImageResource(R.drawable.ic_true_answer);
        }else if (trueCase.equals("3")) {
            ivCaseC.setImageResource(R.drawable.ic_true_answer);
        } else {
            ivCaseD.setImageResource(R.drawable.ic_true_answer);
        }
        imageView.setImageResource(R.drawable.ic_play_answer_wrong);
    }

    private void goToNextQuestion(int level) {
        if (level == 16) {
            index = 1;
            App.getInstance().getStorage().setCurrentLevel(0);
            if(mPlayer == null){
                mPlayer = MediaPlayer.create(getContext(), R.raw.best_player);
            }
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                     mPlayer.release();
                     mPlayer = null;
                }
            });
        } else {
            QuestionManager.getInstance().getQuestionByLevel(level, new QuestionManager.OnResultCallBack() {
                @Override
                public void callBack(Object data) {

                    getActivity().runOnUiThread(() -> {
                        if (level == 6 || level == 11) {
                            App.getInstance().getStorage().setCurrentLevel(level);
                            callBack.onCallBack(KEY_SHOW_QUESTION_FRAGMENT,null);
                        }
                        initDataQuestion(data);
                        setTextForQuestion(level);
                    });
                }
            });
        }
    }

    private void setTextForQuestion(int id) {
        switch (id) {
            case 1 : {
                tvIndexQuestion.setText("Câu 1");
                break;
            }
            case 2 : {
                tvIndexQuestion.setText("Câu 2");
                break;
            }
            case 3 : {
                tvIndexQuestion.setText("Câu 3");
                break;
            }
            case 4 : {
                tvIndexQuestion.setText("Câu 4");
                break;
            }
            case 5 : {
                tvIndexQuestion.setText("Câu 5");
                break;
            }
            case 6 : {
                tvIndexQuestion.setText("Câu 6");
                break;
            }
            case 7 : {
                tvIndexQuestion.setText("Câu 7");
                break;
            }
            case 8 : {
                tvIndexQuestion.setText("Câu 8");
                break;
            }
            case 9 : {
                tvIndexQuestion.setText("Câu 9");
                break;
            }
            case 10 : {
                tvIndexQuestion.setText("Câu 10");
                break;
            } case 11 : {
                tvIndexQuestion.setText("Câu 11");
                break;
            }
            case 12 : {
                tvIndexQuestion.setText("Câu 12");
                break;
            }
            case 13 : {
                tvIndexQuestion.setText("Câu 13");
                break;
            }
            case 14: {
                tvIndexQuestion.setText("Câu 14");
                break;
            }
            case 15 : {
                tvIndexQuestion.setText("Câu 15");
                break;
            }
            default: {
                tvIndexQuestion.setText("Câu 1");
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
        return R.layout.m003_play_fragment;
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.callBack = callBack;
    }
}
