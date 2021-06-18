package it.uniba.di.sms2021.gruppokdl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachDietDayAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Meal;

public interface CoachMyClientDietSpecificationContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di ottenere la mail del cilente
         * @return mail del cliente
         */
        String getClientMail();

        /**
         * Il seguente metodo permette di ottenere il giorno della settimana relativo alla
         * giornata di dieta specifica
         * @return giorno della settimana
         */
        String getDayOfTheWeek();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere l'adapter per la recycler view
         *
         * @param clientName mail del cliente
         * @param dayOfTheWeek giorno della settimana
         * @param mealType tipo di pasto
         * @return adapter
         */
        CoachDietDayAdapter getAdapter(String clientName, String dayOfTheWeek, int mealType);
        void removeMeal(Meal meal);
    }

}
