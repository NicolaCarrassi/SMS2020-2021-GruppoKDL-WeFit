package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import android.location.Location;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;

public interface RunActivityContract {

    interface View{
        void setLocation(Location location);
    }

    interface Presenter{
        void getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map, FragmentActivity activity);
    }

}
