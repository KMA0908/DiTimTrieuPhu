package com.example.ditimtrieuphu.view.fragment;

import static com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog.TAG_DIALOG_MESSAGE;
import static com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog.TAG_DIALOG_BLUR;
import static com.example.ditimtrieuphu.view.fragment.SignUpFragment.KEY_SHOW_SIGNUP_FRAGMENT;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog;
import com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog;
import com.example.ditimtrieuphu.viewmodel.LoginViewModel;

public class LoginFragment extends BaseFragment<LoginViewModel> {
    public static final String KEY_SHOW_LOGIN_FRAGMENT = "KEY_SHOW_LOGIN_FRAGMENT";

    private OnActionCallBack mCallBack;
    private TextView mSignUpTextView;
    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private AppCompatButton mLoginButton;

    @Override
    protected void initViews() {
        //Check user da dang nhap tu phien dang nhap truoc hay chua thi can login user vao main luon
        //khong hien thi dang nhap nua, va can refresh token khi het han de luon giu user dang nhap.
        if (mModel.isUserSignedIn()) {
            mCallBack.onCallBack(M001SplashFragment.KEY_SHOW_HOME_FRAGMENT);
        }

        mSignUpTextView = findViewById(R.id.tv_sign_up, this);
        mAccountEditText = findViewById(R.id.et_account);
        mPasswordEditText = findViewById(R.id.et_password);
        mLoginButton = findViewById(R.id.bt_login, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sign_up: {
                mCallBack.onCallBack(KEY_SHOW_SIGNUP_FRAGMENT);
                break;
            }
            case R.id.bt_login: {
                String account = mAccountEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    return;
                }
                // show blur
                final WaitingLoadingBlurDialog waitingLoadingBlurDialog = new WaitingLoadingBlurDialog();
                waitingLoadingBlurDialog.show(getParentFragmentManager(), TAG_DIALOG_BLUR);
                mModel.loginWithAccountAndPassword(account, password, objects -> {
                    // Dang nhap thanh cong thi dua user vao main screen
                    mModel.syncGameResources(o -> {
                        mCallBack.onCallBack(M001SplashFragment.KEY_SHOW_HOME_FRAGMENT);
                    }, o -> {
                        waitingLoadingBlurDialog.dismiss();
                        // dang nhap fail hien dialog
                        if (o != null && o.length > 0) {
                            String message = (String) o[0];
                            SimpleMessageDialog simpleMessageDialog = new SimpleMessageDialog();
                            simpleMessageDialog.setDialogMessage(message);
                            simpleMessageDialog.show(getParentFragmentManager(), TAG_DIALOG_MESSAGE);
                        }
                    });
                }, objects -> {
                    waitingLoadingBlurDialog.dismiss();
                    // dang nhap fail hien dialog
                    if (objects != null && objects.length > 0) {
                        String message = (String) objects[0];
                        SimpleMessageDialog simpleMessageDialog = new SimpleMessageDialog();
                        simpleMessageDialog.setDialogMessage(message);
                        simpleMessageDialog.show(getParentFragmentManager(), TAG_DIALOG_MESSAGE);
                    }
                });
                break;
            }
        }
    }

    @Override
    protected Class<LoginViewModel> getClassViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.m005_login_fragment;
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.mCallBack = callBack;
    }
}
