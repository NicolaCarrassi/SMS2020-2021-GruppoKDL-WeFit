package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;

public class DietDayViewHolder extends RecyclerView.ViewHolder {

    public interface DietDayCallbacks{
        void goToDietDay(int position);
    }


    private TextView mDayLabel;
    private CardView mDietDayCard;
    private DietDayCallbacks mCallback;

    public DietDayViewHolder(@NonNull View itemView, DietDayCallbacks callback) {
        super(itemView);


        mCallback = callback;
        mDayLabel = itemView.findViewById(R.id.day_of_the_week_label);
        mDietDayCard = itemView.findViewById(R.id.diet_day_card);

        mDietDayCard.setOnClickListener(v -> mCallback.goToDietDay(getAdapterPosition()));
    }

    public void setValues(String model){
        mDayLabel.setText(model);
    }


}
