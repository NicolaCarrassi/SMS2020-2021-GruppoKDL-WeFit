package it.uniba.di.sms2021.gruppokdl.wefit.fragment.coach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.button.MaterialButton;



import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.EditTrainingDialog;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachMyClientDailyTrainingContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.presenter.coach.CoachMyClientDailyTrainingPresenter;
import it.uniba.di.sms2021.gruppokdl.wefit.recyclerview.CustomRecyclerView;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.DayOfTheWeek;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingDetailViewHolder;

public class CoachMyClientDailyTrainingFragment extends Fragment implements CoachMyClientDailyTrainingContract.View, EditTrainingDialog.EditTrainingDialogCallbacks {

    public static String TAG;

    private static final String CLIENT_MAIL = "clientMail";
    private static final String TRAINING = "training";

    private String mClientMail;
    private Training mTraining;
    private CoachMyClientDailyTrainingContract.Presenter mPresenter;

    private TextView mTrainingName;
    private TextView mTrainingDay;
    private TextView mTrainingTime;


    private FirestoreRecyclerAdapter<Exercise, TrainingDetailViewHolder> mAdapter;


    public CoachMyClientDailyTrainingFragment(){
        //empty constructor
    }

    public static CoachMyClientDailyTrainingFragment newInstance(String clientMail, Training training) {
        CoachMyClientDailyTrainingFragment fragment = new CoachMyClientDailyTrainingFragment();
        TAG =  CoachMyClientDailyTrainingFragment.class.getSimpleName() + training.getId();
        Bundle args = new Bundle();
        args.putString(CLIENT_MAIL, clientMail);
        args.putParcelable(TRAINING, training);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mClientMail = getArguments().getString(CLIENT_MAIL);
            mTraining = getArguments().getParcelable(TRAINING);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.coach_my_client_daily_traning, container, false);


        mPresenter = new CoachMyClientDailyTrainingPresenter(this);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act =(WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication)getActivity().getApplicationContext()).setToolbar(layout,act);
        }

        mTrainingName = layout.findViewById(R.id.training_name);
        mTrainingDay = layout.findViewById(R.id.training_day);
        mTrainingTime = layout.findViewById(R.id.training_time);
        CustomRecyclerView mRecycler = layout.findViewById(R.id.train_recycler);
        MaterialButton mBtnAddExercises = layout.findViewById(R.id.btn_add_exercise);
        TextView mEmpty = layout.findViewById(R.id.no_exercises);
        ImageView mEditTrainingImage = layout.findViewById(R.id.edit_training_info);

        String trainingTime = mTrainingTime.getText() + mTraining.convertDurationTime();
        String trainingDay = mTrainingDay.getText() + DayOfTheWeek.getDayOfTheWeek(mTraining.dayOfWeek, layout);

        mTrainingName.setText(mTraining.title);
        mTrainingTime.setText(trainingTime);
        mTrainingDay.setText(trainingDay);


        //GESTIONE RECYCLERVIEW
        mAdapter = mPresenter.getAdapter(mClientMail, mTraining);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setEmptyView(mEmpty);


        //IMPOSTO LISTENERS PER IL BOTTONE
        mBtnAddExercises.setOnClickListener(v -> showAdd());
        mEditTrainingImage.setOnClickListener(v -> openEditTrainingDialog());

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

    /**
     * Il metodo permette di mostrare il fragment per aggiungere un esercizio
     */
    private void showAdd(){
        final CoachAddExerciseFragment addFragment = CoachAddExerciseFragment.newInstance(mClientMail,mTraining);
        assert getActivity() != null;
        addFragment.show(getActivity().getSupportFragmentManager(), CoachAddExerciseFragment.TAG);
    }


    @Override
    public void editTraining(Training training){
        mTraining = training;
        String trainingDay = getResources().getString(R.string.training_day) + DayOfTheWeek.getDayOfTheWeek(mTraining.dayOfWeek,getView());
        String trainingDuration = getResources().getString(R.string.duration) + mTraining.convertDurationTime();

        // UPDATE INFO NELLA VIEW
        mTrainingDay.setText(trainingDay); //FUNZIONA?
        mTrainingName.setText(mTraining.title);
        mTrainingTime.setText(trainingDuration);

        mPresenter.updateTraining(mClientMail, mTraining);
    }

    /**
     * Il metodo permette di aprire il dialogo per modificare le informazioni
     * dell'allenamento
     */
    private void openEditTrainingDialog(){
        assert getActivity() != null;
        EditTrainingDialog dialog = new EditTrainingDialog(getActivity(), this, mTraining);
        dialog.show();
    }




}

