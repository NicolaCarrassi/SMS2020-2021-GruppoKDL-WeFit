package it.uniba.di.sms2021.gruppokdl.wefit.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Training;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.DayOfTheWeek;

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


    /**
     * il metodo permette di associare al viewholder gli elementi specifici
     * del model a cui esso è associato
     *
     * @param model model del viewHolder
     * @param isEditable indica la possibilità di effettuare operazioni di modifica
     */
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
        /**
         *
         * Il metodo permette di cancellare l'allenamento in una data posizione
         * @param position posizione dell'allenamento da cancellare
         */
        void deleteTraining(int position);

        /**
         * Il metodo permette di gestire l'evento di touch su un dato elemento
         * @param position posizione dell'elemento
         */
        void onElementChecked(int position);
    }



}
