package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;

public class ClientMyTrainingViewHolder extends RecyclerView.ViewHolder {

    private TextView mDay;
    private TextView mTitle;
    private TextView mTime;
    private ClientMyTrainingCallbacks mCallbacks;

    private Training mTraining;

    public ClientMyTrainingViewHolder(View itemView, ClientMyTrainingCallbacks callback) {
        super(itemView);
        mCallbacks = callback;
        mDay = itemView.findViewById(R.id.day_label);
        mTitle = itemView.findViewById(R.id.title_label);
        mTime = itemView.findViewById(R.id.time_label);
        //cardview.setOnClickListener(.gesu(getAdapterPosition)
    }

    //TODO SISTEMARE
    public void setValues(Training model){
        mTraining = model;

        mDay.setText(model.dayOfWeek);
        mTitle.setText(model.title);
        mTime.setText(model.time);

    }

    public interface ClientMyTrainingCallbacks{

    }
}
