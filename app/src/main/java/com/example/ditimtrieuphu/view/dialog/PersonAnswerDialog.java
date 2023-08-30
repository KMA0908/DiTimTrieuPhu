package com.example.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.model.PeopleCall;

public class PersonAnswerDialog extends DialogFragment {
    public static String TAG = PersonAnswerDialog.class.getName();
    private TextView tvThank, tvNameAnswer, tvContentAnswer;
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_person_answer, container);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvThank = view.findViewById(R.id.tv_thank_person);
        tvNameAnswer = view.findViewById(R.id.tv_name_answer);
        tvContentAnswer = view.findViewById(R.id.tv_content_answer);
        Bundle args = getArguments();
        if (args != null) {
            PeopleCall data = (PeopleCall) args.getSerializable(HelpCallDialog.KEY_ANSWER);
            tvNameAnswer.setText(data.getName());
            tvContentAnswer.setText(data.getAnswer());
        }

        tvThank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}