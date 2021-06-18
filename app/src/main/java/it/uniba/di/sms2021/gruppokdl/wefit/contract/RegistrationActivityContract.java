package it.uniba.di.sms2021.gruppodkl.wefit.contract;

import android.net.Uri;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public interface RegistrationActivityContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il metodo permette di gestire la risposta positiva alla creazione dell'utente
         *
         * @param user istanza dell'utente creato
         */
        void onSuccess(User user);

        /**
         * Il metodo permette di gestire il mancato successo nella creazione dell'utente
         */
        void onFailure();

        /**
         * Il metodo permette di ottenere l'estensione del file uri
         * @return Estensione del file
         */
        String getFileExtension();

        /**
         * Il metodo permette di ottenere il fileURI
         * @return FileURI
         */
        Uri getFileURI();
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo permette di effettuare la registrazione dell'utente, indipendentemente
         * dal ruolo che esso rappresenta nella piattaforma (Cliente o Coach).
         *
         * @param userData Dati comuni ad entrambe le tipologie di utenti
         * @param specificData Dati specifici per la tipologia di utente rappresentata
         */
        void registerUser(Map<String, String> userData, Map<String,String> specificData);
    }

}
