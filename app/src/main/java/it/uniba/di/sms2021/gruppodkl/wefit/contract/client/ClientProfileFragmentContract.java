package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import android.net.Uri;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public interface ClientProfileFragmentContract {

    /**
     * Interfaccia contenente i metodi che la view deve implementare
     */
    interface View{
        /**
         * Il metodo permette di ottenere l'estensione di un file, fornito in input il proprio URI
         *
         * @param uri uri del file di cui si vuole conoscere l'estensione
         * @return Stringa contenente l'estensione del file
         */
        String getFileExtension(Uri uri);
    }

    /**
     * Interfaccia contenente i metodi che il presenter deve implementare
     */
    interface Presenter{
        /**
         * Il metodo permette di savlare una immagine
         *
         * @param uri Uri dell'immagine da salvare
         * @param client Client di cui bisogna salvare l'immagine
         */
        void saveImage(Uri uri, Client client);

        /**
         * Il metodo permette di effettuare l'update del profilo del cliente
         *
         * @param map Mappa contenente tutti i valori da aggiornare
         * @param client Cliente di cui bisogna effettuare l'update del profilo
         */
        void updateUser(Map<String, Object> map, Client client);
    }
}
