package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;

public interface ClientAddWeightContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di notificare il successo nell'aggiunta di un peso
         */
        void onSuccess();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di far registrare un nuovo peso ad un cliente
         *
         * @param client cliente
         * @param weight peso
         */
        void addWeight(Client client, float weight);
    }

}
