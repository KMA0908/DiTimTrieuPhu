package com.nhn.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.nhn.ditimtrieuphu.Executable;
import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.async.QuestionManager;
import com.nhn.ditimtrieuphu.common.CommonUtils;
import com.nhn.ditimtrieuphu.common.UiUtils;
import com.nhn.ditimtrieuphu.database.AppDatabase;
import com.nhn.ditimtrieuphu.entity.HighScore;

import java.util.List;

public class GameResultDialog extends DialogFragment {
    public static final String TAG = "GameResultDialog";

    private AppCompatButton mConfirmButton;
    private TextView mMoneyTextView;
    private TextView tvTitle;
    private TextView mExpTextView;
    private Executable mConfirmExecutable;
    private String money;
    private int exp;
    private boolean isUserStop = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_result_gameplay, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = view.findViewById(R.id.title_result);
        mConfirmButton = view.findViewById(R.id.bt_confirm);
        mMoneyTextView = view.findViewById(R.id.tv_money_result);
        mExpTextView = view.findViewById(R.id.tv_exp_result);
        AppDatabase database =  Room.databaseBuilder(getActivity(),
                        AppDatabase.class, "databases/Question.db")
                .allowMainThreadQueries()
                .build();
        mConfirmButton.setOnClickListener(view1 -> {
            if (mConfirmExecutable != null) {
                mConfirmExecutable.execute(money, exp);
            }
            QuestionManager.getInstance().addNewScore(new QuestionManager.OnResultCallBack() {
                @Override
                public void callBack(Object data) {
                    updateScoreUser();
                }
            }, new HighScore(CommonUtils.getInstance().getPref("username"), money, String.valueOf(exp)));
            dismiss();
        });
        String moneyString = String.valueOf(money);
        String expString = String.valueOf(exp);
        mMoneyTextView.setText(moneyString);
        mExpTextView.setText(expString);
        if (isUserStop) {
            tvTitle.setText("Bạn có chắc chắn muốn dừng cuộc chơi ở đây không?");
        } else {
            tvTitle.setText("Kết quả của bạn");
        }
    }

    private void updateScoreUser() {
        if (CommonUtils.getInstance().isExistPref("username")) {
            String username = CommonUtils.getInstance().getPref("username");
            QuestionManager.getInstance().getHighScoreUser(username, new QuestionManager.OnResultCallBack() {
                @Override
                public void callBack(Object data) {
                    List<HighScore> highScoreUsers = (List<HighScore>) data;
                    double s = 0;
                    for (int i = 0; i < highScoreUsers.size(); i++) {
                        double number = CommonUtils.getInstance().convertStringScoreToDouble(highScoreUsers.get(i).getScore());
                        s += number;
                    }
                    String formattedNumber = String.format("%,.0f", s);
                    CommonUtils.getInstance().saveScore("score", formattedNumber);
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = UiUtils.getInstance(getContext()).calculateWidthWithMargin(24f);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setmConfirmExecutable(Executable executable) {
        mConfirmExecutable = executable;
        if (mConfirmButton != null) {
            mConfirmButton.setOnClickListener(view1 -> {
                if (mConfirmExecutable != null) {
                    long money = Long.parseLong(mMoneyTextView.getText().toString());
                    int exp = Integer.parseInt(mExpTextView.getText().toString());
                    mConfirmExecutable.execute(money, exp);
                }
                dismiss();
            });
        }
    }

    public void setDataResult(String money, int exp, boolean isUserStop) {
        this.money = money;
        this.exp = exp;
        this.isUserStop = isUserStop;

        if (mMoneyTextView != null) {
            String moneyString = money;
            String expString = String.valueOf(exp);
            mMoneyTextView.setText(moneyString);
            mExpTextView.setText(expString);
        }
    }
}
