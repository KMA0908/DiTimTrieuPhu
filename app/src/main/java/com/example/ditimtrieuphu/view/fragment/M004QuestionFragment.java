package com.example.ditimtrieuphu.view.fragment;

import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.viewmodel.QuestionViewModel;

public class M004QuestionFragment extends BaseFragment<QuestionViewModel> {
    private OnActionCallBack callBack;

    @Override
    protected void initViews() {

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

}

