package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientAddWeightContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientAddWeightPresenter;


public class ClientAddWeightFragment extends Fragment implements ClientAddWeightContract.View {

    public static final String TAG = ClientAddWeightFragment.class.getSimpleName();

    private static final float MIN_WEIGHT = 40.0f;

    private Button mButtonDecrease;
    private Button mButtonIncrease;
    private EditText mWeightValue;
    private Button mAddWeight;
    private Client mClient;
    private ClientAddWeightPresenter mPresenter;


    public ClientAddWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.client_add_weight_fragment, container, false);


        mPresenter = new ClientAddWeightPresenter(this);
        mClient =(Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();

        bind(layout);
        setListener();

        return layout;
    }


    private void bind(View layout){
        mButtonDecrease = layout.findViewById(R.id.buttonDecrease);
        mButtonIncrease = layout.findViewById(R.id.buttonIncrease);
        mWeightValue = layout.findViewById(R.id.weightValue);
        mWeightValue.setText(Float.toString(mClient.weight));
        mAddWeight = layout.findViewById(R.id.btn_add_weight);
    }

    private void setListener(){
        mButtonDecrease.setOnClickListener(v -> {
            final BigDecimal newValue = new BigDecimal(mWeightValue.getText().toString()).subtract(BigDecimal.valueOf(0.1));
            mWeightValue.setText(newValue.toString().substring(0,4));
        });

        mButtonIncrease.setOnClickListener(v -> {
            final BigDecimal newValue = new BigDecimal(mWeightValue.getText().toString()).add(BigDecimal.valueOf(0.1));
            mWeightValue.setText(newValue.toString().substring(0,4));
        });

        mAddWeight.setOnClickListener(v -> {
            float weight = Float.parseFloat(mWeightValue.getText().toString());

            if(weight > MIN_WEIGHT) {
                mClient.weight = weight;
                mPresenter.addWeight(mClient, weight);
            }
        });
    }

    @Override
    public void onSuccess() {
        //TODO Aggiungi un effetto grafico (NON UN TOAST CHE HANNO ROTTO IL CAZZO)
        // per avvisare che il peso è stato registrato, chiudi anche la schermata
        // di add
    }
}