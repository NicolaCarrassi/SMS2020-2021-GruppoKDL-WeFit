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


    /**
     * La seguente interfaccia contiene i metodi di callback relativi
     * alle risposte successive all'invio di richieste
     */
    public interface ClientDAOCallbacks{
        /**
         * Il metodo contiene la risposta all'invio di una richiesta ad un coach
         *
         * @param isSuccessful il parametro indica il corretto invio della richiesta.
         *                     true in caso di richiesta inviata con successo , false altrimenti
         */
        void requestSent(boolean isSuccessful);
    }

    /**
     * La seguente interfaccia contiene i metodi di callback per l'ottenimento dei pesi
     * inseriti dal cliente
     */
    public interface WeightsLoaded{
        /**
         * Il seguente metodo contiene la risposta alla richiesta di ottenere tutti i pesi
         * insertiti dal cliente
         *
         * @param weightList lista dei pesi
         * @param dateList lista di date in cui sono stati inseriti i pesi
         */
        void onWeightsLoaded(List<Float> weightList, List<String> dateList);
    }

    /**
     * La seguente interfaccia contiene i metodi relativi alla risposta per la richiesta di
     * ottenere lo storico delle corse di un cliente
     */
    public interface RunLoaded{
        /**
         * Il seguente metodo permette di ottenere i dati relativi all'ultima corsa
         * effettuata dall'utente
         *
         * @param run ultima corsa effettuata
         */
        void onLastRunLoaded(Run run);
    }

    private static boolean sSuccess;
    private static List<Float> sWeightList;
    private static List<String> sDateList;
    private static Run sLastRun;

    /**
     * Il seguente metodo permette di inviare una richiesta di iscrizione ad un coach
     *
     * @param client cilente che intende effettuare la richiesta
     * @param coach coach a vui si intende effettuare una richiesta
     * @param requestsElement mappa contenente gli elementi costituenti di una richiesta ad un coach
     * @param callback implementazione della classe di callback
     */
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

    /**
     * Il seguente metodo permette di annullare una richiesta effettuata ad un coach
     * @param clientMail mail del cliente che ha effettuato la richiesta
     * @param coachMail mail del coach cui si intende annullare la richiesta
     */
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

    /**
     * Il seguente metodo permette di registrare un nuovo pasto nella dieta del cliente
     *
     * @param clientMail mail del cliente che intende registrare un pasto
     * @param dayOfTheWeek giorno della settimana in cui il pasto viene programmato
     * @param meal pasto consumato
     */
    public static void addMeal(String clientMail, String dayOfTheWeek, Meal meal){
        DocumentReference ref = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.DIET).document(dayOfTheWeek).collection(Keys.Collections.MEALS).document();

        meal.setId(ref.getId());

        ref.set(meal);
    }

    /**
     * Il seguente metodo permette di cancellare un pasto dalla dieta di un cliente
     *
     * @param clientMail mail del cliente
     * @param dayOfTheWeek giorno della settimana in cui il pasto è programmato
     * @param mealID codice identificativo del pasto nella dieta del cliente
     */
    public static void removeMeal(String clientMail, String dayOfTheWeek, String mealID){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.DIET).document(dayOfTheWeek).collection(Keys.Collections.MEALS)
                .document(mealID).delete();
    }

    /**
     * Il seguente metodo permette di ottenere la lista di tutti i pesi registrati da un dato cliente
     *
     * @param clientMail mail del cliente di cui si vogliono conoscere tutti i pesi registrati
     * @param callback implementazione della classe di callback
     */
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
                .collection(Keys.Collections.WEIGHT).orderBy(Client.ClientKeys.WEIGHT_DATE).get()
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


    /**
     * Il seguente metodo permette di ottenere le informazioni dell'ultima corsa effettuata da un dato cliente
     *
     * @param clientMail mail del cliente
     * @param callback implementazione della classe di callback
     */
    public static void getLastRun(String clientMail, RunLoaded callback){
        sLastRun=null;
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.RUNS).orderBy(Run.RunKeys.DATE).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()&&task.getResult().getDocuments().size()>0){
                        sLastRun = task.getResult().getDocuments().get(task.getResult().getDocuments().size() -1).toObject(Run.class);
                        callback.onLastRunLoaded(sLastRun);
                    }else{
                        callback.onLastRunLoaded(null);
                    }
                });
    }

    /**
     * Il seguente metodo permette di registrare una nuova corsa
     *
     * @param clientMail mail del cliente
     * @param run corsa da registrare
     */
    public static void saveRun(String clientMail, Run run){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.RUNS).document().set(run);
    }

    /**
     * Il seguente metodo permette di ottenere la query per ottenere
     * tutti gli allenamenti di un cliente
     *
     * @param clientMail mail del cliente
     * @return query
     */
    public static Query queryTraining(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.TRAINING);
    }

    /**
     * Il seguente metodo permette di ottenere la query per ottenere
     * tutti gli alimenti che compongono un pasto di un cliente in un dato giorno della settimana
     *
     * @param clientMail mail del cliente
     * @param dayOftheWeek giorno della settimana
     * @param mealType tipologia di pasto (0 = COLAZIONE; 2= PRANZO, 4= CENA)
     * @return query
     */
    public static Query getAllDishesOfTheMeal(String clientMail, String dayOftheWeek, int mealType){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.DIET).document(dayOftheWeek).collection(Keys.Collections.MEALS)
                .whereEqualTo(Meal.MealKeys.MEAL_TYPE, mealType);
    }


    /**
     * Il seguente metodo consente di ottenere la lista dei pasti consumati in una data giornata
     *
     * @param clientMail mail del cliente
     * @param day data
     * @param mealType tipo di pasto
     * @return query
     */
    public static Query getRegisteredMealsOfTheDay(String clientMail, String day, int mealType) {
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.REGISTERED_MEALS).document(day).collection(Keys.Collections.MEALS)
                .whereEqualTo(Meal.MealKeys.MEAL_TYPE, mealType);
    }

    /**
     * Il seuguente metodo permette di ottenere tutti i coach della piattaforma
     * @return query
     */
    public static Query listAllCoach(){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .whereEqualTo(User.UserKeys.ROLE, Keys.Role.COACH);
    }

    /**
     *
     * Il seguente metodo permette di ottenere tutte le corse effettuate da un dato cliente
     *
     * @param clientMail mail del cliente
     * @return query
     */
    public static Query queryAllRun(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.RUNS);
    }


}
