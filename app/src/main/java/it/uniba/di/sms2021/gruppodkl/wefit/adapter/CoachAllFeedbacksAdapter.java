package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.CoachAllFeedbacksViewHolder;

public class CoachAllFeedbacksAdapter extends FirestoreRecyclerAdapter<Feedback, CoachAllFeedbacksViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CoachAllFeedbacksAdapter(@NonNull FirestoreRecyclerOptions<Feedback> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(CoachAllFeedbacksViewHolder holder, int position, Feedback model) {
        holder.setValues(model);
    }

    @NonNull
    @Override
    public CoachAllFeedbacksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_feedback_list_item,parent, false);

        return new CoachAllFeedbacksViewHolder(view);
    }
}
