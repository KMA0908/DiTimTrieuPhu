package com.example.ditimtrieuphu.view.act;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ditimtrieuphu.R;

public abstract class BaseAct<T extends ViewModel> extends AppCompatActivity implements View.OnClickListener {
    protected T mModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        mModel=new ViewModelProvider(this).get(getClassViewModel());
        initViews();
    }

    protected abstract Class<T> getClassViewModel();

    protected abstract int getLayoutID();

    protected abstract void initViews();

    public final <T extends View> T findViewById(int id,View.OnClickListener event) {
        T v=findViewById(id);
        if(v!=null && event!=null){
            v.setOnClickListener(this);
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        // do something
    }

    protected void showFragment(int layoutID, Fragment fragment, boolean addToBackStack){

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_view,fragment);
        if(addToBackStack){
            transaction.addToBackStack("add");
        }
        transaction.commit();
    }
}

