package it.uniba.di.sms2021.gruppodkl.wefit.contract;

import android.net.Uri;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public interface RegistrationActivityContract {

    interface View{
        void onSuccess(User user);
        void onFailure();
        String getFileExtension();
        Uri getFileURI();
    }

    interface Presenter{
        void registerUser(Map<String, String> userData, Map<String,String> specificData);
    }

}
