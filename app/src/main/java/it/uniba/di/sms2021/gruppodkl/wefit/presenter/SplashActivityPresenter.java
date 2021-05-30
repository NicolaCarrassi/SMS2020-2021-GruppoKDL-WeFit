package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.SplashActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class SplashActivityPresenter implements SplashActivityContract.Presenter {

    private final SplashActivityContract.View mView;
    private User mUser;

    /**
     * Costruttore della classe
     *
     * @param view View che implementa il contract
     */
    public SplashActivityPresenter(SplashActivityContract.View view){
        this.mView = view;
    }


    @Override
    public void fetchUserData(String email) {
        DocumentReference usersRef = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(email);
        usersRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        if(documentSnapshot.contains(User.UserKeys.ROLE) &&
                                documentSnapshot.getString(User.UserKeys.ROLE) != null &&
                                documentSnapshot.getString(User.UserKeys.ROLE).equals(Keys.Role.CLIENT)) {

                            Double temp = documentSnapshot.getDouble(Client.ClientKeys.HEIGHT);

                            assert temp != null;
                            int height = temp.intValue() ;

                            temp = documentSnapshot.getDouble(Client.ClientKeys.WEIGHT);
                            assert temp != null;
                            float weight = temp.floatValue();

                            mUser = new Client(documentSnapshot.getString(Client.ClientKeys.FULL_NAME), email, documentSnapshot.getString(Client.ClientKeys.BIRTH_DATE),
                                    documentSnapshot.getString(Client.ClientKeys.GENDER), Keys.Role.CLIENT, height,
                                    weight, documentSnapshot.getString(Client.ClientKeys.OBJECTIVE));

                        } else {

                            mUser = new Coach(documentSnapshot.getString(Coach.CoachKeys.FULL_NAME), email, documentSnapshot.getString(Coach.CoachKeys.BIRTH_DATE),
                                    documentSnapshot.getString(Coach.CoachKeys.GENDER), Keys.Role.COACH, documentSnapshot.getBoolean(Coach.CoachKeys.IS_PERSONAL_TRAINER),
                                    documentSnapshot.getBoolean(Coach.CoachKeys.IS_DIETIST), documentSnapshot.getString(Coach.CoachKeys.CERTIFICATION));
                        }

                        if(documentSnapshot.contains(User.UserKeys.IMAGE) && documentSnapshot.getString(User.UserKeys.IMAGE) != null) {
                            mUser.setImage(documentSnapshot.getString(User.UserKeys.IMAGE));
                            mUser.createImageBitmap();
                        }
                        mView.onSuccess(mUser);
                    }
                });
    }
}
