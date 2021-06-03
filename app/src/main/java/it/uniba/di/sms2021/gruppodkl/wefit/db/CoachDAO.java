package it.uniba.di.sms2021.gruppodkl.wefit.db;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class CoachDAO extends UserDAO {


    private static Feedback sFeedback;
    private static int sNumElement;
    private static float sRatingMean;
    private static Client sClient;
    private static List<Float> sClientWeightList;



    public interface RatingCallbacks{
        void ratingMeanLoaded(float ratingMean);
        void lastFeedbackLoaded(Feedback feedback, float mean, int numElem);
    }

    public interface RequestCallbacks{
        void requestNumberLoaded(int numRequest);
    }

    public interface ClientCallbacks{

        void failure();
        void success(Client client, List<Float> weightList);
    }


    public static void getLastFeedback(String coachEmail, RatingCallbacks callbacks){
        sFeedback = null;
        sNumElement = 0;
        sRatingMean = 0;

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(coachEmail).collection(Keys.Collections.RATINGS).get()
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
    }


    public static void getAllClientInfo(String clientMail,ClientCallbacks callback){
        //inizializzo le variabili statiche
        sClient = null;

        if(sClientWeightList != null)
            sClientWeightList.clear();
        else
            sClientWeightList = new ArrayList<>();

        FirebaseFirestore instance = FirebaseFirestore.getInstance();

        instance.collection(Keys.Collections.USERS).document(clientMail).get().addOnCompleteListener(clientTask -> {
            if(clientTask.isComplete()) {
                sClient = clientTask.getResult().toObject(Client.class);
                instance.collection(Keys.Collections.USERS).document(clientMail).collection(Keys.Collections.WEIGHT)
                        .get().addOnCompleteListener(weightTask -> {
                            if(weightTask.isComplete()){
                                for (DocumentSnapshot documentSnapshot : weightTask.getResult()) {
                                    float weight = documentSnapshot.getDouble(Client.ClientKeys.WEIGHT).floatValue();
                                    sClientWeightList.add(weight);
                                }
                                callback.success(sClient, sClientWeightList);
                            } else
                                callback.failure();
                        });
            } else
                callback.failure();
        });
    }


    public static Query queryAllRequests(String coachMail) {
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(coachMail).collection(Keys.Collections.REQUESTS);
    }


    public static Query queryClentsList(String coachMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .whereEqualTo(Client.ClientKeys.COACH, coachMail)
                .whereEqualTo(Client.ClientKeys.HAS_PENDING_REQUESTS, false);
    }


    public static Query queryFeedbackList(String coachMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(coachMail).collection(Keys.Collections.RATINGS);
    }


}
