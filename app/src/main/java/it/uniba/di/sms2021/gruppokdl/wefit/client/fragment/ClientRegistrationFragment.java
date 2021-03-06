package it.uniba.di.sms2021.gruppokdl.wefit.client.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.RegistrationFragmentContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;


public class ClientRegistrationFragment extends Fragment implements RegistrationFragmentContract.View {

    private EditText mHeightEdit;
    private EditText mWeightEdit;
    private RadioGroup mObjectiveRadio;




    public ClientRegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.client_registration_fragment, container, false);

        bind(layout);

        return layout;
    }


    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View layout){

        mHeightEdit = layout.findViewById(R.id.height_edit_text);
        mWeightEdit = layout.findViewById(R.id.weight_edit_text);
        mObjectiveRadio = layout.findViewById(R.id.radio_objective);
    }


    @Override
    public boolean areCorrect() {
        final int MIN_HEIGHT = 100;
        final int MAX_HEIGHT = 230;
        final float MIN_WEIGHT = 40.0f;

        boolean result = true;
        String temp; //contiene i valori
        int height, objective;
        float weight;

        temp = mHeightEdit.getText().toString();
        if(!TextUtils.isEmpty(temp)){
            try{
                height = Integer.parseInt(temp);

                if(height < MIN_HEIGHT || height > MAX_HEIGHT)
                    result = false;

            } catch(Exception e){
                result = false;
            }
        } else {
            result = false;
        }

        if(!result)
            mHeightEdit.setError(getResources().getString(R.string.error_height));


        temp = mWeightEdit.getText().toString();
        if(!TextUtils.isEmpty(temp)){
            try {
                weight = Float.parseFloat(temp);

                if(weight < MIN_WEIGHT)
                    result = false;

            } catch (Exception e){
                result = false;
                mWeightEdit.setError(getResources().getString(R.string.error_weight));
            }
        } else {
            result = false;
            mWeightEdit.setError(getResources().getString(R.string.error_weight));
        }


        objective = mObjectiveRadio.getCheckedRadioButtonId();

        if(objective <= 0){
            int lastChild = mObjectiveRadio.getChildCount() -1;
            ((RadioButton)mObjectiveRadio.getChildAt(lastChild)).setError(getResources().getString(R.string.error_radio));
            result = false;
        }

        return result;
    }

    @Override
    public Map<String, String> getAddictionalData() {
        Map<String,String> addictionalData = new HashMap<>();


        addictionalData.put(Client.ClientKeys.HEIGHT, mHeightEdit.getText().toString());
        addictionalData.put(Client.ClientKeys.WEIGHT, mWeightEdit.getText().toString());


        int radioResult = mObjectiveRadio.getCheckedRadioButtonId();

        if(radioResult == R.id.fit_objective_radio)
            addictionalData.put(Client.ClientKeys.OBJECTIVE, Keys.Objectives.FIT_OBJECTIVE);

        if(radioResult == R.id.lose_objective_radio)
            addictionalData.put(Client.ClientKeys.OBJECTIVE, Keys.Objectives.LOSE_WEIGHT);

        if(radioResult == R.id.shape_objective_radio)
            addictionalData.put(Client.ClientKeys.OBJECTIVE,Keys.Objectives.GAIN_MASS);

        return addictionalData;
    }


}