package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientAddTrainingContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientAddTrainingPresenter;


public class ClientAddTrainingFragment extends Fragment implements ClientAddTrainingContract.View{

    public static final String TAG = ClientAddTrainingFragment.class.getSimpleName();

    private String mClientMail;

    private Spinner mSpinner;
    private ClientAddTrainingContract.Presenter mPresenter;
    private MaterialButton mSendBtn;

    public ClientAddTrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.client_add_training_fragment, container, false);
        mPresenter = new ClientAddTrainingPresenter(this);
        mSendBtn = layout.findViewById(R.id.send_button);
        mSpinner = layout.findViewById(R.id.spinner_training);


        mSendBtn.setOnClickListener(v -> registerTraining());

        mClientMail = ((WeFitApplication) getActivity().getApplicationContext()).getUser().email;

        return layout;
    }


    @Override
    public void onStart() {
        super.onStart();

        mPresenter.loadTrainingInformation(mClientMail);
    }

    @Override
    public void trainingListNotEmpty(List<String> trainingNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout);
        adapter.addAll(trainingNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void emptyTrainingList() {
        mSendBtn.setVisibility(View.GONE);
        mSendBtn.setVisibility(View.GONE);
        //TODO Fai comparire qualcosa, ottieni il riferimento nell'onCreateView e falla comparire qua
    }

    @Override
    public void onTrainingAdded() {
        //TODO AVVISA L'UTENTE CHE HA INSERITO CON SUCCESSO IL TRAINING
    }

    private void registerTraining(){
        mSendBtn.setClickable(false);

        String trainingName = (String) mSpinner.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String today = sdf.format(new Date());

        mPresenter.registerTrainingComplete(mClientMail,trainingName, today);
    }
}