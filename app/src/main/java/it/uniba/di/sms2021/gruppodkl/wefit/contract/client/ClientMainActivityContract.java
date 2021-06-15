package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

public interface ClientMainActivityContract {

    interface View{
        void onSuccess();
    }

    interface Presenter{
        void addCoachFromNFC(String clientMail, String coachMail);
    }

}
