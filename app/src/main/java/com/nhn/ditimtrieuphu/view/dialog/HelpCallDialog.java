package com.nhn.ditimtrieuphu.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhn.ditimtrieuphu.App;
import com.nhn.ditimtrieuphu.R;
import com.nhn.ditimtrieuphu.model.PeopleCall;
import com.nhn.ditimtrieuphu.view.adapter.HelpCallAdapter;

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
        callList.add(new PeopleCall("Huấn Hoa Hồng","Liều thì ăn nhiều không liều thì ăn ít. Chọn đáp án A cho thầy đi",R.drawable.ic_huanhoahong));
        callList.add(new PeopleCall("Khá Bảnh","Bảnh nghĩ đáp án đúng là B đấy",R.drawable.ic_khabanh));
        callList.add(new PeopleCall("Trấn Thành","Chọn C nhé Thành nghĩ C là đáp án đúng",R.drawable.ic_tranthanh));
        callList.add(new PeopleCall("Đầu cắt moi","Tôi nghĩ đáp án đúng là D. Sau thành triệu phú thì kết bạn với tôi nhé",R.drawable.ic_daucatmoi));
        callList.add(new PeopleCall("Nờ ô nô","Nô chọn đáp án A",R.drawable.ic_no));
        callList.add(new PeopleCall("Tiến Bịp","kkk thực ra tôi cũng k rõ câu này đâu . Nhưng tôi sẽ giúp bạn. Tôi nghĩ đáp án đúng là A",R.drawable.ic_tienbip));
        callList.add(new PeopleCall("Bác Đa","Năm nay hơn 70 tuổi đầu mà tôi chưa gặp trường hợp này bao giờ. Đáp án cuối cùng của tôi là B",R.drawable.ic_bacda));
        callList.add(new PeopleCall("Thầy Ba","Em tin anh Ba đáp án đúng phải là C",R.drawable.ic_thayba));
        callList.add(new PeopleCall("Lê Bống","Theo Bống thì đáp án đúng là D",R.drawable.ic_lebong));
        callList.add(new PeopleCall("Ngọc Trinh","Trinh nghĩ đáp án đúng là C đó",R.drawable.ic_ngoctrinh));
        callList.add(new PeopleCall("Albert Einstein","Theo hiểu biết của tôi thì đáp án đúng là A",R.drawable.ic_einstein));
        callList.add(new PeopleCall("Nikola Tesla","Đáp án đúng chắc chắn là C",R.drawable.ic_nikolatesla));
        callList.add(new PeopleCall("Ronaldo","Đáp án đúng là B. Siuuuuuu",R.drawable.ic_ronaldo));
        callList.add(new PeopleCall("Messi","Tôi nghĩ đáp án đúng là D",R.drawable.ic_messi));
        callList.add(new PeopleCall("Pep Guardiola","Tôi đang phân vân giữa 2 đáp án nhưng đáp án tôi chọn là D",R.drawable.ic_pep));

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
