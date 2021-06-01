package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachFeedbacksContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach.CoachFeedbacksPresenter;


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
    private Button mReadAllButton;
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
       mUser = ((WeFitApplication) getActivity().getApplicationContext()).getUser();
       bind(layout);


        return layout;
    }


    private void bind(View view){
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
        mMeanRating.setRating(mean);
        mFeedbackNumberText.setText(getResources().getString(R.string.feedback_number) + Integer.toString(numElem));
        mLastFeedbackUserName.setText(feedback.clientFullName.split(" ")[0]);
        mLastFeedbackRating.setRating(feedback.rate);
        mLastReviewText.setText(feedback.message);

        if(numElem > 1){
            mReadAllButton.setVisibility(View.VISIBLE);
            mReadAllButton.setClickable(true);
            mReadAllButton.setFocusable(true);

            mReadAllButton.setOnClickListener(v -> {
                  //TODO CREA SCHERMATA CON TUTTI I FEEDBACK
            });
        }
    }

    @Override
    public void onNoFeedbackReceived() {
        mFeedbackNumberText.setText(mFeedbackNumberText.getText() + "0");
        mLastFeedbackLabel.setVisibility(View.GONE);
        mLastFeedbackUserName.setVisibility(View.GONE);
        mLastReviewText.setVisibility(View.GONE);
        mLastFeedbackRating.setVisibility(View.GONE);

        mNoFeedbackTitle.setVisibility(View.VISIBLE);
        mNoFeedbackParagraph.setVisibility(View.VISIBLE);
    }
}