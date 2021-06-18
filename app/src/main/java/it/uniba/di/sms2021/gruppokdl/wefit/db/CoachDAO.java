package it.uniba.di.sms2021.gruppokdl.wefit.db;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Request;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;

public class CoachDAO extends UserDAO {


    private static Feedback sFeedback;
    private static int sNumElement;
    private static float sRatingMean;
    private static Client sClient;
    private static List<Float> sClientWeightList;
    private static List<String> sDateList;


    /**
     * Interfaccia che permette di gestire le risposte relative
     * alla gestione dei feebdack
     */
    public interface RatingCallbacks{
        /**
         * Il metodo permette di notificare la valutazione media
         * @param ratingMean valutazione media
         */
        void ratingMeanLoaded(float ratingMean);

        /**
         * Il metodo permette di fornire l'ultimo feebdack ricevuto
         * @param feedback feedback
         * @param mean media dei feedback
         * @param numElem numero di feedback ricevuti
         */
        void lastFeedbackLoaded(Feedback feedback, float mean, int numElem);
    }

    /**
     * Interfaccia che permette di notificare le operazioni relative alla richiesta
     */
    public interface RequestCallbacks{
        /**
         * Metodo che pemrette di registrare il numero di richieste ricevute
         * @param numRequest numero di richieste
         */
        void requestNumberLoaded(int numRequest);
    }

    /**
     * Interfaccia che permette di notificare le risposte alle operazioni relative ai clienti
     */
    public interface ClientCallbacks{
        /**
         * Il metodo permette di notificare eventuali errori avvenuti
         */
        void failure();

        /**
         * IL metodo permette di ottenere le informazioni della pagina riepilogativa del cliente,
         * @param client cliente
         * @param weightList lista dei pesi del cliente
         * @param dateList lista delle date in cui il cliente ha inserito un peso
         */
        void success(Client client, List<Float> weightList, List<String> dateList);
    }


    /**
     * Il metodo permette di ottenere l'ultimo feedback ricevuto
     * @param coachEmail mail del coach
     * @param callbacks implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void getLastFeedback(String coachEmail, RatingCallbacks callbacks){
        sFeedback = null;
        sNumElement = 0;
        sRatingMean = 0;

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(coachEmail).collection(Keys.Collections.RATINGS).orderBy(Keys.RatingInfo.DATE).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        assert task.getResult() != null;
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            double temp = documentSnapshot.getDouble(Keys.RatingInfo.RATE);
                            sRatingMean += (float) temp;
                            sNumElement++;
                        }
                        if(sNumElement > 0) {
                            sFeedback = task.getResult().getDocuments().get(task.getResult().size() - 1).toObject(Feedback.class);
                            sRatingMean = sRatingMean / sNumElement;
                        }
                        callbacks.lastFeedbackLoaded(sFeedback, sRatingMean,sNumElement);
                    }
                });
    }


    /**
     * Il metodo permette di ottenere la valutazione media di un dato coach
     * @param coach coach di cui si vuole conoscere la valutazione
     * @param callbacks implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void getCoachRatingStars(Coach coach, RatingCallbacks callbacks){
        sRatingMean = 0;
        sNumElement = 0;

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(coach.email)
                .collection(Keys.Collections.RATINGS).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        assert task.getResult() != null;
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            if(documentSnapshot.contains(Keys.RatingInfo.RATE) && documentSnapshot.getDouble(Keys.RatingInfo.RATE) != null) {
                                double temp = documentSnapshot.getDouble(Keys.RatingInfo.RATE);
                                sRatingMean += (float) temp;
                                sNumElement++;
                            }
                        }
                        if(sNumElement != 0)
                            callbacks.ratingMeanLoaded(sRatingMean/sNumElement);
                        else
                            callbacks.ratingMeanLoaded(0);
                    }
                });
    }

    /**
     * Il metodo permette di ottenere il numero di richieste ricevute da un coach
     * @param coachEmail mail del coach
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void getRequestNumber(String coachEmail, RequestCallbacks callback){
        sNumElement=0;
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(coachEmail)
                .collection(Keys.Collections.REQUESTS).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        assert task.getResult() !=null;
                        sNumElement = task.getResult().getDocuments().size();
                        callback.requestNumberLoaded(sNumElement);
                    }
                });
    }


    /**
     * Il metodo permette di gestire la risposta alle richieste dei clienti di essere seguiti
     * da un coach
     *
     * @param coach coach che risponde alla richiesta
     * @param request richiesta per il coach
     * @param accepted indica l'accettazione o meno della richiesta, true indica che il coach
     *                 intende seguire il cliente della richiesta, false indica che la richiesta
     *                 è rifiutata.
     */
    public static void handleRequest(Coach coach, Request request, boolean accepted){

        CollectionReference collection = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS);

        Map<String, Object> map = new HashMap<>();
        if(accepted)
            map.put(Client.ClientKeys.COACH, coach.email);
        else
            map.put(Client.ClientKeys.COACH, null);

        map.put(Client.ClientKeys.HAS_PENDING_REQUESTS, false);

        WriteBatch batch = FirebaseFirestore.getInstance().batch();

        DocumentReference removeRequest = collection.document(coach.email).collection(Keys.Collections.REQUESTS).document(request.email);

        DocumentReference updateClientInfo = collection.document(request.email);

        batch.delete(removeRequest);
        batch.update(updateClientInfo, map);

        batch.commit();

        //in caso di richiesta accettata verrà inviata la notifica al cliente
        if(accepted){
            Map<String, Object> notificationMap = new HashMap<>();
            notificationMap.put("coachName", coach.fullName);
            notificationMap.put("clientMail", request.email);

            FirebaseFunctions.getInstance().getHttpsCallable("acceptedRequest").call(notificationMap);
        }
    }

    /**
     * Il metodo permette di ottenere tutte le informazioni dei clienti di un dato coach
     *
     * @param clientMail mail del coach
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void getAllClientInfo(String clientMail,ClientCallbacks callback){
        //inizializzo le variabili statiche
        sClient = null;

        if(sClientWeightList != null)
            sClientWeightList.clear();
        else
            sClientWeightList = new ArrayList<>();

        if(sDateList != null)
            sDateList.clear();
        else
            sDateList = new ArrayList<>();


        FirebaseFirestore instance = FirebaseFirestore.getInstance();

        instance.collection(Keys.Collections.USERS).document(clientMail).get().addOnCompleteListener(clientTask -> {
            if(clientTask.isComplete()) {
                sClient = clientTask.getResult().toObject(Client.class);
                instance.collection(Keys.Collections.USERS).document(clientMail).collection(Keys.Collections.WEIGHT)
                        .orderBy(Client.ClientKeys.WEIGHT_DATE).get().addOnCompleteListener(weightTask -> {
                            if(weightTask.isComplete()){
                                for (DocumentSnapshot documentSnapshot : weightTask.getResult()) {
                                    float weight = documentSnapshot.getDouble(Client.ClientKeys.WEIGHT).floatValue();
                                    sClientWeightList.add(weight);
                                    sDateList.add(documentSnapshot.getString(Client.ClientKeys.WEIGHT_DATE));
                                }
                                callback.success(sClient, sClientWeightList,sDateList);
                            } else
                                callback.failure();
                        });
            } else
                callback.failure();
        });
    }


    /**
     * Il seguente metdoo permette di ottenere la query per ottenere tutte le richieste ricevute
     * da un dato coach
     * @param coachMail mail del coach
     *
     * @return query
     */
    public static Query queryAllRequests(String coachMail) {
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(coachMail).collection(Keys.Collections.REQUESTS);
    }


    /**
     * Il metodo permette di ottenere la lista dei clienti di un dato coach
     *
     * @param coachMail mail del coach
     * @return query
     */
    public static Query queryClentsList(String coachMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .whereEqualTo(Client.ClientKeys.COACH, coachMail)
                .whereEqualTo(Client.ClientKeys.HAS_PENDING_REQUESTS, false);
    }


    /**
     * Il seguente metodo permette di ottenere la query per la lista dei feedback ricevuti da un dato
     * coach
     * @param coachMail mail del coach
     * @return query
     */
    public static Query queryFeedbackList(String coachMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(coachMail).collection(Keys.Collections.RATINGS);
    }


}
