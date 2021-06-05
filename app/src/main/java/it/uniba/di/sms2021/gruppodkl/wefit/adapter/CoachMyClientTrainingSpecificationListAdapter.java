package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingDetailViewHolder;

public class CoachMyClientTrainingSpecificationListAdapter extends FirestoreRecyclerAdapter<Exercise, TrainingDetailViewHolder>
        implements TrainingDetailViewHolder.TrainingDetailCoach {


    private final String mClientMail;
    private final String mTrainingId;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options opzioni di configurazione
     */
    public CoachMyClientTrainingSpecificationListAdapter(@NonNull FirestoreRecyclerOptions<Exercise> options, String cilentMail, String trainingId) {
        super(options);
        this.mClientMail = cilentMail;
        this.mTrainingId = trainingId;
    }


    @NonNull
    @Override
    public TrainingDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_exercise_item, parent, false);

        return new TrainingDetailViewHolder(view, this);
    }

    @Override
    protected void onBindViewHolder(@NonNull  TrainingDetailViewHolder holder, int position, @NonNull  Exercise model) {
        holder.setValues(model);
    }



    @Override
    public void deleteExercise(int position) {
        TrainingDAO.deleteExercise(getItem(position).getId(),mTrainingId, mClientMail);
    }
}
