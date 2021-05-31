package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import android.util.Log;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.LoginActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDb;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {

    private final LoginActivityContract.View mView;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final UserDb.UserCallbacks mCallBack;

    /**
     * Costruttore del presenter
     *
     * @param view View per i callbacks
     */
    public LoginActivityPresenter(LoginActivityContract.View view) {

        this.mView = view;

        mCallBack = new UserDb.UserCallbacks() {
            @Override
            public void userLoaded(User user, boolean success) {
                if (success && user != null)
                    mView.onSuccess(user);
                else
                    mView.onFailure();
            }

            @Override
            public void hasBeenCreated(User user, boolean res) {
            }
        };
    }



    @Override
    public void doLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                UserDb.getUser(email, mCallBack);
            } else
                mView.onFailure();
        });
    }


    @Override
    public void forgotPassword(String email) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            mView.emailSent();
                        else
                            mView.failedToSendEmail();
                    });
        } else {
            mView.wrongEmail();
        }
    }
}


