package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientDietDayAdapter;

public interface ClientDailyDietContract {

    interface View{


    }

    interface Presenter{
        ClientDietDayAdapter getAdapter(String clientMail, String dayOfTheWeek, int mealType);
    }

}
