package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientMyCoachContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientMyCoachPresenter implements ClientMyCoachContract.Presenter, ClientDAO.ClientDAOCallbacks {

    private final ClientMyCoachContract.View mView;
    private final UserDAO.UserCallbacks mUserCallbacks;
    private final CoachDAO.RatingCallbacks mRatingCallbacks;

    public ClientMyCoachPresenter(ClientMyCoachContract.View mView) {
        this.mView = mView;

        mUserCallbacks = new UserDAO.UserCallbacks() {
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

        mRatingCallbacks = new CoachDAO.RatingCallbacks() {
            @Override
            public void ratingMeanLoaded(float ratingMean) {
                mView.onCoachRatingStarsObtained(ratingMean);
            }

            @Override
            public void lastFeedbackLoaded(Feedback feedback, float mean, int numElem) {
                //
            }
        };
    }


    @Override
    public void getCoachData(String coachEmail) {
        UserDAO.getUser(coachEmail, mUserCallbacks);
    }

    @Override
    public void addFeedback(Map<String, Object> map, String coachEmail) {
        UserDAO.addInSubCollection(coachEmail, Keys.Collections.RATINGS, map);
    }

    @Override
    public void leaveCoach(Client client) {

        Map<String, Object> map = new HashMap<>();
        map.put(Client.ClientKeys.COACH, null);
        UserDAO.update(client, map);

        mView.onCoachNotFound();
    }

    @Override
    public void getCoachRatingStars(Coach coach) {
        CoachDAO.getCoachRatingStars(coach, mRatingCallbacks);
    }

    @Override
    public void sendRequestToCoach(Client client, Coach coach) {
        Map<String, Object> map = new HashMap<>();
        map.put(Keys.Request.NAME, client.fullName);
        map.put(Keys.Request.MAIL, client.email);
        map.put(Keys.Request.IMAGE, client.image);

        ClientDAO.requestToCoach(client, coach,map,this);
    }

    @Override
    public void deleteRequestToCoach(Client client, Coach coach) {
        ClientDAO.deleteRequestToCoach(client.email, coach.email);
    }

    @Override
    public void requestSent(boolean isSuccessful) {
        if(isSuccessful)
            mView.requestSentSuccessfully();
        else
            mView.requestFailed();
    }
}
