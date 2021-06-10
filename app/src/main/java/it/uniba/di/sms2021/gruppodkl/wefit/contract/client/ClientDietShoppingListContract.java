package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import java.util.Map;

public interface ClientDietShoppingListContract {

    interface View{
        void onShoppingInformationLoaded(Map<String, Integer> mealsMap);
    }


    interface Presenter{
        void loadShoppingList(String clientMail, int numberOfDays);
    }

}
