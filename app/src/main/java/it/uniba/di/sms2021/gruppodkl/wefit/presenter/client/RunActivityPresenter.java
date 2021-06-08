package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

import it.uniba.di.sms2021.gruppodkl.wefit.RunActivity;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.RunActivityContract;

public class RunActivityPresenter implements RunActivityContract.Presenter {

    private final RunActivityContract.View mView;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private Location mCurrentLocation;

    public RunActivityPresenter(RunActivityContract.View mView) {
        this.mView = mView;
    }

    @Override
    @SuppressLint("MissingPermission")
    public void getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map,FragmentActivity activity){
        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(activity, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(Task<Location> location) {
                if(location!=null){
                    mView.setLocation(location.getResult());
                }
            }
        });
    }


}
