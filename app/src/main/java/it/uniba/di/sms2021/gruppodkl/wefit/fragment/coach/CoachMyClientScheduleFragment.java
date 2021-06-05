package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.button.MaterialButton;


import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientScheduleContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachMyClientSchedulePresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.recyclerview.CustomRecyclerView;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachMyClientScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachMyClientScheduleFragment extends Fragment implements CoachMyClientScheduleContract.View{

    public static final String TAG = CoachMyClientScheduleFragment.class.getSimpleName();

    private static final String CLIENT_MAIL = "coachMail";
    private String mClientMail;

    private TextView mClientName;
    private CustomRecyclerView mRecyclerView;
    private TextView mEmptyTrainingLabel;
    private MaterialButton mAddTrainingButton;
    private FirestoreRecyclerAdapter<Training, TrainingViewHolder> mAdapter;

    private CoachMyClientScheduleContract.Presenter mPresenter;


    public CoachMyClientScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clientMail mail del cliente di cui si vuole visualizzare la schedule
     * @return A new instance of fragment CoachMyClientScheduleFragment.
     */
    public static CoachMyClientScheduleFragment newInstance(String clientMail) {
        CoachMyClientScheduleFragment fragment = new CoachMyClientScheduleFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mClientMail = getArguments().getString(CLIENT_MAIL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.coach_my_client_schedule_fragment, container, false);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view,act);
        }

        mPresenter = new CoachMyClientSchedulePresenter(this, mClientMail);

        mClientName = view.findViewById(R.id.client_name);
        mRecyclerView = view.findViewById(R.id.train_recycler);
        mEmptyTrainingLabel = view.findViewById(R.id.no_trainings);
        mAddTrainingButton = view.findViewById(R.id.btn_new_training);

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


}