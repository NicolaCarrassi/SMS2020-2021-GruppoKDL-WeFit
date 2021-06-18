package it.uniba.di.sms2021.gruppokdl.wefit.contract.client;

import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientDietDiaryAdapter;

public interface ClientDietDiaryContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{

    }

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface Presenter{

        /**
         * Il seguente metodo permette di ottenere l'adapter necessario per ottenere il diario alimentare
         * dell'utente
         *
         * @param clientMail mail del cliente
         * @param day giorno della settimana di cui si vogliono ottenere le informazioni
         * @param mealType tipo di pasto (0 COLAZIONE; 2 PRANZO; 4 CENA)
         * @return Adapter
         */
        ClientDietDiaryAdapter getAdapter(String clientMail, String day, int mealType);
    }

}
