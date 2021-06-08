package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientDietListContract;

public class ClientDietListPresenter implements ClientDietListContract.Presenter {

    private final ClientDietListContract.View mView;


    public ClientDietListPresenter(ClientDietListContract.View view){this.mView = view;}

}
