package it.uniba.di.sms2021.gruppodkl.wefit.db;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class UserDb {

    public interface UserCallbacks{
        void userLoaded(User user, boolean success);
        void hasBeenCreated(User user, boolean success);
    }

    public interface RatingsCallbacks{
        void ratingMeanLoaded(float ratingMean);
    }





    private static User sUser;
    private static boolean sResult;
    private static float sRatingMean;
    private static  int sNumElement;


    public static void getUser(String email, UserCallbacks callback){
        sUser = null;

        DocumentReference usersRef = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(email);

        usersRef.get().addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        boolean result = false;

                        if(documentSnapshot != null && documentSnapshot.exists()){

                            if(documentSnapshot.contains(User.UserKeys.ROLE) &&
                                    documentSnapshot.getString(User.UserKeys.ROLE) != null &&
                                    documentSnapshot.getString(User.UserKeys.ROLE).equals(Keys.Role.CLIENT))

                                sUser = documentSnapshot.toObject(Client.class);

                            else
                                sUser = documentSnapshot.toObject(Coach.class);

                            result = true;

                            if(documentSnapshot.contains(User.UserKeys.IMAGE) && documentSnapshot.getString(User.UserKeys.IMAGE) != null) {
                                sUser.setImage(documentSnapshot.getString(User.UserKeys.IMAGE));
                                sUser.setImageBitmap(null);
                            }
                        }
                        callback.userLoaded(sUser, result);
                    }
                });
    }


    public static void create(User user, UserCallbacks callbacks){

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(user.email).set(user)
                .addOnCompleteListener(task -> {
                    sResult= task.isSuccessful();

                    callbacks.hasBeenCreated(user, sResult);
                });
    }


    public static void update(User user, Map<String, Object> map){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(user.email).update(map);
    }


    public static void addInSubCollection(String userEmail, String subCollection, Map<String,Object> map){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(userEmail)
                .collection(subCollection).add(map);
    }


    public static void getCoachRatingStars(Coach coach, RatingsCallbacks callbacks){
        sRatingMean = 0;
        sNumElement = 0;

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(coach.email)
                .collection(Keys.Collections.RATINGS).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
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

}
