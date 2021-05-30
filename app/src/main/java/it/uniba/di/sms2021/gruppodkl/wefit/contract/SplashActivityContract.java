package it.uniba.di.sms2021.gruppodkl.wefit.contract;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public interface SplashActivityContract {

    interface View{
       void onSuccess(User user);
    }

    interface Presenter{
        void fetchUserData(String email);
    }

}
