package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientRunStatsAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientRunStatsContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;

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

        return  new ClientRunStatsAdapter(options, this);
    }

    @Override
    public void openSpecifiedRun(Run run) {
        if (run != null)
            mView.openRun(run);
        else
            mView.runNotFound();
    }
}
