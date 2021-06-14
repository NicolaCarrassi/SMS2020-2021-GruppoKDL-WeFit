package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.RunActivity;


public class ClientRunFragment extends Fragment {

    public static final String TAG = ClientRunFragment.class.getSimpleName();
    private MaterialButton mStartButton;
    private MaterialButton mStatsButton;


    public ClientRunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_client_run, container, false);

        bind(layout);
        setListener();

        return layout;
    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View layout){
        mStartButton = layout.findViewById(R.id.start_run_activity);
        mStatsButton = layout.findViewById(R.id.run_statistics);
    }


    /**
     * Il metodo permette di associare i listeners agli elementi
     * della view
     *
     */
    private void setListener(){
        mStartButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RunActivity.class);
            startActivity(intent);
        });

        mStatsButton.setOnClickListener(v -> openStatistics());
    }


    /**
     * Il metodo permette di passare alla pagina delle statistiche
     */
    private void openStatistics(){
        ClientRunStatsFragment fragment = new ClientRunStatsFragment();

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment, ClientRunStatsFragment.TAG)
                .addToBackStack(ClientRunStatsFragment.TAG).commit();
    }
}