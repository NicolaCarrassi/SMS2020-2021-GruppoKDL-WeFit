package it.uniba.di.sms2021.gruppodkl.wefit.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class ClientCoachListViewHolder extends RecyclerView.ViewHolder implements User.MyImageBitmapCallback {

    private final ImageView mCoachImage;
    private final TextView mCoachName;
    private MaterialButton mRequestButton;
    private Coach mCoach;
    private final ItemClickListener mItemClickListener;

    public ClientCoachListViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);

        mCoachImage = itemView.findViewById(R.id.pfp_coach);
        mCoachName = itemView.findViewById(R.id.name_item_coach_list);
        mRequestButton = itemView.findViewById(R.id.button_item_coach_list);

        mItemClickListener = itemClickListener;
        mRequestButton.setOnClickListener(v -> mItemClickListener.onItemClick(mCoach));
        mCoachImage.setOnClickListener(v -> mItemClickListener.onProfileOpened(mCoach));
    }

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



    public interface ItemClickListener{
        void onItemClick(Coach coach);
        void onProfileOpened(Coach coach);
    }
}
