package it.uniba.di.sms2021.gruppodkl.wefit.db;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientDAO extends UserDAO {


    public interface ClientDAOCallbacks{
        void requestSent(boolean isSuccessful);
    }
    public interface WeightsLoaded{
        void onWeightsLoaded(List<Float> weightList, List<String> dateList);
    }

    public interface RunLoaded{
        void onLastRunLoaded(Run run);
    }

    private static boolean sSuccess;
    private static List<Float> sWeightList;
    private static List<String> sDateList;
    private static Run sLastRun;

    public static void requestToCoach(Client client, Coach coach, Map<String, Object> requestsElement, ClientDAOCallbacks callback){

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(coach.email)
                .collection(Keys.Collections.REQUESTS).document(client.email).set(requestsElement)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        client.pendingRequests = true;
                        client.coach = coach.email;
                        Map<String, Object> map = new HashMap<>();
                        map.put(Client.ClientKeys.COACH, coach.email);
                        map.put(Client.ClientKeys.HAS_PENDING_REQUESTS, true);
                        UserDAO.update(client, map);
                    }
                    sSuccess = task.isSuccessful();
                    callback.requestSent(sSuccess);
                });
    }

    public static void deleteRequestToCoach(String clientMail, String coachMail){

        FirebaseFirestore firebaseInstance = FirebaseFirestore.getInstance();

        WriteBatch batch = firebaseInstance.batch();

        DocumentReference deleteRequest = firebaseInstance.collection(Keys.Collections.USERS).document(coachMail)
                .collection(Keys.Collections.REQUESTS).document(clientMail);


        DocumentReference updateRequest = firebaseInstance.collection(Keys.Collections.USERS).document(clientMail);

        //valori da aggiornare nel cliente
        Map<String, Object> map = new HashMap<>();
        map.put(Client.ClientKeys.COACH, null);
        map.put(Client.ClientKeys.HAS_PENDING_REQUESTS, false);

        batch.delete(deleteRequest);
        batch.update(updateRequest, map);

        batch.commit();
    }


    public static void addMeal(String clientMail, String dayOfTheWeek, Meal meal){
        DocumentReference ref = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.DIET).document(dayOfTheWeek).collection(Keys.Collections.MEALS).document();

        meal.setId(ref.getId());

        ref.set(meal);
    }

    public static void removeMeal(String clientMail, String dayOfTheWeek, String mealID){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.DIET).document(dayOfTheWeek).collection(Keys.Collections.MEALS)
                .document(mealID).delete();
    }

    public static void getAllWeights(String clientMail, WeightsLoaded callback){
        if(sWeightList != null)
            sWeightList.clear();
        else
            sWeightList = new ArrayList<>();
        if(sDateList != null)
            sDateList.clear();
        else
            sDateList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.WEIGHT).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot qs = task.getResult();
                        if(qs != null && !qs.isEmpty()){
                            List<DocumentSnapshot> documents = qs.getDocuments();
                            for(DocumentSnapshot document : documents){
                                float weight = document.getDouble(Client.ClientKeys.WEIGHT).floatValue();
                                sWeightList.add(weight);
                                sDateList.add(document.getString(Client.ClientKeys.WEIGHT_DATE));
                            }
                            callback.onWeightsLoaded(sWeightList, sDateList);
                        }
                    }
                });
    }


    public static void getLastRun(String clientMail, RunLoaded callback){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.RUNS).orderBy(Run.RunKeys.DATE).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        sLastRun = task.getResult().getDocuments().get(0).toObject(Run.class);
                        callback.onLastRunLoaded(sLastRun);
                    }
                });
    }

    public static void saveRun(String clientMail, Run run){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.RUNS).document().set(run);
    }

    public static Query queryTraining(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.TRAINING);
    }

    public static Query getAllDishesOfTheMeal(String clientMail, String dayOftheWeek, int mealType){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.DIET).document(dayOftheWeek).collection(Keys.Collections.MEALS)
                .whereEqualTo(Meal.MealKeys.MEAL_TYPE, mealType);
    }


    public static Query getRegisteredMealsOfTheDay(String clientMail, String day, int mealType) {
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.REGISTERED_MEALS).document(day).collection(Keys.Collections.MEALS)
                .whereEqualTo(Meal.MealKeys.MEAL_TYPE, mealType);
    }


    public static Query listAllCoach(){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).whereEqualTo(User.UserKeys.ROLE, Keys.Role.COACH);
    }

    public static Query queryAllRun(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail).collection(Keys.Collections.RUNS);
    }


}
