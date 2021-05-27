package it.uniba.di.sms2021.gruppodkl.wefit.presenter.fragment;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.ClientRegistrationFragmentContract;

public class ClientRegistrationFragmentPresenter implements ClientRegistrationFragmentContract.Presenter {

    private ClientRegistrationFragmentContract.View mView;

    public ClientRegistrationFragmentPresenter(ClientRegistrationFragmentContract.View view){
        this.mView = view;
    }
}
