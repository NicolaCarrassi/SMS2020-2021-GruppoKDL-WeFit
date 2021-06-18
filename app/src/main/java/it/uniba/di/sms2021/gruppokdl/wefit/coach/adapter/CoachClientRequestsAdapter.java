package it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachClientsRequestsContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Request;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.viewholder.CoachClientRequestsViewHolder;

public class CoachClientRequestsAdapter extends FirestoreRecyclerAdapter<Request, CoachClientRequestsViewHolder>
        implements CoachClientRequestsViewHolder.ViewHolderCallback{

    private final CoachClientsRequestsContract.Presenter mPresenter;
    private final Coach mCoach;


    public CoachClientRequestsAdapter(FirestoreRecyclerOptions<Request> options, CoachClientsRequestsContract.Presenter presenter, Coach coach) {
        super(options);
        this.mPresenter = presenter;
        this.mCoach = coach;
    }

    @Override
    protected void onBindViewHolder(@NonNull CoachClientRequestsViewHolder holder, int position, @NonNull Request model) {
      holder.setValues(model);
    }


    @NonNull
    @Override
    public CoachClientRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_list_requests_item, parent, false);

       return  new CoachClientRequestsViewHolder(view, this);
    }

    @Override
    public void acceptRequest(int position) {
        CoachDAO.handleRequest(mCoach, getItem(position),  true);
    }

    @Override
    public void declineRequest(int position) {
       CoachDAO.handleRequest(mCoach, getItem(position),  false);
    }

    @Override
    public void showUserProfile(int position) {
        mPresenter.showUserProfile(getItem(position));
    }


}
