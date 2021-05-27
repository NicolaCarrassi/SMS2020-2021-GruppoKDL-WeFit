package it.uniba.di.sms2021.gruppodkl.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.bind();
        this.setListeners();
        this.mPresenter = new LoginActivityPresenter(this);
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
            String emailText = mEmail.getText().toString();
            String passwordText = mPassword.getText().toString();
            if(checkAttributes(emailText, passwordText)){
                mPresenter.doLogin(emailText,passwordText);
            }
        });

        mForgotPassword.setOnClickListener(v -> mPresenter.forgotPassword());

        mNewUser.setOnClickListener(v -> mPresenter.newUser());


    }

    private boolean checkAttributes(String emailText, String passwordText) {
        boolean res = true;

        if (TextUtils.isEmpty(emailText) || !(Patterns.EMAIL_ADDRESS.matcher(emailText).matches())) {
            //email sbagliata o vuota
            res = false;
            mEmail.setError(getResources().getString(R.string.error_email));
            mEmail.requestFocus();
        }
        if (res) {
            if (TextUtils.isEmpty(passwordText) || !(Pattern.compile(UtilityStrings.PASSOWRD_REGEX).matcher(passwordText).matches())) {
                //email corretta, password sbagliata
                res = false;
                mPassword.setError(getResources().getString(R.string.error_password));
                mPassword.requestFocus();
            }
        }
        return res;
    }




    @Override
    public void onSuccess() {
        Log.d("AOO", "Sto qua");
    }

    @Override
    public void onFailure() {
        Log.d("AOO", "Sto al punto giusto");
    }

}