package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;

public class DietDayViewHolder extends RecyclerView.ViewHolder {

    /**
     * Interfaccia contentente le operazioni di callback
     */
    public interface DietDayCallbacks{
        /**
         * Il metodo permette di visualizzare il giorno di dieta, data la sua posizone
         * @param position posizione
         */
        void goToDietDay(int position);
    }


    private final TextView mDayLabel;
    private final DietDayCallbacks mCallback;

    public DietDayViewHolder(@NonNull View itemView, DietDayCallbacks callback) {
        super(itemView);


        mCallback = callback;
        mDayLabel = itemView.findViewById(R.id.day_of_the_week_label);
        CardView mDietDayCard = itemView.findViewById(R.id.diet_day_card);

        mDietDayCard.setOnClickListener(v -> mCallback.goToDietDay(getAdapterPosition()));
    }

    /**
     * Il metodo permette di associare i valori del model al viewholder
     * @param model Stringa contenente il giorno della settimana
     */
    public void setValues(String model){
        mDayLabel.setText(model);
    }


}
