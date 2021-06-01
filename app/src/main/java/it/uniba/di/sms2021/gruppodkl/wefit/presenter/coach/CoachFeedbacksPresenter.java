package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachFeedbacksContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;

public class CoachFeedbacksPresenter implements CoachFeedbacksContract.Presenter, CoachDAO.RatingCallbacks {

    private final CoachFeedbacksContract.View mView;

    public CoachFeedbacksPresenter(CoachFeedbacksContract.View mView) {
        this.mView = mView;
    }

    public void fetchLastFeedback(Coach coach){
        CoachDAO.getLastFeedback(coach.email, this);
    }

    @Override
    public void ratingMeanLoaded(float ratingMean) {
        //NON GESTITO
    }

    @Override
    public void feedbacksLoaded(List<Feedback> feedbackList) {
        //NON GESTITO
    }

    public void lastFeedbackLoaded(Feedback feedback, float mean, int numElem){
        if(numElem > 0)
            mView.onLastFeedbackReceived(feedback, mean, numElem);
        else
            mView.onNoFeedbackReceived();
    }


}
