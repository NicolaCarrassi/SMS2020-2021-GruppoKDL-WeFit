package it.uniba.di.sms2021.gruppodkl.wefit;

import android.app.Application;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class WeFitApplication extends Application {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
