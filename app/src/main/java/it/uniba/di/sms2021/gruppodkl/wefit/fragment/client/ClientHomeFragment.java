package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;


public class ClientHomeFragment extends Fragment {

    public static final String TAG = ClientHomeFragment.class.getSimpleName();


    private Toolbar mToolbar;
    private MenuItem mDrawable;
    private WeFitApplication.OpenDrawer mActivity;
    private Client mUser;
    private ImageView mImageView;
    private CardView mRecapTab;
    private CardView mTrainingTab;
    private CardView mDietTab;


    public ClientHomeFragment() {
        //empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WeFitApplication.OpenDrawer){
            mActivity = (WeFitApplication.OpenDrawer) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout =  inflater.inflate(R.layout.client_home_fragment, container, false);

        mUser = (Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();

        bind(layout);
        setListener();

        return layout;
    }

    private void bind(View view) {
        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, mActivity);
        mImageView = view.findViewById(R.id.user_image);
        mRecapTab = view.findViewById(R.id.recap_tab);
        mTrainingTab = view.findViewById(R.id.training_tab);
        mDietTab = view.findViewById(R.id.diet_tab);

        TextView mTextView = view.findViewById(R.id.hi_user);
        mTextView.setText(getResources().getString(R.string.hi_user_string) + " " + mUser.fullName.split(" ")[0] + " !");

        if(mUser.image != null) {
            if (!mUser.isBitmapImageAvailable())
                mUser.createImageBitmap();

            mImageView.setImageBitmap(mUser.getImageBitmap());
        }
    }

    private void setListener(){
        mImageView.setOnClickListener(v -> {
            final ClientMyProfileFragment clientMyProfileFragment = new ClientMyProfileFragment();

            FragmentActivity activity = getActivity();
            if(activity != null)
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientMyProfileFragment).commit();
        });

        mRecapTab.setOnClickListener(v -> {
            final ClientMyProgressFragment clientMyProgressFragment = new ClientMyProgressFragment();

            FragmentActivity activity = getActivity();
            if(activity != null)
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientMyProgressFragment).commit();
        });

        mTrainingTab.setOnClickListener(v -> {
            final ClientMyTrainingFragment clientMyTrainingFragment = new ClientMyTrainingFragment();

            FragmentActivity activity = getActivity();
            if(activity != null)
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientMyTrainingFragment).commit();
        });

        mDietTab.setOnClickListener(v -> {
            final ClientDietFragment clientDietFragment = new ClientDietFragment();

            FragmentActivity activity = getActivity();
            if(activity != null)
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point, clientDietFragment).commit();
        });
    }


}