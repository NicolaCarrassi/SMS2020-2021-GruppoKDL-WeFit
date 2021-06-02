package it.uniba.di.sms2021.gruppodkl.wefit.db;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class CoachDAO extends UserDAO {

    private static List<Feedback> sFeedbackList;
    private static Feedback sFeedback;
    private static int sNumElement;
    private static float sRatingMean;



    public interface RatingCallbacks{
        void ratingMeanLoaded(float ratingMean);
        void feedbacksLoaded(List<Feedback> feedbackList);
        void lastFeedbackLoaded(Feedback feedback, float mean, int numElem);
    }

    public interface RequestCallbacks{
        void requestNumberLoaded(int numRequest);
    }




    public static void getFeedbackList(String coachEmail, RatingCallbacks callback){
        if(sFeedbackList != null)
            sFeedbackList.clear();
        else
            sFeedbackList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(coachEmail)
                .collection(Keys.Collections.RATINGS).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        assert task.getResult() != null;
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult())

                            sFeedbackList.add(documentSnapshot.toObject(Feedback.class));
                    }

                    callback.feedbacksLoaded(sFeedbackList);
                });
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
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            sNumElement++;
                        }
                        callback.requestNumberLoaded(sNumElement);
                    }
                });
    }
    public static Query queryAllRequests(String coachMail) {
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(coachMail).collection(Keys.Collections.REQUESTS);
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



}
