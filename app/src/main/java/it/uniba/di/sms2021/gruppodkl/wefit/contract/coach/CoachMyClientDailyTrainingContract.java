package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingDetailViewHolder;

public interface CoachMyClientDailyTrainingContract {

    interface View{

    }

    interface Presenter{
        FirestoreRecyclerAdapter<Exercise, TrainingDetailViewHolder> getAdapter(String clientMail, Training training);
    }

}
