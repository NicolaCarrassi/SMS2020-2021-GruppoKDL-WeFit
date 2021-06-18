package it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import java.util.Objects;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachClientsContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.viewholder.CoachMyClientListViewHolder;

public class CoachMyClientListAdapter extends FirestorePagingAdapter<Client, CoachMyClientListViewHolder>
    implements CoachMyClientListViewHolder.ClientListCallbacks{


    private final CoachClientsContract.Presenter mPresenter;

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options paging options
     * @param presenter presenter per le operazioni di callback
     */
    public CoachMyClientListAdapter(@NonNull FirestorePagingOptions<Client> options, CoachClientsContract.Presenter presenter) {
        super(options);

        mPresenter = presenter;
    }

    @NonNull
    @Override
    public CoachMyClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.client_list_item, parent, false);

        return new CoachMyClientListViewHolder(view, this);
    }

    @Override
    protected void onBindViewHolder(@NonNull CoachMyClientListViewHolder holder, int position, @NonNull  Client model) {
        holder.setValues(model);
    }


    @Override
    public void openYourClientProfile(int position){
        String clientMail = Objects.requireNonNull(getItem(position)).getString(Client.ClientKeys.EMAIL);
        mPresenter.onRequestToOpenProfile(clientMail);
    }

}
