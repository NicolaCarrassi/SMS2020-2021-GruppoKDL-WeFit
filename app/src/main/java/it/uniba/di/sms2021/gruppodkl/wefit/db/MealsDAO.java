package it.uniba.di.sms2021.gruppodkl.wefit.db;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class MealsDAO {

    public interface MealsLoaded{
        void onMealsLoaded(List<String> meals);
    }


    private static List<String> sMealsList;


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


}
