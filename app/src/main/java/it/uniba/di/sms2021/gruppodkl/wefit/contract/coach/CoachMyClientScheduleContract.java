package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingViewHolder;

public interface CoachMyClientScheduleContract {

    interface View{
    }

    interface Presenter{
        FirestoreRecyclerAdapter<Training, TrainingViewHolder> getAdapter(String clientMail);
        void addNewTraining(String trainingName);
        void deleteTraining(Training training);
        void openTrainingSpecification(Training training);
    }
}
