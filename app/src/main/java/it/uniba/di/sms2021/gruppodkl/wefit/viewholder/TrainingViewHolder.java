package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.DayOfTheWeek;

public class TrainingViewHolder extends RecyclerView.ViewHolder {

    private final TextView mDay;
    private final TextView mTitle;
    private final TextView mTime;
    private final ClientMyTrainingCallbacks mCallbacks;
    private final Button mDeleteTrainingButton;
    private final CardView mTrainingCard;
    private final View mView;


    public TrainingViewHolder(View itemView, ClientMyTrainingCallbacks callback) {
        super(itemView);
        mCallbacks = callback;
        mDay = itemView.findViewById(R.id.day_label);
        mTitle = itemView.findViewById(R.id.title_label);
        mTime = itemView.findViewById(R.id.time_label);
        mDeleteTrainingButton = itemView.findViewById(R.id.btn_delete_training);
        mTrainingCard = itemView.findViewById(R.id.training_card);
        mView = itemView;

        mTrainingCard.setClickable(true);
        mTrainingCard.setOnClickListener(v -> mCallbacks.onElementChecked(getAdapterPosition()));
    }


    public void setValues(Training model, boolean isEditable){

        mDay.setText(DayOfTheWeek.getDayOfTheWeek(model.dayOfWeek, mView));
        mTitle.setText(model.title);
        mTime.setText(model.convertDurationTime());

        if(isEditable) {
            mDeleteTrainingButton.setVisibility(View.VISIBLE);
            mDeleteTrainingButton.setOnClickListener(v -> mCallbacks.deleteTraining(getAdapterPosition()));
        }
    }




    public interface ClientMyTrainingCallbacks{
        void deleteTraining(int position);
        void onElementChecked(int position);
    }



}
