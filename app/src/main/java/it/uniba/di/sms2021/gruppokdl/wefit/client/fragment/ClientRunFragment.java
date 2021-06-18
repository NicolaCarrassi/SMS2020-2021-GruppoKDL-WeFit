package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.client.RunActivity;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientRunContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.MyLocation;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientRunPresenter;


public class ClientRunFragment extends Fragment implements ClientRunContract.View, OnMapReadyCallback {

    public static final String TAG = ClientRunFragment.class.getSimpleName();
    private MaterialButton mStartButton;
    private MaterialButton mStatsButton;
    private ClientRunContract.Presenter mPresenter;
    private GoogleMap mMap;
    private TextView mDistance;
    private TextView mTime;
    private TextView mSpeed;
    private LinearLayout mMapBlock;
    private LinearLayout mStatsBlock;
    private TextView mNoRunMessage;



    public ClientRunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.client_run_fragment, container, false);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(layout,act);
        }

        mPresenter = new ClientRunPresenter(this);

        bind(layout);
        setListener();

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        String mail = ((WeFitApplication) getActivity().getApplicationContext()).getUser().email;
        mPresenter.getLastRun(mail);
    }

    private void bind(View layout){
        mStartButton = layout.findViewById(R.id.start_run_activity);
        mStatsButton = layout.findViewById(R.id.run_statistics);
        mDistance = layout.findViewById(R.id.distance);
        mSpeed = layout.findViewById(R.id.speed);
        mTime = layout.findViewById(R.id.time);
        mMapBlock = layout.findViewById(R.id.map_block);
        mStatsBlock = layout.findViewById(R.id.stats_block);
        mNoRunMessage = layout.findViewById(R.id.text_no_run);
    }

    private void setListener(){
        mStartButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RunActivity.class);
            startActivity(intent);
        });

        mStatsButton.setOnClickListener(v -> openStatistics());
    }


    private void openStatistics(){
        ClientRunStatsFragment fragment = new ClientRunStatsFragment();

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment, ClientRunStatsFragment.TAG)
                .addToBackStack(ClientRunStatsFragment.TAG).commit();
    }

    @Override
    public void showLastRun(Run run) {
        LatLng startPoint = new LatLng(run.locationList.get(0).getLatitude(),run.locationList.get(0).getLongitude());
        LatLng endPoint = new LatLng(run.locationList.get(run.locationList.size()-1).getLatitude(),run.locationList.get(run.locationList.size()-1).getLongitude());
        mMap.addMarker(new MarkerOptions().position(startPoint).title(getString(R.string.starting_point)));
        mMap.addMarker(new MarkerOptions().position(endPoint).title(getString(R.string.stop_point)));
        for(int i=0;i<(run.locationList.size()-1);i++){
            mMap.addPolyline(new PolylineOptions()
                    .color(getResources().getColor(R.color.blue, getActivity().getTheme()))
                    .add(new LatLng(run.locationList.get(i).getLatitude(),run.locationList.get(i).getLongitude()),
                            new LatLng(run.locationList.get(i+1).getLatitude(),run.locationList.get(i+1).getLongitude())));
        }
        centerCamera(run.locationList);
        mSpeed.setText(run.convertAvgSpeed());
        mDistance.setText(run.convertRunDistance());
        mTime.setText(run.convertTime());
    }

    public void centerCamera(List<MyLocation> locationList){

        LatLng southwest = new LatLng(locationList.get(0).getLatitude(),locationList.get(0).getLongitude());
        LatLng northeast = new LatLng(locationList.get(0).getLatitude(),locationList.get(0).getLongitude());
        LatLngBounds myPosition;

        for(int i=1;i<locationList.size();i++){
            if(locationList.get(i).getLatitude()+locationList.get(i).getLongitude() > northeast.latitude + northeast.longitude){
                northeast = new LatLng(locationList.get(i).getLatitude(),locationList.get(i).getLongitude());
            } else if(locationList.get(i).getLatitude() + locationList.get(i).getLongitude() < southwest.latitude + southwest.longitude){
                southwest = new LatLng(locationList.get(i).getLatitude(),locationList.get(i).getLongitude());
            }
        }

        myPosition = new LatLngBounds(southwest,northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(myPosition,150));
    }

    @Override
    public void lastRunEmpty() {
        mMapBlock.setVisibility(View.GONE);
        mStatsBlock.setVisibility(View.GONE);
        mStatsButton.setVisibility(View.GONE);
        mNoRunMessage.setVisibility(View.VISIBLE);
    }

}