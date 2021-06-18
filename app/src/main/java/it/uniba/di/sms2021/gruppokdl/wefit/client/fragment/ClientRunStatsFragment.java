package it.uniba.di.sms2021.gruppokdl.wefit.client.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientRunStatsAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientRunStatsContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;
import it.uniba.di.sms2021.gruppokdl.wefit.client.presenter.ClientRunStatsPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;


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

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(view, act);
        }


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

    @Override
    public void openRun(Run run) {
        ClientRunDetailFragment fragment = ClientRunDetailFragment.newInstance(run);

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment, ClientRunDetailFragment.TAG)
                .addToBackStack(ClientRunDetailFragment.TAG).commit();
    }

    @Override
    public void runNotFound() {
        Toast.makeText(getActivity(), R.string.error_general, Toast.LENGTH_SHORT).show();
    }
}