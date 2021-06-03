package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.CoachAllFeedbacksAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachAllFeedbacksContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;

public class CoachAllFeedbacksPresenter implements CoachAllFeedbacksContract.Presenter {

    private final CoachAllFeedbacksContract.View mView;

    public CoachAllFeedbacksPresenter(CoachAllFeedbacksContract.View view){
        this.mView = view;
    }


    @Override
    public CoachAllFeedbacksAdapter getAdapter(String coachMail) {

        FirestoreRecyclerOptions<Feedback> options = new FirestoreRecyclerOptions.Builder<Feedback>()
                .setQuery(CoachDAO.queryFeedbackList(coachMail), Feedback.class).build();

        return new CoachAllFeedbacksAdapter(options);
    }
}
