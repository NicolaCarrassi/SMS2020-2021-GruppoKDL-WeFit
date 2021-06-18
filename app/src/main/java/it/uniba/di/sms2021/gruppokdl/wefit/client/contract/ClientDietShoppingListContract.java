package it.uniba.di.sms2021.gruppokdl.wefit.contract.client;

import java.util.Map;

public interface ClientDietShoppingListContract {


    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di gestire l'ottenimento delle informazioni
         * relative alla lista della spesa
         *
         * @param mealsMap mappa contenente come chiavi i nomi degli alimenti e come
         *                 valore il quantitativo in grammi necessario per la spesa
         */
        void onShoppingInformationLoaded(Map<String, Integer> mealsMap);
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere la lista della spesa per un dato cliente
         *
         * @param clientMail mail del cliente di cui si vuole ottenere la lista della spesa
         * @param numberOfDays numero di giorni cui si vuole conoscere la lista della spesa
         */
        void loadShoppingList(String clientMail, int numberOfDays);
    }

}
