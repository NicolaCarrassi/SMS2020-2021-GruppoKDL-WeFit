package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Exercise;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.TrainingDetailViewHolder;

public interface ClientTrainingSpecificationContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo peremtte di aprire la pagina di un esercizio, dato il nome
         * @param exerciseName nome dell'esercizio
         */
        void openExercisePage(String exerciseName);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo permette di ottenere l'adapter per la specifica dell'allenamento
         *
         * @param clientMail mail del cliente
         * @param training istanza dell'allenamento
         * @return adapter
         */
        FirestorePagingAdapter<Exercise, TrainingDetailViewHolder> getAdapter(String clientMail, Training training);

        /**
         * Il metodo permette di mostrare la specifica di un esercizio
         *
         * @param exercise esercizio di cui si vuole visualizzare la specifica
         */
        void showExercise(Exercise exercise);
    }

}
