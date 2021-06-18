package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.contract.DietListBaseContract;

public interface ClientDietListContract {


    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View extends DietListBaseContract.View{
        /*
        * NON VI SONO METODI IN QUANTO QUELLI DELL'INTERFACCIA PADRE PERMETTONO
        * DI SVOLGERE TUTTE LE FUNZIONI NECESSARIE, L'INTERFACCIA RIMANE VUOTA
        * CONSENTENDO LA POSSIBILITA' DI INSERIRE NUOVE FUNZIONALITA' SPECIFICHE PER IL
        * CLIENTE
        */
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter extends DietListBaseContract.Presenter{
        /*
         * NON VI SONO METODI IN QUANTO QUELLI DELL'INTERFACCIA PADRE PERMETTONO
         * DI SVOLGERE TUTTE LE FUNZIONI NECESSARIE, L'INTERFACCIA RIMANE VUOTA
         * CONSENTENDO LA POSSIBILITA' DI INSERIRE NUOVE FUNZIONALITA' SPECIFICHE PER IL
         * CLIENTE
         */
    }

}
