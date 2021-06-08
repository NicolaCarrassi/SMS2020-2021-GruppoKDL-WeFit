package it.uniba.di.sms2021.gruppodkl.wefit;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.RunActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.databinding.ActivityRunBinding;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.RunActivityPresenter;

public class RunActivity extends FragmentActivity implements OnMapReadyCallback, RunActivityContract.View {

    private GoogleMap mMap;
    private ActivityRunBinding binding;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private RunActivityPresenter mPresenter;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location mActualLocation;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mPresenter = new RunActivityPresenter(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
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
            mPresenter.getCurrentLocation(fusedLocationProviderClient,mMap,RunActivity.this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.getCurrentLocation(fusedLocationProviderClient,mMap,RunActivity.this);
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setLocation(Location location) {
        if (location != null) {
            LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
            mMap.addMarker(new MarkerOptions().position(myPosition).title("Posizione di Partenza"));
            } else {
                mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(defaultLocation, 15));
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
    }
}