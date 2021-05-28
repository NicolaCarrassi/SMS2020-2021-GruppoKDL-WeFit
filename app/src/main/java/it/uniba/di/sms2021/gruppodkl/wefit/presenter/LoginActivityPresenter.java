package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import com.google.firebase.auth.FirebaseAuth;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.LoginActivityContract;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {

    private final LoginActivityContract.View mView;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public LoginActivityPresenter(LoginActivityContract.View view){
        this.mView = view;
    }


    @Override
    public void doLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
                mView.onSuccess();
            else
                mView.onFailure();
        });
    }


    @Override
    public void forgotPassword() {
        mView.onFailure();
    }
}


