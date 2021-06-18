package it.uniba.di.sms2021.gruppokdl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Feedback;

public interface CoachFeedbacksContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di gestire l'ottenimento dell'ultimo feedback ricevuto dal
         * coach
         * @param feedback feedback ricevuto
         * @param mean media dei feedback
         * @param numElem numero di feedback
         */
        void onLastFeedbackReceived(Feedback feedback, float mean, int numElem);

        /**
         * Il metodo permette di gestire la situazione in cui il coach non abbia ricevuto ancora
         * alcun feedback
         */
        void onNoFeedbackReceived();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere le infomazioni dell'ultimo feedback di un dato coach
         *
         * @param coach coach di cui si vuole conoscere l'informazione
         */
        void fetchLastFeedback(Coach coach);
    }
}
