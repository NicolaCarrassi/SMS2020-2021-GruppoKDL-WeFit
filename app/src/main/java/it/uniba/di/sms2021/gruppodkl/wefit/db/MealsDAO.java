package it.uniba.di.sms2021.gruppodkl.wefit.db;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
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


    private static List<String> sMealsList;
    private static Map<String, Integer> sMealsMap;


    public static void loadAll(MealsLoaded callback){
        if(sMealsList != null)
            sMealsList.clear();
        else
            sMealsList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.MEALS).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot  snapshot = task.getResult();
                        for(DocumentSnapshot doc : snapshot.getDocuments())
                            sMealsList.add(doc.getString(Meal.MealKeys.NAME));

                        callback.onMealsLoaded(sMealsList);
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


}
