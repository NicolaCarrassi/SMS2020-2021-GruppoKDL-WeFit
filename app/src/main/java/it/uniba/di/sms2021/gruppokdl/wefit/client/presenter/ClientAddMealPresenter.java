package it.uniba.di.sms2021.gruppokdl.wefit.presenter.client;

import android.content.Context;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientAddMealContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.MealsDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.DayOfTheWeek;

public class ClientAddMealPresenter implements ClientAddMealContract.Presenter, MealsDAO.MealRegistered {

    private final ClientAddMealContract.View mView;
    private final String mClientMail;
    private boolean mBreakfastToAdd;
    private boolean mLunchToAdd;
    private boolean mDinnerToAdd;

    public ClientAddMealPresenter(ClientAddMealContract.View view, @NonNull String clientMail){
        this.mView = view;
        this.mClientMail = clientMail;
    }

    @Override
    public void registerMeal(String mealOfTheDay) {
        Context context = mView.getActivity();
        int mealType;
        String today = DayOfTheWeek.convertPositionToDayOfTheWeek(DayOfTheWeek.getDayOfTheWeekFromCalendar(0));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        if(mealOfTheDay.equals(context.getString(R.string.breakfast)))
            mealType = Meal.MealType.BREAKFAST;
        else if(mealOfTheDay.equals(context.getString(R.string.lunch)))
            mealType = Meal.MealType.LUNCH;
        else
            mealType = Meal.MealType.DINNER;


        MealsDAO.registerMeal(mClientMail, today, mealType, date, this);
    }

    @Override
    public void checkMealsAvailable() {
        String today = DayOfTheWeek.convertPositionToDayOfTheWeek(DayOfTheWeek.getDayOfTheWeekFromCalendar(0));

        MealsDAO.checkAvailableMealsForClient(mClientMail, today, this);
    }

    @Override
    public void giveMealsAvailable(boolean breakfastAvailable, boolean lunchAvailable, boolean dinnerAvailable) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        mBreakfastToAdd = breakfastAvailable;
        mLunchToAdd = lunchAvailable;
        mDinnerToAdd = dinnerAvailable;

        MealsDAO.checkRegisteredMeals(mClientMail, today, this);
    }

    @Override
    public void onMealRegistered(boolean success) {
        if(success)
            mView.mealRegistered();
        else
            mView.onFailure();
    }

    @Override
    public void mealsRegistered(boolean breakfastAvailable, boolean lunchAvailable, boolean dinnerAvailable) {
        Context context = mView.getActivity();
        List<String> res = new ArrayList<>();
        boolean dietNotEmpty;

        if(mBreakfastToAdd && breakfastAvailable)
            res.add(context.getString(R.string.breakfast));

        if(mLunchToAdd && lunchAvailable)
            res.add(context.getString(R.string.lunch));

        if(mDinnerToAdd && dinnerAvailable)
            res.add(context.getString(R.string.dinner));

        dietNotEmpty = mBreakfastToAdd || mLunchToAdd || mDinnerToAdd;

        mView.setAvailableMealsToRegister(res, dietNotEmpty);
    }


}
