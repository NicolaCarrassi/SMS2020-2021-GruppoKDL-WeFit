package it.uniba.di.sms2021.gruppodkl.wefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.LoginActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.LoginActivityPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.UtilityStrings;

public class LoginActivity extends AppCompatActivity implements LoginActivityContract.View {

    private LoginActivityContract.Presenter mPresenter;

    private EditText mEmail;
    private EditText mPassword;

    private TextView mNewUser;
    private TextView mForgotPassword;

    private Button mLoginButton;
    private ImageView mPasswordImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.bind();
        this.setListeners();
        this.mPresenter = new LoginActivityPresenter(this);
    }

    /**
     * Il metodo permette di effettuare il binding degli elementi della view
     */
    private void bind(){
        this.mEmail = findViewById(R.id.email_edit_text);
        this.mPassword = findViewById(R.id.password_edit_text);
        this.mLoginButton = findViewById(R.id.login_button);
        this.mNewUser = findViewById(R.id.new_user_label);
        this.mForgotPassword = findViewById(R.id.forgot_password_label);
        this.mPasswordImageView = findViewById(R.id.password_visible);
    }

    /**
     * Il metodo permette di impostare i listeners degli elementi grafici collegati alla view
     * Deve essere chiamato dopo il metodo bind()
     */
    private void setListeners(){
        mLoginButton.setOnClickListener(v -> {
            String emailText = mEmail.getText().toString().trim().toLowerCase();
            String passwordText = mPassword.getText().toString().trim();
            if(checkAttributes(emailText, passwordText)){
                mPresenter.doLogin(emailText,passwordText);
            }
        });

        mForgotPassword.setOnClickListener(v -> mPresenter.forgotPassword(mEmail.getText().toString()));

        mNewUser.setOnClickListener(v -> newUser());

        mPasswordImageView.setOnClickListener(v -> {
            if(mPassword.getInputType() == 129) {
                mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else {
                mPassword.setInputType(129);
            }
        });

    }

    /**
     * Il metodo permette di controllare che gli attributi di email e password
     * siano stati inseriti correttamente
     *
     * @param emailText stringa contenente il testo della email
     * @param passwordText stringa contenente il testo della password
     * @return true se gli attributi sono conformi ai requisiti, false altrimenti
     */
    private boolean checkAttributes(String emailText, String passwordText) {
        boolean res = true;

        if (TextUtils.isEmpty(emailText) || !(Patterns.EMAIL_ADDRESS.matcher(emailText).matches())) {
            //email sbagliata o vuota
            res = false;
            mEmail.setError(getResources().getString(R.string.error_email));
            mEmail.requestFocus();
        }
        if (TextUtils.isEmpty(passwordText) || !(Pattern.compile(UtilityStrings.PASSOWRD_REGEX).matcher(passwordText).matches())) {
            if(res)
                mPassword.requestFocus();

            res = false;
            mPassword.setError(getResources().getString(R.string.error_password));
        }

        return res;
    }

    /**
     * Il metodo permette di accedere alla schermata di registrazione
     */
    private void newUser(){
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSuccess(User user) {
        ((WeFitApplication) getApplication()).setUser(user);
        Intent intent = new Intent(this, MainActivityUser.class);
        startActivity(intent);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Nome utente o password errati", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailSent() {
        Toast.makeText(this,getResources().getString(R.string.forgot_mail_sent), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedToSendEmail() {
        Toast.makeText(this,getResources().getString(R.string.error_sending_mail), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void wrongEmail() {
        Toast.makeText(this, getResources().getString(R.string.error_email), Toast.LENGTH_SHORT).show();
    }

}