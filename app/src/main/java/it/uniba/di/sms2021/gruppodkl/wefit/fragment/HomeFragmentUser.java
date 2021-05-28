package it.uniba.di.sms2021.gruppodkl.wefit.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.di.sms2021.gruppodkl.wefit.R;


public class HomeFragmentUser extends Fragment {


    private Toolbar mToolbar;


    public HomeFragmentUser() {
        //empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout =  inflater.inflate(R.layout.user_fragment_home, container, false);

        bind(layout);

        return layout;
    }

    private void bind(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setNavigationIcon(R.drawable.back_24);
    }

}