package it.uniba.di.sms2021.gruppokdl.wefit.presenter.coach;

import it.uniba.di.sms2021.gruppokdl.wefit.contract.coach.CoachHomeContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;

public class CoachHomePresenter implements CoachHomeContract.Presenter, CoachDAO.RequestCallbacks {
    private final CoachHomeContract.View mView;

    public CoachHomePresenter(CoachHomeContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void loadNumberRequest(Coach coach) {
        CoachDAO.getRequestNumber(coach.email, this);
    }

    @Override
    public void requestNumberLoaded(int numRequest) {
        mView.onRequestNumberLoaded(numRequest);
    }
}
