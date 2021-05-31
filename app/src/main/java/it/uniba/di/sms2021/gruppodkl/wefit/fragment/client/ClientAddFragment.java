package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;


import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import it.uniba.di.sms2021.gruppodkl.wefit.R;

public class ClientAddFragment extends BottomSheetDialogFragment {

    public static final String TAG = ClientAddFragment.class.getSimpleName();
    private LinearLayout mAddWeight;
    private LinearLayout mAddTraining;
    private LinearLayout mAddPanel;
    private LinearLayout mAddMeal;



    public ClientAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.client_add_fragment, container, false);

        bind(layout);
        setListener();

        return layout;
    }

    private void bind(View layout){
        mAddPanel = layout.findViewById(R.id.add_container);
        mAddWeight = layout.findViewById(R.id.add_weight);
        mAddTraining = layout.findViewById(R.id.add_training);
        mAddMeal = layout.findViewById(R.id.add_meal);

    }

    private void setListener(){
        mAddWeight.setOnClickListener(v -> {
            mAddPanel.setVisibility(View.GONE);
            final ClientAddWeightFragment clientAddWeightFragment = new ClientAddWeightFragment();

            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.bottom_sheet_container, clientAddWeightFragment, ClientAddWeightFragment.TAG).commit();
        });

        mAddTraining.setOnClickListener(v -> {
            mAddPanel.setVisibility(View.GONE);
            final ClientAddTrainingFragment clientAddTrainingFragment = new ClientAddTrainingFragment();

            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.bottom_sheet_container, clientAddTrainingFragment, ClientAddTrainingFragment.TAG).commit();
        });

        mAddMeal.setOnClickListener(v -> {
            mAddPanel.setVisibility(View.GONE);
            final ClientAddMealFragment clientAddMealFragment = new ClientAddMealFragment();

            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.bottom_sheet_container, clientAddMealFragment, ClientAddMealFragment.TAG).commit();
        });
    }
}