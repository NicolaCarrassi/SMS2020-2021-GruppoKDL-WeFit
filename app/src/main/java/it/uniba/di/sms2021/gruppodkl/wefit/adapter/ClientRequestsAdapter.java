package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientsRequestsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.ClientRequestsViewHolder;

public class ClientRequestsAdapter extends FirestoreRecyclerAdapter<Request, ClientRequestsViewHolder>
        implements ClientRequestsViewHolder.ViewHolderCallback{

    private CoachClientsRequestsContract.Presenter mPresenter;
    private Coach mCoach;


    public ClientRequestsAdapter(FirestoreRecyclerOptions<Request> options, CoachClientsRequestsContract.Presenter presenter, Coach coach) {
        super(options);
        this.mPresenter = presenter;
        this.mCoach = coach;
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
    public void acceptRequest(int position) {
        CoachDAO.handleRequest(mCoach, getItem(position),  true);
    }

    @Override
    public void declineRequest(int position) {
       CoachDAO.handleRequest(mCoach, getItem(position),  false);
    }


}
