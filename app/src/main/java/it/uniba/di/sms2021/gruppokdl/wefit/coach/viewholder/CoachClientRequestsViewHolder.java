package it.uniba.di.sms2021.gruppokdl.wefit.coach.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;

public class CoachClientRequestsViewHolder extends RecyclerView.ViewHolder implements Request.RequestImageBitmapCallback {

    /**
     * Interfaccia contenente le operazioni di callback per
     * la gestione delle richieste
     */
    public interface ViewHolderCallback{
        /**
         * Il metodo permette di accettare una richiesta data la sua posizione nella RecyclerView
         * @param position posizione della richiesta
         */
        void acceptRequest(int position);

        /**
         * Il metodo permette di rifiutare una richiesta data la sua posizione nella RecyclerView
         * @param position posizione della richiesta
         */
        void declineRequest(int position);

        /**
         * Il metodo permette di visualizzare il profilo di un cliente che ha
         * fatto una richiesta, data la sua posizione nella RecyclerView
         * @param position posizione della richiesta
         */
        void showUserProfile(int position);
    }

    private final ViewHolderCallback mCallback;
    private Request mRequest;

    private final ImageView mProfilePicture;
    private final TextView mClientName;

    public CoachClientRequestsViewHolder(@NonNull  View itemView, ViewHolderCallback callback) {
        super(itemView);
        mCallback = callback;

        mProfilePicture = itemView.findViewById(R.id.client_pfp);
        mClientName = itemView.findViewById(R.id.client_name);
        Button mAcceptButton = itemView.findViewById(R.id.accept_button);
        Button mDeclineButton = itemView.findViewById(R.id.decline_button);

        mAcceptButton.setOnClickListener(v -> mCallback.acceptRequest(getAdapterPosition()));
        mDeclineButton.setOnClickListener(v -> mCallback.declineRequest(getAdapterPosition()));
        mProfilePicture.setOnClickListener(v -> callback.showUserProfile(getAdapterPosition()));
    }


    /**
     * Il metodo permette di associare i valori del model al viewHolder
     * @param request model
     */
    public void setValues(Request request){
        mRequest = request;

        if(mRequest.image != null){
            if(! mRequest.isBitmapImageAvailable())
                mRequest.createImageBitmap(this);
        }

        mClientName.setText(mRequest.fullName);
    }

    @Override
    public void handleCallback() {mProfilePicture.setImageBitmap(mRequest.getImageBitmap());}



}
