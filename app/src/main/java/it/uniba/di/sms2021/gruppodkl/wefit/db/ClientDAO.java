package it.uniba.di.sms2021.gruppodkl.wefit.db;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientDAO extends UserDAO {

    public interface ClientDAOCallbacks{
        void requestSent(boolean isSuccessful);
    }

    private static boolean sSuccess;

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


    public static Query listAllCoach(){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).whereEqualTo(User.UserKeys.ROLE, Keys.Role.COACH);
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

    public static Query queryTraining(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.TRAINING);
    }

    public static Query getAllDishesOfTheMeal(String clientMail, String dayOftheWeek, int mealType){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.DIET).document(dayOftheWeek).collection(Keys.Collections.MEALS)
                .whereEqualTo(Meal.MealKeys.MEAL_TYPE, mealType);
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

}
