package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientMyTrainingAdapter;

public interface ClientMyTrainingContract {

    interface View{

    }

    interface Presenter{
        ClientMyTrainingAdapter getAdapter(String clientEmail);
    }

}
