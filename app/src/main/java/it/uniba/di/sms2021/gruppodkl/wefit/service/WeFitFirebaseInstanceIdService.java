package it.uniba.di.sms2021.gruppodkl.wefit.service;



import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class WeFitFirebaseInstanceIdService extends FirebaseMessagingService {



    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


        FirebaseAuth auth = FirebaseAuth.getInstance();


        if(auth.getCurrentUser() != null) {
            String userMail = auth.getCurrentUser().getEmail();
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put(Keys.Collections.TOKEN, s);

            assert userMail != null;
            FirebaseFirestore.getInstance().collection(Keys.Collections.TOKEN).document(userMail).set(tokenMap);
        }
    }
}
