package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachAllFeedbacksAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachAllFeedbacksContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachAllFeedbacksPresenter;

public class CoachAllFeedbackFragment extends Fragment implements CoachAllFeedbacksContract.View{

    public static final String TAG = CoachAllFeedbackFragment.class.getSimpleName();

    private CoachAllFeedbacksAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.coach_all_feedback_fragment, container, false);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations activity = (WeFitApplication.CallbackOperations)getActivity();
            ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(layout, activity);
        }

        CoachAllFeedbacksContract.Presenter mPresenter = new CoachAllFeedbacksPresenter(this);
        RecyclerView mRecycler = layout.findViewById(R.id.recycler_feedback);

        assert getActivity() != null;
        Coach mCoach = (Coach) ((WeFitApplication) getActivity().getApplicationContext()).getUser();
        mAdapter = mPresenter.getAdapter(mCoach.email);

        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

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
