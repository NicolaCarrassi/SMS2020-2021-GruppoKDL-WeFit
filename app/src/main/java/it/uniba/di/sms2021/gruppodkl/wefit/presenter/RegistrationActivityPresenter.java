package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class RegistrationActivityPresenter implements RegistrationActivityContract.Presenter {

    private static final String COLLECTION_USER = "Users";
    public final  RegistrationActivityContract.View mView;
    public final FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public RegistrationActivityPresenter(RegistrationActivityContract.View view){
        this.mView = view;
    }


    @Override
    public void registerUser(Map<String, String> userData, Map<String, String> specificData) {
       createAuthenticationData(userData,specificData);

    }

    private void createAuthenticationData(Map<String,String> userData, Map<String,String> specificData){
        String email = userData.get(Keys.RegistrationKeys.EMAIL);
        String password = userData.get(Keys.RegistrationKeys.PASSWORD);

        assert email != null;
        assert password != null;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        createUser(userData,specificData);
                    } else {
                        mView.onFailure();
                    }
                });
    }

    private void createUser(Map<String, String> userData, Map<String, String> specificData){
        String fullName = userData.get(Keys.RegistrationKeys.FIRST_NAME) + " " + userData.get(Keys.RegistrationKeys.LAST_NAME);
        String email = userData.get(Keys.RegistrationKeys.EMAIL);
        String birthDate = userData.get(Keys.RegistrationKeys.BIRTH_DATE);
        String role = userData.get(Keys.RegistrationKeys.ROLE);
        String gender = userData.get(Keys.RegistrationKeys.GENDER);

        //Dati addizionali
        if(role.equals(Keys.Role.CLIENT)){
            float weight = Float.parseFloat(specificData.get(Keys.ClientRegistrationKeys.WEIGHT));
            int height = Integer.parseInt(specificData.get(Keys.ClientRegistrationKeys.HEIGHT));
            String objective = specificData.get(Keys.ClientRegistrationKeys.OBJECTIVE);

            Client client = new Client(fullName, email, birthDate, gender,role,height,weight,objective);

            FirebaseFirestore.getInstance().collection(COLLECTION_USER).document(client.email).set(client)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            createClientSubCollections(client);
                        } else {
                            mView.onFailure();
                        }
                    });
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

            Coach coach = new Coach(fullName, email, birthDate, gender,role,isPersonalTrainer,isDietist,certificationUri);

            FirebaseFirestore.getInstance().collection(COLLECTION_USER).add(coach)
                    .addOnCompleteListener(task -> {
                if(task.isSuccessful())
                    mView.onSuccess(coach);
                else
                    mView.onFailure();
            });
        }
    }

    public void createClientSubCollections(Client client){
        final String WEIGHT = "weight";
        final Map<String, Float> weightMap = new HashMap<>();

        weightMap.put(Keys.ClientRegistrationKeys.WEIGHT,client.weight);

        //creo la collection dei pesi nel document avente email quella dell'utente appena registrato

        FirebaseFirestore.getInstance().collection(COLLECTION_USER).document(client.email)
                .collection(WEIGHT).add(weightMap);

        mView.onSuccess(client);
    }

}


