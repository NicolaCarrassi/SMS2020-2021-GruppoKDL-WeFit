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
    private MaterialButton mButton;


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

    private void bind(View layout){
        mButton = layout.findViewById(R.id.start_run_activity);
    }

    private void setListener(){
        mButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RunActivity.class);
            startActivity(intent);
        });
    }
}