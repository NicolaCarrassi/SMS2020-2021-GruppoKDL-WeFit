package it.uniba.di.sms2021.gruppodkl.wefit.contract.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachAllFeedbacksAdapter;

public interface CoachAllFeedbacksContract {

    interface View{

    }

    interface Presenter{
        CoachAllFeedbacksAdapter getAdapter(String coachMail);
    }

}
