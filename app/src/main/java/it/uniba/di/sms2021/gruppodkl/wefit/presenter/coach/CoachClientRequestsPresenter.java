package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRequestsAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientsRequestsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;

public class CoachClientRequestsPresenter implements CoachClientsRequestsContract.Presenter {

    private final CoachClientsRequestsContract.View mView;
    private Coach mCoach;


    public CoachClientRequestsPresenter(CoachClientsRequestsContract.View view, Coach coach){
        this.mView = view;
        this.mCoach = coach;
    }

    @Override
    public ClientRequestsAdapter makeAdapter() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(5)
                .setPrefetchDistance(3)
                .setPageSize(5)
                .build();

        FirestorePagingOptions<Request> options = new FirestorePagingOptions.Builder<Request>()
                .setQuery(CoachDAO.queryAllRequests(mCoach.email), config, Request.class).build();

        return new ClientRequestsAdapter(options, this);
    }
}
