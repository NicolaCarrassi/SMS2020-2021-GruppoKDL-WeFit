package it.uniba.di.sms2021.gruppodkl.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.LoginActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.LoginActivityPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.UtilityStrings;

public class LoginActivity extends AppCompatActivity implements LoginActivityContract.View {




    private LoginActivityContract.Presenter mPresenter;

    private EditText mEmail;
    private EditText mPassword;

    private TextView mNewUser;
    private TextView mForgotPassword;

    private Button mLoginButton;

    private String mEmailText;
    private String mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.bind();
        this.setListeners();
        this.mPresenter = new LoginActivityPresenter(this);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {
    }


    private void bind(){
        this.mEmail = findViewById(R.id.email_edit_text);
        this.mPassword = findViewById(R.id.password_edit_text);
        this.mLoginButton = findViewById(R.id.login_button);
        this.mNewUser = findViewById(R.id.new_user_label);
        this.mForgotPassword = findViewById(R.id.forgot_password_label);
    }

    private void setListeners(){
        mLoginButton.setOnClickListener(v -> {
            if(checkAttributes()){
                mPresenter.doLogin(mEmailText, mPasswordText);
            }
        });


    }

    private boolean checkAttributes(){
        mEmailText = mEmail.getText().toString();
        mPasswordText = mPassword.getText().toString();

        boolean res = true;

        if(TextUtils.isEmpty(mEmailText) || !(Patterns.EMAIL_ADDRESS.matcher(mEmailText).matches())) {
            //email sbagliata o vuota
                res = false;
                mEmail.setError(getResources().getString(R.string.error_email));
                mEmail.requestFocus();
        }

        if(!res || TextUtils.isEmpty(mPasswordText) || !(UtilityStrings.PASSOWRD_REGEX.matches(mPasswordText))){

            res = false;
            mPassword.setError(getResources().getString(R.string.error_password));
            mPassword.requestFocus();
        }

        return res;
    }

}