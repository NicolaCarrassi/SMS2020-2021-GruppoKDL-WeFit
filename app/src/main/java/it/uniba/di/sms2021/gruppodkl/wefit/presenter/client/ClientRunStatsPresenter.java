package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientRunStatsAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientRunStatsContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;

public class ClientRunStatsPresenter implements ClientRunStatsContract.Presenter {

    private final ClientRunStatsContract.View mView;

    public ClientRunStatsPresenter(ClientRunStatsContract.View view){this.mView = view;}


    @Override
    public ClientRunStatsAdapter getAdapter(String clientMail) {

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(6)
                .setPrefetchDistance(3)
                .setPageSize(6)
                .build();

        FirestorePagingOptions<Run> options = new FirestorePagingOptions.Builder<Run>()
                .setQuery(ClientDAO.queryAllRun(clientMail), config, Run.class).build();

        return  new ClientRunStatsAdapter(options);
    }
}
