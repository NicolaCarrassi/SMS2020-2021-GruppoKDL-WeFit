package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;

public interface CoachClientRequestProfileContract {

    interface View{
        void onClientInfoLoaded(Client client);
        void onFailure();
    }

    interface Presenter{
        void clientRequest(Coach coach, Request request, boolean isAccepted);
        void getClientInfo(String clientMail);
    }
}
