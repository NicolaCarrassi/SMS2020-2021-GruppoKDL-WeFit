package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;

public interface ClientAddWeightContract {

    interface View{
        void onSuccess();
    }

    interface Presenter{
        void addWeight(Client client, float weight);
    }

}
