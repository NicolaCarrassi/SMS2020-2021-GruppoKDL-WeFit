package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientRunDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// TODO Inserire grafico della corsa
public class ClientRunDetailFragment extends Fragment {
    public static final String TAG = ClientRunDetailFragment.class.getSimpleName();

    private static final String RUN_KEY = "run";

    private Run mRun;
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

    /**
     * Il metodo permette di impostare i valori della corsa
     */
    private void setValues(){
        mRunDate.setText(mRun.date);
        mRunTime.setText(mRun.elapsedTime);
        mRunKcal.setText(mRun.convertKcal());
        mRunDistance.setText(mRun.convertRunDistance());
        mRunSpeed.setText(mRun.convertAvgSpeed());
    }


}