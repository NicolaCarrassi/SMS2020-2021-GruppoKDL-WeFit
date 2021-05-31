package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ObjectStreamException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDb;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class RegistrationActivityPresenter implements RegistrationActivityContract.Presenter {

    private final  RegistrationActivityContract.View mView;
    private final UserDb.UserCallbacks mCallbacks;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();


    /**
     * Costruttore del presenter
     *
     * @param view La view che implementa i metodi del contract
     */
    public RegistrationActivityPresenter(RegistrationActivityContract.View view){
        this.mView = view;

        mCallbacks = new UserDb.UserCallbacks() {
            @Override
            public void userLoaded(User user, boolean success) {
                // OPERAZIONE NON GESTITA
            }

            @Override
            public void hasBeenCreated(User user, boolean success) {
                if(user instanceof Client){
                    if(success) {
                        createClientSubCollections((Client) user);
                        mView.onSuccess(user);
                    }else
                        mView.onFailure();
                    //coach
                } else
                    if(success){
                        loadCertification((Coach) user);
                        mView.onSuccess(user);
                    }else
                        mView.onFailure();
            }
        };

    }


    @Override
    public void registerUser(Map<String, String> userData, Map<String, String> specificData) {
       createAuthenticationData(userData,specificData);

    }

    /**
     * Il metodo permette di creare i dati per l'autenticazione dell'utente
     *
     * @param userData dati comuni a tutti gli utenti
     * @param specificData dati specifici della tipologia dell'utennte
     */
    private void createAuthenticationData(Map<String,String> userData, Map<String,String> specificData){
        String email = userData.get(Keys.RegistrationKeys.EMAIL);
        String password = userData.get(Keys.RegistrationKeys.PASSWORD);

        assert email != null;
        assert password != null;
        email = email.toLowerCase();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        createUser(userData,specificData);
                    } else {
                        mView.onFailure();
                    }
                });
    }

    /**
     * Il metodo permette di creare una istanza di un utente
     *
     * @param userData dati comuni all'utente in modo indipendente dal fatto che sia un client o un coach
     * @param specificData dati specifici alla tipologia dell'utente
     */
    private void createUser(Map<String, String> userData, Map<String, String> specificData){
        String firstName = userData.get(Keys.RegistrationKeys.FIRST_NAME);
        String lastName = userData.get(Keys.RegistrationKeys.LAST_NAME);

        assert firstName != null;
        assert lastName != null;

        firstName = firstName.trim();
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.trim();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

        String fullName = firstName + " " + lastName;
        String email = userData.get(Keys.RegistrationKeys.EMAIL);
        String birthDate = userData.get(Keys.RegistrationKeys.BIRTH_DATE);
        String role = userData.get(Keys.RegistrationKeys.ROLE);
        String gender = userData.get(Keys.RegistrationKeys.GENDER);

        //Dati addizionali
        assert role != null;
        if(role.equals(Keys.Role.CLIENT)){
            float weight = Float.parseFloat(Objects.requireNonNull(specificData.get(Keys.ClientRegistrationKeys.WEIGHT)));
            int height = Integer.parseInt(Objects.requireNonNull(specificData.get(Keys.ClientRegistrationKeys.HEIGHT)));
            String objective = specificData.get(Keys.ClientRegistrationKeys.OBJECTIVE);

            Client client = new Client(fullName, email, birthDate, gender,role,height,weight,objective);

            UserDb.create(client, mCallbacks);

        }else {
           boolean isPersonalTrainer = false;
           boolean isDietist = false;
           String certificationUri = null;

            if(specificData.containsKey(Keys.CoachRegistrationKeys.IS_PERSONAL_TRAINER))
                isPersonalTrainer = true;

           if (specificData.containsKey(Keys.CoachRegistrationKeys.IS_DIETICIAN))
               isDietist = true;

           if(specificData.containsKey(Keys.CoachRegistrationKeys.ATTACHED_FILE))
                certificationUri = specificData.get(Keys.CoachRegistrationKeys.ATTACHED_FILE);


            Coach coach = new Coach(fullName, email, birthDate, gender,role,isPersonalTrainer,isDietist, certificationUri);

            UserDb.create(coach, mCallbacks);
           }
        }


    /**
     * Il metodo permette di creare la subcollection del peso dell'utente nel db
     * La subcollection conterrà tutti i pesi registrati dal client
     *
     * @param client Il client di cui si intende registrare il peso
     */
    public void createClientSubCollections(Client client){
        final String WEIGHT = "weight";
        final Map<String, Object> weightMap = new HashMap<>();

        weightMap.put(Keys.ClientRegistrationKeys.WEIGHT,client.weight);

        //creo la collection dei pesi nel document avente email quella dell'utente appena registrato
        UserDb.addInSubCollection(client.email, Keys.Collections.WEIGHT, weightMap);

        mView.onSuccess(client);
    }

    public void loadCertification(Coach coach){
        if(coach.certificationUri != null) {
            StorageReference fileRef = FirebaseStorage.getInstance().getReference(Keys.Collections.CERTIFICATION)
                    .child(System.currentTimeMillis() + "." + mView.getFileExtension());

            fileRef.putFile(mView.getFileURI())
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                        while(!uriTask.isComplete());

                        Uri uri = uriTask.getResult();

                        Map<String, Object> map = new HashMap<>();
                        assert uri != null;
                        map.put("certificationUri", uri.toString());

                        UserDb.update(coach, map);
                    });
        }
    }
}