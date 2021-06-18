package it.uniba.di.sms2021.gruppokdl.wefit.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import java.util.Objects;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientTrainingSpecificationContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingDetailViewHolder;

public class ClientTrainingSpecificationListAdapter extends FirestorePagingAdapter<Exercise, TrainingDetailViewHolder> implements TrainingDetailViewHolder.TrainingDetailClient {

    private final ClientTrainingSpecificationContract.Presenter mPresenter;

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options opzioni di sistema
     * @param presenter presenter
     */
    public ClientTrainingSpecificationListAdapter(@NonNull  FirestorePagingOptions<Exercise> options, ClientTrainingSpecificationContract.Presenter presenter) {
        super(options);
        this.mPresenter = presenter;
    }

    @Override
    protected void onBindViewHolder(@NonNull  TrainingDetailViewHolder holder, int position, @NonNull Exercise model) {
        holder.setValues(model);
    }

    @NonNull
    @Override
    public TrainingDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_exercise_item, parent, false);

        return new TrainingDetailViewHolder(view, this);
    }

    @Override
    public void showExerciseInfo(int position) {
        mPresenter.showExercise(Objects.requireNonNull(getItem(position)).toObject(Exercise.class));
    }
}
