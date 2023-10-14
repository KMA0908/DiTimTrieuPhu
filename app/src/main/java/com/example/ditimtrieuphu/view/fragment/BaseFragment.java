package com.example.ditimtrieuphu.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ditimtrieuphu.App;
import com.example.ditimtrieuphu.BackgroundService;
import com.example.ditimtrieuphu.ContextAccessable;
import com.example.ditimtrieuphu.Storage;


public abstract class BaseFragment<T extends ViewModel> extends Fragment implements View.OnClickListener {
    protected T mModel;
    protected View rootView;
    protected Context mContext;

    // Minh: them ref service
    protected BackgroundService mBackgroundService;

    @Override
    public final void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        // Minh: sua lai owner la activity thay vi tung frag rieng de dung chung lai viewmodel giua cac fragment can logic chung
        mModel = new ViewModelProvider(requireActivity()).get(getClassViewModel());
        // Minh: neu view model can access context thi set
        if (mModel instanceof ContextAccessable) {
            ((ContextAccessable) mModel).setContext(mContext);
        }
        initViews();
        return rootView;
    }

    protected abstract void initViews();

    protected abstract Class<T> getClassViewModel();

    protected abstract int getLayoutId();

    public final <T extends View> T findViewById(int id, View.OnClickListener event) {
        T v = rootView.findViewById(id);
        if (v != null && event != null) {
            v.setOnClickListener(this);
        }
        return v;
    }

    public final <T extends View> T findViewById(int id) {
        return findViewById(id, null);
    }

    @Override
    public void onClick(View v) {

    }

    protected final Storage getStorage(){
        return App.getInstance().getStorage();
    }

    public void setBackgroundService(BackgroundService service) {
        this.mBackgroundService = service;
    }
}