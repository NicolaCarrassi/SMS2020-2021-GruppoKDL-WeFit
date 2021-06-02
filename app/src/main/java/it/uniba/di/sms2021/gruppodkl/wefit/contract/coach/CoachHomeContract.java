package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;

public interface CoachHomeContract {

    interface View{
        void onRequestNumberLoaded(int num);
    }
    interface Presenter{
        void loadNumberRequest(Coach coach);
    }
}
