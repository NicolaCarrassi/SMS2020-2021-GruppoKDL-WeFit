package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;

public interface ClientProgressContract {

    interface View{
        void onFailure();
        void onClientDataReceived(List<Float> weightList, List<Date> dateList);
    }

    interface Presenter{
        void findUserData(String clientMail);
    }

}
