package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientRequestsViewHolder extends RecyclerView.ViewHolder {

    public interface ViewHolderCallback{

    }

    private ViewHolderCallback mCallback;

    public ClientRequestsViewHolder(@NonNull  View itemView, ViewHolderCallback callback) {
        super(itemView);
        mCallback = callback;
    }
}
