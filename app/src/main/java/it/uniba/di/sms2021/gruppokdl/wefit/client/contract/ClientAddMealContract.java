package it.uniba.di.sms2021.gruppokdl.wefit.contract.client;


import androidx.fragment.app.FragmentActivity;

import java.util.List;

public interface ClientAddMealContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di notificare la registrazione di un pasto
         */
        void mealRegistered();

        /**
         * Il seguente metodo permette di notificare l'eventuale errore nella registrazione
         * di un pasto
         */
        void onFailure();

        /**
         * Il seguente metodo permette di ottenere un riferimento all'activity
         * @return activity
         */
        FragmentActivity getActivity();

        /**
         * Il seguente metodo permette di inserire i pasti che l'utente può aggiungere nella giornata
         *
         * @param mealsList lista contenente i pasti della giornata
         * @param dietNotEmpty indica che la dieta non è vuota, permette di discriminare la situazione
         *                     in cui tutti i pasti del giorno sono stati inseriti dalla situazione in
         *                     cui non vi sono pasti per la giornata
         */
        void setAvailableMealsToRegister(List<String> mealsList, boolean dietNotEmpty);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di registrare un dato pasto per la giornata corrente
         * @param mealOfTheDay pasto della giornata
         */
       void registerMeal(String mealOfTheDay);

        /**
         * Il seguente metodo permette di controllare i pasti disponibili
         */
        void checkMealsAvailable();
    }
}
