package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientsRequestsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.ClientRequestsViewHolder;

public class ClientRequestsAdapter extends FirestorePagingAdapter<Request, ClientRequestsViewHolder>
        implements ClientRequestsViewHolder.ViewHolderCallback {

    private CoachClientsRequestsContract.Presenter mPresenter;


    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public ClientRequestsAdapter(FirestorePagingOptions<Request> options, CoachClientsRequestsContract.Presenter presenter) {
        super(options);
        this.mPresenter = presenter;
    }

    @Override
    protected void onBindViewHolder(@NonNull ClientRequestsViewHolder holder, int position, @NonNull Request model) {
      holder.setValues(model);
    }


    @Override
    public ClientRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_list_requests_item, parent, false);

       return  new ClientRequestsViewHolder(view, this);
    }

    @Override
    public void acceptRequest(Request request) {
        //TODO Gestisci accetta
    }

    @Override
    public void declineRequest(Request request) {
        //TODO Gestisci rifiuta
    }
}
