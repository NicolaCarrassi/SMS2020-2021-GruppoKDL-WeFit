package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientDietDiaryAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientDietDiaryContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;

public class ClientDietDiaryPresenter implements ClientDietDiaryContract.Presenter {

    private final ClientDietDiaryContract.View mView;

    public ClientDietDiaryPresenter(ClientDietDiaryContract.View view){ this.mView = view;}


    @Override
    public ClientDietDiaryAdapter getAdapter(String clientMail, String day, int mealType) {

        FirestoreRecyclerOptions<Meal> options = new FirestoreRecyclerOptions.Builder<Meal>()
                .setQuery(ClientDAO.getRegisteredMealsOfTheDay(clientMail, day, mealType), Meal.class)
                .build();

        return new ClientDietDiaryAdapter(options);
    }
}
