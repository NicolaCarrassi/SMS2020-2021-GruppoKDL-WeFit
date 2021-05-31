package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.uniba.di.sms2021.gruppodkl.wefit.R;


public class ClientAddWeightFragment extends Fragment {

    public static final String TAG = ClientAddWeightFragment.class.getSimpleName();

    private Button mButtonDecrease;
    private Button mButtonIncrease;
    private EditText mWeightValue;


    public ClientAddWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.client_add_weight_fragment, container, false);

        bind(layout);
        setListener();

        return layout;
    }


    private void bind(View layout){
        mButtonDecrease = layout.findViewById(R.id.buttonDecrease);
        mButtonIncrease = layout.findViewById(R.id.buttonIncrease);
        mWeightValue = layout.findViewById(R.id.weightValue);
    }

    private void setListener(){
        mButtonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String presentValStr=mWeightValue.getText().toString();
                double presentDoubleVal= Double.parseDouble(presentValStr);
                presentDoubleVal--;
                mWeightValue.setText(String.valueOf(presentDoubleVal));
            }
        });

        mButtonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String presentValStr=mWeightValue.getText().toString();
                double presentDoubleVal= Double.parseDouble(presentValStr);
                presentDoubleVal++;
                mWeightValue.setText(String.valueOf(presentDoubleVal));
            }
        });
    }

}