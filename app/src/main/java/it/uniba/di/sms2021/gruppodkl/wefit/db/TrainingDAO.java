package it.uniba.di.sms2021.gruppodkl.wefit.db;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class TrainingDAO {

    public interface ExercisesCallbacks{
        void onExercisesLoaded(List<String> exerciseNames);
        void onInformationLoaded(Exercise exercise);
    }



    private static List<String> sExerciseNames;
    private static Exercise sExercise;

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

    public static void addExerciseInTraining(String clientMail, String trainingId, Exercise exercise){
        DocumentReference ref = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING).document(trainingId).collection(Keys.Collections.EXERCISES)
                .document();

        exercise.setId(ref.getId());

        ref.set(exercise);
    }


    public static void updateTraining(String clientMail, Map<String,Object> map){
        String id = (String) map.get(Training.TrainingKeys.ID);

        if(id != null) {
            map.remove(Training.TrainingKeys.ID);


            FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                    .collection(Keys.Collections.TRAINING).document(id).update(map);
        }
    }

    public static void fetchAllExercises(ExercisesCallbacks callbacks){
        if(sExerciseNames != null)
            sExerciseNames.clear();
        else
            sExerciseNames = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.EXERCISES).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot documentSnapshot: task.getResult())
                            sExerciseNames.add(documentSnapshot.getString(Exercise.ExerciseKeys.NAME));

                        callbacks.onExercisesLoaded(sExerciseNames);
                    }
                });
    }

    public static void loadExerciseInformation(String exerciseName, ExercisesCallbacks callbacks){
        sExercise = null;

        FirebaseFirestore.getInstance().collection(Keys.Collections.EXERCISES).whereEqualTo(Exercise.ExerciseKeys.NAME, exerciseName)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        sExercise = task.getResult().toObjects(Exercise.class).get(0); //GLI ESERCIZI DOVREBBERO DIFFERIRE PER NOME, QUINDI PRENDO SOLO IL PRIMO
                        callbacks.onInformationLoaded(sExercise);
                    }
        });
    }


}
