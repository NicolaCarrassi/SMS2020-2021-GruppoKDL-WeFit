package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;

public class ClientMyCoachFragment extends Fragment {

    public static final String TAG = ClientMyCoachFragment.class.getSimpleName();

    private Toolbar mToolbar;
    private MenuItem mDrawable;
    private WeFitApplication.CallbackOperations mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof WeFitApplication.CallbackOperations){
            mActivity = (WeFitApplication.CallbackOperations) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    public ClientMyCoachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View layout =  inflater.inflate(R.layout.client_my_coach_profile_fragment, container, false);

        bind(layout);

        return layout;
    }

    private void bind(View view) {
        ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view, mActivity);
    }


}