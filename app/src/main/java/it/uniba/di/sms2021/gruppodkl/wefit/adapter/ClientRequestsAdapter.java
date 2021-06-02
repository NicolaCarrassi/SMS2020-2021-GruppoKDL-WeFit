package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.ClientRequestsViewHolder;

public class ClientRequestsAdapter extends FirestorePagingAdapter<Client, ClientRequestsViewHolder> implements ClientRequestsViewHolder.ViewHolderCallback {

    private Coach mCurrentCoach;
    //private mPresenter;


    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public ClientRequestsAdapter(@NonNull FirestorePagingOptions<Client> options, Coach currentCoach) {
        super(options);
        mCurrentCoach = currentCoach;
    }

    @Override
    protected void onBindViewHolder(@NonNull ClientRequestsViewHolder holder, int position, @NonNull Client model) {
      //holder.setValues(model);
    }


    @Override
    public ClientRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_list_requests_item, parent, false);

       return  new ClientRequestsViewHolder(view, this);
    }
}
