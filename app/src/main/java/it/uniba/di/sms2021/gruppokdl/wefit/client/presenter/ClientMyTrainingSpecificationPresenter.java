package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientTrainingSpecificationListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientTrainingSpecificationContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingDetailViewHolder;

public class ClientMyTrainingSpecificationPresenter implements ClientTrainingSpecificationContract.Presenter {

    private final ClientTrainingSpecificationContract.View mView;


    public ClientMyTrainingSpecificationPresenter(ClientTrainingSpecificationContract.View view){
        this.mView = view;
    }

    @Override
    public FirestorePagingAdapter<Exercise, TrainingDetailViewHolder> getAdapter(String clientMail, Training training) {

        Query query = TrainingDAO.queryGetAllTrainingExercises(clientMail,training.getId());

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(15)
                .setPrefetchDistance(8)
                .setPageSize(20)
                .build();

        FirestorePagingOptions<Exercise> options = new FirestorePagingOptions.Builder<Exercise>()
                .setQuery(query, config, Exercise.class)
                .build();


        return new ClientTrainingSpecificationListAdapter(options, this);
    }

    @Override
    public void showExercise(Exercise exercise) {
        if(exercise != null)
            mView.openExercisePage(exercise.name);
    }
}
