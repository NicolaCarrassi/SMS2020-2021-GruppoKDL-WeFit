package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.LoginActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {

    private final LoginActivityContract.View mView;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private User user;

    /**
     * Costruttore del presenter
     *
     * @param view View per i callbacks
     */
    public LoginActivityPresenter(LoginActivityContract.View view){
        this.mView = view;
    }



    @Override
    public void doLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                DocumentReference usersRef = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(email);

                usersRef.get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if(documentSnapshot.exists()){
                                if(documentSnapshot.contains(User.UserKeys.ROLE) &&
                                        documentSnapshot.getString(User.UserKeys.ROLE).equals(Keys.Role.CLIENT)) {

                                    Double temp = documentSnapshot.getDouble(Client.ClientKeys.HEIGHT);

                                    assert temp != null;
                                    int height = temp.intValue() ;

                                    temp = documentSnapshot.getDouble(Client.ClientKeys.WEIGHT);
                                    assert temp != null;
                                    float weight = temp.floatValue();

                                    user = new Client(documentSnapshot.getString(Client.ClientKeys.FULL_NAME), email, documentSnapshot.getString(Client.ClientKeys.BIRTH_DATE),
                                            documentSnapshot.getString(Client.ClientKeys.GENDER), Keys.Role.CLIENT, height,
                                            weight, documentSnapshot.getString(Client.ClientKeys.OBJECTIVE));

                                } else {

                                    user = new Coach(documentSnapshot.getString(Coach.CoachKeys.FULL_NAME), email, documentSnapshot.getString(Coach.CoachKeys.BIRTH_DATE),
                                            documentSnapshot.getString(Coach.CoachKeys.GENDER), Keys.Role.COACH, documentSnapshot.getBoolean(Coach.CoachKeys.IS_PERSONAL_TRAINER),
                                            documentSnapshot.getBoolean(Coach.CoachKeys.IS_DIETIST), documentSnapshot.getString(Coach.CoachKeys.CERTIFICATION));
                                }

                                if(documentSnapshot.contains(User.UserKeys.IMAGE) && documentSnapshot.getString(User.UserKeys.IMAGE) != null)
                                    user.setImage(documentSnapshot.getString(User.UserKeys.IMAGE));

                                mView.onSuccess(user);
                            }
                        });
            }else
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


