package it.uniba.di.sms2021.gruppokdl.wefit.coach.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachClientRequestsAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Request;

public interface CoachClientsRequestsContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di visualizzare il profilo di un cliente che ha effettuato una
         * richiesta al coach
         * @param request richiesta ricevuta dal coach
         */
        void showClientProfile(Request request);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenre un adapter
         * @return adapter
         */
        CoachClientRequestsAdapter makeAdapter();

        /**
         * Il seguente metodo permette di effettuare la richiesta di visualizzazione di un profilo data
         * una richiesta
         *
         * @param request richiesta
         */
        void showUserProfile(Request request);
    }
}
