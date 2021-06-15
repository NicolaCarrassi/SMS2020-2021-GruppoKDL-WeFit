package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

public interface ClientHomeContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di gestire l'ottenimento degli allenamenti
         * settimanali completati dall'utente
         *
         * @param completedTrainings numero di allenamenti completati
         * @param trainingsNumber numero di allenamenti settimanali assegnati
         * @param flag valore intero necessario per indicare la presenza o assenza di uno dei valori
         *             (valori ammessi sono:  NO_DENOMINATOR = 0;  NO_NUMERATOR = 1; CORRECT = 2;)
         */
        void userCompletedTrainings(int completedTrainings, int trainingsNumber, int flag);
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere le informazioni dell'allenamento di un cliente
         * @param clientMail mail del cliente
         */
        void loadTrainingInformation(String clientMail);
    }

}
