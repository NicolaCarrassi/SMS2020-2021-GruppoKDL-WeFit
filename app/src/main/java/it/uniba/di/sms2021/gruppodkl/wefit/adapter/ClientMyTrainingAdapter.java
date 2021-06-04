package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingViewHolder;

public class ClientMyTrainingAdapter extends FirestorePagingAdapter<Training, TrainingViewHolder> implements TrainingViewHolder.ClientMyTrainingCallbacks {


    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public ClientMyTrainingAdapter(FirestorePagingOptions<Training> options) {
        super(options);
    }

    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_day_item, parent, false);
        return new TrainingViewHolder(view, this);
    }

    @Override
    protected void onBindViewHolder(TrainingViewHolder holder, int position, Training model) {
        holder.setValues(model, false);
    }


    @Override
    public void deleteTraining(int position) {
        //NON NECESSARIO, IL CLIENT NON HA PERMESSI PER CANCELLARE GLI ALLENAMENTI
    }

    @Override
    public void onElementChecked(int position) {
        //TODO APERTURA PAGINA
    }
}
