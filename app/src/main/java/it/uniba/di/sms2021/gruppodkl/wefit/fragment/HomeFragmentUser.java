package it.uniba.di.sms2021.gruppodkl.wefit.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.di.sms2021.gruppodkl.wefit.R;


public class HomeFragmentUser extends Fragment {


    private Toolbar mToolbar;
    private MenuItem mDrawable;
    private OpenDrawer mActivity;


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

        bind(layout);
        setListener();

        return layout;
    }

    private void bind(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setNavigationIcon(R.drawable.back_24);
        mDrawable = mToolbar.getMenu().findItem(R.id.action_menu);
    }

    private void setListener(){
        mDrawable.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mActivity.openDrw();
                return false;
            }
        });
    }

    public interface OpenDrawer {
        void openDrw();
    }

}