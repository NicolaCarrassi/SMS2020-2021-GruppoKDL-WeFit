package it.uniba.di.sms2021.gruppokdl.wefit.contract.client;

import java.util.Date;
import java.util.List;

public interface ClientProgressContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il metodo permette di notificare l'utente di eventuali errori
         */
        void onFailure();

        /**
         * Il metodo permette di gestire la ricezione della lista dei pesi da parte dell'utente
         *
         * @param weightList lista dei pesi registrati dall'utente
         * @param dateList lista di date in cui l'utente ha
         */
        void onClientDataReceived(List<Float> weightList, List<Date> dateList);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo permette di ottenere le informazioni di un cliente data la sua email
         * @param clientMail email del cliente
         */
        void findUserData(String clientMail);
    }

}
