package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRunStatsAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;

public interface ClientRunStatsContract {

    interface View{
        void openRun(Run run);
        void runNotFound();
    }


    interface Presenter{
        ClientRunStatsAdapter getAdapter(String clientMail);
        void openSpecifiedRun(Run run);
    }

}
