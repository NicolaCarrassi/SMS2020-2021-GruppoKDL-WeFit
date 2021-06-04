package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.ClientMyTrainingViewHolder;

public class ClientMyTrainingAdapter extends FirestorePagingAdapter<Training,ClientMyTrainingViewHolder> implements ClientMyTrainingViewHolder.ClientMyTrainingCallbacks {


    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public ClientMyTrainingAdapter(FirestorePagingOptions<Training> options) {
        super(options);
    }

    @Override
    public ClientMyTrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_training_day_item, parent, false);
        return new ClientMyTrainingViewHolder(view, this);
    }

    @Override
    protected void onBindViewHolder(ClientMyTrainingViewHolder holder, int position, Training model) {
        holder.setValues(model);
    }


}
