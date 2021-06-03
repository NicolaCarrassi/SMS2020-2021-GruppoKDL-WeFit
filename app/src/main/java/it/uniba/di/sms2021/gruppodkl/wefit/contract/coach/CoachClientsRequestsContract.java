package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRequestsAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;

public interface CoachClientsRequestsContract {

    interface View{
        void showClientProfile(Request request);
    }

    interface Presenter{
        ClientRequestsAdapter makeAdapter();
        void showUserProfile(Request request);
    }
}
