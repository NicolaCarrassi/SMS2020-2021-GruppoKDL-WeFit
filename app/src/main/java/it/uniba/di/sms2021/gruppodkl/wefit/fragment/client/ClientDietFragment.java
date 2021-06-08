package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;


public class ClientDietFragment extends Fragment {

    public static final String TAG = ClientDietFragment.class.getSimpleName();

    public ClientDietFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.client_diet_fragment, container, false);

        if(getActivity() instanceof WeFitApplication.CallbackOperations){
            WeFitApplication.CallbackOperations act = (WeFitApplication.CallbackOperations) getActivity();
            ((WeFitApplication) getActivity().getApplicationContext()).setToolbar(view,act);
        }

        if(savedInstanceState == null){
            Fragment fragment = new ClientDietDiaryFragment();
            getChildFragmentManager().beginTransaction().add(R.id.diet_fragment_anchor, fragment, ClientDietDiaryFragment.TAG)
                    .addToBackStack(ClientDietDiaryFragment.TAG).commit();
        }


        TabLayout mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                handleTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                handleTabSelected(tab);
            }
        });
        return view;
    }


    private void handleTabSelected(TabLayout.Tab tab){
        Fragment fragment;
        String text;
        String tabText = (String)tab.getText();

        assert tabText != null;

        if(tabText.equals(getResources().getString(R.string.food_diary))){
            fragment = new ClientDietDiaryFragment();
            text = ClientDietDiaryFragment.TAG;
        } else if(tabText.equals(getResources().getString(R.string.diet_caps))){
            fragment = new ClientDietListFragment();
            text = ClientDietListFragment.TAG;
        } else{
            fragment = new ClientDietShoppingListFragment();
            text = ClientDietShoppingListFragment.TAG;
        }

        getChildFragmentManager().beginTransaction().replace(R.id.diet_fragment_anchor, fragment, text)
                .addToBackStack(text).commit();
    }

}