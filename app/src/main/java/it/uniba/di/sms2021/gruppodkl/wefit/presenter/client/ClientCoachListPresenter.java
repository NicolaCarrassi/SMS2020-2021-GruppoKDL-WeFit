package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientCoachListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;




public class ClientCoachListPresenter implements ClientCoachListContract.Presenter {

    private final ClientCoachListContract.View mView;
    private final Client mClient;


    public ClientCoachListPresenter(ClientCoachListContract.View mView, Client currentClient) {
        this.mView = mView;
        mClient = currentClient;
    }

    public CoachListAdapter getAdapter(){

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPrefetchDistance(5)
                .setPageSize(10)
                .build();

        FirestorePagingOptions<Coach> options = new FirestorePagingOptions.Builder<Coach>()
                .setQuery(UserDAO.listAllCoach(), config, Coach.class).build();

        return new CoachListAdapter(options, mClient, this);
    }

    @Override
    public void openCoachProfile(Coach coach) {
        //TODO IN ATTESA DI MODIFICA A PROFILO COACH VISTA CLIENT
    }


}
