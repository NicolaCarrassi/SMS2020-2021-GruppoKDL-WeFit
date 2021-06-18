package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import android.content.Context;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;

public interface ClientExerciseSpecificationContract {


    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di visualizzare le informazioni di un esercizio
         *
         * @param exercise esercizio di cui mostrare le informazioni
         */
        void onInfoLoaded(Exercise exercise);

        /**
         * Il seguente metodo permette di notificare l'utente di eventuali errori
         * nella visualizzazione di un esercizio
         */
        void onFailure();
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere le informazioni relative al video esplicativo
         * di un dato esercizio
         *
         * @param exerciseName nome dell'esercizio
         */
        void loadVideoInformation(String exerciseName);

        /**
         * Il seguente metodo permette di ottenere la descrizione di un esercizio
         *
         * @param exerciseName nome dell'esercizio
         * @param context context necessario per la gestione delle risorse
         * @return Descrizione dell'esercizio
         */
        String getExerciseDescription(String exerciseName, Context context);
    }

}
