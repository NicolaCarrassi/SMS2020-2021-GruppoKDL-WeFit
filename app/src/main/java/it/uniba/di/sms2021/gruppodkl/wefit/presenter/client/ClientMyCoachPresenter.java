package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientMyCoachContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientMyCoachPresenter implements ClientMyCoachContract.Presenter {

    private ClientMyCoachContract.View mView;
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

            }

            @Override
            public void feedbacksLoaded(List<Feedback> feedbackList) {
                //
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
        String coachEmail = client.coach;

        Map<String, Object> map = new HashMap<>();
        map.put(Client.ClientKeys.COACH, null);

        UserDAO.update(client,map);

        //TODO Rimuovi anche il collegamento opposto COACH --> CLIENT --> CLIENT_LIST
    }

    @Override
    public void getCoachRatingStars(Coach coach) {
        CoachDAO.getCoachRatingStars(coach, mRatingCallbacks);
    }
}
