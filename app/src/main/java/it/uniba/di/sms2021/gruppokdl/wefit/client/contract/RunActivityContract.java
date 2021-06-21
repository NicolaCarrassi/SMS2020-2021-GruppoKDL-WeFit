package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;
import android.widget.Chronometer;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;

public interface RunActivityContract {

    interface View{
        /**
         * Il metodo permette di aggiungere un marker sulla mappa
         * @param location posizione in cui si desidera aggiungere il marker
         * @param markerTitle titolo del marker
         */
        void addMarker(Location location, String markerTitle);

        /**
         * Il metodo permette di disegnare una linea che colleghi due punti sulla mappa
         * @param first prima posizione
         * @param second seconda poszione
         */
        void drawPath(Location first, Location second);

        /**
         * Il metodo permette di centrare la mappa su una posizione
         * @param location posizione in cui centrare la mappa
         * @param zoom zoom per la mappa
         */
        void centerCamera(Location location,int zoom);

        /**
         * Il metodo permette di creare un alert nel caso in cui il gps sia disattivato
         */
        void buildAlertMessageNoGps();

        /**
         * Il metodo permette di cambiare la visibilità del circular progress indicator
         * @param visibility visibilità da assegnare
         */
        void setLoaderVisibility(int visibility);

        /**
         * Il metodo permette di cambiare la visibilità del cronometro
         * @param visibility visibilità da assegnare
         */
        void setChronometerVisibility(int visibility);
    }

    interface Presenter{
        /**
         * Il metodo consente di aggiornare la posizione attuale
         * @param fusedLocationProviderClient riferimento al provider della location
         * @param map istanza della mappa
         * @param activity riferimento all' activity
         */
        void updateCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map, FragmentActivity activity);

        /**
         * Il metodo restituisce la posizione attuale
         * @param fusedLocationProviderClient riferimento al provider della location
         * @param map istanza della mappa
         * @param activity riferimento all' activity
         * @return posizione
         */
        Location getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map,FragmentActivity activity);

        /**
         * Metodo che consente di sapere se il Location service sia running
         * @param context context
         * @return true se è running, false altrimenti
         */
        boolean isLocationServiceRunning(Context context);

        /**
         * Metodo che consente di startare il location service
         * @param context context
         * @param mMessageReceiver istanza del broadcast receiver
         * @param activity riferimento all' activity
         */
        void startLocationService(Context context, BroadcastReceiver mMessageReceiver, FragmentActivity activity);

        /**
         * Metodo che consente di stoppare il location service
         * @param context context
         * @param activity riferimento all' activity
         */
        void stopLocationService(Context context, FragmentActivity activity);

        /**
         * Metodo che consente di calcolare la distanza totale tra una serie di location
         * @param locationList lista di location
         * @return distanza
         */
        float calculateDistance(List<Location> locationList);

        /**
         * Metodo che consente di calcolare il tempo trascorso durante la corsa
         * @param chronometer istanza del cronometro
         * @return tempo trascorso
         */
        String calculateTime(Chronometer chronometer);

        /**
         * Metodo che consente di calcolare la velocità media
         * @param chronometer istanza del cronometro
         * @param distance distanza percorsa
         * @return velocità media
         */
        float calculateAverageSpeed(Chronometer chronometer, float distance);

        /**
         * Metodo che consente di calcolare le calorie bruciate durante la corsa
         * @param distance distanza percorsa
         * @param averageSpeed velocità media
         * @param weight peso
         * @return calorie bruciate
         */
        float calculateAverageKcal(float distance, float averageSpeed, float weight);

        /**
         * Metodo che consente di salvare la corsa sul database
         * @param clientMail mail del cliente
         * @param run corsa
         */
        void saveRun(String clientMail, Run run);
    }

}
