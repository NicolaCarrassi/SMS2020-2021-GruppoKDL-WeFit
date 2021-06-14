package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;

public interface ClientRunContract {

    interface View{
        void showLastRun(Run run);
        void lastRunEmpty();
    }

    interface Presenter{
        void getLastRun(String mail);
    }

}
