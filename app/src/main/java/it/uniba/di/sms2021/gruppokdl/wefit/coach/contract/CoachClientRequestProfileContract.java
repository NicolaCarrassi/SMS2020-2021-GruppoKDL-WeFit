package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;

public interface CoachClientRequestProfileContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il sueguente metodo permette di gestire la situazione in cui sono state caricate le informazioni
         * relative al cliente
         * @param client profilo del cliente
         */
        void onClientInfoLoaded(Client client);

        /**
         * Il metodo permette di notificare un errore nell'ottenimento delle informazioni
         */
        void onFailure();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di gestire le richieste effettuate dai clienti ad un coach
         *
         * @param coach coach che ha ricevuto la richiesta
         * @param request richiesta formulata da un cliente
         * @param isAccepted stato della richiesta, se true indica che il coach ha accettato di
         *                   seguire il cliente, in caso di rifiuto essa è false
         */
        void clientRequest(Coach coach, Request request, boolean isAccepted);

        /**
         * Il seguente metodo permette di ottenere le informazione di un cliente
         * @param clientMail mail del cliente
         */
        void getClientInfo(String clientMail);
    }
}
