package it.uniba.di.sms2021.gruppodkl.wefit.db;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class TrainingDAO {


    public static Query getClientTrainingSchedule(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING);
    }

    public static Query queryGetAllTrainingExercises(String clientMail, String trainingId){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING).document(trainingId).collection(Keys.Collections.EXERCISES);
    }


    public static void addTraining(String clientMail, Training training){
        DocumentReference reference = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.TRAINING).document();

        //IMPOSTO ID UNIVOCO PER IL TRAINING AL MOMENTO DELLA CREAZIONE
        training.setId(reference.getId());

        reference.set(training);
    }

    public static void deleteTraining(String client, String trainingId){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(client)
                .collection(Keys.Collections.TRAINING).document(trainingId).delete();
    }


    public static void deleteExercise(String clientMail, String trainingId, String exerciseId){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING).document(trainingId).collection(Keys.Collections.EXERCISES)
                .document(exerciseId).delete();
    }


}
