package it.uniba.di.sms2021.gruppodkl.wefit.db;






import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class MealsDAO {

    public interface MealsLoaded{
        void onMealsLoaded(List<String> meals);
    }

    public interface ShoppingCartLoaded{
        void onShoppingCartLoaded(Map<String, Integer> mealsMap);
    }

    public interface MealRegistered{
        void giveMealsAvailable(boolean breakfastAvailable, boolean lunchAvailable, boolean dinnerAvailable);
        void onMealRegistered(boolean success);
        void mealsRegistered(boolean breakfastAvailable, boolean lunchAvailable, boolean dinnerAvailable);
    }


    private static boolean sBreakfastAvailable;
    private static boolean sLunchAvailable;
    private static boolean sDinnerAvailable;

    private static List<String> sMealsNameList;
    private static Map<String, Integer> sMealsMap;


    public static void loadAll(MealsLoaded callback){
        if(sMealsNameList != null)
            sMealsNameList.clear();
        else
            sMealsNameList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.MEALS).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot  snapshot = task.getResult();
                        for(DocumentSnapshot doc : snapshot.getDocuments())
                            sMealsNameList.add(doc.getString(Meal.MealKeys.NAME));

                        callback.onMealsLoaded(sMealsNameList);
                    }
                });
    }

    public static void fetchShoppingCart(String clientMail,List<String> daysToQuery, ShoppingCartLoaded callback) {
        if (sMealsMap != null)
            sMealsMap.clear();
        else
            sMealsMap = new HashMap<>();


        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.DIET);

        for(String day: daysToQuery){
            collectionReference.document(day).collection(Keys.Collections.MEALS).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            QuerySnapshot qs = task.getResult();
                            if (qs != null && !qs.isEmpty()) {
                                List<DocumentSnapshot> documents = qs.getDocuments();
                                for (DocumentSnapshot document : documents) {
                                    if (!sMealsMap.containsKey(document.getString(Meal.MealKeys.NAME)))
                                        sMealsMap.put(document.getString(Meal.MealKeys.NAME), document.get(Meal.MealKeys.QUANTITY, Integer.class));
                                    else {
                                        Integer temp = sMealsMap.get(document.getString(Meal.MealKeys.NAME));
                                        temp += document.get(Meal.MealKeys.QUANTITY, Integer.class);
                                        sMealsMap.put(document.getString(Meal.MealKeys.NAME), temp);
                                    }
                                }
                                callback.onShoppingCartLoaded(sMealsMap);
                            }
                        }
                    });
        }
        callback.onShoppingCartLoaded(sMealsMap);
    }


    public static void registerMeal(String clientMail, String day, int mealType, String date, MealRegistered callback){

        DocumentReference userRef = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail);

        userRef.collection(Keys.Collections.DIET).document(day).collection(Keys.Collections.MEALS)
                .whereEqualTo(Meal.MealKeys.MEAL_TYPE, mealType).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot qs = task.getResult();
                        if(!qs.isEmpty()){
                            for(Meal meal : qs.toObjects(Meal.class))
                                userRef.collection(Keys.Collections.REGISTERED_MEALS).document(date)
                                    .collection(Keys.Collections.MEALS).document().set(meal);
                            callback.onMealRegistered(true);
                        }
                    }
                    callback.onMealRegistered(false);
        });
    }


    public static void checkAvailableMealsForClient(String clientMail, String day, MealRegistered callback){
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        CollectionReference ref = instance.collection(Keys.Collections.USERS).document(clientMail)
            .collection(Keys.Collections.DIET).document(day).collection(Keys.Collections.MEALS);

        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.BREAKFAST).get()
        .addOnCompleteListener(breakfastTask -> {
            if(breakfastTask.isSuccessful()){
                sBreakfastAvailable = breakfastTask.getResult().getDocuments().size() > 0;

                ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.LUNCH).get()
                        .addOnCompleteListener(lunchTask -> {
                           if(lunchTask.isSuccessful()) {
                               sLunchAvailable = lunchTask.getResult().getDocuments().size() > 0;

                               ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.DINNER).get()
                                       .addOnCompleteListener(dinnerTask -> {
                                           if (dinnerTask.isSuccessful()) {
                                               sDinnerAvailable = dinnerTask.getResult().getDocuments().size() > 0;
                                           }

                                           callback.giveMealsAvailable(sBreakfastAvailable, sLunchAvailable, sDinnerAvailable);
                                       });
                           }
                        });
            }
        });
    }


    public static void checkRegisteredMeals(String clientMail, String today, MealRegistered callback){
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        CollectionReference ref = instance.collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.REGISTERED_MEALS).document(today).collection(Keys.Collections.MEALS);


        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.BREAKFAST).get()
                .addOnCompleteListener(breakfastTask -> {
                    if(breakfastTask.isSuccessful()){
                        sBreakfastAvailable = breakfastTask.getResult().getDocuments().size() == 0;

                        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.LUNCH).get()
                                .addOnCompleteListener(lunchTask -> {
                                    if(lunchTask.isSuccessful()) {
                                        sLunchAvailable = lunchTask.getResult().getDocuments().size() == 0;

                                        ref.whereEqualTo(Meal.MealKeys.MEAL_TYPE, Meal.MealType.DINNER).get()
                                                .addOnCompleteListener(dinnerTask -> {
                                                    if (dinnerTask.isSuccessful()) {
                                                        sDinnerAvailable = dinnerTask.getResult().getDocuments().size() == 0;
                                                    }

                                                    callback.mealsRegistered(sBreakfastAvailable, sLunchAvailable, sDinnerAvailable);
                                                });
                                    }
                                });
                    }
                });
    }

}
