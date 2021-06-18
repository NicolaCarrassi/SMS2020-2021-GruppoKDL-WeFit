package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientDietShoppingListContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.MealsDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.DayOfTheWeek;

public class ClientDietShoppingListPresenter implements ClientDietShoppingListContract.Presenter, MealsDAO.ShoppingCartLoaded {

    private final ClientDietShoppingListContract.View mView;


    public ClientDietShoppingListPresenter(ClientDietShoppingListContract.View view){this.mView = view;}

    @Override
    public void loadShoppingList(String clientMail, int numberOfDays) {
        List<String> daysToQuery = new ArrayList<>();

        for(int i = 1; i<= numberOfDays ; i++)
            daysToQuery.add(DayOfTheWeek.convertPositionToDayOfTheWeek(DayOfTheWeek.getDayOfTheWeekFromCalendar(i)));


        MealsDAO.fetchShoppingCart(clientMail, daysToQuery, this);
    }

    @Override
    public void onShoppingCartLoaded(Map<String, Integer> mealsMap) {
        if(mealsMap == null)
            mealsMap = new HashMap<>();


        mView.onShoppingInformationLoaded(mealsMap);
    }
}
