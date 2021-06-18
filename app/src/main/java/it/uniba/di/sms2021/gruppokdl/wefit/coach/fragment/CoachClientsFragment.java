package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachMyClientListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachClientsPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.recyclerview.CustomRecyclerView;


public class CoachClientsFragment extends Fragment implements CoachClientsContract.View {

    public static final String TAG = CoachClientsFragment.class.getSimpleName();
    private CoachMyClientListAdapter mAdapter;


    private CoachClientsContract.Presenter mPresenter;

    public CoachClientsFragment(){
        //empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.coach_clients_fragment, container, false);

        mPresenter = new CoachClientsPresenter(this);

        bind(layout);

        return layout;
    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View view){
        //toolbar
        if(getActivity() instanceof WeFitApplication.CallbackOperations) {
            WeFitApplication.CallbackOperations activity = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, activity);
        }

        //prendi elementi view
        CustomRecyclerView recyclerView = view.findViewById(R.id.recycler_client_list);
        TextView emptyView = view.findViewById(R.id.empty_client_list);

        //gestione recycler
        assert getActivity() != null;
        String coachEmail = ((WeFitApplication) getActivity().getApplicationContext()).getUser().email;
        mAdapter = mPresenter.getAdapter(coachEmail);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setEmptyView(emptyView);
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
    public void openClientProfile(String clientMail) {
        Fragment clientProfileFragment = CoachMyClientProfileFragment.newInstance(clientMail);

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,clientProfileFragment, CoachMyClientProfileFragment.TAG)
                .addToBackStack(CoachMyClientProfileFragment.TAG).commit();
    }

    @Override
    public void errorOpeningProfile() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error_loading_profile), Toast.LENGTH_SHORT).show();
    }
}