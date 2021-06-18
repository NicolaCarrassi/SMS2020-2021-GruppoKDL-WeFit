package it.uniba.di.sms2021.gruppokdl.wefit.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.model.User;

public interface SplashActivityContract {

    /**
     * Interfaccia contenente i metodi che la view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di gestire l'ottenimento dei dati di un utente
         *
         * @param user istanza dell'utente
         */
       void onSuccess(User user);
    }

    /**
     * Interfaccia contenente i metodi che il presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo permette di recuperare le informazioni di un cliente data la sua email
         * @param email email del cliente
         */
        void fetchUserData(String email);
    }

}
