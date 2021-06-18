package it.uniba.di.sms2021.gruppokdl.wefit.fragment.coach;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachClientRequestsAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachClientsRequestsContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Request;
import it.uniba.di.sms2021.gruppokdl.wefit.presenter.coach.CoachClientRequestsPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;

public class CoachClientsRequestsFragment extends Fragment
    implements CoachClientsRequestsContract.View {

    public static final String TAG = CoachClientsRequestsFragment.class.getSimpleName();


    public CoachClientsRequestsFragment() {
        // Required empty public constructor
    }

    private CoachClientRequestsAdapter mAdapter;
    private WeFitApplication.CallbackOperations mActivity;
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

        assert getActivity() != null;
        Coach coach = (Coach) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        mPresenter = new CoachClientRequestsPresenter(this, coach);


        ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(layout, mActivity);
        CustomRecyclerView mRecyclerView = layout.findViewById(R.id.recycler_requests);
        mAdapter = mPresenter.makeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        TextView mEmpty = layout.findViewById(R.id.empty_requests);
        mRecyclerView.setEmptyView(mEmpty);

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


    @Override
    public void showClientProfile(Request request) {
        CoachClientRequestProfileFragment fragment = CoachClientRequestProfileFragment.newInstance(request);

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment,CoachClientRequestProfileFragment.TAG)
                .addToBackStack(CoachClientRequestProfileFragment.TAG).commit();
    }
}