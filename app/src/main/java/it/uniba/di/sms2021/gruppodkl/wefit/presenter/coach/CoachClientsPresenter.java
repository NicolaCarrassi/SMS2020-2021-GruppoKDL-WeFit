package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachMyClientListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;

public class CoachClientsPresenter implements CoachClientsContract.Presenter {

    private final CoachClientsContract.View mView;


    public CoachClientsPresenter(CoachClientsContract.View view) {
        this.mView = view;
    }

    @Override
    public CoachMyClientListAdapter getAdapter(String coachEmail) {

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPrefetchDistance(5)
                .setPageSize(10)
                .build();

        FirestorePagingOptions<Client> options = new FirestorePagingOptions.Builder<Client>()
                .setQuery(CoachDAO.queryClentsList(coachEmail), config, Client.class).build();

        return new CoachMyClientListAdapter(options,this);
    }

    @Override
    public void onRequestToOpenProfile(String clientEmail){
        if(clientEmail != null)
            mView.openClientProfile(clientEmail);
        else
            mView.errorOpeningProfile();
    }

}


