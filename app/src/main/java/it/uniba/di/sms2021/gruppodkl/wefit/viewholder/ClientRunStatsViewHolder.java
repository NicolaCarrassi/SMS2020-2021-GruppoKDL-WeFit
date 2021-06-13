package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Run;


public class ClientRunStatsViewHolder extends RecyclerView.ViewHolder {

    public interface ClientRunCallback{
        void openRunDetail(int position);
    }

    private final TextView mRunDate;
    private final TextView mRunDistance;
    private final TextView mRunKcal;
    private final ClientRunCallback mCallback;

    public ClientRunStatsViewHolder(@NonNull View itemView, @NonNull ClientRunCallback callback) {
        super(itemView);

        mCallback = callback;
        mRunDate = itemView.findViewById(R.id.run_date);
        mRunDistance = itemView.findViewById(R.id.run_distance);
        mRunKcal = itemView.findViewById(R.id.run_kcal);
        CardView mCardView = itemView.findViewById(R.id.run_card);
        mCardView.setOnClickListener(v -> mCallback.openRunDetail(getAdapterPosition()));
    }

    public void setValues(Run run){
        mRunDate.setText(run.date);
        mRunDistance.setText(run.convertRunDistance());
        mRunKcal.setText(run.convertKcal());
    }
}
