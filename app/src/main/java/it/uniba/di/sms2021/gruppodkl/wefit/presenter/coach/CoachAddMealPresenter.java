package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachAddMealContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.db.MealsDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;

public class CoachAddMealPresenter implements CoachAddMealContract.Presenter, MealsDAO.MealsLoaded {

    private final CoachAddMealContract.View mView;
    private final Context mContext;




    public CoachAddMealPresenter(CoachAddMealContract.View view, Context context){
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void loadAllMeals() {
        MealsDAO.loadAll(this);
    }

    @Override
    public void addMeal(String clientMail, String dayOfTheWeek, String mealSelected, int momentOfTheDay, int quantity) {
        String meal = getMealName(mealSelected);

        if(meal != null){
            ClientDAO.addMeal(clientMail, dayOfTheWeek, new Meal(meal,quantity, momentOfTheDay));
        } else{
            mView.onFailure();
        }
    }

    @Override
    public void onMealsLoaded(List<String> meals) {
        List<String> translatedList = new ArrayList<>();

        for(String meal: meals)
            translatedList.add(Meal.convertName(meal,mContext));

        mView.mealsLoaded(translatedList);
    }


    private String getMealName(String mealName){
        String res = null;
        boolean found = false;

        if(mealName.equals(mContext.getResources().getString(R.string.fette_biscottate))){
            found = true;
            res = Meal.MealList.FETTE_BISCOTTATE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.biscotti))){
            found = true;
            res = Meal.MealList.BISCOTTI;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.tacchino))){
            found = true;
            res = Meal.MealList.TACCHINO;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.pesce))){
            found = true;
            res = Meal.MealList.PESCE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.uova))){
            found = true;
            res = Meal.MealList.UOVA;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.banana))){
            found = true;
            res = Meal.MealList.BANANA;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.lenticchie))){
            found = true;
            res = Meal.MealList.LENTICCHIE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.pera))){
            found = true;
            res = Meal.MealList.PERA;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.carote))){
            found = true;
            res = Meal.MealList.CAROTE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.caffe))){
            found = true;
            res = Meal.MealList.CAFFE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.patate))){
            found = true;
            res = Meal.MealList.PATATE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.mela))){
            found = true;
            res = Meal.MealList.MELA;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.pollo))){
            found = true;
            res = Meal.MealList.POLLO;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.latte))){
            found = true;
            res = Meal.MealList.LATTE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.formaggio))){
            found = true;
            res = Meal.MealList.FORMAGGIO;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.mozzarella))){
            found = true;
            res = Meal.MealList.MOZZARELLA;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.tofu))){
            found = true;
            res = Meal.MealList.TOFU;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.riso))){
            found = true;
            res = Meal.MealList.RISO;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.cereali))){
            found = true;
            res = Meal.MealList.CEREALI;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.pizza))){
            found = true;
            res = Meal.MealList.PIZZA;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.insalata))){
            found = true;
            res = Meal.MealList.INSALATA;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.pane))){
            found = true;
            res = Meal.MealList.PANE;
        }
        if(!found && mealName.equals(mContext.getResources().getString(R.string.pasta))){
            found = true;
            res = Meal.MealList.PASTA;
        }

        return res;
    }
}
