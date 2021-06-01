package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;

public interface CoachFeedbacksContract {

    interface View{
        void onLastFeedbackReceived(Feedback feedback, float mean, int numElem);
        void onNoFeedbackReceived();
    }

    interface Presenter{
        void fetchLastFeedback(Coach coach);
    }
}
