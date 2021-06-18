package it.uniba.di.sms2021.gruppokdl.wefit.contract.client;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;

public interface ClientRunContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il metodo permette di visualizzare l'ultima corsa effettuata
         * @param run ultima corsa
         */
        void showLastRun(Run run);

        /**
         * Il metodo permette di gestire la situazione in cui l'utente non ha ancora effettuato
         * nessuna corsa
         */
        void lastRunEmpty();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        void getLastRun(String mail);
    }

}
