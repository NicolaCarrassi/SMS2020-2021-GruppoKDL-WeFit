package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

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


    public interface ClientListCallbacks{
        void openYourClientProfile(int position);
    }
}
