package it.uniba.di.sms2021.gruppodkl.wefit.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.SplashActivity;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;


public class WeFitFirebaseMessagingService extends FirebaseMessagingService {
    private static final int NOTIFICATION_MAX_CHARACTERS = 30;


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

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification  = remoteMessage.getNotification();

        if(notification != null && notification.getBody() != null) {
            if(notification.getTitle() != null)
                sendNotification(notification.getTitle(), notification.getBody());
            else
                sendNotification(getString(R.string.app_name), notification.getBody());
        }
    }



    private void sendNotification(String title, String message) {
        String channelID = "message_notification_channel";

        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Create the pending intent to launch the activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (message.length() > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026";
        }



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelID) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(channelID, title, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("This channel is used by messaging service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        assert notificationManager != null;
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
