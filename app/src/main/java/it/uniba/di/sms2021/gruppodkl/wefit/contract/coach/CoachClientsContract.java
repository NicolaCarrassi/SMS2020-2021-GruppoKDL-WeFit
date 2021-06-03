package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachMyClientListAdapter;

public interface CoachClientsContract {

    interface View{
        void openClientProfile(String clientMail);
        void errorOpeningProfile();
    }

    interface Presenter{
        CoachMyClientListAdapter getAdapter(String coachEmail);
        void onRequestToOpenProfile(String clientEmail);
    }

}
