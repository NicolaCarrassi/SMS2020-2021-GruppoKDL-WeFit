package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientRequestProfileContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachClientRequestProfilePresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;


public class CoachClientRequestProfileFragment extends Fragment implements CoachClientRequestProfileContract.View,
        User.MyImageBitmapCallback{

    public static final String TAG = CoachClientRequestProfileFragment.class.getSimpleName();


    private static final String REQUEST = "request";

    private Request mRequest;
    private CoachClientRequestProfileContract.Presenter mPresenter;
    private Client mClient;

    private TextView mClientName;
    private ImageView mClientImage;
    private TextView mClientAge;
    private TextView mClientGender;
    private TextView mClientObjective;
    private TextView mClientHeight;
    private TextView mClientWeight;
    private MaterialButton mAcceptButton;
    private MaterialButton mDeclineButton;


    public CoachClientRequestProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param request Richiesta del cliente di cui si vuole visualizzare il profilo
     *
     * @return A new instance of fragment CoachClientRequestProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static CoachClientRequestProfileFragment newInstance(Request request) {
        CoachClientRequestProfileFragment fragment = new CoachClientRequestProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(REQUEST, request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mRequest = getArguments().getParcelable(REQUEST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.coach_client_request_profile_fragment, container, false);

        mPresenter = new CoachClientRequestProfilePresenter(this);

        bind(layout);
        setListeners();

        return layout;
    }

    /**
     * Il metodo permette di effettuare il binding degli elementi del layout
     *
     * @param view View di cui si è fatto l'inflate
     */
    private void bind(View view){
        if(getActivity() instanceof WeFitApplication.CallbackOperations) {
            WeFitApplication.CallbackOperations  activity= (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, activity);
        }
        mClientName = view.findViewById(R.id.client_name);
        mClientImage = view.findViewById(R.id.client_profile_image);
        mClientAge = view.findViewById(R.id.client_age);
        mClientGender = view.findViewById(R.id.client_gender);
        mClientObjective = view.findViewById(R.id.client_objective);
        mClientHeight = view.findViewById(R.id.client_height);
        mClientWeight = view.findViewById(R.id.client_weight);
        mAcceptButton = view.findViewById(R.id.accept_button);
        mDeclineButton = view.findViewById(R.id.decline_button);
    }

    /**
     * Il metodo permette di impostare i listeners agli elementi della view
     * di cui si ha già il riferimento
     */
    private void setListeners(){
        mAcceptButton.setOnClickListener(v -> {
            mAcceptButton.setClickable(false);
            mDeclineButton.setClickable(false);
            Coach coach = (Coach) ((WeFitApplication)getActivity().getApplicationContext()).getUser();
            mPresenter.clientRequest(coach,mRequest, true);
            mAcceptButton.setVisibility(View.GONE);
            mDeclineButton.setVisibility(View.GONE);
        });

        mDeclineButton.setOnClickListener(v -> {
            mAcceptButton.setClickable(false);
            mDeclineButton.setClickable(false);
            Coach coach = (Coach) ((WeFitApplication)getActivity().getApplicationContext()).getUser();
            mPresenter.clientRequest(coach, mRequest, false);
            mAcceptButton.setVisibility(View.GONE);
            mDeclineButton.setVisibility(View.GONE);
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getClientInfo(mRequest.email);
    }


    @Override
    public void onClientInfoLoaded(Client client) {
        mClient = client;

        if(client.image != null){
            if(!client.isBitmapImageAvailable())
                client.createImageBitmap(this);
            else
                mClientImage.setImageBitmap(client.getImageBitmap());
        }

        mClientName.setText(client.fullName);
        mClientAge.setText(client.birthDate);

        if(client.gender.equals(Keys.Gender.MALE))
            mClientGender.setText(getResources().getString(R.string.male));
        else
            mClientGender.setText(getResources().getString(R.string.female));

        String objective;

        if(client.objective.equals(Keys.Objectives.LOSE_WEIGHT)){
            objective = getResources().getString(R.string.lose_weight_objective);
        } else {
            if(client.objective.equals(Keys.Objectives.FIT_OBJECTIVE))
                objective = getResources().getString(R.string.fit_objective);
            else
                objective = getResources().getString(R.string.gain_mass_objective);
        }

        mClientObjective.setText(objective);
        mClientHeight.setText(Integer.toString(mClient.height));
        mClientWeight.setText(Float.toString(mClient.weight));
    }


    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error_loading_profile), Toast.LENGTH_SHORT).show();
        mAcceptButton.setVisibility(View.GONE);
        mDeclineButton.setVisibility(View.GONE);
    }

    @Override
    public void handleCallback() {
        mClientImage.setImageBitmap(mClient.getImageBitmap());
    }
}