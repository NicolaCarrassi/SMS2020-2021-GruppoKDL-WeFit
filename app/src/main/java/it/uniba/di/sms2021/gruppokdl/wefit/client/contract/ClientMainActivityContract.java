package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;

public interface ClientMainActivityContract {


    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il metodo permette di notificare la corretta aggiunta del coach mediante NFC
         */
        void onSuccess();
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * il seguente metodo permette di effettuare l'associazione cliente/coach mediante NFC
         * @param clientMail mail del cliente
         * @param coachMail mail del coach
         */
        void addCoachFromNFC(String clientMail, String coachMail);
    }

}
