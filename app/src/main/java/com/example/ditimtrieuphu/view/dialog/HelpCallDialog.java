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

public class HelpCallDialog extends DialogFragment implements HelpCallAdapter.OnItemClick {
    public static String TAG = HelpCallDialog.class.getName();
    private RecyclerView recyclerView;
    private TextView tvClose;
    private List<PeopleCall> callList;
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
        HelpCallAdapter helpCallAdapter = new HelpCallAdapter(App.getInstance(),callList);
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

    private void initDummyData() {
        callList = new ArrayList<>();
        callList.add(new PeopleCall("Huấn Hoa Hồng",""));
        callList.add(new PeopleCall("Khá Bảnh",""));
        callList.add(new PeopleCall("Trấn Thành",""));
        callList.add(new PeopleCall("Đầu cắt moi",""));
        callList.add(new PeopleCall("Nờ ô nô",""));
    }

    @Override
    public void onItemClick(PeopleCall peopleCall) {
        Toast.makeText(App.getInstance(),"Tôi nghĩ đáp án A là câu trả đúng",Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
