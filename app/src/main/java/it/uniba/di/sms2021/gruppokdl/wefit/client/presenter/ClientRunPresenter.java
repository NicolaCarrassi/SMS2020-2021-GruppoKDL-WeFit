package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientRunContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;

public class ClientRunPresenter implements ClientRunContract.Presenter, ClientDAO.RunLoaded {

    private final ClientRunContract.View mView;


    public ClientRunPresenter(ClientRunContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void getLastRun(String mail) {
        ClientDAO.getLastRun(mail,this);
    }

    @Override
    public void onLastRunLoaded(Run run) {
        if(run!=null){
            mView.showLastRun(run);
        } else mView.lastRunEmpty();
    }
}
