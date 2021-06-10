package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.WeFitApplication;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientShoppingListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientDietShoppingListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientDietShoppingListPresenter;
import it.uniba.di.sms2021.gruppodkl.wefit.recyclerview.CustomRecyclerView;


public class ClientDietShoppingListFragment extends Fragment implements ClientDietShoppingListContract.View {

    public static final String TAG = ClientDietShoppingListFragment.class.getSimpleName();


    private ClientDietShoppingListContract.Presenter mPresenter;
    private LinearProgressIndicator mLinearProgress;
    private Spinner mNumberOfDaysSpinner;
    private CustomRecyclerView mRecycler;
    private ClientShoppingListAdapter mAdapter;


    public ClientDietShoppingListFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.client_diet_shopping_list_fragment, container, false);


        mPresenter = new ClientDietShoppingListPresenter(this);

        mLinearProgress = layout.findViewById(R.id.shopping_list_loading);
        mNumberOfDaysSpinner = layout.findViewById(R.id.spinner_days);
        mRecycler = layout.findViewById(R.id.recycler_shopping_list);
        mRecycler.setEmptyView(layout.findViewById(R.id.empty));
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.number_of_days, R.layout.spinner_layout);
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNumberOfDaysSpinner.setAdapter(daysAdapter);

        mNumberOfDaysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.setSelection(position);
                if(position > 0)
                    fetchInformations(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        return layout;
    }



    private void fetchInformations(int numberOfDays){
        mNumberOfDaysSpinner.setEnabled(false);
        mLinearProgress.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);

        assert getActivity() != null;
        String clientMail = ((WeFitApplication)getActivity().getApplicationContext()).getUser().email;

        mPresenter.loadShoppingList(clientMail, numberOfDays);
    }

    @Override
    public void onShoppingInformationLoaded(Map<String, Integer> mealsMap) {
        mNumberOfDaysSpinner.setEnabled(true);
        mLinearProgress.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mAdapter = new ClientShoppingListAdapter(mealsMap);
        mRecycler.setAdapter(mAdapter);
    }
}