package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachClientRequestProfileContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class CoachClientRequestProfilePresenter implements CoachClientRequestProfileContract.Presenter,
        UserDAO.UserCallbacks{

    private final CoachClientRequestProfileContract.View mView;


    public CoachClientRequestProfilePresenter(CoachClientRequestProfileContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void clientRequest(Coach coach, Request request, boolean isAccepted) {
        CoachDAO.handleRequest(coach, request, isAccepted);
    }

    public void getClientInfo(String clientMail){
        UserDAO.getUser(clientMail, this);
    }

    @Override
    public void userLoaded(User user, boolean success) {
        if(success)
            mView.onClientInfoLoaded((Client) user);
        else
            mView.onFailure();
    }

    @Override
    public void hasBeenCreated(User user, boolean success) {
        //NON GESTITO
    }
}
