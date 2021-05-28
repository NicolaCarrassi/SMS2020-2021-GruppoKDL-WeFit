package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class RegistrationActivityPresenter implements RegistrationActivityContract.Presenter {

    private RegistrationActivityContract.View mView;
    private FirebaseAuth mAuth;


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
        mAuth = FirebaseAuth.getInstance();

        assert email != null;
        assert password != null;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        createUser(userData,specificData);
                    } else {
                        Log.d("AOO", "Fallimento nella auth");
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

            Log.d("AOO", "Provo a inserire cliente nel db");

//            FirebaseDatabase.getInstance().getReference("Users")
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .setValue(client).addOnCompleteListener(task -> {
//                        if(task.isSuccessful()) {
//                            Log.d("AOO", "Success");
//                            mView.onSuccess();
//                        } else {
//                            Log.d("AOO", "Fallimento nella istanziazione");
//                            mView.onFailure();
//                        }
//                    });

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

//            FirebaseFirestore.getInstance().collection("Users")
//                    child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .setValue(coach).addOnCompleteListener(task -> {
//                if(task.isSuccessful())
//                    mView.onSuccess();
//                else
//                    mView.onFailure();
//            });
        }
    }


}


