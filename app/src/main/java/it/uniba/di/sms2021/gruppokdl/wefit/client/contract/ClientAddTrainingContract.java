package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;

import java.util.List;

public interface ClientAddTrainingContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di mostrare all'utente gli allenamenti che può registrare
         *
         * @param trainingNames nomi degli allenamenti
         */
        void trainingListNotEmpty(List<String> trainingNames);

        /**
         * Il seguente metodo permette di notificare l'utente che la lista degli allenamenti è vuota
         */
        void emptyTrainingList();

        /**
         * Il seguente metodo permette di notificare l'aggiunta di un allenamento
         */
        void onTrainingAdded();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di caricare le informazioni di un allenamento
         *
         * @param clientMail mail del cliente
         */
        void loadTrainingInformation(String clientMail);

        /**
         * Il seguente metodo permette di registrare il completamento di un allenamento da parte di
         * un cliente
         *
         * @param clientMail mail del cliente
         * @param trainingName nome dell'allenamento
         * @param date data dell'allenamento
         */
        void registerTrainingComplete(String clientMail, String trainingName, String date);
    }
}
