package it.uniba.di.sms2021.gruppodkl.wefit.contract;

import java.util.Map;

public interface RegistrationFragmentContract {

    interface View {
        boolean areCorrect();
        Map<String, String> getAddictionalData();
    }

    interface Presenter{

    }
}