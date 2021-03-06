package it.uniba.di.sms2021.gruppokdl.wefit.coach.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.contract.CoachFeedbacksContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;
import it.uniba.di.sms2021.gruppokdl.wefit.coach.presenter.CoachFeedbacksPresenter;


public class CoachFeedbacksFragment extends Fragment implements User.MyImageBitmapCallback, CoachFeedbacksContract.View {

    public static final String TAG = CoachFeedbacksFragment.class.getSimpleName();

    private WeFitApplication.CallbackOperations mActivity;
    private User mUser;
    private CoachFeedbacksContract.Presenter mPresenter;

    private ImageView mProfilePicture;
    private RatingBar mMeanRating;
    private TextView mFeedbackNumberText;
    private TextView mLastFeedbackUserName;
    private RatingBar mLastFeedbackRating;
    private TextView mLastReviewText;
    private TextView mLastFeedbackLabel;
    private MaterialButton mReadAllButton;
    private TextView mNoFeedbackTitle;
    private TextView mNoFeedbackParagraph;



    public CoachFeedbacksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof WeFitApplication.CallbackOperations)
            mActivity = (WeFitApplication.CallbackOperations) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View layout = inflater.inflate(R.layout.coach_feedbacks_fragment, container, false);

       mPresenter = new CoachFeedbacksPresenter(this);

       assert getActivity() != null;
       mUser = ((WeFitApplication) getActivity().getApplicationContext()).getUser();
       bind(layout);


        return layout;
    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     */
    private void bind(View view){
        assert getActivity() != null;
        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, mActivity);

        mProfilePicture = view.findViewById(R.id.profile_image);

        if(mUser.image != null){
            if(!mUser.isBitmapImageAvailable())
                mUser.createImageBitmap(this);
            else
                mProfilePicture.setImageBitmap(mUser.getImageBitmap());
        }

        mMeanRating = view.findViewById(R.id.rating_coach);
        mFeedbackNumberText = view.findViewById(R.id.feedback_number_text);
        mLastFeedbackUserName = view.findViewById(R.id.last_feedback_user_name);
        mLastFeedbackRating = view.findViewById(R.id.last_rating_received);
        mLastReviewText = view.findViewById(R.id.review_text);
        mReadAllButton = view.findViewById(R.id.btn_read_all);
        mLastFeedbackLabel = view.findViewById(R.id.last_feedback_label);
        mNoFeedbackTitle = view.findViewById(R.id.no_feedback_title);
        mNoFeedbackParagraph = view.findViewById(R.id.no_feedback_label);

        mReadAllButton.setVisibility(View.GONE);
        mReadAllButton.setClickable(false);
        mReadAllButton.setFocusable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.fetchLastFeedback((Coach)mUser);
    }

    @Override
    public void handleCallback() {
        mProfilePicture.setImageBitmap(mUser.getImageBitmap());
    }

    @Override
    public void onLastFeedbackReceived(Feedback feedback, float mean, int numElem) {
        if(getActivity() != null) {
            String feedbackNumber = getResources().getString(R.string.feedback_number) + numElem;
            String firstName = feedback.clientFullName.split(" ")[0];

            mMeanRating.setRating(mean);
            mFeedbackNumberText.setText(feedbackNumber);
            mLastFeedbackUserName.setText(firstName);
            mLastFeedbackRating.setRating(feedback.rate);
            mLastReviewText.setText(feedback.message);

            if (numElem > 1) {
                mReadAllButton.setVisibility(View.VISIBLE);
                mReadAllButton.setClickable(true);
                mReadAllButton.setFocusable(true);

                mReadAllButton.setOnClickListener(v -> openAllFeedbackPage());
            }
        }
    }

    /**
     * Il metodo permette di visualizzare la pagina in cui non vi sono esercizi
     */
    private void openAllFeedbackPage() {
        CoachAllFeedbackFragment fragment = new CoachAllFeedbackFragment();

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, fragment, CoachAllFeedbackFragment.TAG)
                .addToBackStack(CoachAllFeedbackFragment.TAG).commit();
    }

    @Override
    public void onNoFeedbackReceived() {
        String temp = mFeedbackNumberText.getText() + "0";

        mFeedbackNumberText.setText(temp);
        mLastFeedbackLabel.setVisibility(View.GONE);
        mLastFeedbackUserName.setVisibility(View.GONE);
        mLastReviewText.setVisibility(View.GONE);
        mLastFeedbackRating.setVisibility(View.GONE);

        mNoFeedbackTitle.setVisibility(View.VISIBLE);
        mNoFeedbackParagraph.setVisibility(View.VISIBLE);
    }
}