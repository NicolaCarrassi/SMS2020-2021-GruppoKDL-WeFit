package it.uniba.di.sms2021.gruppodkl.wefit.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;


public class HomeFragmentUser extends Fragment {


    private Toolbar mToolbar;
    private MenuItem mDrawable;
    private OpenDrawer mActivity;
    private Client mUser;
    private ImageView mImageView;
    private CardView mRecapTab;
    private CardView mTrainingTab;
    private CardView mDietTab;


    public HomeFragmentUser() {
        //empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OpenDrawer){
            mActivity = (OpenDrawer) context;
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
        final View layout =  inflater.inflate(R.layout.user_fragment_home, container, false);

        mUser = (Client) ((WeFitApplication) getActivity().getApplicationContext()).getUser();

        bind(layout);
        setListener();

        return layout;
    }

    private void bind(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setNavigationIcon(R.drawable.back_24);
        mDrawable = mToolbar.getMenu().findItem(R.id.action_menu);
        mImageView = view.findViewById(R.id.user_image);
        mRecapTab = view.findViewById(R.id.recap_tab);
        mTrainingTab = view.findViewById(R.id.training_tab);
        mDietTab = view.findViewById(R.id.diet_tab);

        TextView mTextView = view.findViewById(R.id.hi_user);
        mTextView.setText(getResources().getString(R.string.hi_user_string) + " " + mUser.fullName.split(" ")[0] + " !");

    }

    private void setListener(){
        mDrawable.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mActivity.openDrw();
                return false;
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProfileFragment profileFragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,profileFragment).commit();
            }
        });

        mRecapTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressFragment progressFragment = new ProgressFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,progressFragment).commit();
            }
        });

        mTrainingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TrainingFragment trainingFragment = new TrainingFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,trainingFragment).commit();
            }
        });

        mDietTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DietFragment dietFragment = new DietFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.anchor_point,dietFragment).commit();
            }
        });
    }

    public interface OpenDrawer {
        void openDrw();
    }

}