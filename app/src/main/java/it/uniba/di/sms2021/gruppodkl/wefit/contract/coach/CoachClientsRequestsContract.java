package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachClientRequestsAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;

public interface CoachClientsRequestsContract {

    interface View{
        void showClientProfile(Request request);
    }

    interface Presenter{
        CoachClientRequestsAdapter makeAdapter();
        void showUserProfile(Request request);
    }
}
