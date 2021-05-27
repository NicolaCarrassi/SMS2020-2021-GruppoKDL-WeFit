package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.LoginActivityContract;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {

    private LoginActivityContract.View mView;

    public LoginActivityPresenter(LoginActivityContract.View view){
        this.mView = view;
    }


    @Override
    public void doLogin(String email, String password) {
        mView.onSuccess();
    }

    @Override
    public void newUser() {
        mView.onFailure();
    }

    @Override
    public void forgotPassword() {
        mView.onFailure();
    }
}


