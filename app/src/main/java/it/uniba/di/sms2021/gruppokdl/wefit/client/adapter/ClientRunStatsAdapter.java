package it.uniba.di.sms2021.gruppokdl.wefit.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientRunStatsContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;
import it.uniba.di.sms2021.gruppokdl.wefit.client.viewholder.ClientRunStatsViewHolder;

public class ClientRunStatsAdapter extends FirestorePagingAdapter<Run, ClientRunStatsViewHolder> implements ClientRunStatsViewHolder.ClientRunCallback {

    private final ClientRunStatsContract.Presenter mPresenter;

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options opzioni
     */
    public ClientRunStatsAdapter(@NonNull FirestorePagingOptions<Run> options, ClientRunStatsContract.Presenter presenter) {
        super(options);
        mPresenter = presenter;
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

        return new ClientRunStatsViewHolder(layout, this);
    }

    @Override
    public void openRunDetail(int position) {
        Run run = getItem(position).toObject(Run.class);
        mPresenter.openSpecifiedRun(run);
    }
}
