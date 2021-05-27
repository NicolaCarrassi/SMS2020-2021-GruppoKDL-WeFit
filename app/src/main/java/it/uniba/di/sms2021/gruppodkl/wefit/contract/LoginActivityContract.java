package it.uniba.di.sms2021.gruppodkl.wefit.contract;

public interface LoginActivityContract {

    interface View{
        void onSuccess();
        void onFailure();
    }

    interface Presenter{
        void doLogin(String email, String password);
    }

}
