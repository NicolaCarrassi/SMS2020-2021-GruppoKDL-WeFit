package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientMyTrainingAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientMyTrainingContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;

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
        return new ClientMyTrainingAdapter(options, this);
    }

    @Override
    public void openTrainingSpecification(Training training) {
        if(training != null)
            mView.openTrainingSchedule(training);
    }
}
