package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientTrainingSpecificationContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientMyTrainingSpecificationPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.recyclerview.CustomRecyclerView;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingDetailViewHolder;

public class ClientMyTrainingSpecificationFragment extends Fragment implements ClientTrainingSpecificationContract.View {

    public static final String TAG = ClientMyTrainingSpecificationFragment.class.getSimpleName();

    private static final String TRAINING = "training";

    private Training mTraining;

    private FirestorePagingAdapter<Exercise, TrainingDetailViewHolder> mAdapter;


    public ClientMyTrainingSpecificationFragment(){
        // empty constructor
    }

    public static ClientMyTrainingSpecificationFragment newInstance(Training training) {
        ClientMyTrainingSpecificationFragment fragment = new ClientMyTrainingSpecificationFragment();


        Bundle args = new Bundle();
        args.putParcelable(TRAINING, training);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
            mTraining = getArguments().getParcelable(TRAINING);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.client_daily_training, container, false);

        ClientTrainingSpecificationContract.Presenter mPresenter = new ClientMyTrainingSpecificationPresenter(this);

        TextView mTrainingName = layout.findViewById(R.id.training_name);
        TextView mTrainingDay = layout.findViewById(R.id.training_day);
        TextView mTrainingTime = layout.findViewById(R.id.training_time);
        CustomRecyclerView mRecycler = layout.findViewById(R.id.train_recycler);
        TextView mEmpty = layout.findViewById(R.id.no_exercises);

        mTrainingName.setText(mTraining.title);
        mTrainingTime.setText(mTrainingTime.getText() + mTraining.getDurationTime());
        mTrainingDay.setText(mTrainingDay.getText() + getDayOfTheWeek(mTraining.dayOfWeek));

        User user = ((WeFitApplication)getActivity().getApplicationContext()).getUser();

        //GESTIONE RECYCLERVIEW
        mAdapter = mPresenter.getAdapter(user.email, mTraining);
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

    private String getDayOfTheWeek(int day){
        String dayOfTheWeek;
        switch (day){
            case Keys.WeekDay.SUNDAY:
                dayOfTheWeek =  getResources().getString(R.string.sunday);
                break;
            case Keys.WeekDay.MONDAY:
                dayOfTheWeek =  getResources().getString(R.string.monday);
                break;
            case Keys.WeekDay.TUESDAY:
                dayOfTheWeek =  getResources().getString(R.string.tuesday);
                break;
            case Keys.WeekDay.WEDNESDAY:
                dayOfTheWeek =  getResources().getString(R.string.wednesday);
                break;
            case Keys.WeekDay.THURSDAY:
                dayOfTheWeek =  getResources().getString(R.string.thursday);
                break;
            case Keys.WeekDay.FRIDAY:
                dayOfTheWeek =  getResources().getString(R.string.friday);
                break;
            case Keys.WeekDay.SATURDAY:
                dayOfTheWeek =  getResources().getString(R.string.saturday);
                break;
            default:
                dayOfTheWeek ="";
        }
        return dayOfTheWeek;
    }



}