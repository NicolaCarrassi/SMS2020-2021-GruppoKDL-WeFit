package it.uniba.di.sms2021.gruppodkl.wefit.presenter;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.RegistrationActivityContract;

public class RegistrationActivityPresenter implements RegistrationActivityContract.Presenter {

    private RegistrationActivityContract.View mView;

    public RegistrationActivityPresenter(RegistrationActivityContract.View view){
        this.mView = view;
    }


}


