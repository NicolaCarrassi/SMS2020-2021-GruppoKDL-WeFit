package it.uniba.di.sms2021.gruppokdl.wefit.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import it.uniba.di.sms2021.gruppokdl.wefit.R;

public class LocationService extends Service {

    private Location mLocation;

    /**
     * Interfaccia contenente le costanti
     */
    public interface LocationServiceConstants{
        int LOCATION_SERVICE_ID = 175;
        String ACTION_START_LOCATION_SERVICE = "startLocationService";
        String ACTION_STOP_LOCATION_SERVICE = "stopLocationService";
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                mLocation = locationResult.getLastLocation();
                sendMessageToActivity(mLocation);
                Log.d("posizioni", "- " + mLocation.getLatitude() + " - " + mLocation.getLongitude());
            }
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    };

    /**
     * Metodo che consente di inviare tramite broadcast le location all' activity
     * @param l location
     */
    private void sendMessageToActivity(Location l) {
        Intent intent = new Intent("LocationUpdates");
        Bundle b = new Bundle();
        b.putParcelable("Location", l);
        intent.putExtra("Location", b);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    /**
     * Metodo che consente di avviare il service e di creare la notifica che avvisa dell' esecuzione
     */
    @SuppressLint("MissingPermission")
    private void startLocationService() {
        String channelID = "location_notification_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(getResources().getString(R.string.notification_location_title));
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText(getResources().getString(R.string.notification_location_description));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelID) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(channelID, getResources().getString(R.string.notification_location_title), NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
        startForeground(LocationServiceConstants.LOCATION_SERVICE_ID,builder.build());

    }

    /**
     * Metodo che consente di stoppare il service
     */
    private void stopLocationService(){
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(mLocationCallback);
        stopForeground(true);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            String action = intent.getAction();
            if(action.equals(LocationServiceConstants.ACTION_START_LOCATION_SERVICE)){
                startLocationService();
            } else if (action.equals(LocationServiceConstants.ACTION_STOP_LOCATION_SERVICE)){
                stopLocationService();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Non ancora implementato");
    }
}
