package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientDietDiaryAdapter;

public interface ClientDietDiaryContract {

    interface View{

    }

    interface Presenter{
        ClientDietDiaryAdapter getAdapter(String clientMail, String day, int mealType);
    }

}
