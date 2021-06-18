package it.uniba.di.sms2021.gruppokdl.wefit.coach.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;

public interface CoachHomeContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di caricare il numero di richieste ottenute dal coach
         * @param num numero di richieste in sospeso
         */
        void onRequestNumberLoaded(int num);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di caricare il numero di richieste in sospeso
         * @param coach coach di cui si vuole conoscere il numero di richieste in sospeso
         */
        void loadNumberRequest(Coach coach);
    }
}
