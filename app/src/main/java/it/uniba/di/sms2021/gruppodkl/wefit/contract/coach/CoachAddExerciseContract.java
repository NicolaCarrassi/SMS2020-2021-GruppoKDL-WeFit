package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import java.util.List;
import java.util.Map;

public interface CoachAddExerciseContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il metodo permette di gestire il caricamento degli esercizi
         *
         * @param list lista contenente i nomi degli esercizi
         */
        void onExercisesNamesLoaded(List<String> list);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere le informazioni di tutti gli esercizi
         */
        void fetchAllExercises();

        /**
         * Il seguente metodo permette di aggiungere un esercizio ad uno specifico allenamento di un
         * esercizio
         *
         * @param clientMail mail del cliente
         * @param trainingId id dell'allenamento
         * @param map mappa di parametri dell'esercizio
         */
        void addExercise(String clientMail, String trainingId, Map<String,Object> map);
    }

}
