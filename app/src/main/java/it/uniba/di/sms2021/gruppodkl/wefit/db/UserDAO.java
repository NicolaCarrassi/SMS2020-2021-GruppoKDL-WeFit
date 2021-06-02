package it.uniba.di.sms2021.gruppodkl.wefit.db;


import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class UserDAO {

    public interface UserCallbacks{
        void userLoaded(User user, boolean success);
        void hasBeenCreated(User user, boolean success);
    }


    private static User sUser;
    private static boolean sResult;


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
        Map<String,Object> requestToCoachUpdate = new HashMap<>();
        WriteBatch batch = FirebaseFirestore.getInstance().batch();

        DocumentReference mandatoryUpdate =
                FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(user.email);

        DocumentReference requestUpdate = null;


        if(user instanceof Client && ((Client) user).pendingRequests){
            Client client = (Client) user;

            requestToCoachUpdate.put(Client.ClientKeys.FULL_NAME, client.fullName);
            requestToCoachUpdate.put(Client.ClientKeys.IMAGE, client.image);

            requestUpdate = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(client.coach)
                    .collection(Keys.Collections.REQUESTS).document(client.email);
        }

        batch.update(mandatoryUpdate, map);

        if(requestUpdate != null){
            batch.update(requestUpdate, requestToCoachUpdate);
        }


        batch.commit();
    }


    public static void addInSubCollection(String userEmail, String subCollection, Map<String,Object> map){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(userEmail)
                .collection(subCollection).add(map);
    }



}