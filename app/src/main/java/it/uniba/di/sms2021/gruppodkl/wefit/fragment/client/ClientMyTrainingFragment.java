package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientMyTrainingAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientMyTrainingContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientMyTrainingPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.recyclerview.CustomRecyclerView;


public class ClientMyTrainingFragment extends Fragment implements ClientMyTrainingContract.View {

    public static final String TAG = ClientMyTrainingFragment.class.getSimpleName();

    private ClientMyTrainingContract.Presenter mPresenter;

    private CustomRecyclerView mRecyclerView;
    private TextView mEmpty;
    private ClientMyTrainingAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.client_training_fragment, container, false);
        mPresenter = new ClientMyTrainingPresenter(this);
        bind(view);

        return view;

    }

    private void bind(View view){

        if(getActivity() instanceof WeFitApplication.CallbackOperations) {
            WeFitApplication.CallbackOperations activity = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, activity);
        }

        mRecyclerView = view.findViewById(R.id.recycler_training_days);
        mEmpty = view.findViewById(R.id.empty_training_days);

        String clientEmail = ((WeFitApplication) getActivity().getApplicationContext()).getUser().email;
        mAdapter = mPresenter.getAdapter(clientEmail);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setEmptyView(mEmpty);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    @Override
    //TODO AGGIUNGI LOGICA MASTER FLOW DETAIL
    public void openTrainingSchedule(Training training) {
        ClientMyTrainingSpecificationFragment fragment = ClientMyTrainingSpecificationFragment.newInstance(training);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.anchor_point,fragment,ClientMyTrainingSpecificationFragment.TAG)
                .addToBackStack(ClientMyTrainingSpecificationFragment.TAG).commit();
    }

}