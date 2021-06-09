package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import java.util.List;

public interface CoachAddMealContract {

    interface View{
        void mealsLoaded(List<String> translatedList);
        void onFailure();
    }

    interface Presenter{
        void loadAllMeals();
        void addMeal(String clientMail, String dayOfTheWeek, String mealSelected, int momentOfTheDay, int quantity);
    }
}
