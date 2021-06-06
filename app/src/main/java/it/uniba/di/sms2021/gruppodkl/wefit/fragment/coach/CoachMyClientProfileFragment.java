package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientProfileContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachMyClientProfilePresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.GraphSettings;


//TODO Continua quando avrai nuove parti

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachMyClientProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachMyClientProfileFragment extends Fragment implements CoachMyClientProfileContract.View, User.MyImageBitmapCallback {

    public static final String TAG = CoachMyClientProfileFragment.class.getSimpleName();

    private static final String CLIENT_MAIL = "clientMail";

    private String mClientMail;
    private Client mClientProfile;
    private CoachMyClientProfileContract.Presenter mPresenter;

    private TextView mClientName;
    private ImageView mClientImage;
    private TextView mClientInitialWeight;
    private TextView mCLientCurrentWeight;
    private Button mClientTrainingSchedule;
    private Button mClientDiet;
    private GraphView mWeightGraph;



    public CoachMyClientProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clientMail mail del cliente di cui si vuole visualizzare il profilo
     * @return A new instance of fragment CoachMyClientProfileFragment.
     */
    public static CoachMyClientProfileFragment newInstance(String clientMail) {
        CoachMyClientProfileFragment fragment = new CoachMyClientProfileFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mClientMail = getArguments().getString(CLIENT_MAIL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.coach_my_client_profile_fragment, container, false);

        mPresenter = new CoachMyClientProfilePresenter(this);
        bind(layout);

        setListeners();
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.findUserData(mClientMail);
    }

    private void bind(View view){
        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations activity = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, activity);
        }
        mClientName = view.findViewById(R.id.client_name);
        mClientImage = view.findViewById(R.id.client_pfp);
        mClientInitialWeight = view.findViewById(R.id.initial_weight);
        mCLientCurrentWeight = view.findViewById(R.id.current_weight);
        mClientTrainingSchedule = view.findViewById(R.id.btn_training_schedule);
        mClientDiet = view.findViewById(R.id.btn_diet);
        mWeightGraph = view.findViewById(R.id.graph);
    }

    private void setListeners(){
        mClientTrainingSchedule.setOnClickListener(v -> goClientTraining());
        mClientDiet.setOnClickListener(v -> goClientDiet());
    }

    private void goClientTraining(){
        CoachMyClientScheduleFragment fragment = CoachMyClientScheduleFragment.newInstance(mClientMail);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment,CoachMyClientScheduleFragment.TAG)
                .addToBackStack(CoachMyClientScheduleFragment.TAG).commit();
    }

    private void goClientDiet(){
        //TODO
    }


    @Override
    public void onFailure() {
        //TODO Avvisa il coach che ci sono stati problemi, possibilmente non con un toast come sto facendo io
        Toast.makeText(getActivity(), "Errore", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClientDataReceived(Client client, List<Float> weightList, List<Date> dateList) {
        mClientName.setText(client.fullName.toUpperCase());
        mClientProfile = client;

        if(client.image != null) {
            if (!client.isBitmapImageAvailable())
                client.createImageBitmap(this);
            else
                mClientImage.setImageBitmap(client.getImageBitmap());
        }

        mClientInitialWeight.setText(Float.toString(weightList.get(0)));
        mCLientCurrentWeight.setText(Float.toString(client.weight));

       GraphSettings.graphSettings(mWeightGraph, dateList, weightList);
    }

    @Override
    public void handleCallback() {
        mClientImage.setImageBitmap(mClientProfile.getImageBitmap());
    }


}