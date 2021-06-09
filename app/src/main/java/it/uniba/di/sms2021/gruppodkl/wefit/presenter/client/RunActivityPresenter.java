package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.RunActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.service.LocationService;

public class RunActivityPresenter implements RunActivityContract.Presenter {

    private final RunActivityContract.View mView;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private Location mCurrentLocation = null;

    public RunActivityPresenter(RunActivityContract.View mView) {
        this.mView = mView;
    }

    @SuppressLint("MissingPermission")
    public Location getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map,FragmentActivity activity){
        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(activity, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(Task<Location> location) {
                if(location!=null){
                    mCurrentLocation = location.getResult();
                }
            }
        });
        return mCurrentLocation;
    }

    @Override
    @SuppressLint("MissingPermission")
    public void updateCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient, GoogleMap map, FragmentActivity activity){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    fusedLocationProviderClient.removeLocationUpdates(this);
                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                        int latestLocationIndex = locationResult.getLocations().size() - 1;
                        mCurrentLocation = locationResult.getLocations().get(latestLocationIndex);
                        LatLng myPosition = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                    }
                }
            }, Looper.getMainLooper());

    }

    public boolean isLocationServiceRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (LocationService.class.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Metodo che consente di avviare il service
     */
    public void startLocationService(Context context, BroadcastReceiver mMessageReceiver, FragmentActivity activity) {
        if (!isLocationServiceRunning(context)) {
            Intent intent = new Intent(context, LocationService.class);
            intent.setAction(LocationService.LocationServiceConstants.ACTION_START_LOCATION_SERVICE);
            activity.startService(intent);
            LocalBroadcastManager.getInstance(activity).registerReceiver(mMessageReceiver, new IntentFilter("LocationUpdates"));
        }
    }

    public void stopLocationService(Context context, FragmentActivity activity) {
        if (isLocationServiceRunning(context)) {
            Intent intent = new Intent(context, LocationService.class);
            intent.setAction(LocationService.LocationServiceConstants.ACTION_STOP_LOCATION_SERVICE);
            context.startService(intent);
            Toast.makeText(activity, activity.getResources().getString(R.string.run_stopped), Toast.LENGTH_SHORT).show();
        }
    }

}
