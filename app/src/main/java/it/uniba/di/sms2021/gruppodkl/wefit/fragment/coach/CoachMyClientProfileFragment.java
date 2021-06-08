package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

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

    private View mView;
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
        mView = layout;
        mPresenter = new CoachMyClientProfilePresenter(this);
        bind();

        setListeners();
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.findUserData(mClientMail);
    }

    private void bind(){
        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations activity = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(mView, activity);
        }
        mClientName = mView.findViewById(R.id.client_name);
        mClientImage = mView.findViewById(R.id.client_pfp);
        mClientInitialWeight = mView.findViewById(R.id.initial_weight);
        mCLientCurrentWeight = mView.findViewById(R.id.current_weight);
        mClientTrainingSchedule = mView.findViewById(R.id.btn_training_schedule);
        mClientDiet = mView.findViewById(R.id.btn_diet);
        mWeightGraph = mView.findViewById(R.id.graph);
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
        CoachMyClientDietListFragment fragment = CoachMyClientDietListFragment.newInstance(mClientMail, mClientProfile.fullName);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,fragment, CoachMyClientDietListFragment.TAG)
                .addToBackStack(CoachMyClientDietListFragment.TAG).commit();
    }


    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error_general), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClientDataReceived(Client client, List<Float> weightList, List<Date> dateList) {
        mClientName.setText(client.fullName.toUpperCase());
        mClientProfile = client;

        if(client.image != null) {
            if (!client.isBitmapImageAvailable()) {
                ((WeFitApplication)getActivity().getApplicationContext()).startProgress(mView);
                client.createImageBitmap(this);
            }else
                mClientImage.setImageBitmap(client.getImageBitmap());
        }


        String clientInitialWeight = Float.toString(weightList.get(0)) ;
        String clientCurrentWeight = Float.toString(client.weight);

        mClientInitialWeight.setText(clientInitialWeight);
        mCLientCurrentWeight.setText(clientCurrentWeight);

       GraphSettings.graphSettings(mWeightGraph, dateList, weightList);
    }

    @Override
    public void handleCallback() {
        ((WeFitApplication)getActivity().getApplicationContext()).stopProgress(mView);
        mClientImage.setImageBitmap(mClientProfile.getImageBitmap());
    }


}