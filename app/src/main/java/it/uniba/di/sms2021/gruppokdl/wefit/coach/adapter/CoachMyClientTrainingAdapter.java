package it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachMyClientScheduleContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingViewHolder;

public class CoachMyClientTrainingAdapter extends FirestoreRecyclerAdapter<Training, TrainingViewHolder>
    implements TrainingViewHolder.ClientMyTrainingCallbacks{

    private final CoachMyClientScheduleContract.Presenter mPresenter;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options opzioni di configurazione
     */
    public CoachMyClientTrainingAdapter(@NonNull FirestoreRecyclerOptions<Training> options, CoachMyClientScheduleContract.Presenter presenter) {
        super(options);
        mPresenter = presenter;
    }

    @Override
    protected void onBindViewHolder(@NonNull TrainingViewHolder holder, int position, @NonNull Training model) {
        holder.setValues(model, true);
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_day_item, parent, false);
        return new TrainingViewHolder(view, this);
    }

    @Override
    public void deleteTraining(int position) {
        mPresenter.deleteTraining(getItem(position));
    }

    @Override
    public void onElementChecked(int position) {
        mPresenter.openTrainingSpecification(getItem(position));
    }
}
