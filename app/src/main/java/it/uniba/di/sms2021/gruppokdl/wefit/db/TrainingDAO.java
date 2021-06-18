package it.uniba.di.sms2021.gruppokdl.wefit.db;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;

public class TrainingDAO {

    public interface RegisterTrainingKeys{
        String TRAINING_NAME = "trainingName";
        String DATE = "date";
    }


    /**
     * Interfaccia contenente i metodi di callback per le
     * operazioni relative agli esercizi
     */
    public interface ExercisesCallbacks{
        /**
         * Il metodo permette di gestire l'ottenimento degli esercizi
         * @param exerciseNames Lista contenente i nomi degli esercizi
         */
        void onExercisesLoaded(List<String> exerciseNames);
        /**
         *  Il metodo permette di gestire le informazioni di un esercizio
         * @param exercise esercizio
         */
        void onInformationLoaded(Exercise exercise);
    }

    /**
     * Interfaccia che permette di gestire le operazioni di callback
     * dell'allenamento
     */
    public interface TrainingCallbacks{
        /**
         * Il metodo permette di ottenere la lista degli allenamenti
         * @param trainingNames lista degli allenamenti
         */
        void onTrainingListLoaded(List<String> trainingNames);
    }



    private static List<String> sList;
    private static Exercise sExercise;


    /**
     * La query permette di ottenere la scheda di allenamento di un dato cliente
     * @param clientMail mail del cliente di cui si intendono conoscere le informazioni
     * @return query
     */
    public static Query getClientTrainingSchedule(String clientMail){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING);
    }

    /**
     * Il metodo permette di ottenere la query che permette di ottenere tutti gli esercizi di un dato
     * allenamento
     *
     * @param clientMail mail del cliente
     * @param trainingId id dell'allenamento
     * @return query
     */
    public static Query queryGetAllTrainingExercises(String clientMail, String trainingId){
        return FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING).document(trainingId).collection(Keys.Collections.EXERCISES);
    }


    /**
     * Il metodo permette di aggiungere un allenamento ad un dato cliente
     *
     * @param clientMail mail del cliente
     * @param training allenamento da inserire
     */
    public static void addTraining(String clientMail, Training training){
        DocumentReference reference = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                .document(clientMail).collection(Keys.Collections.TRAINING).document();

        //IMPOSTO ID UNIVOCO PER IL TRAINING AL MOMENTO DELLA CREAZIONE
        training.setId(reference.getId());

        reference.set(training);
    }


    /**
     * Il metodo permette di cancellare un allenamento
     * @param client mail del cliente
     * @param trainingId id del training da eliminare
     */
    public static void deleteTraining(String client, String trainingId){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(client)
                .collection(Keys.Collections.TRAINING).document(trainingId).delete();
    }


    /**
     * Il metodo permette di eliminare un esercizio da un dato allenamento
     * @param clientMail mail del cliente
     * @param trainingId id dell'allenamento
     * @param exerciseId id dell'esercizio
     */
    public static void deleteExercise(String clientMail, String trainingId, String exerciseId){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING).document(trainingId).collection(Keys.Collections.EXERCISES)
                .document(exerciseId).delete();
    }

    /**
     * Il metodo permette di inserire un esercizio in un dato allenamento
     *
     * @param clientMail mail del cliente
     * @param trainingId id dell'allenamento
     * @param exercise id dell'esercizio
     */
    public static void addExerciseInTraining(String clientMail, String trainingId, Exercise exercise){
        DocumentReference ref = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING).document(trainingId).collection(Keys.Collections.EXERCISES)
                .document();

        exercise.setId(ref.getId());

        ref.set(exercise);
    }


    /**
     * Il metodo permette di modificare le informazioni di un allenamento
     * @param clientMail mail del cliente
     * @param map mappa contenente i valori dell'allenamento
     */
    public static void updateTraining(String clientMail, Map<String,Object> map){
        String id = (String) map.get(Training.TrainingKeys.ID);

        if(id != null) {
            map.remove(Training.TrainingKeys.ID);


            FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                    .collection(Keys.Collections.TRAINING).document(id).update(map);
        }
    }

    /**
     * Il metodo permette di ottenere tutti gli esercizi
     * @param callbacks implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void fetchAllExercises(ExercisesCallbacks callbacks){
        if(sList != null)
            sList.clear();
        else
            sList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.EXERCISES).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot documentSnapshot: task.getResult())
                            sList.add(documentSnapshot.getString(Exercise.ExerciseKeys.NAME));

                        callbacks.onExercisesLoaded(sList);
                    }
                });
    }

    /**
     * Il metodo permette di caricare le informazioni di un determinato esercizio, dato il suo nome
     * @param exerciseName nome dell'esercizio
     * @param callbacks implementazione dell'interfaccia di callback necessaria per la risposta
     */
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


    /**
     * Il metodo permette di ottenere i mnomi di tutti gli allenamenit
     *
     * @param clientMail mail del cliente
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void getAllTrainingNames(String clientMail, TrainingCallbacks callback){
        if(sList != null)
            sList.clear();
        else
            sList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.TRAINING).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();
                        for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments())
                            sList.add(documentSnapshot.getString(Training.TrainingKeys.TITLE));

                        callback.onTrainingListLoaded(sList);
                    }
                });
    }


    /**
     * Il metodo permette di registrare l'avvenuta esecuzione di un allenamento
     *
     * @param clientMail mail del cliente
     * @param map mappa di valori per la registrazione dell'allenamento
     */
    public static void registerTrainingComplete(String clientMail, Map<String, Object> map){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail)
                .collection(Keys.Collections.COMPLETED_TRAINING).add(map);
    }


}
