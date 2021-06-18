package it.uniba.di.sms2021.gruppokdl.wefit.coach.contract;

import java.util.List;

public interface CoachAddMealContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di ottenere la listq di pasti
         *
         * @param translatedList lista di pasti tradotta nella lingua dell'app
         */
        void mealsLoaded(List<String> translatedList);

        /**
         * Il seguente metodo permette di notificare eventuali errori
         */
        void onFailure();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere le informazioni di tutti i pasti
         */
        void loadAllMeals();

        /**
         *  Il seguente metodo permette di aggiungere un nuovo pasto al cliente, in uno specifico momento della giornata,
         *  indicandone anche la quantità
         *
         * @param clientMail mail del cliente
         * @param dayOfTheWeek giorno della settimana
         * @param mealSelected pasto selezionato
         * @param momentOfTheDay momento della giornata
         * @param quantity quantità del pasto
         */
        void addMeal(String clientMail, String dayOfTheWeek, String mealSelected, int momentOfTheDay, int quantity);
    }
}
