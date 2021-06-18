package it.uniba.di.sms2021.gruppokdl.wefit.contract.coach;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingDetailViewHolder;

public interface CoachMyClientDailyTrainingContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{

    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere l'adapter necessario
         * @param clientMail mail del cliente
         * @param training allenamento di cui si vuole ottenere la specifica
         * @return adapter
         */
        FirestoreRecyclerAdapter<Exercise, TrainingDetailViewHolder> getAdapter(String clientMail, Training training);

        /**
         * Il seguente metodo permette di modificare le informazioni di un allenamento
         * @param clientMail mail del cliente
         * @param training allenamento da modificare
         */
        void updateTraining(String clientMail, Training training);
    }

}
