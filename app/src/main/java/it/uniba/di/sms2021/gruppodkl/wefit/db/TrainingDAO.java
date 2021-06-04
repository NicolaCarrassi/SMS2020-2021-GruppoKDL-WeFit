package it.uniba.di.sms2021.gruppodkl.wefit.db;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class TrainingDAO {

    public static Query getClientTrainingSchedule(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING);
    }
}
