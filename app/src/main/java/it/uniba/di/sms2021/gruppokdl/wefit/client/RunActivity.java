package it.uniba.di.sms2021.gruppokdl.wefit.client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.RunActivityContract;
import it.uniba.di.sms2021.gruppokdl.wefit.databinding.ActivityRunBinding;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;
import it.uniba.di.sms2021.gruppokdl.wefit.client.presenter.RunActivityPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.MyBroadcastReceiver;

public class RunActivity extends FragmentActivity implements OnMapReadyCallback, RunActivityContract.View, MyBroadcastReceiver.OnBroadcastReceiveListener {

    private GoogleMap mMap;

    private MaterialButton mStartButton;
    private MaterialButton mStopButton;
    private ImageView mBackButton;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private RunActivityContract.Presenter mPresenter;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private final LatLng defaultLocation = new LatLng(41.1171, 16.8719);
    private Location mStartingPosition;

    private MyBroadcastReceiver mMessageReceiver;
    List<Location> listLocation = new ArrayList<>();
    private float mDistanceRun;
    private String mElapsedTime;
    private float mAverageSpeed;
    private float mAverageKcal;

    private Chronometer mCrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        it.uniba.di.sms2021.gruppokdl.wefit.databinding.ActivityRunBinding binding = ActivityRunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mPresenter = new RunActivityPresenter(this);

        mMessageReceiver = new MyBroadcastReceiver(listLocation,this);

        bind();
        setListener();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Il seguente metodo permette di associare gli elementi della view
     *  ad oggetti
     */
    private void bind(){
        mStartButton = findViewById(R.id.start);
        mStopButton = findViewById(R.id.stop);
        mCrono = findViewById(R.id.crono);
        mBackButton = findViewById(R.id.back);
    }

    /**
     * Il seguente metodo permette di impostare i listeners per gli elementi della
     * view
     */
    private void setListener(){
        mStartButton.setOnClickListener(v -> startRun());
        mStopButton.setOnClickListener(v -> stopRun());
        mBackButton.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        if(mPresenter.isLocationServiceRunning(getApplicationContext())) {
            new AlertDialog.Builder(RunActivity.this).setTitle(getResources().
                    getString(R.string.cancel_run))
                    .setMessage(getResources().getString(R.string.cancel_run_message))
                    .setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {
                        dialog.dismiss();
                        mPresenter.stopLocationService(getApplicationContext(),RunActivity.this);
                        finish();
                    })
                    .show();
        }else{
            finish();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RunActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            mMap.setMyLocationEnabled(true);
            mPresenter.updateCurrentLocation(fusedLocationProviderClient,mMap,this);
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                finish();
                Intent intent = new Intent(this, RunActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void addMarker(Location location, String markerTitle) {
        if (location != null) {
            LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition,17));
            mMap.addMarker(new MarkerOptions().position(myPosition).title(markerTitle));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
            }
    }

    @Override
    public void drawPath(Location first, Location second){
        LatLng start = new LatLng(first.getLatitude(),first.getLongitude());
        LatLng end = new LatLng(second.getLatitude(),second.getLongitude());
        mMap.addPolyline(new PolylineOptions().color(getColor(R.color.blue)).add(start,end));
    }

    @Override
    public void centerCamera(Location location, int zoom){
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),zoom));
    }

    /**
     * Il seguente metodo permette di calcolare il punto più a nord-est e più a sud-ovest.
     * Grazie ai due punti è possibile effettuare lo zoom out in modo da inquadrare il percorso
     *
     * @param locationList lista di posizioni
     */
    public void zoomOutPath(List<Location> locationList){
        LatLng southwest = new LatLng(locationList.get(0).getLatitude(),locationList.get(0).getLongitude());
        LatLng northeast = new LatLng(locationList.get(0).getLatitude(),locationList.get(0).getLongitude());
        LatLngBounds.Builder myPosition = new LatLngBounds.Builder();

        for(int i=1;i<locationList.size();i++){
            if(locationList.get(i).getLatitude()+locationList.get(i).getLongitude() > northeast.latitude + northeast.longitude){
                northeast = new LatLng(locationList.get(i).getLatitude(),locationList.get(i).getLongitude());
            } else if(locationList.get(i).getLatitude() + locationList.get(i).getLongitude() < southwest.latitude + southwest.longitude){
                southwest = new LatLng(locationList.get(i).getLatitude(),locationList.get(i).getLongitude());
            }
        }

        myPosition.include(southwest).include(northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(myPosition.build(),150));
    }

    @Override
    public void onBroadcastReceive() {
        if(listLocation.size()<=2){
            drawPath(mStartingPosition,listLocation.get(0));
        } else {
            drawPath(listLocation.get(listLocation.size()-2),listLocation.get(listLocation.size()-1));
            centerCamera(listLocation.get(listLocation.size()-1),17);
        }
    }

    @Override
    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.gps_off))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))

                .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                    dialog.cancel();
                    finish();
                    Toast.makeText(getApplicationContext(), getString(R.string.gps_off_toast),Toast.LENGTH_SHORT).show();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * Il seguente metodo permette di avviare la corsa
     */
    private void startRun(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RunActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            mStartingPosition = mPresenter.getCurrentLocation(fusedLocationProviderClient,mMap,RunActivity.this);
            addMarker(mStartingPosition, getResources().getString(R.string.starting_point));
            mPresenter.startLocationService(getApplicationContext(), mMessageReceiver,RunActivity.this);
            mCrono.setBase(SystemClock.elapsedRealtime());
            mCrono.start();
            mStartButton.setVisibility(View.GONE);
            mStopButton.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Il seguente metodo permette di stoppare la corsa
     */
    private void stopRun(){
        Location mStopPosition = listLocation.get(listLocation.size() - 1);
        addMarker(mStopPosition, getResources().getString(R.string.stop_point));
        mPresenter.stopLocationService(getApplicationContext(), RunActivity.this);
        zoomOutPath(listLocation);
        mDistanceRun = mPresenter.calculateDistance(listLocation);
        mCrono.stop();
        mElapsedTime = mPresenter.calculateTime(mCrono);
        mAverageSpeed = mPresenter.calculateAverageSpeed(mCrono,mDistanceRun);
        float weight = ((Client) ((WeFitApplication) getApplicationContext()).getUser()).weight;
        mAverageKcal = mPresenter.calculateAverageKcal(mDistanceRun,mAverageSpeed,weight);
        mStartButton.setVisibility(View.VISIBLE);
        mStopButton.setVisibility(View.GONE);
        fetchData();
    }

    /**
     * Il seguente metodo permette di salvare le informazioni della corsa
     *  e le salva nel database
     */
    private void fetchData(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        Run thisRun = new Run(date,listLocation, mElapsedTime, mAverageSpeed, mAverageKcal, mDistanceRun);
        String userMail = ((WeFitApplication)getApplicationContext()).getUser().email;
        mPresenter.saveRun(userMail, thisRun);
    }
}