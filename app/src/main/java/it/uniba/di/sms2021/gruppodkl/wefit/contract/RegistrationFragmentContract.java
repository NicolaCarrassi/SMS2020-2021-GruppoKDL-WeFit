package it.uniba.di.sms2021.gruppodkl.wefit.contract;

import java.util.Map;

public interface RegistrationFragmentContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View {
        /**
         * Il metodo è necessario per verificare che i parametri obbligatori per la tipologia di
         * utente siano presenti in modo corretto
         * @return true se tutti i parametri obbligatori sono presenti, false altrimenti
         */
        boolean areCorrect();

        /**
         * Il seguente metodo permette di ottenere tutti gli attributi specifici di una determinata
         * tipologia di utente
         * @return mappa contenente chiavi e valori dei dati opzionali
         */
        Map<String, String> getAddictionalData();
    }

    /**
     * La seguente interfaccia contiene i metodi che l'implementazione del presenter deve
     * contenere
     */
    interface Presenter{

    }
}
