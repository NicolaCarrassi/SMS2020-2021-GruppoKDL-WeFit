package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;

public interface ClientMyCoachContract {

    interface View{
        void onCoachDataReceived(Coach coach);
        void onCoachNotFound();
        void onCoachRatingStarsObtained(float numStars);
        void requestSentSuccessfully();
        void requestFailed();

    }

    interface Presenter{
        void getCoachData(String coachEmail);
        void addFeedback(Map<String, Object> map, String coachEmail);
        void leaveCoach(Client client);
        void getCoachRatingStars(Coach coach);
        void sendRequestToCoach(Client client, Coach coach);
        void deleteRequestToCoach(Client client, Coach coach);
    }
}
