package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Request;

public class CoachClientRequestsViewHolder extends RecyclerView.ViewHolder implements Request.RequestImageBitmapCallback {


    public interface ViewHolderCallback{
        void acceptRequest(int position);
        void declineRequest(int position);
        void showUserProfile(int position);
    }

    private final ViewHolderCallback mCallback;
    private Request mRequest;

    private final ImageView mProfilePicture;
    private final TextView mClientName;
    private final Button mAcceptButton;
    private final Button mDeclineButton;

    public CoachClientRequestsViewHolder(@NonNull  View itemView, ViewHolderCallback callback) {
        super(itemView);
        mCallback = callback;

        mProfilePicture = itemView.findViewById(R.id.client_pfp);
        mClientName = itemView.findViewById(R.id.client_name);
        mAcceptButton = itemView.findViewById(R.id.accept_button);
        mDeclineButton = itemView.findViewById(R.id.decline_button);

        mAcceptButton.setOnClickListener(v -> mCallback.acceptRequest(getAdapterPosition()));
        mDeclineButton.setOnClickListener(v -> mCallback.declineRequest(getAdapterPosition()));
        mProfilePicture.setOnClickListener(v -> callback.showUserProfile(getAdapterPosition()));
    }


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
