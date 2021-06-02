package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRequestsAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientsRequestsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachClientRequestsPresenter;

public class CoachClientsRequestsFragment extends Fragment
    implements CoachClientsRequestsContract.View {

    public static final String TAG = CoachClientsRequestsFragment.class.getSimpleName();


    public CoachClientsRequestsFragment() {
        // Required empty public constructor
    }

    private RecyclerView mRecyclerView;
    private ClientRequestsAdapter mAdapter;
    private WeFitApplication.CallbackOperations mActivity;
    private TextView mEmpty;
    private CoachClientsRequestsContract.Presenter mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof WeFitApplication.CallbackOperations)
            mActivity = (WeFitApplication.CallbackOperations) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.coach_clients_requests_fragment, container, false);

        Coach coach = (Coach) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        mPresenter = new CoachClientRequestsPresenter(this, coach);


        ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(layout, mActivity);
        mRecyclerView = layout.findViewById(R.id.recycler_requests);
        mAdapter = mPresenter.makeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mEmpty = layout.findViewById(R.id.empty_requests);

        return layout;
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




}