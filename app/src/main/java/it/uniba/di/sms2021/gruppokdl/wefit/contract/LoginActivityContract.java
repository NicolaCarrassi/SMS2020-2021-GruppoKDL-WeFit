package it.uniba.di.sms2021.gruppokdl.wefit.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.model.User;

public interface LoginActivityContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di gestire la situazione di login effettuato con successo
         * @param user istanza di utente che ha effettuato il login
         */
        void onSuccess(User user);

        /**
         * Il seguente metodo permette di gestire il tentativo di login fallito
         */
        void onFailure();

        /**
         * Il seguente metodo permette di notificare l'invio della email di recupero password
         */
        void emailSent();

        /**
         * Il seguente metodo permette di notificare un eventuale errore in caso di invio di mail
         * di recupero password
         */
        void failedToSendEmail();

        /**
         * Il seguente metodo permette di informare l'utente di aver inserito una mail non
         * corretta.
         */
        void wrongEmail();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di effettuare un tentativo di  login
         * @param email email dell'utente
         * @param password password dell'utente
         */
        void doLogin(String email, String password);

        /**
         * Il seguente metodo permette di inviare una mail di recupero password
         * @param email email dell'utente
         */
        void forgotPassword(String email);
    }

}
