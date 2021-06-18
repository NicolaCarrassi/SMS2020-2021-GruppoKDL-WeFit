package it.uniba.di.sms2021.gruppokdl.wefit.coach.contract;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingViewHolder;

public interface CoachMyClientScheduleContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di aprire la pagina di specifica di un dato allenamento
         *
         * @param training scheda di allenamento
         */
        void openTrainingSpecification(Training training);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere l'adapter necessario, da associare alla recycler
         * view
         *
         * @param clientMail mail del cliente
         * @return adapter per la visualizzazione degli allenamenti
         */
        FirestoreRecyclerAdapter<Training, TrainingViewHolder> getAdapter(String clientMail);

        /**
         * Il seguente metodo permette di aggiungere un nuovo allenamento dato il suo nome
         * @param trainingName nome dell'allenamento
         */
        void addNewTraining(String trainingName);

        /**
         * Il seguente metodo permette di cancellare un allenamento
         * @param training allenamento da rimuovere
         */
        void deleteTraining(Training training);

        /**
         * Il seguente metodo peremtte di gestire la richiesta di apertura di un allenamento specifico
         * @param training allenamento che si intende visualizzare
         */
        void openTrainingSpecification(Training training);
    }
}
