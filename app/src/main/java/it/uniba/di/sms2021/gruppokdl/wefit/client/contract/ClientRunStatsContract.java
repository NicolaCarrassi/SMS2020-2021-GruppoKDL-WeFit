package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientRunStatsAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;

public interface ClientRunStatsContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il metodo permette di visualizzare la specifica di una data corsa
         *
         * @param run corsa di cui si sta visualizzando la specifica
         */
        void openRun(Run run);

        /**
         * Il metodo permette di notificare l'utente in caso di errori
         */
        void runNotFound();
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo permette di ottenere l'adapter per le statistiche delle corse
         * @param clientMail mail del cliente
         * @return adapter
         */
        ClientRunStatsAdapter getAdapter(String clientMail);

        /**
         * Il seguente metodo permette di aprire una data corsa
         * @param run corsa di cui si intende visualizzare la specifica
         */
        void openSpecifiedRun(Run run);
    }

}
