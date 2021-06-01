package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientCoachListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientCoachListPresenter implements ClientCoachListContract.Presenter {


    private final ClientCoachListContract.View mView;

    private FirebaseFirestore firebaseFirestore;

    public ClientCoachListPresenter(ClientCoachListContract.View mView) {
        this.mView = mView;
    }

    public void queryCoachList(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection(Keys.Collections.USERS).whereEqualTo(User.UserKeys.ROLE, Keys.Role.COACH);

    }





}
