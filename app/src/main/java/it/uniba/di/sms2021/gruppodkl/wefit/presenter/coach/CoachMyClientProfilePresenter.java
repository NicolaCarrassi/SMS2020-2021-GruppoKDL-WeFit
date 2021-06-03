package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientProfileContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;

public class CoachMyClientProfilePresenter implements CoachMyClientProfileContract.Presenter, CoachDAO.ClientCallbacks {

    private final CoachMyClientProfileContract.View mView;

    public CoachMyClientProfilePresenter(CoachMyClientProfileContract.View view){
        this.mView = view;
    }


    @Override
    public void findUserData(String clientMail) {
        CoachDAO.getAllClientInfo(clientMail, this);
    }

    @Override
    public void failure() {
        mView.onFailure();
    }

    @Override
    public void success(Client client, List<Float> weightList) {
        if(client != null && !weightList.isEmpty())
            mView.onClientDataReceived(client, weightList);
        else
            mView.onFailure();
    }
}
