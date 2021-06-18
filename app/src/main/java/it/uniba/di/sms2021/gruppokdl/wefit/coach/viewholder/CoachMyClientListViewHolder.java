package it.uniba.di.sms2021.gruppokdl.wefit.coach.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;

public class CoachMyClientListViewHolder extends RecyclerView.ViewHolder implements User.MyImageBitmapCallback {

    private final ImageView mClientImage;
    private final TextView mClientName;
    private final ClientListCallbacks mCallback;

    private Client mClient;

    public CoachMyClientListViewHolder(@NonNull View itemView, ClientListCallbacks callback) {
        super(itemView);

        mCallback = callback;
        mClientImage = itemView.findViewById(R.id.client_pfp);
        mClientName = itemView.findViewById(R.id.client_name);
        CardView mCard = itemView.findViewById(R.id.card_client);

        mCard.setOnClickListener(v -> mCallback.openYourClientProfile(getAdapterPosition()));
        mClientImage.setOnClickListener(v -> mCallback.openYourClientProfile(getAdapterPosition()));
    }

    /**
     * Il metodo permette di associare i valori del model al viewholder
     * @param model istanza del cliente
     */
    public void setValues(Client model){
        mClient = model;

        if(model.image != null){
            if(!model.isBitmapImageAvailable())
                model.createImageBitmap(this);
            else
                mClientImage.setImageBitmap(model.getImageBitmap());
        }

        mClientName.setText(model.fullName);
    }


    @Override
    public void handleCallback() {
        mClientImage.setImageBitmap(mClient.getImageBitmap());
    }


    /**
     * Interfaccia conentente le operazioni di callback
     */
    public interface ClientListCallbacks{
        /**
         * Il metodo permette di aprire il profilo del cliente data la sua posizione
         * @param position posizione del cliente nella recyclerView
         */
        void openYourClientProfile(int position);
    }
}
