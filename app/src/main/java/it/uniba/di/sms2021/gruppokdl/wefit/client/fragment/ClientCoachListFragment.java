package it.uniba.di.sms2021.gruppokdl.wefit.client.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppokdl.wefit.client.ClientRequestCoachSentDialog;
import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientCoachListAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientCoachListContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.client.presenter.ClientCoachListPresenter;

public class ClientCoachListFragment extends Fragment implements ClientCoachListContract.View {

    public static final String TAG = ClientCoachListFragment.class.getSimpleName();

    private ClientCoachListContract.Presenter mPresenter;
    private ClientCoachListAdapter mAdapter;
    private WeFitApplication.CallbackOperations mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.client_coach_list, container, false);

        assert getActivity() != null;
        Client client = (Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        mPresenter = new ClientCoachListPresenter(this, client);

        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(layout, mActivity);

        mAdapter = mPresenter.getAdapter();
        RecyclerView mRecycler = layout.findViewById(R.id.recycler_coach);
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
        mAdapter.setClickable(true);
        Toast.makeText(getActivity(), getResources().getString(R.string.error_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(){
        ClientRequestCoachSentDialog addDialog = new ClientRequestCoachSentDialog(getActivity());
        addDialog.show();
    }

    @Override
    public void onRequestSent() {
        Toast.makeText(getActivity(), getResources().getString(R.string.request_sent), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openCoachProfileWithMail(String mail) {
        ClientMyCoachFragment fragment = ClientMyCoachFragment.newInstance(false ,mail);

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment, ClientMyCoachFragment.TAG)
                .addToBackStack(ClientMyCoachFragment.TAG).commit();
    }
}
