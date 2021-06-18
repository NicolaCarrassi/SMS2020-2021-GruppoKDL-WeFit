package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Chronometer;
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
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.RunActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;
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
        locationResult.addOnCompleteListener(activity, location -> {
            if(location!=null){
                mCurrentLocation = location.getResult();
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

    @Override
    public float calculateDistance(List<Location> locationList) {
        float result = 0;

        for(int i = 0; i<locationList.size()-1; i++){
            result += locationList.get(i).distanceTo(locationList.get(i+1));
        }

        return result;
    }

    @Override
    public String calculateTime(Chronometer chronometer){
        String result;
        long elapsedTime;

        elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();

        result = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(elapsedTime),
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % TimeUnit.MINUTES.toSeconds(1));

        return result;
    }

    @Override
    public float calculateAverageSpeed(Chronometer chronometer, float distance){
        float result;
        long elapsedTime;

        elapsedTime = TimeUnit.MILLISECONDS.toSeconds(SystemClock.elapsedRealtime() - chronometer.getBase());

        result = distance/elapsedTime;

        return result;
    }

    @Override
    public float calculateAverageKcal(float distance, float averageSpeed, float weight) {
        float result;

        if(averageSpeed<1.7){
            result = (float) (0.5*weight*(distance/1000));
        } else {
            result = (float) (1*weight*(distance/1000));
        }

        return result;
    }

    @Override
    public void saveRun(String clientMail, Run run) {
        ClientDAO.saveRun(clientMail, run);
    }

}
