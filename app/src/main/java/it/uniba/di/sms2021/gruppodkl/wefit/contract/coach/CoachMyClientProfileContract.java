package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;

public interface CoachMyClientProfileContract  {

    interface View{
        void onFailure();
        void onClientDataReceived(Client client, List<Float> weightList, List<Date> dateList);
    }

    interface Presenter{
        void findUserData(String clientMail);
    }
}
