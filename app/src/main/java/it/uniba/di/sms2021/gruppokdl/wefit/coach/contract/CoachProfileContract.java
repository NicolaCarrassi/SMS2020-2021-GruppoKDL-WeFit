package it.uniba.di.sms2021.gruppokdl.wefit.coach.contract;

import android.net.Uri;

import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;

public interface CoachProfileContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
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
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il metodo permette di savlare una immagine
         *
         * @param uri Uri dell'immagine da salvare
         * @param coach Coach di cui bisogna salvare l'immagine
         */
        void saveImage(Uri uri, Coach coach);


        /**
         * Il metodo permette di effettuare l'update del profilo del Coach
         *
         * @param map Mappa contenente tutti i valori da aggiornare
         * @param coach Coach di cui bisogna effettuare l'update del profilo
         */
        void updateCoach(Map<String,Object> map, Coach coach);

        /**
         *Il metodo permette di caricare un file nello storage
         *
         * @param coach Coach di cui si vuole caricare il file
         */
        void uploadFile(Coach coach);
    }
}
