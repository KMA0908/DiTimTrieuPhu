package com.nhn.ditimtrieuphu.view.act;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nhn.ditimtrieuphu.BackgroundService;
import com.nhn.ditimtrieuphu.R;

public abstract class BaseAct<T extends ViewModel> extends AppCompatActivity implements View.OnClickListener {
    protected T mModel;
    // Minh them service connection - start
    protected BackgroundService mService;
    protected boolean mServiceBound;
    protected ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = ((BackgroundService.AppServiceBinder)iBinder).getAppService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBound = false;
        }
    };
    // Minh them service connection - end
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        mModel=new ViewModelProvider(this).get(getClassViewModel());
        initViews();
        // Minh: bind service
        Intent intent = new Intent(this, BackgroundService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        //No call for super(). Bug on API Level > 11.
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

