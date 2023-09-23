package com.example.ditimtrieuphu.view.fragment;

import static com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog.TAG_DIALOG_MESSAGE;
import static com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog.TAG_DIALOG_BLUR;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.ditimtrieuphu.Executable;
import com.example.ditimtrieuphu.OnActionCallBack;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.dialog.SimpleMessageDialog;
import com.example.ditimtrieuphu.view.dialog.WaitingLoadingBlurDialog;
import com.example.ditimtrieuphu.viewmodel.LoginViewModel;

public class SignUpFragment extends BaseFragment<LoginViewModel>{
    public static final String KEY_SHOW_SIGNUP_FRAGMENT = "KEY_SHOW_SIGNUP_FRAGMENT";

    private OnActionCallBack mCallBack;
    // Views
    private ImageView mBackImageView;
    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private AppCompatButton mSignUpButton;

    @Override
    protected void initViews() {
        mBackImageView = findViewById(R.id.iv_back, this);
        mAccountEditText = findViewById(R.id.et_account);
        mPasswordEditText = findViewById(R.id.et_password);
        mConfirmPasswordEditText = findViewById(R.id.et_password_confirm);
        mSignUpButton = findViewById(R.id.bt_sign_up, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                mCallBack.onCallBack(LoginFragment.KEY_SHOW_LOGIN_FRAGMENT);
                break;
            }
            case R.id.bt_sign_up: {
                String email = mAccountEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    return;
                }
                // show blur
                final WaitingLoadingBlurDialog waitingLoadingBlurDialog = new WaitingLoadingBlurDialog();
                waitingLoadingBlurDialog.show(getParentFragmentManager(), TAG_DIALOG_BLUR);

                mModel.createAccount(email, password, objects -> {
                    // dang ky thanh cong thi login user vao main screen
                    mCallBack.onCallBack(M001SplashFragment.KEY_SHOW_HOME_FRAGMENT);
                }, objects -> {
                    waitingLoadingBlurDialog.dismiss();
                    // dang ky fail hien dialog
                    if (objects != null) {
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
        return R.layout.m006_signup_fragment;
    }

    public void setCallBack(OnActionCallBack callBack) {
        this.mCallBack = callBack;
    }
}
