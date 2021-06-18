package it.uniba.di.sms2021.gruppokdl.wefit.client.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.model.MyLocation;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientRunDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ClientRunDetailFragment extends Fragment implements OnMapReadyCallback {
    public static final String TAG = ClientRunDetailFragment.class.getSimpleName();

    private static final String RUN_KEY = "run";

    private Run mRun;
    private GoogleMap mMap;
    private TextView mRunDate;
    private TextView mRunTime;
    private TextView mRunKcal;
    private TextView mRunDistance;
    private TextView mRunSpeed;

    public ClientRunDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param run Corsa di cui si vogliono conoscere i dettagli.
     * @return A new instance of fragment ClientRunDetailFragment.
     */
    public static ClientRunDetailFragment newInstance(Run run) {
        ClientRunDetailFragment fragment = new ClientRunDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(RUN_KEY, run);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mRun = getArguments().getParcelable(RUN_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.client_run_detail_fragment, container, false);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act =(WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(view, act);
        }

        mRunDate = view.findViewById(R.id.run_date);
        mRunTime = view.findViewById(R.id.run_time_elapsed);
        mRunKcal = view.findViewById(R.id.run_calories);
        mRunDistance = view.findViewById(R.id.run_distance);
        mRunSpeed = view.findViewById(R.id.run_avg_speed);

        setValues();

        return view;
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

    /**
     * Il metodo permette di impostare i valori della corsa
     */
    private void setValues(){
        mRunDate.setText(mRun.date);
        mRunKcal.setText(mRun.convertKcal());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        showLastRun(mRun);
    }

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
        mRunSpeed.setText(run.convertAvgSpeed());
        mRunDistance.setText(run.convertRunDistance());
        mRunTime.setText(run.convertTime());
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

        if(northeast.latitude>southwest.latitude) {
            myPosition = new LatLngBounds(southwest, northeast);
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(myPosition, 150));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(southwest.latitude,southwest.longitude),16));
        }
    }

}