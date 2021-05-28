package it.uniba.di.sms2021.gruppodkl.wefit.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.CoachRegistrationFragmentContract;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;


public class CoachRegistrationFragment extends Fragment implements CoachRegistrationFragmentContract.View {

    private CheckBox mPersonalTrainerCheckBox;
    private CheckBox mDieticianCheckBox;
    private ImageView mAddCertification;
    private Uri mCertificationUri;
    private boolean mHasBeenClicked = false;

    private CoachCallBackActivity mActivity;

    public interface CoachCallBackActivity{
        void openFindFile();
        Uri getFileURI();
    }




    public CoachRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof CoachCallBackActivity){
            mActivity = (CoachCallBackActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_coach_registration, container, false);

        bind(layout);
        setListeners();

        return layout;
    }


    private void bind(View view){
        mPersonalTrainerCheckBox = view.findViewById(R.id.personal_trainer_checkbox);
        mDieticianCheckBox = view.findViewById(R.id.dietician_checkbox);
        mAddCertification = view.findViewById(R.id.send_certification);
        mAddCertification.setClickable(true);
    }

    private void setListeners(){
        mAddCertification.setOnClickListener(v -> {
            //TODO gestisci click
            if(!mHasBeenClicked)
                 mActivity.openFindFile();
                 mCertificationUri = mActivity.getFileURI();

            //In caso di certificazione aggiunta
            if(mCertificationUri != null) {
                mHasBeenClicked = true;
                mAddCertification.setClickable(false);
            }
        });
    }


    @Override
    public boolean areCorrect() {
        //in questo caso viene restituito sempre true in quanto nessuno dei campi è obbligatorio
        return  true;
    }

    @Override
    public Map<String, String> getAddictionalData() {
        Map<String,String> addictionalData = new HashMap<>();


        final String PERSONAL_TRAINER = "PERSONAL TRAINER";
        final String DIETICIAN = "DIETICIAN";


        if(mPersonalTrainerCheckBox.isChecked())
            addictionalData.put(Keys.CoachRegistrationKeys.IS_PERSONAL_TRAINER, PERSONAL_TRAINER);
        if(mDieticianCheckBox.isChecked())
            addictionalData.put(Keys.CoachRegistrationKeys.IS_DIETICIAN, DIETICIAN);

        if(mCertificationUri != null)
            addictionalData.put(Keys.CoachRegistrationKeys.ATTACHED_FILE, mCertificationUri.toString());

        return addictionalData;
    }
}