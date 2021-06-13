package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRunStatsAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientRunStatsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientRunStatsPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.recyclerview.CustomRecyclerView;


public class ClientRunStatsFragment extends Fragment implements ClientRunStatsContract.View {


    public static final String TAG = ClientRunStatsFragment.class.getSimpleName();

    private ClientRunStatsContract.Presenter mPresenter;
    private ClientRunStatsAdapter mAdapter;

    public ClientRunStatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.client_run_stats_fragment, container, false);

        mPresenter = new ClientRunStatsPresenter(this);
        assert getActivity() != null;
        String clientMail = ((WeFitApplication) getActivity().getApplicationContext()).getUser().email;

        CustomRecyclerView mRecycler = view.findViewById(R.id.recycler_runs);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setEmptyView(view.findViewById(R.id.empty));

        mAdapter = mPresenter.getAdapter(clientMail);
        mRecycler.setAdapter(mAdapter);

        return view;
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