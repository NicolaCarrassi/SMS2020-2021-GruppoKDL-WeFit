package it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.client;

import android.net.Uri;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public interface ClientProfileFragmentContract {

    interface View{
        String getFileExtension(Uri uri);
    }

    interface Presenter{
        void saveImage(Uri uri, User user);
        void updateUser(Map<String, Object> map, User user);
    }
}
