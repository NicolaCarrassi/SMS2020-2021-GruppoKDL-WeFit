package it.uniba.di.sms2021.gruppodkl.wefit.contract;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public interface LoginActivityContract {

    interface View{
        void onSuccess(User user);
        void onFailure();
        void emailSent();
        void failedToSendEmail();
        void wrongEmail();
    }

    interface Presenter{
        void doLogin(String email, String password);
        void forgotPassword(String email);
    }

}
