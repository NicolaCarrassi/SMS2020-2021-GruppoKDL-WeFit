package it.uniba.di.sms2021.gruppokdl.wefit.db;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;

public class UserDAO {

    /**
     * Interfaccia contentente operazioni di callback relative al
     * caricamento e creazione utente
     */
    public interface UserCallbacks{
        /**
         * Il metodo permette di ottenere un riferimento all'utente
         * @param user istanza dell'utente ottenuta dal database
         *
         * @param success paramentro che indica il successo della operazione,
         *                true se l'operazione ha avuuto esito positivo,
         *                false altrimenti
         */
        void userLoaded(User user, boolean success);

        /**
         * Il metodo permette di notificare l'avvenuta creazione dell'utente
         * @param user istanza dell'utente appena creato
         *
         * @param success paramentro che indica il successo della operazione,
         *                true se l'operazione ha avuuto esito positivo,
         *                false altrimenti
         */
        void hasBeenCreated(User user, boolean success);
    }

    /**
     * Interfaccia contenente le operazioni di callback per i training
     */
    public interface TrainingLoaded{
        /**
         * Il metodo restituisce l'insieme degli allenamenti svolti dal cliente e quelli a
         * esso assegnati dal coach
         *
         * @param trainingMade nomi degli allenameni svolti
         * @param trainingAssigned nomi degli allenamenti assegnati
         */
        void trainingLoaded(List<String> trainingMade, List<String> trainingAssigned);
    }


    private static User sUser;
    private static boolean sResult;
    private static List<String> sTrainingMade;
    private static List<String> sTrainingAssigned;


    /**
     * Il metodo permette di ottenere una istanza dell'utente
     *
     * @param email email dell'utente di cui si vuole ottenere il riferiemnto
     * @param callback implementazione dell'interfaccia di callback necessaria per la risposta
     */
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


    /**
     * Il metodo permette di creare una istanza dell'utente
     *
     * @param user Utente che si vuole inserire nel db
     * @param callbacks implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void create(User user, UserCallbacks callbacks){

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(user.email).set(user)
                .addOnCompleteListener(task -> {
                    sResult= task.isSuccessful();

                    callbacks.hasBeenCreated(user, sResult);
                });
    }


    /**
     * Il metodo permette di modificare le informazioni dell'utente
     *
     * @param user Utente di cui si vuole effettuare l'update
     * @param map mappa delle informazioni da aggiornare
     */
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


    /**
     * Il metodo permette di inserire delle informazioni in una subcollection dell'utente
     *
     * @param userEmail mail dell'utente cui si vogliono aggiungere info
     * @param subCollection nome della subcollection
     * @param map mappa degli elementi da inserire
     */
    public static void addInSubCollection(String userEmail, String subCollection, Map<String,Object> map){
        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(userEmail)
                .collection(subCollection).add(map);
    }


    /**
     * Il metodo permette di ottenere le informazioni relative agli allenamenti del cliente
     *
     * @param clientMail mail del cliente di cui si vogliono ottenere le informazioni
     * @param callback  implementazione dell'interfaccia di callback necessaria per la risposta
     */
    public static void loadClientTrainingInformation(String clientMail, TrainingLoaded callback){

        if(sTrainingAssigned != null)
            sTrainingAssigned.clear();
        else
            sTrainingAssigned = new ArrayList<>();

        if(sTrainingMade != null)
            sTrainingMade.clear();
        else
            sTrainingMade = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String startDate = sdf.format(cal.getTime());

        DocumentReference instance = FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(clientMail);

        instance.collection(Keys.Collections.TRAINING).get()
                .addOnCompleteListener(completed -> {
                    if(completed.isSuccessful()) {
                        QuerySnapshot querySnapshot = completed.getResult();
                        for (DocumentSnapshot document : querySnapshot.getDocuments())
                            sTrainingAssigned.add(document.getString(Training.TrainingKeys.TITLE));

                        instance.collection(Keys.Collections.COMPLETED_TRAINING)
                                .whereGreaterThan(TrainingDAO.RegisterTrainingKeys.DATE, startDate)
                                .get().addOnCompleteListener(task -> {

                                    if (task.isSuccessful()) {
                                        QuerySnapshot snapshots = task.getResult();
                                        for (DocumentSnapshot document : snapshots.getDocuments())
                                            sTrainingMade.add(document.getString(TrainingDAO.RegisterTrainingKeys.TRAINING_NAME));

                                    callback.trainingLoaded(sTrainingMade, sTrainingAssigned);
                            }
                        });
                    }else
                        callback.trainingLoaded(sTrainingMade, sTrainingAssigned);
                    });
            }
}
