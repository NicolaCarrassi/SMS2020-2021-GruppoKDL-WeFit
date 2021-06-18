package it.uniba.di.sms2021.gruppokdl.wefit.client.fragment;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientAddTrainingContract;
import it.uniba.di.sms2021.gruppokdl.wefit.client.presenter.ClientAddTrainingPresenter;


public class ClientAddTrainingFragment extends BottomSheetDialogFragment implements ClientAddTrainingContract.View{

    public static final String TAG = ClientAddTrainingFragment.class.getSimpleName();

    private String mClientMail;

    private Spinner mSpinner;
    private ClientAddTrainingContract.Presenter mPresenter;
    private MaterialButton mSendBtn;
    private TextView mNoTraining;
    private AnimatedVectorDrawable mSuccessAnimation;
    private LinearLayout mAddTrainingPanel;
    private LinearLayout mAddTrainingSuccess;
    private CircularProgressIndicator mProgressIndicator;


    public ClientAddTrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View layout =  inflater.inflate(R.layout.client_add_training_fragment, container, false);
        mPresenter = new ClientAddTrainingPresenter(this);
        bind(layout);

        assert getActivity() != null;
        mClientMail = ((WeFitApplication) getActivity().getApplicationContext()).getUser().email;
        mSendBtn.setVisibility(View.GONE);
        mSpinner.setVisibility(View.GONE);

        return layout;
    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View layout){
        mSendBtn = layout.findViewById(R.id.send_button);
        mSpinner = layout.findViewById(R.id.spinner_training);
        mNoTraining = layout.findViewById(R.id.no_training_label);
        mAddTrainingPanel = layout.findViewById(R.id.add_training_panel);
        mAddTrainingSuccess = layout.findViewById(R.id.add_training_success);
        ImageView mImageView = layout.findViewById(R.id.success_anim);
        mImageView.setBackgroundResource(R.drawable.success_anim);
        mSuccessAnimation = (AnimatedVectorDrawable) mImageView.getBackground();
        MaterialButton mBackButton = layout.findViewById(R.id.back_home);
        mProgressIndicator = layout.findViewById(R.id.loading_indicator);
        mBackButton.setOnClickListener(v -> {
            dismiss();
            assert getActivity() != null;
            ClientAddFragment parent = (ClientAddFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ClientAddFragment.TAG);
            if(parent!=null){
                parent.dismiss();
            }
        });

        mSendBtn.setOnClickListener(v -> registerTraining());

    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadTrainingInformation(mClientMail);
        mProgressIndicator.setVisibility(View.VISIBLE);
    }



    @Override
    public void trainingListNotEmpty(List<String> trainingNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout);
        adapter.addAll(trainingNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mProgressIndicator.setVisibility(View.GONE);
        mSpinner.setVisibility(View.VISIBLE);
        mSendBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void emptyTrainingList() {
        mProgressIndicator.setVisibility(View.GONE);
        mSendBtn.setVisibility(View.GONE);
        mSendBtn.setVisibility(View.GONE);
        mSpinner.setVisibility(View.GONE);
        mNoTraining.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTrainingAdded() {
        mSuccessAnimation.start();
        mAddTrainingPanel.setVisibility(View.GONE);
        mAddTrainingSuccess.setVisibility(View.VISIBLE);

    }

    private void registerTraining(){
        mSendBtn.setClickable(false);

        String trainingName = (String) mSpinner.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String today = sdf.format(new Date());

        mPresenter.registerTrainingComplete(mClientMail,trainingName, today);
        onTrainingAdded();

    }
}