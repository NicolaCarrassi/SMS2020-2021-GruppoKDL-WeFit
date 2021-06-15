package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientDietDayAdapter;

public interface ClientDailyDietContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{


    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere l'adapter necessario per ottenere la dieta dell'utente
         * in un dato giorno
         *
         * @param clientMail mail del cliente
         * @param dayOfTheWeek giorno della settimana di cui si vogliono ottenere le informazioni
         * @param mealType tipo di pasto (0 COLAZIONE; 2 PRANZO; 4 CENA)
         * @return Adapter
         */
        ClientDietDayAdapter getAdapter(String clientMail, String dayOfTheWeek, int mealType);
    }

}
