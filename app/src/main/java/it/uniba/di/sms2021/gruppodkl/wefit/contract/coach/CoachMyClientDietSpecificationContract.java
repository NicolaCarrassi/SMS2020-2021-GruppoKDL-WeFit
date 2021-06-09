package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachDietDayAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;

public interface CoachMyClientDietSpecificationContract {

    interface View{
        String getClientMail();
        String getDayOfTheWeek();
    }

    interface Presenter{
        CoachDietDayAdapter getAdapter(String clientName, String dayOfTheWeek, int mealType);
        void removeMeal(Meal meal);
    }

}
