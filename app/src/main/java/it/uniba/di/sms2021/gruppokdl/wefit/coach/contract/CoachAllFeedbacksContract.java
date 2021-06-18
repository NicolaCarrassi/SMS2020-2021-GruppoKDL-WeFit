package it.uniba.di.sms2021.gruppokdl.wefit.coach.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachAllFeedbacksAdapter;

public interface CoachAllFeedbacksContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface View{

    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo peremtte di generare l'adapter di tutti i feedback
         * @param coachMail mail del coach
         * @return adapter
         */
        CoachAllFeedbacksAdapter getAdapter(String coachMail);
    }

}
