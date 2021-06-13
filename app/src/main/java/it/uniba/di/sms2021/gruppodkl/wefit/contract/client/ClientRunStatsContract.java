package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRunStatsAdapter;

public interface ClientRunStatsContract {

    interface View{

    }


    interface Presenter{
        ClientRunStatsAdapter getAdapter(String clientMail);
    }

}
