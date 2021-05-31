package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.SplashActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDb;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class SplashActivityPresenter implements SplashActivityContract.Presenter {

    private final SplashActivityContract.View mView;
    private final UserDb.UserCallbacks mCallbacks;


    /**
     * Costruttore della classe
     *
     * @param view View che implementa il contract
     */
    public SplashActivityPresenter(SplashActivityContract.View view){
        this.mView = view;

        mCallbacks = new UserDb.UserCallbacks() {
            @Override
            public void userLoaded(User user, boolean success) {
                if(user != null)
                    mView.onSuccess(user);
            }

            @Override
            public void hasBeenCreated(User user, boolean res) {
            }
        };

    }


    @Override
    public void fetchUserData(String email) {
        UserDb.getUser(email, mCallbacks);
    }
}
