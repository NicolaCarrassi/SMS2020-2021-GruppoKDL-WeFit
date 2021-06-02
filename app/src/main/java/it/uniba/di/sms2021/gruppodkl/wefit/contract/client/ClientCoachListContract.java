package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;


import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;

public interface ClientCoachListContract {

        interface View{
                void onFailure();
                void onSuccess();
        }

        interface Presenter{
                CoachListAdapter getAdapter();
                void openCoachProfile(Coach coach);
                void sendRequestToCoach(Coach coach);
        }


}

