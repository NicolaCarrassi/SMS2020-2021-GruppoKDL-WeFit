package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;

import android.app.Activity;

import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientRunContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Run;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;

public class ClientRunPresenter implements ClientRunContract.Presenter, ClientDAO.RunLoaded, UserDAO.UserCallbacks {

    private final ClientRunContract.View mView;


    public ClientRunPresenter(ClientRunContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void getLastRun(String mail) {
        ClientDAO.getLastRun(mail,this);
    }

    @Override
    public void restoreUser(String mail) {
        UserDAO.getUser(mail,this);
    }

    @Override
    public void onLastRunLoaded(Run run) {
        if(run!=null){
            mView.showLastRun(run);
        } else mView.lastRunEmpty();
    }

    @Override
    public void userLoaded(User user, boolean success) {
        Activity activity = mView.getActivity();
        ((WeFitApplication) activity.getApplicationContext()).setUser(user);
    }

    @Override
    public void hasBeenCreated(User user, boolean success) {
        //NON NECESSARIO
    }
}
