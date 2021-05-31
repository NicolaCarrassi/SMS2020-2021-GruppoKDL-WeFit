package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import it.uniba.di.sms2021.gruppodkl.wefit.R;


public class ClientAddTrainingFragment extends Fragment {

    public static final String TAG = ClientAddTrainingFragment.class.getSimpleName();

    private Spinner mSpinner;

    public ClientAddTrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.client_add_training_fragment, container, false);

        bind(layout);

        return layout;
    }

    private void bind(View layout){
        mSpinner = layout.findViewById(R.id.spinner_training);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(layout.getContext(), R.array.training, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }
}