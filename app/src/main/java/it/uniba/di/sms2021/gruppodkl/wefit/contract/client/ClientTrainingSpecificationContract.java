package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.TrainingDetailViewHolder;

public interface ClientTrainingSpecificationContract {

    interface View{

    }

    interface Presenter{
        FirestorePagingAdapter<Exercise, TrainingDetailViewHolder> getAdapter(String clientMail, Training training);
    }

}
