package it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachMyClientTrainingSpecificationListAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.contract.CoachMyClientDailyTrainingContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.TrainingDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingDetailViewHolder;

public class CoachMyClientDailyTrainingPresenter implements CoachMyClientDailyTrainingContract.Presenter {

    private final CoachMyClientDailyTrainingContract.View mView;


    public CoachMyClientDailyTrainingPresenter(CoachMyClientDailyTrainingContract.View view){
        this.mView = view;
    }

    @Override
    public FirestoreRecyclerAdapter<Exercise, TrainingDetailViewHolder> getAdapter(String clientMail, Training training) {

        Query query = TrainingDAO.queryGetAllTrainingExercises(clientMail, training.getId());

        FirestoreRecyclerOptions<Exercise> options = new FirestoreRecyclerOptions.Builder<Exercise>()
                .setQuery(query, Exercise.class).build();

        return new CoachMyClientTrainingSpecificationListAdapter(options, clientMail, training.getId());
    }



    @Override
    public void updateTraining(String clientMail, Training training) {
        Map<String, Object> map = new HashMap<>();

        map.put(Training.TrainingKeys.ID, training.getId());
        map.put(Training.TrainingKeys.TITLE, training.title);
        map.put(Training.TrainingKeys.DAY_OF_WEEK, training.dayOfWeek);
        map.put(Training.TrainingKeys.TIME, training.time);

        TrainingDAO.updateTraining(clientMail,map);
    }
}
