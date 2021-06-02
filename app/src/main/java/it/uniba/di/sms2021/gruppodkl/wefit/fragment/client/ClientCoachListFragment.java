package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.AddFeedbackDialog;
import it.uniba.di.sms2021.gruppodkl.wefit.ClientRequestCoachSentDialog;
import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientCoachListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientCoachListPresenter;

public class ClientCoachListFragment extends Fragment implements ClientCoachListContract.View {

    public static final String TAG = ClientCoachListFragment.class.getSimpleName();

    private ClientCoachListContract.Presenter mPresenter;
    private RecyclerView mRecycler;
    private CoachListAdapter mAdapter;
    private Client mClient;
    private WeFitApplication.CallbackOperations mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.client_coach_list, container, false);

        mClient = (Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        mPresenter = new ClientCoachListPresenter(this, mClient);

        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(layout, mActivity);

        mAdapter = mPresenter.getAdapter();
        mRecycler = layout.findViewById(R.id.recycler_coach);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);

        return  layout;
    }

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
    public void onFailure() {
        mAdapter.setClickable(false);
        //TODO Notifica errore all'utente
    }

    @Override
    public void onSuccess(){
        ClientRequestCoachSentDialog addDialog = new ClientRequestCoachSentDialog(getActivity());
        addDialog.show();
    }

}
