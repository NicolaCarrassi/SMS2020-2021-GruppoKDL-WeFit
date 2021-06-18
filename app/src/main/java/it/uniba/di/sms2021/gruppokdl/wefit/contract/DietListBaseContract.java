package it.uniba.di.sms2021.gruppodkl.wefit.contract;

public interface DietListBaseContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di mostrare la dieta di un dato giorno della settimana
         *
         * @param weekDay giorno della settimana
         */
        void showDietOfTheDay(String weekDay);
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo permette di caricare la pagina contenete le informazioni della dieta di un dato
         * giorno della settimana
         *
         * @param weekDay giorno della settimana
         */
        void onWeekDaySelected(String weekDay);
    }

}
