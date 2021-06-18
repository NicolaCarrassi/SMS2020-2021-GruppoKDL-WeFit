package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientDietDayAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientDailyDietContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;

public class ClientDailyDietPresenter implements ClientDailyDietContract.Presenter {

    private final ClientDailyDietContract.View mView;

    public ClientDailyDietPresenter(ClientDailyDietContract.View view){ this.mView = view;}


    @Override
    public ClientDietDayAdapter getAdapter(String clientMail, String dayOftheWeek, int mealType) {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPrefetchDistance(3)
                .setPageSize(5)
                .build();


        FirestorePagingOptions<Meal> options = new FirestorePagingOptions.Builder<Meal>()
                .setQuery(ClientDAO.getAllDishesOfTheMeal(clientMail, dayOftheWeek, mealType), config, Meal.class).build();

        return new ClientDietDayAdapter(options);
    }
}
