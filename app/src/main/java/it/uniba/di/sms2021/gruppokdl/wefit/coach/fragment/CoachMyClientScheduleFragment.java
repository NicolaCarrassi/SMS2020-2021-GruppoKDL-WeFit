package it.uniba.di.sms2021.gruppokdl.wefit.coach.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.button.MaterialButton;


import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.contract.CoachMyClientScheduleContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter.CoachMyClientSchedulePresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachMyClientScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachMyClientScheduleFragment extends Fragment implements CoachMyClientScheduleContract.View{

    public static final String TAG = CoachMyClientScheduleFragment.class.getSimpleName();

    private static final String CLIENT_NAME = "clientName";
    private String mClientName;
    private static final String CLIENT_MAIL = "coachMail";
    private String mClientMail;

    private FirestoreRecyclerAdapter<Training, TrainingViewHolder> mAdapter;

    private CoachMyClientScheduleContract.Presenter mPresenter;
    private boolean mTwoPane = false;
    private boolean mSomethingAdded = false;
    private TextView mClientNameTextView;


    public CoachMyClientScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clientMail mail del cliente di cui si vuole visualizzare la schedule
     * @param clientName nome del cliente di cui si visualizza la schedule
     * @return A new instance of fragment CoachMyClientScheduleFragment.
     */
    public static CoachMyClientScheduleFragment newInstance(String clientMail, String clientName) {
        CoachMyClientScheduleFragment fragment = new CoachMyClientScheduleFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        args.putString(CLIENT_NAME,clientName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClientMail = getArguments().getString(CLIENT_MAIL);
            mClientName = getArguments().getString(CLIENT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.coach_my_client_schedule_fragment, container, false);

        if(view.findViewById(R.id.training_detail_container) != null)
            mTwoPane = true;
        else if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view,act);
        }

        mClientNameTextView = view.findViewById(R.id.client_name);
        mClientNameTextView.setText(mClientName);

        mPresenter = new CoachMyClientSchedulePresenter(this, mClientMail);

        CustomRecyclerView mRecyclerView = view.findViewById(R.id.train_recycler);
        TextView mEmptyTrainingLabel = view.findViewById(R.id.no_trainings);
        MaterialButton mAddTrainingButton = view.findViewById(R.id.btn_new_training);

        mAdapter = mPresenter.getAdapter(mClientMail);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setEmptyView(mEmptyTrainingLabel);

        mAddTrainingButton.setOnClickListener(v -> addNewTraining());

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

    private void addNewTraining() {
        String defaultTrainingName = getResources().getString(R.string.new_training);
        mPresenter.addNewTraining(defaultTrainingName);
    }

    @Override
    public void openTrainingSpecification(Training training) {
        CoachMyClientDailyTrainingFragment fragment = CoachMyClientDailyTrainingFragment.newInstance(mClientMail,training);
        int containerId = mTwoPane ? R.id.training_detail_container : R.id.anchor_point;
        assert getActivity() != null;

        if(mTwoPane){
            if(!mSomethingAdded){
                mSomethingAdded = true;
                getChildFragmentManager().beginTransaction()
                        .add(containerId, fragment, CoachMyClientDailyTrainingFragment.TAG)
                        .addToBackStack(CoachMyClientDailyTrainingFragment.TAG).commit();
            } else
                getChildFragmentManager().beginTransaction()
                        .replace(containerId, fragment, CoachMyClientDailyTrainingFragment.TAG)
                        .addToBackStack(CoachMyClientDailyTrainingFragment.TAG).commit();
        }

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, CoachMyClientDailyTrainingFragment.TAG)
                .addToBackStack(CoachMyClientDailyTrainingFragment.TAG).commit();
    }
}