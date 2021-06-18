package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationFragmentContract;

public interface CoachRegistrationFragmentContract{

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View extends RegistrationFragmentContract.View {
        //non ha metodi aggiuntivi rispetto alla interfaccia che estende
        // interfaccia creata in caso di eventuali situzazioni in cui il coach
        // debba avere dei metodi che il cliente non necessita
    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter extends RegistrationFragmentContract.Presenter{
        //non ha metodi aggiuntivi rispetto alla interfaccia che estende
        // interfaccia creata in caso di eventuali situzazioni in cui il coach
        // debba avere dei metodi che il cliente non necessita
    }

}
