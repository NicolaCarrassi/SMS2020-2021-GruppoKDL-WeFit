package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachClientTrainingAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientScheduleContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingViewHolder;

public class CoachMyClientSchedulePresenter implements CoachMyClientScheduleContract.Presenter {

    private final CoachMyClientScheduleContract.View mView;
    private final String mClientMail;


    public CoachMyClientSchedulePresenter(CoachMyClientScheduleContract.View view, String clientMail){
        this.mView = view;
        this.mClientMail = clientMail;
    }


    @Override
    public FirestoreRecyclerAdapter<Training, TrainingViewHolder> getAdapter(String clientMail) {

        FirestoreRecyclerOptions<Training> options = new FirestoreRecyclerOptions.Builder<Training>()
                .setQuery(TrainingDAO.getClientTrainingSchedule(clientMail), Training.class).build();

        return new CoachClientTrainingAdapter(options, this);
    }

    @Override
    public void addNewTraining(String trainingName) {
        TrainingDAO.addTraining(mClientMail, new Training(null, trainingName, Keys.WeekDay.NOT_SET, 0));
    }

    @Override
    public void deleteTraining(Training training) {
        TrainingDAO.deleteTraining(mClientMail, training.getId());
    }

    @Override
    public void openTrainingSpecification(Training training) {
        //TODO
    }
}
