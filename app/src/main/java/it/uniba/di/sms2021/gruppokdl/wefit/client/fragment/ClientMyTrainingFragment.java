package it.uniba.di.sms2021.gruppokdl.wefit.fragment.client;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientMyTrainingAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientMyTrainingContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.presenter.client.ClientMyTrainingPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;


public class ClientMyTrainingFragment extends Fragment implements ClientMyTrainingContract.View {

    public static final String TAG = ClientMyTrainingFragment.class.getSimpleName();

    private ClientMyTrainingContract.Presenter mPresenter;

    private ClientMyTrainingAdapter mAdapter;

    private boolean mTwoPane = false;
    private boolean somethingShown = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.client_training_fragment, container, false);

        if(view.findViewById(R.id.training_detail_container) != null){
            mTwoPane = true;
        }
        mPresenter = new ClientMyTrainingPresenter(this);
        bind(view);

        return view;

    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View view){

        if(!mTwoPane && getActivity() instanceof WeFitApplication.CallbackOperations) {
            WeFitApplication.CallbackOperations activity = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, activity);
        }

        CustomRecyclerView mRecyclerView = view.findViewById(R.id.recycler_training_days);
        TextView mEmpty = view.findViewById(R.id.empty_training_days);

        assert getActivity() != null;
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
    public void openTrainingSchedule(Training training) {
        ClientMyTrainingSpecificationFragment fragment = ClientMyTrainingSpecificationFragment.newInstance(training, mTwoPane);
        assert getActivity() != null;
        if (mTwoPane) {
            if(!somethingShown){
                somethingShown = true;
                getChildFragmentManager().beginTransaction()
                        .add(R.id.training_detail_container, fragment, ClientMyTrainingSpecificationFragment.TAG)
                        .addToBackStack(ClientMyTrainingSpecificationFragment.TAG).commit();
            } else
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.training_detail_container, fragment, ClientMyTrainingSpecificationFragment.TAG)
                        .addToBackStack(ClientMyTrainingSpecificationFragment.TAG).commit();
        }else
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.anchor_point, fragment, ClientMyTrainingSpecificationFragment.TAG)
                    .addToBackStack(ClientMyTrainingSpecificationFragment.TAG).commit();
    }




}