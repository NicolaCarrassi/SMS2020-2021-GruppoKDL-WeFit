package it.uniba.di.sms2021.gruppodkl.wefit.fragment.coach;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.fragment.client.ClientMyProfileFragment;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;


public class CoachHomeFragment extends Fragment implements User.MyImageBitmapCallback{

    public static final String TAG = CoachHomeFragment.class.getSimpleName();

    private WeFitApplication.CallbackOperations mActivity;
    private Coach mCoach;
    private ImageView mImageView;
    private CardView mTrainingCard;
    private CardView mDietCard;
    private CardView mRequestsTab;

    public CoachHomeFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof WeFitApplication.CallbackOperations)
            mActivity = (WeFitApplication.CallbackOperations) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout =  inflater.inflate(R.layout.coach_home_fragment, container, false);

        mCoach = (Coach) ((WeFitApplication) getActivity().getApplicationContext()).getUser();

        bind(layout);
        setListeners();

        return layout;
    }

    /**
     * Il metodo permette di associare gli elementi della view ad oggetti
     *
     * @param layout View in cui sono presenti elementi grafici
     */
    private void bind(View layout){
        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(layout, mActivity);

        mImageView = layout.findViewById(R.id.user_image);
        mDietCard = layout.findViewById(R.id.diet_tab);
        mTrainingCard = layout.findViewById(R.id.training_tab);
        mRequestsTab = layout.findViewById(R.id.requests_tab);

        TextView textView = layout.findViewById(R.id.hi_user);
        textView.setText(getResources().getString(R.string.hi_user_string)+ " "+ mCoach.fullName.split(" ")[0]+ " !");

        if(mCoach.image != null)
            if(!mCoach.isBitmapImageAvailable())
                mCoach.createImageBitmap(this);
            else
                mImageView.setImageBitmap(mCoach.getImageBitmap());
    }

    /**
     * Il metodo permette di impostare i listeners per gli elementi del
     * layout
     *
     */
    private void setListeners(){
        mImageView.setOnClickListener(v -> {
            final CoachProfileFragment coachProfileFragment = new CoachProfileFragment();

            FragmentActivity activity = getActivity();
            if(activity != null)
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, coachProfileFragment).commit();
        });

        //TODO Imposta gli altri listeners delle card
    }

    @Override
    public void handleCallback() {
        mImageView.setImageBitmap(mCoach.getImageBitmap());
    }
}