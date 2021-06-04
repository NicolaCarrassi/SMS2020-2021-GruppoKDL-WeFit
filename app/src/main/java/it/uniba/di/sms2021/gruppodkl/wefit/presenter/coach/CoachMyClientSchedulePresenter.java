package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachClientTrainingAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientScheduleContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingViewHolder;

public class CoachMyClientSchedulePresenter implements CoachMyClientScheduleContract.Presenter {

    private final CoachMyClientScheduleContract.View mView;


    public CoachMyClientSchedulePresenter(CoachMyClientScheduleContract.View view){ this.mView = view;}


    @Override
    public FirestoreRecyclerAdapter<Training, TrainingViewHolder> getAdapter(String clientMail) {

        FirestoreRecyclerOptions<Training> options = new FirestoreRecyclerOptions.Builder<Training>()
                .setQuery(TrainingDAO.getClientTrainingSchedule(clientMail), Training.class).build();

        return new CoachClientTrainingAdapter(options, this);
    }
}
