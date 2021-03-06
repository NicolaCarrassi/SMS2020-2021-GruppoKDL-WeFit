package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;

import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientMainActivityContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.ClientDAO;

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
