package it.uniba.di.sms2021.gruppodkl.wefit;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.RunActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.databinding.ActivityRunBinding;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.RunActivityPresenter;

public class RunActivity extends FragmentActivity implements OnMapReadyCallback, RunActivityContract.View {

    private GoogleMap mMap;
    private ActivityRunBinding binding;

    private MaterialButton mStartButton;
    private MaterialButton mStopButton;
    private MaterialButton mLogButton;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private RunActivityPresenter mPresenter;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private final LatLng defaultLocation = new LatLng(41.1171, 16.8719);
    private Location mStartingPosition;

    private Location mReceivedLocations;
    double latitude, longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mPresenter = new RunActivityPresenter(this);

        bind();
        setListener();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void bind(){
        mStartButton = findViewById(R.id.start);
        mStopButton = findViewById(R.id.stop);
        mLogButton = findViewById(R.id.print_log);
    }

    private void setListener(){
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RunActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    mStartingPosition = mPresenter.getCurrentLocation(fusedLocationProviderClient,mMap,RunActivity.this);
                    addMarker(mStartingPosition, getResources().getString(R.string.starting_point));
                    mPresenter.startLocationService(getApplicationContext(), mMessageReceiver,RunActivity.this);
                }
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.stopLocationService(getApplicationContext(), RunActivity.this);
            }
        });

        mLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pos", "- " + mReceivedLocations.getLatitude() + " - " + mReceivedLocations.getLongitude());
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RunActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            mMap.setMyLocationEnabled(true);
            mPresenter.updateCurrentLocation(fusedLocationProviderClient,mMap,this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Bundle b = intent.getBundleExtra("Location");
            mReceivedLocations = (Location) b.getParcelable("Location");
            if (mReceivedLocations != null) {
                latitude = mReceivedLocations.getLatitude();
                longitude = mReceivedLocations.getLongitude();
            }
        }
    };

}