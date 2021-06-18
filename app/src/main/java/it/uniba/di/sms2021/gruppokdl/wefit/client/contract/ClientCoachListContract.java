package it.uniba.di.sms2021.gruppokdl.wefit.client.contract;


import it.uniba.di.sms2021.gruppokdl.wefit.client.adapter.ClientCoachListAdapter;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;

public interface ClientCoachListContract {

        /**
         * Interfaccia contenente i metodi che l'implementazione della view deve contenere
         */
        interface View{
                /**
                 * Il seguente metodo permette di notificare all'utente eventuali situazioni di
                 * errori
                 */
                void onFailure();

                /**
                 * Il seguente metodo permette di notificare all'utente il successo delle operazione
                 *
                 */
                void onSuccess();

                /**
                 * il seguente metodo permette di notificare al cliente il corretto invio di una richiesta
                 */
                void onRequestSent();

                /**
                 * Il seguente metodo permette di aprire il profilo di un coach data la sua mail
                 *
                 * @param mail mail del coach
                 */
                void openCoachProfileWithMail(String mail);
        }

        /**
         * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
         */
        interface Presenter{
                /**
                 * Il seguente metodo permette di ottenere un adapter per la gestione
                 * della lista dei coach
                 *
                 * @return lista dei coach
                 */
                ClientCoachListAdapter getAdapter();

                /**
                 * IL seguente metodo permette di aprire il profilo di un dato coach
                 * @param coach coach
                 */
                void openCoachProfile(Coach coach);

                /**
                 * Il seguente metodo permette di inviare una richiesta di iscrizione ad un coach
                 *
                 * @param coach coach
                 */
                void sendRequestToCoach(Coach coach);

                /**
                 * Il seguente metodo permette di gestire la situazione in cui è gia stata inviata
                 * una richiesta ad un coach
                 */
                void requestAlreadySent();
        }


}

