package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.SplashActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class SplashActivityPresenter implements SplashActivityContract.Presenter {

    private final SplashActivityContract.View mView;
    private final UserDAO.UserCallbacks mCallbacks;


    /**
     * Costruttore della classe
     *
     * @param view View che implementa il contract
     */
    public SplashActivityPresenter(SplashActivityContract.View view){
        this.mView = view;

        mCallbacks = new UserDAO.UserCallbacks() {
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
        UserDAO.getUser(email, mCallbacks);
    }
}
