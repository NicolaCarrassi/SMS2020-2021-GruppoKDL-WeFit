package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Feedback;

public class CoachAllFeedbacksViewHolder extends RecyclerView.ViewHolder{

    private RatingBar mRating;
    private TextView mClientName;
    private TextView mFeedbackText;

    public CoachAllFeedbacksViewHolder(@NonNull View itemView) {
        super(itemView);

        mRating = itemView.findViewById(R.id.feedback_rating);
        mClientName = itemView.findViewById(R.id.client_name);
        mFeedbackText = itemView.findViewById(R.id.feedback_text);
    }


    public void setValues(Feedback feedback){
        mRating.setRating(feedback.rate);
        mClientName.setText(feedback.clientFullName);
        mFeedbackText.setText(feedback.message != null? feedback.message : "");
    }
}