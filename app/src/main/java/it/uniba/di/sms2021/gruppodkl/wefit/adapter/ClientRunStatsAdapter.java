package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.ClientRunStatsViewHolder;

public class ClientRunStatsAdapter extends FirestorePagingAdapter<Run, ClientRunStatsViewHolder> {

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public ClientRunStatsAdapter(@NonNull FirestorePagingOptions<Run> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ClientRunStatsViewHolder holder, int position, @NonNull Run model) {
        holder.setValues(model);
    }

    @NonNull
    @Override
    public ClientRunStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_my_runs_list_item, parent, false);

        return new ClientRunStatsViewHolder(layout);
    }
}
