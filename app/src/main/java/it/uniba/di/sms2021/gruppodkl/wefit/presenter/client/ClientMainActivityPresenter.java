package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientMainActivityContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;

public class ClientMainActivityPresenter implements ClientMainActivityContract.Presenter, ClientDAO.NFCCallback {

    private final ClientMainActivityContract.View mView;

    public ClientMainActivityPresenter(ClientMainActivityContract.View view){ this.mView = view;}


    @Override
    public void addCoachFromNFC(String clientMail, String coachMail) {
        ClientDAO.setCoachFromNFC(clientMail, coachMail, this);
    }

    @Override
    public void notifySuccess() {
        mView.onSuccess();
    }
}
