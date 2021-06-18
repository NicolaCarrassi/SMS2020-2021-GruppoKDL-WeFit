package it.uniba.di.sms2021.gruppokdl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter.CoachMyClientListAdapter;

public interface CoachClientsContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di aprire il profilo di un cliente data la sua mail
         * @param clientMail mail del cliente
         */
        void openClientProfile(String clientMail);

        /**
         * Il seguente metodo serve a notificare eventuali errori nella fase di apertura del profilo
         * di un cliente
         */
        void errorOpeningProfile();
    }


    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo serve per ottenere la lista dei clienti di un dato coach
         * @param coachEmail mail del coach
         * @return adapter
         */
        CoachMyClientListAdapter getAdapter(String coachEmail);

        /**
         * Il seguente metodo permette di richiedere di aprire il profilo di un cliente del coach
         * @param clientEmail mail del cliente
         */
        void onRequestToOpenProfile(String clientEmail);
    }

}
