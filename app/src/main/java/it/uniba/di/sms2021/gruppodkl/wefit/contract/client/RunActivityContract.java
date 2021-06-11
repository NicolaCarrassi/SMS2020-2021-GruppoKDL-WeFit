package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;
import android.widget.Chronometer;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public interface RunActivityContract {

    interface View{
        void addMarker(Location location, String markerTitle);
        void drawPath(Location first, Location second);
        void centerCamera(Location location,int zoom);
    }

    interface Presenter{
        void updateCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map, FragmentActivity activity);
        Location getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map,FragmentActivity activity);
        boolean isLocationServiceRunning(Context context);
        void startLocationService(Context context, BroadcastReceiver mMessageReceiver, FragmentActivity activity);
        void stopLocationService(Context context, FragmentActivity activity);
        float calculateDistance(List<Location> locationList);
        String calculateTime(Chronometer chronometer);
    }

}
