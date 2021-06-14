package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Exercise;

public class TrainingDetailViewHolder extends RecyclerView.ViewHolder {

    /**
     * Interfaccia contenente le operazioni di callback per il cliente
     */
    public interface TrainingDetailClient{
        /**
         * Il metodo permette di visualizzare le informazioni di un dato esercizio
         * @param position posizione dell'esercizio
         */
        void showExerciseInfo(int position);
    }

    /**
     * Interfaccia contenente le operazioni di callback per il coach
     */
    public interface TrainingDetailCoach{
        /**
         * Il metodo permette di cancellare un esercizio
         * @param position posizione dell'esercizio
         */
        void deleteExercise(int position);
    }


    private TrainingDetailClient mClientCallback;
    private TrainingDetailCoach mCoachCallback;

    private final TextView mRepetitionNumber;
    private final TextView mExerciseName;

    /**
     * Costruttore del viewholder per il coach
     *
     * @param itemView view
     * @param coachCallback interfaccia di callback per le operazioni del coach
     */
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


    /**
     * Costruttore del viewholder per il cliente
     *
     * @param itemView view
     * @param clientCallback operazioni di callback per il cliente
     */
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


    /**
     * Il metodo permette di impostare i valori del viewHolder
     * @param exercise classe model
     */
    public void setValues(Exercise exercise) {
        mRepetitionNumber.setText(exercise.convertRepsNumberToString());

        mExerciseName.setText(exercise.name);
    }






}
