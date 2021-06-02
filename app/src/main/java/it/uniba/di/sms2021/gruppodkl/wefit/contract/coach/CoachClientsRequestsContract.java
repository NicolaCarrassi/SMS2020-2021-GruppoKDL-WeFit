package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRequestsAdapter;

public interface CoachClientsRequestsContract {

    interface View{

    }

    interface Presenter{
        ClientRequestsAdapter makeAdapter();
    }
}
