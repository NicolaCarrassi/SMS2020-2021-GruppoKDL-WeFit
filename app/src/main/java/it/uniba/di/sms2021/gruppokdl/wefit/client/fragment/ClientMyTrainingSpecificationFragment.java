package it.uniba.di.sms2021.gruppokdl.wefit.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientTrainingSpecificationContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.client.presenter.ClientMyTrainingSpecificationPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.DayOfTheWeek;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingDetailViewHolder;

public class ClientMyTrainingSpecificationFragment extends Fragment implements ClientTrainingSpecificationContract.View {

    public static final String TAG = ClientMyTrainingSpecificationFragment.class.getSimpleName();

    private static final String TRAINING = "training";
    private static final String TWO_PANE = "two_pane";

    private Training mTraining;
    private boolean mTwoPane;

    private FirestorePagingAdapter<Exercise, TrainingDetailViewHolder> mAdapter;


    public ClientMyTrainingSpecificationFragment(){
        // empty constructor
    }

    public static ClientMyTrainingSpecificationFragment newInstance(Training training, boolean twoPane) {
        ClientMyTrainingSpecificationFragment fragment = new ClientMyTrainingSpecificationFragment();

        Bundle args = new Bundle();
        args.putParcelable(TRAINING, training);
        args.putBoolean(TWO_PANE, twoPane);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mTraining = getArguments().getParcelable(TRAINING);
            mTwoPane = getArguments().getBoolean(TWO_PANE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.client_daily_training, container, false);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(layout,act);
        }

        ClientTrainingSpecificationContract.Presenter mPresenter = new ClientMyTrainingSpecificationPresenter(this);

        TextView mTrainingName = layout.findViewById(R.id.training_name);
        TextView mTrainingDay = layout.findViewById(R.id.training_day);
        TextView mTrainingTime = layout.findViewById(R.id.training_time);
        CustomRecyclerView mRecycler = layout.findViewById(R.id.train_recycler);
        TextView mEmpty = layout.findViewById(R.id.no_exercises);

        String temp = mTrainingTime.getText() + "\n" + mTraining.convertDurationTime();

        mTrainingName.setText(mTraining.title);
        mTrainingTime.setText(temp);

        temp = mTrainingDay.getText() + "\n" + DayOfTheWeek.getDayOfTheWeek(mTraining.dayOfWeek, layout);

        mTrainingDay.setText(temp);

        assert getActivity() != null;
        String userMail = ((WeFitApplication)getActivity().getApplicationContext()).getUser().email;

        //GESTIONE RECYCLERVIEW
        mAdapter = mPresenter.getAdapter(userMail, mTraining);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setEmptyView(mEmpty);


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
    public void openExercisePage(String exerciseName) {
        ClientExerciseSpecificationFragment fragment = ClientExerciseSpecificationFragment.newInstance(exerciseName);
        assert getActivity() != null;

        if(mTwoPane)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.training_detail_container, fragment, ClientExerciseSpecificationFragment.TAG)
                    .addToBackStack(ClientExerciseSpecificationFragment.TAG).commit();
        else
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment, ClientExerciseSpecificationFragment.TAG)
                .addToBackStack(ClientExerciseSpecificationFragment.TAG).commit();
    }
}
