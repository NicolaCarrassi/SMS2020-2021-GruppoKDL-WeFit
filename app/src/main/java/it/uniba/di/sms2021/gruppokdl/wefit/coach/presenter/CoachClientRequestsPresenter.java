package it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachClientRequestsAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.contract.CoachClientsRequestsContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Request;

public class CoachClientRequestsPresenter implements CoachClientsRequestsContract.Presenter {

    private final CoachClientsRequestsContract.View mView;
    private final Coach mCoach;


    public CoachClientRequestsPresenter(CoachClientsRequestsContract.View view, Coach coach){
        this.mView = view;
        this.mCoach = coach;
    }

    @Override
    public CoachClientRequestsAdapter makeAdapter() {

        FirestoreRecyclerOptions<Request> options = new FirestoreRecyclerOptions.Builder<Request>()
                .setQuery(CoachDAO.queryAllRequests(mCoach.email), Request.class).build();

        return new CoachClientRequestsAdapter(options, this, mCoach);
    }

    @Override
    public void showUserProfile(Request request) {
        if(request != null)
            mView.showClientProfile(request);
    }
}
