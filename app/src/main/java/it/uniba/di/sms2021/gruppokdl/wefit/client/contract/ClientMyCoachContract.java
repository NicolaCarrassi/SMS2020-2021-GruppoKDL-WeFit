package it.uniba.di.sms2021.gruppokdl.wefit.contract.client;

import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;

public interface ClientMyCoachContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View{
        /**
         * Il seguente metodo permette di gestire la ricezione dei dati del coach da remoto
         *
         * @param coach coach
         */
        void onCoachDataReceived(Coach coach);

        /**
         * Il seguente metodo permette di gestire eventuali situazioni di errore
         * nell'ottenimento dei dati del coach
         */
        void onCoachNotFound();

        /**
         *
         * Il seguente metodo permette di gestire l'ottenimento della valutazione
         * media del coach
         *
         * @param numStars valutazione media
         */
        void onCoachRatingStarsObtained(float numStars);

        /**
         * Il seguente metodo permette di notificare l'utente del corretto invio
         * della richiesta di sottoscrizione
         */
        void requestSentSuccessfully();

        /**
         * Il seguente metodo permette di notificare l'utente di eventuali errori
         * nell'invio della richiesta di sottoscrizione
         */
        void requestFailed();

    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter{
        /**
         * Il seguente metodo permette di ottenere le informazioni del coach di cui si
         * sta visitando il profilo
         *
         * @param coachEmail mail del coach di cui si cercano informazioni
         */
        void getCoachData(String coachEmail);

        /**
         * Il seguente metodo permette di lasciare un feedback ad un coach
         *
         * @param map mappa contenente i valori necessari per inviare un feedback
         * @param coachEmail mail del coach a cui si vuole inviare un feedback
         */
        void addFeedback(Map<String, Object> map, String coachEmail);

        /**
         * Il seguente metodo permette di cancellare la propria sottoscrizione al coach
         * @param client cliente che vuole lasciare il coach
         */
        void leaveCoach(Client client);

        /**
         * Il seguente metodo permette di ottenere la valutazione media di un dato coach
         *
         * @param coach coach di cui si vuole conoscere la valutazione media
         */
        void getCoachRatingStars(Coach coach);

        /**
         * Il seguente metodo permette ad un cliente di inviare una richiesta di sottoscrizione
         * ad un dato coach
         *
         * @param client cliente che intende effettuare la richiesta di sottoscrizione
         * @param coach coach a cui intende sottoscriversi
         */
        void sendRequestToCoach(Client client, Coach coach);

        /**
         * Il seguente metodo permette al coach di annullare la richiesta di sottoscrizione al coach
         *
         *
         * @param client cliente che intende cancellare la sottoscrizione al coach
         * @param coach coach
         */
        void deleteRequestToCoach(Client client, Coach coach);
    }
}
