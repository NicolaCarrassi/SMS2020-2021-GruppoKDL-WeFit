package it.uniba.di.sms2021.gruppokdl.wefit.client.fragment;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.preference.PreferenceManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientAddWeightContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.client.presenter.ClientAddWeightPresenter;


public class ClientAddWeightFragment extends BottomSheetDialogFragment implements ClientAddWeightContract.View {

    public static final String TAG = ClientAddWeightFragment.class.getSimpleName();

    private static final float MIN_WEIGHT = 40.0f;

    private MaterialButton mButtonDecrease;
    private MaterialButton mButtonIncrease;
    private EditText mWeightValue;
    private MaterialButton mAddWeight;
    private Client mClient;
    private ClientAddWeightPresenter mPresenter;
    private AnimatedVectorDrawable mSuccessAnimation;
    private MaterialButton mBackButton;
    private LinearLayout mAddWeightPanel;
    private LinearLayout mAddWeightSuccess;


    private SharedPreferences prefs;
    private LinearLayout mWeightLimitLabel;

    public ClientAddWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.client_add_weight_fragment, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mPresenter = new ClientAddWeightPresenter(this);
        assert getActivity() != null;
        mClient =(Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();

        bind(layout);
        setListener();

        return layout;
    }


    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View layout){
        String weight = Float.toString(mClient.weight);

        mButtonDecrease = layout.findViewById(R.id.buttonDecrease);
        mButtonIncrease = layout.findViewById(R.id.buttonIncrease);
        mWeightValue = layout.findViewById(R.id.weightValue);
        mWeightValue.setText(weight);
        mAddWeight = layout.findViewById(R.id.btn_add_weight);
        ImageView mImageView = layout.findViewById(R.id.success_anim);
        mImageView.setBackgroundResource(R.drawable.success_anim);
        mSuccessAnimation = (AnimatedVectorDrawable) mImageView.getBackground();
        mAddWeightPanel = layout.findViewById(R.id.add_weight_panel);
        mAddWeightSuccess = layout.findViewById(R.id.add_weight_success);
        mBackButton = layout.findViewById(R.id.back_home);

        mWeightLimitLabel = layout.findViewById(R.id.weight_limit_label);
        if (isNewDay()){
            mAddWeightPanel.setVisibility(View.GONE);
            mWeightLimitLabel.setVisibility(View.VISIBLE);
        }else{
            mAddWeightPanel.setVisibility(View.VISIBLE);
            mWeightLimitLabel.setVisibility(View.GONE);
        }
    }

    /**
     * Il metodo permette di impostare i listeners
     *
     */
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

                Date date = new Date(System.currentTimeMillis());
                prefs.edit().putLong("time", date.getTime()).apply();
            }
        });

        mBackButton.setOnClickListener(v -> {
            dismiss();
            assert getActivity() != null;
            ClientAddFragment parent = (ClientAddFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ClientAddFragment.TAG);
            if(parent!=null){
                parent.dismiss();
            }
        });
    }

    @Override
    public void onSuccess() {
        mAddWeightPanel.setVisibility(View.GONE);
        mAddWeightSuccess.setVisibility(View.VISIBLE);
        mSuccessAnimation.start();
    }

    private boolean isNewDay(){
        boolean check = false;
        Date prevDate = new Date(prefs.getLong("time", 0));
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        if(fmt.format(prevDate).equals(fmt.format(today))) {
            check = true;
        }
        return check;
    }

}