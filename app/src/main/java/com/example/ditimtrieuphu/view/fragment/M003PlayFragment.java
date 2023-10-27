package com.example.ditimtrieuphu.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.ditimtrieuphu.common.GameConstant;
import com.example.ditimtrieuphu.database.AppDatabase;
import com.example.ditimtrieuphu.entity.BonusItem;
import com.example.ditimtrieuphu.entity.Question;
import com.example.ditimtrieuphu.view.dialog.BarChartQuestion;
import com.example.ditimtrieuphu.view.dialog.GameResultDialog;
import com.example.ditimtrieuphu.view.dialog.HelpCallDialog;
import com.example.ditimtrieuphu.viewmodel.MainFragViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class M003PlayFragment extends BaseFragment<MainFragViewModel> {
    public static final String KEY_SHOW_QUESTION_FRAGMENT = "KEY_SHOW_QUESTION_FRAGMENT";
    public static final String KEY_SHOW_MAIN_FRAGMENT = "KEY_SHOW_MAIN_FRAGMENT";
    private static final long TIME_PER_QUESTION = 30000; // Minh: thoi gian choi mac dinh mot cau hoi 30s
    private OnActionCallBack callBack;
    private ProgressBar progressBar;

    private TextView timerTextView;

    private TextView tvQuestion,tvCaseA,tvCaseB,tvCaseC,tvCaseD, tvIndexQuestion, tvMoney;
    private ImageView ivCaseA,ivCaseB,ivCaseC,ivCaseD,ivHelp50,ivChangeQuestion,ivAudienceHelp,ivCallHelp;
    private FrameLayout frameCaseA,frameCaseB,frameCaseC,frameCaseD;
    private MediaPlayer mPlayer;
    private String trueCase;
    private int index;
    private List<FrameLayout> frameCaseList ;

    private CountDownTimer countDownTimer; // Minh: de countDownTimer lam global
    private long mSecondsLeft; // Minh: thoi gian con lai tren timer

    @Override
    protected void initViews() {
        frameCaseList =  new ArrayList<>();
        index = 1;
        tvIndexQuestion = findViewById(R.id.tv_index_question);
        tvQuestion = findViewById(R.id.question);
        tvMoney = findViewById(R.id.tv_money);
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
        countDownQuestion(TIME_PER_QUESTION);
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

        // observe money
        mModel.totalMoneyLiveData.observe(this, money -> {
            String moneyString = String.valueOf(money);
            tvMoney.setText(moneyString);
        });
    }

    private void countDownQuestion(long time) {
       countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) (time - millisUntilFinished));
                mSecondsLeft = millisUntilFinished;
                timerTextView.setText(String.valueOf(mSecondsLeft/1000));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0");
                //TODO thong bao roi moi ket thuc tro choi
                callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT);
                App.getInstance().getStorage().resetPlaySession();
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
                callBack.onCallBack(KEY_SHOW_QUESTION_FRAGMENT);
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
                    // Minh: dung su tro giup pause timer cho den khi dung xong
                    countDownTimer.cancel();
                    resetMedia();
                    addCaseFailHelp50(trueCase);
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
                                // to do crash
                                int randomIndex = random.nextInt(frameCaseList.size());
                                if (!hiddenIndices.contains(randomIndex)) {
                                    hiddenIndices.add(randomIndex);
                                    frameCaseList.get(randomIndex).setVisibility(View.GONE);
                                }
                            }
                            // Minh: thuc hien xong su tro giup resume timer
                            countDownQuestion(mSecondsLeft);
                        }
                    },5000);
                } else {
                    Toast.makeText(App.getInstance(),"Bạn đã dùng sự trợ giúp 50/50 rồi",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_change_question_help:
                if (!App.getInstance().getStorage().isStateChange()) {
                    // Minh: dung su tro giup pause timer cho den khi dung xong
                    countDownTimer.cancel();
                    ivChangeQuestion.setClickable(false);
                    App.getInstance().getStorage().setStateChange(true);
                    ivChangeQuestion.setImageResource(R.drawable.ic_reset_done);
                    QuestionManager.getInstance().getQuestionByLevel(index, new QuestionManager.OnResultCallBack() {
                        @Override
                        public void callBack(Object data) {

                            getActivity().runOnUiThread(() -> {
                                initDataQuestion(data);
                                // Minh: thuc hien xong su tro giup resume timer
                                countDownQuestion(mSecondsLeft);
                            });
                        }
                    });
                } else {
                    Toast.makeText(App.getInstance(),"Bạn đã dùng sự trợ giúp đổi câu hỏi khác rồi",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_audience_help:
                if (!App.getInstance().getStorage().isStateAudi()) {
                    // Minh: dung su tro giup pause timer cho den khi dung xong
                    countDownTimer.cancel();
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
                    // Minh: dung su tro giup pause timer cho den khi dung xong
                    countDownTimer.cancel();
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
                callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT);
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
                        // Minh: thuc hien xong su tro giup resume timer
                        countDownQuestion(mSecondsLeft);
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
                        // Minh: thuc hien xong su tro giup resume timer
                        countDownQuestion(mSecondsLeft);
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
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void handleClickQuestion(int id) {
        // Minh: click cau tra loi thi dung timer lai
        countDownTimer.cancel();
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
                mPlayer.release();
                mPlayer = null;
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
                                    if (index == 15) {
                                        showAlertDialogWinner();
                                    } else {
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
                                        // Cong tien thuong hien co
                                        long currentMoney = mModel.totalMoneyLiveData.getValue();
                                        mModel.totalExp += GameConstant.EXP_REWARD[index-1];
                                        mModel.totalMoneyLiveData.setValue(currentMoney + GameConstant.MONEY_REWARD[index-1]);
                                        goToNextQuestion(++index);
                                    }
                                }
                            },2500);
                        } else {
                            showFalseQuestion(findViewById(id));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameResultDialog dialog = new GameResultDialog();
                                    // Tinh bonus tu item va Tinh bonus tu huy hieu
                                    float heSo = mModel.getHeSoBadges() + mModel.getHeSoBonusItem();
                                    final long finalMoney = (long) (mModel.totalMoneyLiveData.getValue() * mModel.heSoChoi + mModel.totalMoneyLiveData.getValue()*heSo);
                                    dialog.setDataResult(finalMoney, mModel.totalExp);
                                    dialog.setmConfirmExecutable(objects -> {
                                        // update thong so nguoi choi
                                        //TODO check flag dung cuoc choi dung luc
                                        mModel.updateMoneyAndExpPlayer(finalMoney, mModel.totalExp);
                                        mModel.resetPlaySessionInfo();
                                        callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT);
                                        App.getInstance().getStorage().resetPlaySession();
                                    });
                                    dialog.show(getParentFragmentManager(), GameResultDialog.TAG);
                                }
                            },2500);

                        }
                    }
                });
            }
        });
    }

    private void showAlertDialogWinner() {
        index = 1;
        App.getInstance().getStorage().resetPlaySession();
        if(mPlayer == null){
            mPlayer = MediaPlayer.create(getContext(), R.raw.best_player);
        }
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.release();
                mPlayer = null;
                // Tạo AlertDialog
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Chúc mừng")
                        .setMessage("Xin chúc mừng bạn chính là triệu phú")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callBack.onCallBack(KEY_SHOW_MAIN_FRAGMENT);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .create();

                alertDialog.show();
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
        QuestionManager.getInstance().getQuestionByLevel(level, new QuestionManager.OnResultCallBack() {
            @Override
            public void callBack(Object data) {
                getActivity().runOnUiThread(() -> {
                    if (level == 6 || level == 11) {
                        App.getInstance().getStorage().setCurrentLevel(level);
                        callBack.onCallBack(KEY_SHOW_QUESTION_FRAGMENT);
                    } else {
                        initDataQuestion(data);
                        setTextForQuestion(level);
                        // Minh: bat dau cau hoi moi thi start lai timer
                        countDownQuestion(TIME_PER_QUESTION);
                    }
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setTextForQuestion(int id) {
        switch (id) {
            case 1 : {
                tvIndexQuestion.setText("Câu 1");
                tvMoney.setText("0");
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
