package com.nhn.ditimtrieuphu.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainFragViewModel extends ViewModel {
    public final MutableLiveData<Long> totalMoneyLiveData;
    public int totalExp;

    public MainFragViewModel() {
        totalMoneyLiveData = new MutableLiveData<>(0L);
    }

    public void resetPlaySessionInfo() {
        totalMoneyLiveData.setValue(0L);
    }
}
