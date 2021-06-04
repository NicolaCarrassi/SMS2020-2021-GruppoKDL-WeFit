package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientMyTrainingAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientMyTrainingContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;

public class ClientMyTrainingPresenter implements ClientMyTrainingContract.Presenter {
    private final ClientMyTrainingContract.View mView;


    public ClientMyTrainingPresenter(ClientMyTrainingContract.View view) {
        this.mView = view;
    }

    public ClientMyTrainingAdapter getAdapter(String clientEmail){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPrefetchDistance(2)
                .setPageSize(4)
                .build();

        FirestorePagingOptions<Training> options = new FirestorePagingOptions.Builder<Training>()
                .setQuery(ClientDAO.queryTraining(clientEmail), config, Training.class)
                .build();
        return new ClientMyTrainingAdapter(options);
    }
}
