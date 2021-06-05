package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class TrainingViewHolder extends RecyclerView.ViewHolder {

    private TextView mDay;
    private TextView mTitle;
    private TextView mTime;
    private ClientMyTrainingCallbacks mCallbacks;
    private Button mDeleteTrainingButton;
    private CardView mTrainingCard;
    private View mView;

    private Training mTraining;

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
        mTraining = model;

        mDay.setText(getDayOfTheWeek(model.dayOfWeek));
        mTitle.setText(model.title);
        mTime.setText(model.getDurationTime());

        if(isEditable) {
            mDeleteTrainingButton.setVisibility(View.VISIBLE);
            mDeleteTrainingButton.setOnClickListener(v -> mCallbacks.deleteTraining(getAdapterPosition()));
        }
    }


    private String getDayOfTheWeek(int day){
        String dayOfTheWeek;
        switch (day){
            case Keys.WeekDay.SUNDAY:
                dayOfTheWeek =  mView.getResources().getString(R.string.sunday);
                break;
            case Keys.WeekDay.MONDAY:
                dayOfTheWeek =  mView.getResources().getString(R.string.monday);
                break;
            case Keys.WeekDay.TUESDAY:
                dayOfTheWeek =  mView.getResources().getString(R.string.tuesday);
                break;
            case Keys.WeekDay.WEDNESDAY:
                dayOfTheWeek =  mView.getResources().getString(R.string.wednesday);
                break;
            case Keys.WeekDay.THURSDAY:
                dayOfTheWeek =  mView.getResources().getString(R.string.thursday);
                break;
            case Keys.WeekDay.FRIDAY:
                dayOfTheWeek =  mView.getResources().getString(R.string.friday);
                break;
            case Keys.WeekDay.SATURDAY:
                dayOfTheWeek =  mView.getResources().getString(R.string.saturday);
                break;
            default:
                dayOfTheWeek ="";
        }
        return dayOfTheWeek;
    }

    public interface ClientMyTrainingCallbacks{
        void deleteTraining(int position);
        void onElementChecked(int position);
    }



}
