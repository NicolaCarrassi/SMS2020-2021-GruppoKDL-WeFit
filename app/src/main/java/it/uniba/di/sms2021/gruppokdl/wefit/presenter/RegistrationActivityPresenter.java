package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class RegistrationActivityPresenter implements RegistrationActivityContract.Presenter {

    private final  RegistrationActivityContract.View mView;
    private final UserDAO.UserCallbacks mCallbacks;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();


    /**
     * Costruttore del presenter
     *
     * @param view La view che implementa i metodi del contract
     */
    public RegistrationActivityPresenter(RegistrationActivityContract.View view){
        this.mView = view;

        mCallbacks = new UserDAO.UserCallbacks() {
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
        String email = userData.get(User.UserKeys.EMAIL);
        String password = userData.get(User.UserKeys.PASSWORD);

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
        String firstName = userData.get(User.UserKeys.FIRST_NAME);
        String lastName = userData.get(User.UserKeys.LAST_NAME);

        assert firstName != null;
        assert lastName != null;

        firstName = firstName.trim();
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.trim();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

        String fullName = firstName + " " + lastName;
        String email = userData.get(User.UserKeys.EMAIL);
        String birthDate = userData.get(User.UserKeys.BIRTH_DATE);
        String role = userData.get(User.UserKeys.ROLE);
        String gender = userData.get(User.UserKeys.GENDER);

        //Dati addizionali
        assert role != null;
        if(role.equals(Keys.Role.CLIENT)){
            float weight = Float.parseFloat(Objects.requireNonNull(specificData.get(Client.ClientKeys.WEIGHT)));
            int height = Integer.parseInt(Objects.requireNonNull(specificData.get(Client.ClientKeys.HEIGHT)));
            String objective = specificData.get(Client.ClientKeys.OBJECTIVE);

            Client client = new Client(fullName, email, birthDate, gender,role,height,weight,objective, false);

            UserDAO.create(client, mCallbacks);

        }else {
           boolean isPersonalTrainer = false;
           boolean isDietist = false;
           String certificationUri = null;

            if(specificData.containsKey(Coach.CoachKeys.IS_PERSONAL_TRAINER))
                isPersonalTrainer = true;

           if (specificData.containsKey(Coach.CoachKeys.IS_DIETICIAN))
               isDietist = true;

           if(specificData.containsKey(Coach.CoachKeys.CERTIFICATION))
                certificationUri = specificData.get(Coach.CoachKeys.CERTIFICATION);


            Coach coach = new Coach(fullName, email, birthDate, gender,role,isPersonalTrainer,isDietist, certificationUri);

            UserDAO.create(coach, mCallbacks);
           }
        }


    /**
     * Il metodo permette di creare la subcollection del peso dell'utente nel db
     * La subcollection conterrà tutti i pesi registrati dal client
     *
     * @param client Il client di cui si intende registrare il peso
     */
    public void createClientSubCollections(Client client){
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dataFormat.format(new Date());
        final Map<String, Object> weightMap = new HashMap<>();


        weightMap.put(Client.ClientKeys.WEIGHT,client.weight);
        weightMap.put(Client.ClientKeys.WEIGHT_DATE, today);

        //creo la collection dei pesi nel document avente email quella dell'utente appena registrato
        UserDAO.addInSubCollection(client.email, Keys.Collections.WEIGHT, weightMap);

        mView.onSuccess(client);
    }

    /**
     * Il metodo permette di effettuare il caricamento della certificazione del coach
     * nello storage
     * @param coach coach di cui si intende caricare la certificazione
     */
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
                        map.put(Coach.CoachKeys.CERTIFICATION, uri.toString());

                        UserDAO.update(coach, map);
                    });
        }
    }
}