package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientMyTrainingAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;

public interface ClientMyTrainingContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di visualizzare la pagina di un dato allenamento
         *
         * @param training allenamento
         */
        void openTrainingSchedule(Training training);
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere un adapter per la visualizzazione di tutti gli allenamenti
         * del cliente
         *
         * @param clientEmail mail del cliente
         * @return adapter
         */
        ClientMyTrainingAdapter getAdapter(String clientEmail);

        /**
         * Il seguente metodo permette di aprire la lista degli esercizi di un dato allenamento
         * @param training allenamento di cui si vogliono visualizzare gli esercizi
         */
        void openTrainingSpecification(Training training);
    }

}
