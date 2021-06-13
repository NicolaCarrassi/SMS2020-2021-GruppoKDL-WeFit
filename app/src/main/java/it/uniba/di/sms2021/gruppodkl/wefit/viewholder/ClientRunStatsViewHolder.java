package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;


//TODO SISTEMA LAYOUT E AGGIUNGI LISTENER PER CARD
public class ClientRunStatsViewHolder extends RecyclerView.ViewHolder {

    private TextView mRunDate;
    private TextView mRunDistance;
    private TextView mRunKcal;

    public ClientRunStatsViewHolder(@NonNull View itemView) {
        super(itemView);

        mRunDate = itemView.findViewById(R.id.run_date);
        mRunDistance = itemView.findViewById(R.id.run_distance);
        mRunKcal = itemView.findViewById(R.id.run_kcal);
    }

    public void setValues(Run run){
        mRunDate.setText(run.date);
        mRunDistance.setText(run.convertRunDistance());
        mRunKcal.setText(run.convertKcal());
    }
}
