package it.uniba.di.sms2021.gruppokdl.wefit.coach.contract;

import it.uniba.di.sms2021.gruppokdl.wefit.contract.DietListBaseContract;

public interface CoachMyClientDietListContract {

    /**
     * Interfaccia contenente i metodi che l'implementazione della view deve contenere
     */
    interface View extends DietListBaseContract.View{

    }

    /**
     * Interfaccia contenente i metodi che l'implementazione del presenter deve contenere
     */
    interface Presenter extends DietListBaseContract.Presenter{

    }
}
