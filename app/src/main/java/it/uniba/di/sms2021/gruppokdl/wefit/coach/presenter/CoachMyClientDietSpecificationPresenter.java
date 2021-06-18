package it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachDietDayAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachMyClientDietSpecificationContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;

public class CoachMyClientDietSpecificationPresenter implements CoachMyClientDietSpecificationContract.Presenter {

    private final CoachMyClientDietSpecificationContract.View mView;


    public CoachMyClientDietSpecificationPresenter(CoachMyClientDietSpecificationContract.View view){this.mView = view;}


    @Override
    public CoachDietDayAdapter getAdapter(String clientName, String dayOfTheWeek, int mealType) {



        FirestoreRecyclerOptions<Meal> options = new FirestoreRecyclerOptions.Builder<Meal>()
                .setQuery(ClientDAO.getAllDishesOfTheMeal(clientName,dayOfTheWeek,mealType),Meal.class)
                .build();


        return new CoachDietDayAdapter(options, this);
    }

    @Override
    public void removeMeal(Meal meal) {
        String clientMail = mView.getClientMail();
        String dayOfTheWeek = mView.getDayOfTheWeek();

        ClientDAO.removeMeal(clientMail, dayOfTheWeek, meal.getId());
    }


}
