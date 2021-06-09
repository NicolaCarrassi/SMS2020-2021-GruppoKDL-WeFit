package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.adapter.DietListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientDietListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientDietListPresenter;


public class ClientDietListFragment extends Fragment implements ClientDietListContract.View {

    public static final String TAG = ClientDietListFragment.class.getSimpleName();


    private ClientDietListContract.Presenter mPresenter;


    public ClientDietListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.client_diet_list_fragment, container, false);

        mPresenter = new ClientDietListPresenter(this);

        //HANDLING RECYCLER
        RecyclerView mRecycler = view.findViewById(R.id.recycler_diet_days);
        assert getActivity() != null;
        mRecycler.setAdapter(new DietListAdapter(getActivity(), mPresenter));
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void showDietOfTheDay(String weekDay) {
        ClientDailyDietFragment fragment = ClientDailyDietFragment.newInstance(weekDay);

        assert getParentFragment() != null;

        getParentFragment().getChildFragmentManager().beginTransaction().replace(R.id.diet_fragment_anchor, fragment, ClientDailyDietFragment.TAG)
                .addToBackStack(ClientDailyDietFragment.TAG).commit();
    }
}