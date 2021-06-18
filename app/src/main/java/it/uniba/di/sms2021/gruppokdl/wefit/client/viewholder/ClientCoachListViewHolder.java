package it.uniba.di.sms2021.gruppokdl.wefit.client.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;

public class ClientCoachListViewHolder extends RecyclerView.ViewHolder implements User.MyImageBitmapCallback {

    private final ImageView mCoachImage;
    private final TextView mCoachName;
    private Coach mCoach;
    private final ItemClickListener mItemClickListener;

    public ClientCoachListViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);

        mCoachImage = itemView.findViewById(R.id.pfp_coach);
        mCoachName = itemView.findViewById(R.id.name_item_coach_list);
        MaterialButton mRequestButton = itemView.findViewById(R.id.button_item_coach_list);

        mItemClickListener = itemClickListener;
        mRequestButton.setOnClickListener(v -> mItemClickListener.onItemClick(mCoach));
        mCoachImage.setOnClickListener(v -> mItemClickListener.onProfileOpened(mCoach));
    }

    /**
     * Il metodo permette di associare i valori del model al viewholder
     * @param coach model
     */
    public void setValues(Coach coach){
        mCoach = coach;
        if(coach.image != null){
            if(!coach.isBitmapImageAvailable())
                coach.createImageBitmap(this);
        }
        mCoachName.setText(coach.fullName);
    }

    @Override
    public void handleCallback() {
        mCoachImage.setImageBitmap(mCoach.getImageBitmap());
    }


    /**
     * Interfaccia che permette di gestire le operazionei di callback
     */
    public interface ItemClickListener{
        /**
         *
         * @param coach
         */
        void onItemClick(Coach coach);

        /**
         * Il metodo permette di gestire l'apertura del profilo di un dato coach
         * @param coach coach di cui si vuole visualizzare il profilo
         */
        void onProfileOpened(Coach coach);
    }
}
