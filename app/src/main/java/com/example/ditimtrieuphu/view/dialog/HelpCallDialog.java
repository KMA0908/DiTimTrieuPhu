package com.example.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.App;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.model.PeopleCall;
import com.example.ditimtrieuphu.view.adapter.HelpCallAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelpCallDialog extends DialogFragment implements HelpCallAdapter.OnItemClick {
    public static final String KEY_ANSWER = "KEY_ANSWER";
    public static String TAG = HelpCallDialog.class.getName();
    private RecyclerView recyclerView;
    private TextView tvClose;
    private List<PeopleCall> callList;
    private List<PeopleCall> randomList;
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
        return inflater.inflate(R.layout.custom_dialog_help_call, container);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvClose = view.findViewById(R.id.tv_thank_people);
        recyclerView = view.findViewById(R.id.rcv_people_call);
        initDummyData();
        initRandomData();
        HelpCallAdapter helpCallAdapter = new HelpCallAdapter(App.getInstance(),randomList);
        helpCallAdapter.setOnItemClick(this);
        recyclerView.setAdapter(helpCallAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    private void initRandomData() {
        randomList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(callList.size());
            randomList.add(callList.get(randomIndex));
            callList.remove(randomIndex);
        }
    }
    private void initDummyData() {
        callList = new ArrayList<>();
        callList.add(new PeopleCall("Huấn Hoa Hồng","kkk thực ra tôi cũng k rõ câu này đâu . Nhưng tôi sẽ giúp b trl. Tôi nghĩ đáp án đúng là A",""));
        callList.add(new PeopleCall("Khá Bảnh","Tôi nghĩ đáp án đúng là B",""));
        callList.add(new PeopleCall("Trấn Thành","Tôi nghĩ đáp án đúng là C",""));
        callList.add(new PeopleCall("Đầu cắt moi","Tôi nghĩ đáp án đúng là D",""));
        callList.add(new PeopleCall("Nờ ô nô","Tôi nghĩ đáp án đúng là A",""));
        callList.add(new PeopleCall("Huấn Hoa Hồng","kkk thực ra tôi cũng k rõ câu này đâu . Nhưng tôi sẽ giúp b trl. Tôi nghĩ đáp án đúng là A",""));
        callList.add(new PeopleCall("Khá Bảnh","Tôi nghĩ đáp án đúng là B",""));
        callList.add(new PeopleCall("Trấn Thành","Tôi nghĩ đáp án đúng là C",""));
        callList.add(new PeopleCall("Đầu cắt moi","Tôi nghĩ đáp án đúng là D",""));
        callList.add(new PeopleCall("Nờ ô nô","Tôi nghĩ đáp án đúng là A",""));
    }

    @Override
    public void onItemClick(PeopleCall peopleCall) {
        PersonAnswerDialog personAnswerDialog = new PersonAnswerDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ANSWER, peopleCall);
        personAnswerDialog.setArguments(bundle);
        personAnswerDialog.show(getActivity().getSupportFragmentManager(),PersonAnswerDialog.TAG);
        dismiss();
    }
}
