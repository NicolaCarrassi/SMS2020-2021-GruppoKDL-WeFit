package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;

public class TrainingDetailViewHolder extends RecyclerView.ViewHolder {

    public interface TrainingDetailClient{
        void showExerciseInfo(int position);
    }

    public interface TrainingDetailCoach{
        void deleteExercise(int position);
    }


    private TrainingDetailClient mClientCallback;
    private TrainingDetailCoach mCoachCallback;

    private final TextView mRepetitionNumber;
    private final TextView mExerciseName;

    public TrainingDetailViewHolder(View itemView, TrainingDetailCoach coachCallback) {
        super(itemView);

        //BINDING ELEMENTI DELLA VIEW
        mRepetitionNumber = itemView.findViewById(R.id.exercise_repetition_number);
        mExerciseName = itemView.findViewById(R.id.exercise_name);
        ImageButton mDeleteExerciseButton = itemView.findViewById(R.id.delete_exercise_button);

        mCoachCallback = coachCallback;

        mDeleteExerciseButton.setVisibility(View.VISIBLE);
        mDeleteExerciseButton.setClickable(true);
        mDeleteExerciseButton.setOnClickListener(v -> mCoachCallback.deleteExercise(getAdapterPosition()));
    }


    public TrainingDetailViewHolder(View itemView, TrainingDetailClient clientCallback) {
        super(itemView);

        //BINDING ELEMENTI DELLA VIEW
        mRepetitionNumber = itemView.findViewById(R.id.exercise_repetition_number);
        mExerciseName = itemView.findViewById(R.id.exercise_name);
        ImageButton mOpenSpecificationButton = itemView.findViewById(R.id.info_exercise_button);

        mClientCallback = clientCallback;

        mOpenSpecificationButton.setVisibility(View.VISIBLE);

        mOpenSpecificationButton.setOnClickListener(v -> mClientCallback.showExerciseInfo(getAdapterPosition()));
    }


    public void setValues(Exercise exercise) {
        mRepetitionNumber.setText(exercise.convertRepsNumberToString());

        mExerciseName.setText(exercise.name);
    }






}
