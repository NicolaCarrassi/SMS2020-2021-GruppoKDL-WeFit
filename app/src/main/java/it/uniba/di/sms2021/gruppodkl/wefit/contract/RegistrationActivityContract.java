package it.uniba.di.sms2021.gruppodkl.wefit.contract;

import java.util.Map;

public interface RegistrationActivityContract {

    interface View{

    }

    interface Presenter{
        void registerUser(Map<String, String> userData, Map<String,String> specificData);
    }

}
