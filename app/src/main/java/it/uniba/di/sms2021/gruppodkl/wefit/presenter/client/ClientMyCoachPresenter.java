package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.client.ClientMyCoachContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDb;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientMyCoachPresenter implements ClientMyCoachContract.Presenter {

    private ClientMyCoachContract.View mView;
    private final UserDb.UserCallbacks mUserCallbacks;
    private final UserDb.RatingsCallbacks mRatingCallbacks;

    public ClientMyCoachPresenter(ClientMyCoachContract.View mView) {
        this.mView = mView;

        mUserCallbacks = new UserDb.UserCallbacks() {
            @Override
            public void userLoaded(User user, boolean success) {
                if(user != null)
                    mView.onCoachDataReceived((Coach) user);
                else
                    mView.onCoachNotFound();
            }

            @Override
            public void hasBeenCreated(User user, boolean res) {
            }
        };

        mRatingCallbacks = mView::onCoachRatingStarsObtained;
    }


    @Override
    public void getCoachData(String coachEmail) {
        UserDb.getUser(coachEmail, mUserCallbacks);
    }

    @Override
    public void addFeedback(Map<String, Object> map, String coachEmail) {
        UserDb.addInSubCollection(coachEmail, Keys.Collections.RATINGS, map);
    }

    @Override
    public void leaveCoach(Client client) {
        String coachEmail = client.coach;

        Map<String, Object> map = new HashMap<>();
        map.put(Client.ClientKeys.COACH, null);

        UserDb.update(client,map);

        //TODO Rimuovi anche il collegamento opposto COACH --> CLIENT --> CLIENT_LIST
    }

    @Override
    public void getCoachRatingStars(Coach coach) {
        UserDb.getCoachRatingStars(coach, mRatingCallbacks);
    }
}
