package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;


import androidx.fragment.app.FragmentActivity;

import java.util.List;

public interface ClientAddMealContract {

    interface View{
        void mealRegistered();
        void onFailure();
        FragmentActivity getActivity();
        void setAvailableMealsToRegister(List<String> mealsList, boolean dietNotEmpty);
    }

    interface Presenter{
       void registerMeal(String mealOfTheDay);
       void checkMealsAvailable();
    }
}
