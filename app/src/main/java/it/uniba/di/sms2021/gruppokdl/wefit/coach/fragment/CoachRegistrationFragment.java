package it.uniba.di.sms2021.gruppokdl.wefit.coach.fragment;

import android.content.Context;
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

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.contract.CoachRegistrationFragmentContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;


public class CoachRegistrationFragment extends Fragment implements CoachRegistrationFragmentContract.View {

    public static final String TAG = CoachRegistrationFragment.class.getSimpleName();

    private CheckBox mPersonalTrainerCheckBox;
    private CheckBox mDieticianCheckBox;
    private ImageView mAddCertificationIcon;
    private ImageView mCertificationAddedIcon;
    private String mCertificationUri;
    private boolean mHasBeenClicked = false;

    private CoachCallBackActivity mActivity;

    public interface CoachCallBackActivity{
        /**
         * Il metodo permette di aprire l'intente che permette di effetuare
         * la scelta di un file
         */
        void openFindFile();

        /**
         * Il metodo permette di ottenre il fileURI
         * @return FileURI
         */
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
        View layout =  inflater.inflate(R.layout.coach_registration_fragment, container, false);

        bind(layout);
        setListeners();

        return layout;
    }


    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     * @param view View di cui si è fatto l'inflate.
     */
    private void bind(View view){
        mPersonalTrainerCheckBox = view.findViewById(R.id.personal_trainer_checkbox);
        mDieticianCheckBox = view.findViewById(R.id.dietician_checkbox);
        mAddCertificationIcon = view.findViewById(R.id.send_certification);
        mAddCertificationIcon.setClickable(true);
        mCertificationAddedIcon = view.findViewById(R.id.check_icon);
    }

    /**
     * Il metodo permette di impostare i listeners
     *
     */
    private void setListeners(){
        mAddCertificationIcon.setOnClickListener(v -> {
            mAddCertificationIcon.setClickable(false);
            if(!mHasBeenClicked)
                mActivity.openFindFile();
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
            addictionalData.put(Coach.CoachKeys.IS_PERSONAL_TRAINER, PERSONAL_TRAINER);

        if(mDieticianCheckBox.isChecked())
            addictionalData.put(Coach.CoachKeys.IS_DIETICIAN, DIETICIAN);

        if(mActivity.getFileURI() != null)
            addictionalData.put(Coach.CoachKeys.CERTIFICATION, mCertificationUri);

        return addictionalData;
    }

    /**
     * Il metodo permette di gestire la situazione di ricezione dell'uri della certificazione
     * caricata.
     *
     * @param uri Uri della risorsa
     */
    public void uriObtained(String uri){
        mCertificationUri = uri;

        if (mCertificationUri != null) {
            mAddCertificationIcon.setVisibility(View.GONE);
            mCertificationAddedIcon.setVisibility(View.VISIBLE);
            mHasBeenClicked = true;
        } else
            mAddCertificationIcon.setClickable(true);
    }

}