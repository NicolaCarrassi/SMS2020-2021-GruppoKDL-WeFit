package it.uniba.di.sms2021.gruppokdl.wefit.contract.coach;

import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;

public interface CoachMyClientProfileContract  {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo peremtte di gestire una situazione di errore
         */
        void onFailure();

        /**
         * Il seguente metodo permette di gestire l'ottenimento delle informazioni di un dato cliente
         *
         * @param client cliente
         * @param weightList lista contenete tutti i pesi registrati dal cliente nella piattaforma
         * @param dateList lista contenente tutte le date in cui un cliente ha registrato un peso
         */
        void onClientDataReceived(Client client, List<Float> weightList, List<Date> dateList);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        void findUserData(String clientMail);
    }
}
